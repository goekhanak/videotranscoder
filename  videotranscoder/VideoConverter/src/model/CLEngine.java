/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


// TODO: Auto-generated Javadoc
/**
 * The Class CLEngine.
 */
public class CLEngine {

	/** The Constant s160_128. */	
	public static final String s160_128="160x128";
	
	/** The Constant NUMBER_OF_KEY_FRAMES. */
	public static final int NUMBER_OF_KEY_FRAMES = 10;
	
	/** The Constant DELAY_TICK. */
	public static final int DELAY_TICK = 100;
	
	
	/** The Constant LOCAL_DIRECTORY. */
	public static final String LOCAL_DIRECTORY = "/home/goekhan/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/VideoConverter/";
	
	/** The Constant AWS_DIRECTORY. */
	public static final String AWS_DIRECTORY = "/home/ubuntu/tomcat/apache-tomcat-6.0.26/webapps/VideoConverter/";
	
	
	/**
	 * Shell runner.
	 *
	 * @param command the command
	 * @return true, if successful
	 */
	public static boolean shellRunner(String command){
		String s = "";
		
		System.out.println("Commad to run: "+ command );
		
		try {
			
	        Process p = Runtime.getRuntime().exec(command);
	        
	        BufferedReader stdInput = new BufferedReader(new 
	             InputStreamReader(p.getInputStream()));

	        BufferedReader stdError = new BufferedReader(new 
	             InputStreamReader(p.getErrorStream()));

	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }
	        
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	       
		    }
	        catch (IOException e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
	            return false;
	        }
	        
	        return true;
	}
	
	
	/**
	 * Sets the number of instances.
	 *
	 * @param i the new number of instances
	 */
	public void setNumberOfInstances(int i){
		
		
	}
	
	/**
	 * Generate key frames.
	 *
	 * @param inputFile the input file
	 */
	public void generateKeyFrames(String inputFile){
		
		
		inputFile = "media/"+inputFile;
		
	    String genFrameCommand = ""; 
	    int offset = 0 ;
	    int videoDuration = 100;
	    Random randomGenerator = new Random();
		
		for (int i = 0; i < CLEngine.NUMBER_OF_KEY_FRAMES; i++) {
			offset  = randomGenerator.nextInt(videoDuration);

			genFrameCommand  = "ffmpeg  -itsoffset -"+offset +
			" -i "+inputFile +
			" -y -vcodec mjpeg -vframes 1 -an -f rawvideo -s 320x240 "
			+inputFile.substring(0,inputFile.lastIndexOf("."))+i+".jpg";
			shellRunner(genFrameCommand);
		}
			
		
		
	}
	
	
	/**
	 * Creates the animated gif.
	 *
	 * @param inputFile the input file
	 */
	public void createAnimatedGif(String inputFile){
		
		inputFile = "media/"+inputFile;
		
		String animatedCommand = "convert -delay " +CLEngine.DELAY_TICK +" ";
		String outputFile = inputFile.substring(0,inputFile.lastIndexOf("."))+".gif"; 
		
		
		
		for (int i = 0; i < CLEngine.NUMBER_OF_KEY_FRAMES; i++) {
			animatedCommand = animatedCommand + " " + 
			inputFile.substring(0,inputFile.lastIndexOf("."))+i+".jpg"; 
		}
		
		animatedCommand = animatedCommand + " " + outputFile;
		
		shellRunner(animatedCommand);
		
		String copyDestionation = outputFile;
		
		if(copyDestionation.indexOf("/") >= 0){
			copyDestionation = copyDestionation.substring(copyDestionation.lastIndexOf("/")+1);
		}
		
		shellRunner("cp -f "+outputFile +" "
				+LOCAL_DIRECTORY+copyDestionation);
		shellRunner("cp -f "+outputFile +" "
				+AWS_DIRECTORY+copyDestionation);
		
	}
	
	
	
/**
 * Convert streaming video.
 *
 * @param sourceVideo the source video
 * @param targetVideo the target video
 * @return the string
 */
public String convertStreamingVideo(String sourceVideo, String targetVideo){
		
		// put size here like " -s 320x240 "
		String size = " ";
		String otherParameters="";
		String encodeCommad = "";
		
		//one pass 
		otherParameters  = "-acodec libfaac -ab 128k -ac 2 -vcodec libx264 -vpre medium -crf 24 -threads 0"; 
		
		encodeCommad = "ffmpeg -y -i "+ sourceVideo +size
			+ otherParameters+" " +targetVideo; 
		
	    if (!shellRunner(encodeCommad)){
	    	return null;
	    }
	    
	    return targetVideo;	
	}

	
	
	
	/**
	 * Convert mobile video.
	 *
	 * @param sourceVideo the source video
	 * @param targetVideo the target video
	 * @return the string
	 */
	public String convertMobileVideo(String sourceVideo, String targetVideo){
		
		// put size here like " -s 320x240 "
		String size = " -s 320x240 ";
		String otherParameters="";
		String encodeCommad = "";
		
		
		
		//one pass 
		otherParameters  = " -acodec libfaac -ab 128k -ac 2 -vcodec libx264 -vpre faster -crf 20 -threads 0"; 
		
		
		//two pass
		
		/*
		otherParameters = "-pass 1 -vcodec libx264 -vpre fast_firstpass -b 512k " +
				"-bt 512k -threads 0 -f mp4 -an  -r 25 -y /dev/null " +
				"&& ffmpeg -i "+sourceVideo  +" -pass 2 -acodec libfaac -ab 128k " +
				"-ac 2 -vcodec libx264 -vpre fast -b 512k -bt 512k -threads 0 ";
		*/
		
		/*
		otherParameters = " -acodec libfaac -ar 44100 -ab 96k -coder ac " +
				" -vcodec libx264 " +
				" -b 1600k "  +
				"-i_qfactor 0.71 -keyint_min 25 -b_strategy 1 -g 250 -r 20 ";
		*/
		
		//otherParameters =  " -y -r 25 -f mpeg -vcodec mpeg2video -ar 48000 -b 500k -ab 128k ";
		
		
		
		//otherParameters =  " -y -r 25 -f mp4 -vcodec mpeg4 -acodec libfaac -ar 48000 -b 500k -ab 128k ";
	
		
		/*
		otherParameters = " -y  -r 25 -an -v 1 -threads auto -vcodec libx264 -b 500k -bt 175k " +
				"-refs 1 -loop 1 -deblockalpha 0 -deblockbeta 0 -parti4x4 1 -partp8x8 1 " +
				"-me full -subq 1 -me_range 21 -chroma 1 -slice 2 -bf 0 -level 30 -g 300 " +
				"-keyint_min 30 -sc_threshold 40 -rc_eq \'blurCplx^(1-qComp)\'"+ 
		        "-qcomp 0.7 -qmax 51 -qdiff 4 -i_qfactor 0.71428572 -maxrate 768k -bufsize 2M -cmp 1 ";
		
		*/
		
		encodeCommad = "ffmpeg -y -i "+ sourceVideo +size
			+ otherParameters+" " +targetVideo; 
		
	    if (!shellRunner(encodeCommad)){
	    	return null;
	    }
	    
	    
	    return targetVideo;	
	}
}

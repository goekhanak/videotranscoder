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
		
		
		inputFile = Video.ABSOLUTE_PATH+inputFile;
		
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
		
		inputFile = Video.ABSOLUTE_PATH+inputFile;
		
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
		otherParameters  = "-acodec libfaac -ab 128k -ac 2 -vcodec libx264 -vpre medium -crf 18 -threads 0"; 
		
		encodeCommad = "/usr/local/bin/ffmpeg -y -i "+ sourceVideo +size
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
		otherParameters  = " -acodec libfaac -ab 128k -ac 2 -vcodec libx264 -vpre faster -crf 24 -threads 0"; 
		
		
		
		encodeCommad = "/usr/local/bin/ffmpeg -y -i "+ sourceVideo +size
			+ otherParameters+" " +targetVideo; 
		
	    if (!shellRunner(encodeCommad)){
	    	return null;
	    }
	    
	    
	    return targetVideo;	
	}
}

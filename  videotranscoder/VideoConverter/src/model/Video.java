/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import aws.S3Connector;

// TODO: Auto-generated Javadoc
/**
 * The Class Video.
 */
public class Video {
	
	/** The Constant VIDEOS_BUCKET. */
	public static final String VIDEOS_BUCKET = "videos-s3-bucket";

	/** The Constant VIDEO_COUNTER. */
	public static final String VIDEO_COUNTER = "videoCounter";
	
	/** The Constant ABSOLUTE_PATH. */
	public static final String ABSOLUTE_PATH = "/home/ubuntu/tomcat/apache-tomcat-6.0.26/media/";
	
	/** The Constant INITIAL. */
	public static final int INITIAL = 0 ;
	
	/** The Constant PROCESSING. */
	public static final int  PROCESSING = 1; 
	
	/** The Constant TRANSCODED. */
	public static final int  TRANSCODED = 2;
	
	/** The orginal file. */
	private String orginalFile = null;
	
	/** The gif file. */
	private String gifFile = null;
	
	/** The mobile video. */
	private String mobileVideo = null;
	
	/** The streaming video. */
	private String streamingVideo = null;	

	/** The state. */
	private int state =  INITIAL;
	
	/**
	 * Gets the s3 url.
	 *
	 * @param fileName the file name
	 * @return the s3 url
	 */
	public static String getS3Url(String fileName){
		return "http://"+VIDEOS_BUCKET+".s3.amazonaws.com/" + fileName;
	}

	/**
	 * Gets the gif file.
	 *
	 * @return the gif file
	 */
	public String getGifFile() {
		return gifFile;
		
	}


	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Sets the gif file.
	 *
	 * @param gifFile the new gif file
	 */
	public void setGifFile(String gifFile) {
		this.gifFile = gifFile;
	}


	/**
	 * Gets the mobile video.
	 *
	 * @return the mobile video
	 */
	public String getMobileVideo() {
		return mobileVideo;
	}


	/**
	 * Sets the mobile video.
	 *
	 * @param mobileVideo the new mobile video
	 */
	public void setMobileVideo(String mobileVideo) {
		this.mobileVideo = getFileName(mobileVideo);
	}

	
	/**
	 * Gets the file name.
	 *
	 * @param fileWithPath the file with path
	 * @return the file name
	 */
	public String getFileName(String fileWithPath){
		if(fileWithPath.lastIndexOf('/') > -1 ){
			fileWithPath = fileWithPath.substring(fileWithPath.lastIndexOf("/")+1);
		}	
		return 	fileWithPath;
	}
	

	/**
	 * Gets the streaming video.
	 *
	 * @return the streaming video
	 */
	public String getStreamingVideo() {
		return streamingVideo;
	}


	/**
	 * Sets the streaming video.
	 *
	 * @param streamingVideo the new streaming video
	 */
	public void setStreamingVideo(String streamingVideo) {
		this.streamingVideo = getFileName(streamingVideo);
	}


	/**
	 * Gets the orginal file.
	 *
	 * @return the orginal file
	 */
	public String getOrginalFile() {
		return orginalFile;
	}


	/**
	 * Sets the orginal file.
	 *
	 * @param orginalFile the new orginal file
	 */
	public void setOrginalFile(String orginalFile) {
		this.orginalFile = orginalFile;
	}


	/**
	 * Instantiates a new video.
	 */
	public Video() {
		// TODO Auto-generated constructor stub
			
		
	}


	/**
	 * Generate gif.
	 */
	public void generateGif() {
		// TODO Auto-generated method stub
		CLEngine e = new CLEngine();
		e.generateKeyFrames(orginalFile);
		e.createAnimatedGif(orginalFile);
		
		setGifFile(orginalFile.substring(0,orginalFile.lastIndexOf(".")) +".gif");		
	}
	
	/**
	 * Convert video.
	 */
	public void transcodeVideo(){
		CLEngine e = new CLEngine();
		
		InputStream inputStream = S3Connector.getS3Connector().downloadFile(VIDEOS_BUCKET,getOrginalFile());
		
		File f;
		
		try {
			f= new File(ABSOLUTE_PATH+getOrginalFile());
			f.createNewFile();
			

			OutputStream out;
			out = new FileOutputStream(f);
			
			byte buf[]=new byte[1024];
			int len;
			while((len=inputStream.read(buf))>0){
				out.write(buf,0,len);
			}
				
			out.close();
			inputStream.close();
			
			String sourceVideo = ABSOLUTE_PATH+getOrginalFile();
			
			String mobileTarget = sourceVideo.substring(0,sourceVideo.lastIndexOf("."))+"-mobile.mp4";
			String streamingTarget = sourceVideo.substring(0,sourceVideo.lastIndexOf("."))+"-streaming.mp4";
			
			setMobileVideo(e.convertMobileVideo(sourceVideo, mobileTarget));
			setStreamingVideo(e.convertStreamingVideo(sourceVideo, streamingTarget));
			
			File mobileVideoFile = new File(ABSOLUTE_PATH+getMobileVideo()); 
			File StreamingVideoFile = new File(ABSOLUTE_PATH+getStreamingVideo());
			
			S3Connector.getS3Connector().uploadFile(VIDEOS_BUCKET,getMobileVideo(), 
					mobileVideoFile);
			S3Connector.getS3Connector().uploadFile(VIDEOS_BUCKET,getStreamingVideo(),		
					StreamingVideoFile);
			
			mobileVideoFile.delete();
			StreamingVideoFile.delete();
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}

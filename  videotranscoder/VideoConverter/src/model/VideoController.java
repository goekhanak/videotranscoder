/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package model;

import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoController.
 */
public class VideoController {

	/** The videos. */
	private Vector <Video> videos = null;
	
	/**
	 * Instantiates a new video controller.
	 */
	public VideoController(){
		videos = new Vector<Video>();
	}
	
	/**
	 * Checks if is all videos transcoded.
	 *
	 * @return true, if is all videos transcoded
	 */
	public boolean isAllVideosTranscoded(){
		
		for(Video v:videos){
			if(v.getState() != Video.TRANSCODED ){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Exists initial video.
	 *
	 * @return true, if successful
	 */
	public boolean existsInitialVideo(){
		
		for(Video v:videos){
			if(v.getState() == Video.INITIAL ){
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Gets the first initial video name.
	 *
	 * @return the first initial video name
	 */
	public String getFirstInitialVideoName(){
		for(Video v:videos){
			if(v.getState() == Video.INITIAL ){
				return v.getOrginalFile();
			}
		}
		
		return null;
	}
	
	/**
	 * Adds the video.
	 *
	 * @param video the video
	 */
	public void addVideo(Video video){
		videos.add(video);
	}
	
	
	/**
	 * Video started transcoding.
	 *
	 * @param videoName the video name
	 */
	public synchronized void VideoStartedTranscoding(String videoName){
		  for (Video video: videos) {
				if(video.getOrginalFile().equals(videoName)){
					video.setState(Video.PROCESSING);
				}
			}
    } 
	
	/**
	 * Video trancoded.
	 *
	 * @param videoName the video name
	 */
	public synchronized void VideoTrancoded(String videoName){
		  for (Video video: videos) {
				if(video.getOrginalFile().equals(videoName)){
					video.setState(Video.TRANSCODED);
				}
			}
     } 
	
	/**
	 * Video trancode failed.
	 *
	 * @param videoName the video name
	 */
	public synchronized void VideoTrancodeFailed(String videoName){
		  for (Video video: videos) {
				if(video.getOrginalFile().equals(videoName)){
					video.setState(Video.INITIAL);
				}
			}
    } 
	
}

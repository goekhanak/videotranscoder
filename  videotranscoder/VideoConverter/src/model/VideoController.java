package model;

import java.util.Vector;

public class VideoController {

	private Vector <Video> videos = null;
	
	public VideoController(){
		videos = new Vector<Video>();
	}
	
	public boolean isAllVideosTranscoded(){
		
		for(Video v:videos){
			if(v.getState() != Video.TRANSCODED ){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean existsInitialVideo(){
		
		for(Video v:videos){
			if(v.getState() == Video.INITIAL ){
				return true;
			}
		}
		
		return false;
	}
	
	
	public String getFirstInitialVideoName(){
		for(Video v:videos){
			if(v.getState() == Video.INITIAL ){
				return v.getOrginalFile();
			}
		}
		
		return null;
	}
	
	public void addVideo(Video video){
		videos.add(video);
	}
	
	
	public synchronized void VideoStartedTranscoding(String videoName){
		  for (Video video: videos) {
				if(video.getOrginalFile().equals(videoName)){
					video.setState(Video.PROCESSING);
				}
			}
    } 
	
	public synchronized void VideoTrancoded(String videoName){
		  for (Video video: videos) {
				if(video.getOrginalFile().equals(videoName)){
					video.setState(Video.TRANSCODED);
				}
			}
     } 
	
	public synchronized void VideoTrancodeFailed(String videoName){
		  for (Video video: videos) {
				if(video.getOrginalFile().equals(videoName)){
					video.setState(Video.INITIAL);
				}
			}
    } 
	
}

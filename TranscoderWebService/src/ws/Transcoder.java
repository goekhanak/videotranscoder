package ws;

import model.Video;

public class Transcoder {

	public boolean transcodeVideo(String videoName){
		Video v = new Video();
		v.setOrginalFile(videoName);
		
		System.out.println("video:  " + videoName +" is transmitted");
		
		v.transcodeVideo();
		return true;
	}
	
}

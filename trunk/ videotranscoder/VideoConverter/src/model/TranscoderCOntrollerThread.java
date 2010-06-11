package model;

import java.util.Vector;

import javax.servlet.http.HttpSession;

import ws.TranscoderClient;
import aws.AutoScaler;
import aws.EC2Connector;

import com.amazonaws.services.ec2.model.Instance;

public class TranscoderCOntrollerThread extends Thread{

	
	private HttpSession session;
	
	public static final String ABSOLUTE_PATH = "/home/ubuntu/tomcat/apache-tomcat-6.0.26/monitoring/";
	
	public  TranscoderCOntrollerThread(HttpSession session){
		super();
		this.session = session;
	}

	
	public void run (){
		
		Integer  vc = (Integer)session.getAttribute(Video.VIDEO_COUNTER) ; 
		
		
		session.setAttribute(Video.IS_READY_SESSION_KEY,new Integer(0));
		
		AutoScaler.setNumberOfInstances(vc);
		
		VideoController videoController =  new VideoController();
		Video v;
		
		
		EC2Connector ec2Connector = EC2Connector.getEC2Connector();
		
		for (int i = 1 ;i <= vc; i++ ) {
			
			v = (Video)session.getAttribute("v"+i);
			if (v != null){
				videoController.addVideo(v);
			}
			
		}
		
		TranscoderClient transcoderClient = null;
		
		
		while(!videoController.isAllVideosTranscoded()){
			
			if(videoController.getFirstInitialVideoName() !=  null){
			
				Instance instance = ec2Connector.getAvailableInstance();
				
				System.out.println("videoController.existsInitialVideo() is more");
				
				if(instance != null){
					System.out.println("AvailabelDNS "+instance.getPublicDnsName());
					
					transcoderClient = new TranscoderClient(instance.getPublicDnsName(), videoController.getFirstInitialVideoName(),videoController);
					transcoderClient.start();
			}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		while(!videoController.isAllVideosTranscoded()){
			System.out.println("videoController.isAllVideosTranscoded is more");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	AutoScaler.setNumberOfInstances(0);
		
	Vector<Video> videos = new Vector<Video>();	
		
	for (int i = 1 ;i <= vc; i++ ) {
			
		v = (Video)session.getAttribute("v"+i);
		if (v != null){
			videos.add(v);
			String orginalVideo = v.getOrginalFile();
			v.setMobileVideo(orginalVideo.substring(0,orginalVideo.lastIndexOf("."))+"-mobile.mp4");
			v.setStreamingVideo(orginalVideo.substring(0,orginalVideo.lastIndexOf("."))+"-streaming.mp4");
			session.setAttribute("v"+i,v);
			

		}	
	}
	
	session.setAttribute(Video.IS_READY_SESSION_KEY,new Integer(1));
}
		
}

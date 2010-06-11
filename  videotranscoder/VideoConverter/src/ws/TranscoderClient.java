/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package ws;

import java.rmi.RemoteException;

import model.VideoController;

import org.apache.axis2.AxisFault;

// TODO: Auto-generated Javadoc
/**
 * The Class TranscoderClient.
 */
public class TranscoderClient extends Thread {

	/** The host name. */
	private String hostName = null;
	
	/** The video name. */
	private String videoName = null;
	
	/** The video controller. */
	private VideoController videoController = null;
	
	
	/**
	 * Instantiates a new transcoder client.
	 *
	 * @param hostName the host name
	 * @param videoName the video name
	 * @param videoController the video controller
	 */
	public TranscoderClient(String hostName, String videoName, VideoController videoController){
		super();
		this.hostName =  hostName;
		this.videoName = videoName;
		this.videoController = videoController;
	}
	
	/**
	 * Gets the host name.
	 *
	 * @return the host name
	 */
	public String getHostName(){
		return this.hostName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
			
	try {   
		videoController.VideoStartedTranscoding(videoName);
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
            TranscoderStub stub = new TranscoderStub( "http://"+hostName+
            		":8080/TranscoderWebService/services/Transcoder.TranscoderHttpSoap12Endpoint/");
            
            
            TranscoderStub.TranscodeVideo tv = new TranscoderStub.TranscodeVideo(); 
            
            MyCallBackHandler myCallBackHandler =  new MyCallBackHandler(videoController,videoName);
           
            tv.setVideoName(videoName);
			
            stub.starttranscodeVideo(tv,myCallBackHandler);
			
		} catch (AxisFault e) {
		// TODO Auto-generated catch block
		e.printStackTrace();    
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

package ws;

import java.rmi.RemoteException;

import model.VideoController;

import org.apache.axis2.AxisFault;

public class TranscoderClient  {

	private String hostName = null;
	private String videoName = null;
	private VideoController videoController = null;
	
	
	public TranscoderClient(String hostName, String videoName, VideoController videoController){
		super();
		this.hostName =  hostName;
		this.videoName = videoName;
		this.videoController = videoController;
	}
	
	public String getHostName(){
		return this.hostName;
	}
	
	public void executeService(){
			
	try {   
		videoController.VideoStartedTranscoding(videoName);
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
            TranscoderStub stub = new TranscoderStub( "http://"+hostName+
            		":8080/TranscoderWebService/services/Transcoder.TranscoderHttpSoap12Endpoint/");
            
           
            /*
            ConfigurationContext conf =  ConfigurationContextFactory.createDefaultConfigurationContext();
            
            
            
            Options options = new Options();
            options.setTimeOutInMilliSeconds(100000);
            
            
            //TranscodeVideo tv = new TranscodeVideo();
           */
            
            
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

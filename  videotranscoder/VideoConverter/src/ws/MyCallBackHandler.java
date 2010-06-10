package ws;

import model.VideoController;

public class MyCallBackHandler extends TranscoderCallbackHandler {

	
  private String videoName = null;
  private VideoController videoController = null;
  private boolean ready = false;
    	
  
  public MyCallBackHandler(VideoController videoController, String videoName) {
		// TODO Auto-generated constructor stub
	  super();
	  this.videoName = videoName;
	  this.videoController  =  videoController;
  }

/**
   * auto generated Axis2 call back method for transcodeVideo method
   * override this method for handling normal response from transcodeVideo operation
   */
  public void receiveResulttranscodeVideo(
           ws.TranscoderStub.TranscodeVideoResponse result
               ) {
	  
	  System.out.println("Inside callback function");
	  
	  ready = true;
	  
	  if (result.get_return() ==  true)
	  {  
		    System.out.println("Web Service Call is succesful");
		    videoController.VideoTrancoded(videoName);
		    
      }else{
     	 System.out.println("Web Service Call is not succesful");
      }
      
  }
  
  /**
   * auto generated Axis2 Error handler
   * override this method for handling error response from transcodeVideo operation
   */
    public void receiveErrortranscodeVideo(java.lang.Exception e) {
    	System.out.println("receiveErrortranscodeVideo  ");
    	videoController.VideoTrancodeFailed(videoName);
    	e.printStackTrace();
    }
    
    

public boolean isFinished() {
	// TODO Auto-generated method stub
	return ready;
}
  
}

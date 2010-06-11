/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package ws;

import model.VideoController;

// TODO: Auto-generated Javadoc
/**
 * The Class MyCallBackHandler.
 */
public class MyCallBackHandler extends TranscoderCallbackHandler {

	
  /** The video name. */
  private String videoName = null;
  
  /** The video controller. */
  private VideoController videoController = null;
  
  /** The ready. */
  private boolean ready = false;
    	
  
  /**
   * Instantiates a new my call back handler.
   *
   * @param videoController the video controller
   * @param videoName the video name
   */
  public MyCallBackHandler(VideoController videoController, String videoName) {
		// TODO Auto-generated constructor stub
	  super();
	  this.videoName = videoName;
	  this.videoController  =  videoController;
  }

/**
 * auto generated Axis2 call back method for transcodeVideo method
 * override this method for handling normal response from transcodeVideo operation.
 *
 * @param result the result
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
   * override this method for handling error response from transcodeVideo operation.
   *
   * @param e the e
   */
    public void receiveErrortranscodeVideo(java.lang.Exception e) {
    	System.out.println("receiveErrortranscodeVideo  ");
    	videoController.VideoTrancodeFailed(videoName);
    	e.printStackTrace();
    }
    
    

/**
 * Checks if is finished.
 *
 * @return true, if is finished
 */
public boolean isFinished() {
	// TODO Auto-generated method stub
	return ready;
}
  
}


/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */

    package ws;

    // TODO: Auto-generated Javadoc
/**
     *  TranscoderCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class TranscoderCallbackHandler{



    /** The client data. */
    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be available at the time this callback is called.
    */
    public TranscoderCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData.
     */
    public TranscoderCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data.
     *
     * @return the client data
     */

     public Object getClientData() {
        return clientData;
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
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from transcodeVideo operation.
           *
           * @param e the e
           */
            public void receiveErrortranscodeVideo(java.lang.Exception e) {
            }
                


    }
    
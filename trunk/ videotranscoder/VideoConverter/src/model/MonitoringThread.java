/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package model;


import java.util.Date;

import javax.servlet.http.HttpSession;

import aws.CloudWatchConnector;

// TODO: Auto-generated Javadoc
/**
 * The Class MonitoringThread.
 */
public class MonitoringThread extends Thread{

	/** The Constant STATISTICS_SESSION_KEY. */
	public static final String STATISTICS_SESSION_KEY = "STATISTICS_SESSION_KEY";
	
	/** The session. */
	private HttpSession session;
	
	/**
	 * Instantiates a new monitoring thread.
	 *
	 * @param session the session
	 */
	public MonitoringThread(HttpSession session){
		super();
		this.session =  session;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run (){
		
		String statistics = "";
		
		Date startingTime = new Date(System.currentTimeMillis() - (1000*60*5));
        
        while((Integer)session.getAttribute(Video.IS_READY_SESSION_KEY) == 0){
        
	        if( session.getAttribute(STATISTICS_SESSION_KEY) != null){
	        	statistics = (String)session.getAttribute(STATISTICS_SESSION_KEY);
	        }
	        
	        statistics = CloudWatchConnector.getCloudWatchConnector().getMetricStatics(
	        		startingTime,
	        		new Date());
						
			
	        session.setAttribute(STATISTICS_SESSION_KEY,statistics);
	        
	        try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        
        try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		statistics = CloudWatchConnector.getCloudWatchConnector().getMetricStatics(
        		startingTime,
        		new Date());
					
		
        session.setAttribute(STATISTICS_SESSION_KEY,statistics);        
	}
	
}

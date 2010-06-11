package model;


import java.util.Date;

import javax.servlet.http.HttpSession;

import aws.CloudWatchConnector;

public class MonitoringThread extends Thread{

	public static final String STATISTICS_SESSION_KEY = "STATISTICS_SESSION_KEY";
	
	private HttpSession session;
	
	public MonitoringThread(HttpSession session){
		super();
		this.session =  session;
	}
	
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
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        
		
	}
	
}

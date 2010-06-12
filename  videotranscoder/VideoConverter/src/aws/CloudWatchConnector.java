/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;

// TODO: Auto-generated Javadoc
/**
 * The Class CloudWatchConnector.
 */
public class CloudWatchConnector {

	/** The cloud watch connector. */
	private static CloudWatchConnector cloudWatchConnector = null;
	
	/** The cloud watch. */
	private AmazonCloudWatch cloudWatch = null;
	
	/**
	 * Instantiates a new cloud watch connector.
	 */
	private CloudWatchConnector(){
		super();
	}
	
	/**
	 * Gets the cloud watch connector.
	 *
	 * @return the cloud watch connector
	 */
	public static CloudWatchConnector getCloudWatchConnector(){
		if(cloudWatchConnector == null){
			cloudWatchConnector =  new CloudWatchConnector();
		}
		
		if(cloudWatchConnector.cloudWatch == null){
			AWSCredentials credentials = null;
			try {
				credentials = new PropertiesCredentials(
				        CloudWatchConnector.class.getResourceAsStream("AwsCredentials.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			cloudWatchConnector.cloudWatch = new AmazonCloudWatchClient(credentials);
			cloudWatchConnector.cloudWatch.setEndpoint("monitoring.eu-west-1.amazonaws.com");
			
		}
		
		return cloudWatchConnector;
		
	}	 
	
	
	
	/**
	 * Display metric statics.
	 *
	 * @param StartTime the start time
	 * @param endTime the end time
	 * @return the metric statics
	 */
	public String getMetricStatics(Date StartTime, Date endTime){
		
		String staticResulstStr = ""; 
		
		GetMetricStatisticsRequest request = new GetMetricStatisticsRequest();
        request.setPeriod(60);
        request.setMeasureName("CPUUtilization");
        request.setNamespace("AWS/EC2");
        List<Dimension> dimensions = new ArrayList<Dimension>();
        Dimension d1  = new Dimension();
        d1.setName("InstanceType");
        d1.setValue("c1.medium");
        
        Dimension d2  = new Dimension();
        d2.setName("ImageID");
        d2.setValue("ami-8f0923fb");
        
        dimensions.add(d1);
       // dimensions.add(d2);
        request.setDimensions(dimensions);
        
        List<String> statistics = new ArrayList<String>();
        statistics.add("Sum");
        statistics.add("Average");
        statistics.add("Maximum");
        request.setStatistics(statistics);
        request.setStartTime(StartTime );
        request.setEndTime(endTime); 
		 
		 
		GetMetricStatisticsResult getMetricStatisticsResult = cloudWatch.getMetricStatistics(request);
		
    
        java.util.List<Datapoint> datapointsList = getMetricStatisticsResult.getDatapoints();
        
        for (Datapoint datapoints : datapointsList) {
        	
        	staticResulstStr = staticResulstStr + " Time:" + datapoints.getTimestamp(); 
            staticResulstStr = staticResulstStr + " Instances:" + datapoints.getSamples();   
            staticResulstStr = staticResulstStr + " AVG-CPU:" +  datapoints.getAverage();
            staticResulstStr = staticResulstStr + " MAX-CPU:" +  datapoints.getMaximum();
            staticResulstStr = staticResulstStr + "\n";
            
        }
        
        return staticResulstStr;
	}
	
	/**
	 * Display metrics.
	 */
	public void displayMetrics(){
		ListMetricsRequest request = new ListMetricsRequest();
		ListMetricsResult listMetricsResult = cloudWatch.listMetrics(request);
		
         
        java.util.List<Metric> metricsList = listMetricsResult.getMetrics();
        for (Metric metrics : metricsList) {
            System.out.println("            Metrics");
            System.out.println();
            
            System.out.println("                MeasureName");
            System.out.println();
            System.out.println("                    " + metrics.getMeasureName());
            System.out.println();
        
            java.util.List<Dimension> dimensionsList = metrics.getDimensions();
            for (Dimension dimensions : dimensionsList) {
                System.out.println("                Dimensions");
                System.out.println();
             
                System.out.println("                    Name");
                System.out.println();
                System.out.println("                        " + dimensions.getName());
                System.out.println();
                
                
                System.out.println("                    Value");
                System.out.println();
                System.out.println("                        " + dimensions.getValue());
                System.out.println();
            
            }
           
                System.out.println("                Namespace");
                System.out.println();
                System.out.println("                    " + metrics.getNamespace());
                System.out.println();
            
        }
        
        System.out.println("            NextToken");
        System.out.println();
        System.out.println("                " + listMetricsResult.getNextToken());
        System.out.println();
	}
}

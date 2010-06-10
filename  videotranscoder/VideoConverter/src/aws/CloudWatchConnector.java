package aws;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class CloudWatchConnector {

	private static CloudWatchConnector cloudWatchConnector = null;
	
	private AmazonCloudWatch cloudWatch = null;
	
	
	
	private CloudWatchConnector(){
		super();
	}
	
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
			//cloudWatchConnector.cloudWatch.setEndpoint("ec2.eu-west-1.amazonaws.com");
			
		}
		
		return cloudWatchConnector;
		
	}	 
	
	
	
	public void displayMetricStatics(){
		GetMetricStatisticsRequest request = new GetMetricStatisticsRequest();
        request.setPeriod(120);
        request.setUnit("Bytes");
        request.setMeasureName("NetworkOut");
        request.setNamespace("AWS/EC2");
        List<Dimension> dimensions = new ArrayList<Dimension>();
        Dimension d1  = new Dimension();
        d1.setName("InstanceType");
        d1.setValue("m1.small");
        
        Dimension d2  = new Dimension();
        d2.setName("ImageID");
        d2.setValue("ami-8f0923fb");
        
        dimensions.add(d1);
        dimensions.add(d2);
        request.setDimensions(dimensions);
        
        List<String> statistics = new ArrayList<String>();
        statistics.add("Sum");
        statistics.add("Average");
        statistics.add("Maximum");
        request.setStatistics(statistics);
        request.setStartTime(new Date());
        request.setEndTime(new Date(System.currentTimeMillis() + 60 * 1000 * 1)); 
		 
		 
		GetMetricStatisticsResult getMetricStatisticsResult = cloudWatch.getMetricStatistics(request);
		
		
		System.out.println ("GetMetricStatistics Action Response");
        System.out.println ("=============================================================================");
        System.out.println ();
        
        System.out.println("        GetMetricStatisticsResult");
        System.out.println();
    
        java.util.List<Datapoint> datapointsList = getMetricStatisticsResult.getDatapoints();
        for (Datapoint datapoints : datapointsList) {
        	System.out.println("            Datapoints");
        	
        	 
        	System.out.println("                Timestamp");
            System.out.println();
            System.out.println("                    " + datapoints.getTimestamp());
            System.out.println();
        }
	}
	
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

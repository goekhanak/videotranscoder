package aws;

import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
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
			cloudWatchConnector.cloudWatch.setEndpoint("ec2.eu-west-1.amazonaws.com");
		}
		
		return cloudWatchConnector;
		
	}	 
	
	public void displatMetrics(){
		ListMetricsRequest request = new ListMetricsRequest();
		
		ListMetricsResult response = cloudWatch.listMetrics(request);
		
		if (response.isSetListMetricsResult()) {
            System.out.println("        ListMetricsResult");
            System.out.println();
            ListMetricsResult  listMetricsResult = response.getListMetricsResult();
            java.util.List<Metric> metricsList = listMetricsResult.getMetrics();
            for (Metric metrics : metricsList) {
                System.out.println("            Metrics");
                System.out.println();
                if (metrics.isSetMeasureName()) {
                    System.out.println("                MeasureName");
                    System.out.println();
                    System.out.println("                    " + metrics.getMeasureName());
                    System.out.println();
                }
                java.util.List<Dimension> dimensionsList = metrics.getDimensions();
                for (Dimension dimensions : dimensionsList) {
                    System.out.println("                Dimensions");
                    System.out.println();
                    if (dimensions.isSetName()) {
                        System.out.println("                    Name");
                        System.out.println();
                        System.out.println("                        " + dimensions.getName());
                        System.out.println();
                    }
                    if (dimensions.isSetValue()) {
                        System.out.println("                    Value");
                        System.out.println();
                        System.out.println("                        " + dimensions.getValue());
                        System.out.println();
                    }
                }
                if (metrics.isSetNamespace()) {
                    System.out.println("                Namespace");
                    System.out.println();
                    System.out.println("                    " + metrics.getNamespace());
                    System.out.println();
                }
            }
            if (listMetricsResult.isSetNextToken()) {
                System.out.println("            NextToken");
                System.out.println();
                System.out.println("                " + listMetricsResult.getNextToken());
                System.out.println();
            }
        } 
        if (response.isSetResponseMetadata()) {
            System.out.println("        ResponseMetadata");
            System.out.println();
            ResponseMetadata  responseMetadata = response.getResponseMetadata();
            if (responseMetadata.isSetRequestId()) {
                System.out.println("            RequestId");
                System.out.println();
                System.out.println("                " + responseMetadata.getRequestId());
                System.out.println();
            }
        } 
        System.out.println();

	}
}

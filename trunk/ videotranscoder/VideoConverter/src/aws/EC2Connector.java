/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;


import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Monitoring;
import com.amazonaws.services.ec2.model.Reservation;

// TODO: Auto-generated Javadoc
/**
 * The Class EC2Connector.
 */
public class EC2Connector {
	
	//private static SQSConnector sQSConnector =  null;
	
	/** The ec2. */
	private AmazonEC2 ec2 =  null;
	
	/** The ec2 connector. */
	private static EC2Connector ec2Connector = null;
	
	/** The busy instances ids. */
	private TreeSet<String> busyInstancesIds = null;
	
	/** The Constant SERVER_ID. */
	private static final String SERVER_ID = "i-fc4fda8b";
	
	/**
	 * Instantiates a new e c2 connector.
	 */
	private EC2Connector(){
		super();
	}
	
	
	/**
	 * Gets the e c2 connector.
	 *
	 * @return the e c2 connector
	 */
	public static EC2Connector getEC2Connector(){
		if(ec2Connector == null){
			ec2Connector =  new EC2Connector();
		}
		
		if(ec2Connector.ec2 == null){
			AWSCredentials credentials = null;
			try {
				credentials = new PropertiesCredentials(
				        EC2Connector.class.getResourceAsStream("AwsCredentials.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ec2Connector.ec2 = new AmazonEC2Client(credentials);
			ec2Connector.ec2.setEndpoint("ec2.eu-west-1.amazonaws.com");
		}
		
		if(ec2Connector.busyInstancesIds == null){
			ec2Connector.busyInstancesIds = new TreeSet<String>();
		}
		
		return ec2Connector;
	}
	
	/**
	 * Gets the cloud watch information.
	 *
	 * @param instance the instance
	 * @return the cloud watch information
	 */
	public String getCloudWatchInformation(Instance instance){
		return null;
	}
	

	/**
	 * Display instances.
	 */
	public void displayInstances(){
		 
		/*
         * Amazon EC2
         *
         * The AWS EC2 client allows you to create, delete, and administer
         * instances programmatically.
         *
         * In this sample, we use an EC2 client to get a list of all the
         * availability zones, and all instances sorted by reservation id.
         */
        try {
            DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
            System.out.println("You have access to " + availabilityZonesResult.getAvailabilityZones().size() +
                    " Availability Zones.");

            
            DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
            List<Reservation> reservations = describeInstancesRequest.getReservations();
            Set<Instance> instances = new HashSet<Instance>();

            for (Reservation reservation : reservations) {
                instances.addAll(reservation.getInstances());
            }
            
            for (Iterator<Instance> iterator = instances.iterator(); iterator.hasNext();) {
				Instance instance =  iterator.next();
				Monitoring monitoring = instance.getMonitoring();
				System.out.println(" ------------------ ");
				System.out.println("Instance PublicDnsName:" +instance.getPublicDnsName());
				System.out.println("Instance getInstanceLifecycle():" +instance.getInstanceLifecycle());
				System.out.println("StateTransitionReason():" +instance.getStateTransitionReason());
				System.out.println("Instance State:" +instance.getState().getName());
				System.out.println("Instance Type:" +instance.getInstanceType() );
				System.out.println("" );
				
				System.out.println("monitoring.getState() "+monitoring.getState() );
			}

            System.out.println("You have " + instances.size() + " Amazon EC2 instance(s) running.");
        } catch (AmazonServiceException ase) {
                System.out.println("Caught Exception: " + ase.getMessage());
                System.out.println("Reponse Status Code: " + ase.getStatusCode());
                System.out.println("Error Code: " + ase.getErrorCode());
                System.out.println("Request ID: " + ase.getRequestId());
        }
	}

	/**
	 * Gets the available instance.
	 *
	 * @return the available instance
	 */
	public Instance getAvailableInstance() {
		DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
        List<Reservation> reservations = describeInstancesRequest.getReservations();
        Set<Instance> instances = new HashSet<Instance>();

        for (Reservation reservation : reservations) {
            instances.addAll(reservation.getInstances());
        }
        
        for (Iterator<Instance> iterator = instances.iterator(); iterator.hasNext();) {
			Instance instance =  iterator.next();
			
			if(instance.getState().getName().equals("running")
					&& !instance.getInstanceType().equals("m1.small")
					&& !busyInstancesIds.contains(instance.getPublicDnsName())
					&& !instance.getInstanceId().equals(SERVER_ID)){
				
				this.busyInstancesIds.add(instance.getPublicDnsName());
				return instance;
			}
		}
		return null;
	}
	
	/**
	 * Reset busy instances.
	 */
	public void resetBusyInstances(){
		busyInstancesIds.removeAll(busyInstancesIds);
	}
	
}

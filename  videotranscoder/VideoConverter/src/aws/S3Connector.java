/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import model.Video;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;



// TODO: Auto-generated Javadoc
/**
 * This sample demonstrates how to make basic requests to Amazon S3 using
 * the AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon S3. For more information on
 * Amazon S3, see http://aws.amazon.com/s3.
 * <p>
 * <b>Important:</b> Be sure to fill in your AWS access credentials in the
 *                   AwsCredentials.properties file before you try to run this
 *                   sample.
 * http://aws.amazon.com/security-credentials
 */
public class S3Connector {
	
	
	
	/** The s3 connector. */
	private static S3Connector s3Connector = null;
	
	/** The s3. */
	private AmazonS3 s3 = null;
	
	
	
	
	/**
	 * Creates the connector.
	 */
	private void CreateConnector(){
		try {
			s3 = new AmazonS3Client(new PropertiesCredentials(
			        S3Connector.class.getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Instantiates a new s3 connector.
	 */
	private S3Connector(){
		super();
		CreateConnector();
	}
	
	/**
	 * Gets the s3 connector.
	 *
	 * @return the s3 connector
	 */
	public static S3Connector getS3Connector(){
		if (s3Connector == null){
			s3Connector = new S3Connector();
		}
		
		return s3Connector;
	}
	
	
	
	
	/**
	 * Upload file.
	 *
	 * @param bucketName the bucket name
	 * @param key the key
	 * @param file the file
	 */
	public  void uploadFile(String bucketName,String key, File file){
		try {
			
			s3.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
			
			System.out.println("Bucket Veriosning status:"+s3.getBucketVersioningConfiguration(bucketName).getStatus());
			
			 
			System.out.println("Uploading a new object to S3 from a file\n");
		    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
		    putObjectRequest = putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead) ;
		    
			s3.putObject(putObjectRequest);
			
			
			AccessControlList objectacl = s3.getObjectAcl(bucketName, key); 
		    objectacl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
			
		    s3.setBucketAcl(bucketName, objectacl);
		    s3.setObjectAcl(bucketName, key, objectacl);
			
		
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
	
	}
	
	
	/**
	 * Download file.
	 *
	 * @param bucketName the bucket name
	 * @param key the key
	 * @return the input stream
	 */
	public void cahnegAclFile(String bucketName,String key){
		try {
			System.out.println("Downloading an object");
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
            
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            //displayTextInputStream(object.getObjectContent());
		
            
            
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } 
        
	}
	
	
	/**
	 * Download file.
	 *
	 * @param bucketName the bucket name
	 * @param key the key
	 * @return the input stream
	 */
	public InputStream downloadFile(String bucketName,String key){
		try {
			System.out.println("Downloading an object");
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
            
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            //displayTextInputStream(object.getObjectContent());
		
            return object.getObjectContent();
            
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } 
        return null;
	}
	
	/**
	 * Delete file.
	 *
	 * @param bucketName the bucket name
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean deleteFile(String bucketName,String key){
		try {
			System.out.println("Deleting an object\n");
            s3.deleteObject(bucketName, key);
		
            return true;
            
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
        return false;
	}
	

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException {
        /*
         * Important: Be sure to fill in your AWS access credentials in the
         *            AwsCredentials.properties file before you try to run this
         *            sample.
         * http://aws.amazon.com/security-credentials
         */
        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
                S3Connector.class.getResourceAsStream("AwsCredentials.properties")));

        String bucketName = Video.VIDEOS_BUCKET;
        String key = "MyObjectKey";

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");

        try {
            /*
             * Create a new S3 bucket - Amazon S3 bucket names are globally unique,
             * so once a bucket name has been taken by any user, you can't create
             * another bucket with that same name.
             *
             * You can optionally specify a location for your bucket if you want to
             * keep your data closer to your applications or users.
             */
            System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);

            /*
             * List the buckets in your account
             */
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();

            /*
             * Upload an object to your bucket - You can easily upload a file to
             * S3, or upload directly an InputStream if you know the length of
             * the data in the stream. You can also specify your own metadata
             * when uploading to S3, which allows you set a variety of options
             * like content-type and content-encoding, plus additional metadata
             * specific to your applications.
             */
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

            /*
             * Download an object - When you download an object, you get all of
             * the object's metadata and a stream from which to read the contents.
             * It's important to read the contents of the stream as quickly as
             * possibly since the data is streamed directly from Amazon S3 and your
             * network connection will remain open until you read all the data or
             * close the input stream.
             *
             * GetObjectRequest also supports several other options, including
             * conditional downloading of objects based on modification times,
             * ETags, and selectively downloading a range of an object.
             */
            System.out.println("Downloading an object");
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());

            /*
             * List objects in your bucket by prefix - There are many options for
             * listing the objects in your bucket.  Keep in mind that buckets with
             * many objects might truncate their results when listing their objects,
             * so be sure to check if the returned object listing is truncated, and
             * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
             * additional results.
             */
            System.out.println("Listing objects");
            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix("My"));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();

            
            /*
             * Delete an object - Unless versioning has been turned on for your bucket,
             * there is no way to undelete an object, so use caution when deleting objects.
             */
            
            /*
             * 
         
            System.out.println("Deleting an object\n");
            s3.deleteObject(bucketName, key);

            /*
             * Delete a bucket - A bucket must be completely empty before it can be
             * deleted, so remember to delete any objects from your buckets before
             * you try to delete them.
             */
            
            /*
            
            System.out.println("Deleting bucket " + bucketName + "\n");
            s3.deleteBucket(bucketName);
            
            
            */
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    /**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3.
     *
     * @return A newly created temporary file with text data.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }

    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input The input stream to display as text.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }

}

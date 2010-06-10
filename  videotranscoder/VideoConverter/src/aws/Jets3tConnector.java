/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;

import java.io.File;


import org.jets3t.service.S3Service;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;


// TODO: Auto-generated Javadoc
/**
 * The Class MyBackup.
 */
public class Jets3tConnector {
	
	
	/**
	 * Upload file.
	 *
	 * @param bucketName the bucket name
	 * @param key the key
	 * @param file the file
	 * @throws Exception the exception
	 */
	public static void UploadFile(String bucketName,String key, File file) throws Exception{
		String myAccessKey = "AKIAJP6LW2CVQXXPCMKA";
        String mySecretKey = "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";
        
        AWSCredentials myCredentials = new AWSCredentials(myAccessKey, mySecretKey);
        S3Service s3 = new RestS3Service(myCredentials);
        
        
        S3Bucket myBucket = new S3Bucket(bucketName);
        
        S3Object object = new S3Object(myBucket,file);
        object.setKey(key);
        
        s3.putObject(bucketName, object);
        
        AccessControlList acl =  s3.getObjectAcl(bucketName, key);  
        acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
        
        s3.putBucketAcl(bucketName, acl);
        s3.putObjectAcl(bucketName, key, acl);   
	}
	



   
}

/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;
/*
 * Copyright 2010 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

// TODO: Auto-generated Javadoc
/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web
 * Services developer account, and be signed up to use Amazon SQS. For more
 * information on Amazon SQS, see http://aws.amazon.com/sqs.
 * <p>
 * <b>Important:</b> Be sure to fill in your AWS access credentials in the
 *                   AwsCredentials.properties file before you try to run this
 *                   sample.
 * http://aws.amazon.com/security-credentials
 */
public class SQSConnector {
	
	/** The Constant TRANSCODER_QUEUE_NAME. */
	public static final String TRANSCODER_QUEUE_NAME = "Transcoder-queue";
	
	/** The Constant TRANSCODER_QUEUE_URL. */
	public static final String TRANSCODER_QUEUE_URL = "https://queue.amazonaws.com/786131973585/Transcoder-queue";
	
	/** The s qs connector. */
	private static SQSConnector sQSConnector =  null;
	
	/** The sqs. */
	private AmazonSQS sqs =  null;;
	
	
	/**
	 * Instantiates a new sQS connector.
	 */
	private SQSConnector(){
		super();
	}
	
	/**
	 * Gets the sQS connector.
	 *
	 * @return the sQS connector
	 */
	public static SQSConnector getSQSConnector(){
		if (sQSConnector == null){
			sQSConnector = new SQSConnector();
		} 
		
		if(sQSConnector.sqs  == null){
			try {
				sQSConnector.sqs = new AmazonSQSClient(new PropertiesCredentials(
				        SQSConnector.class.getResourceAsStream("AwsCredentials.properties")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sQSConnector;
	} 
	
	
	/**
	 * Creates the queue.
	 */
	public void createQueue(){
		
		System.out.println("Creating a new SQS queue called " +TRANSCODER_QUEUE_NAME);
		
		CreateQueueRequest createQueueRequest = new CreateQueueRequest(TRANSCODER_QUEUE_NAME);
        String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
        
        System.out.println("Created QueueUrl for " +TRANSCODER_QUEUE_NAME +": " + myQueueUrl);
        
        // List queues
        System.out.println("Listing all queues in your account.\n");
        for (String queueUrl : sqs.listQueues().getQueueUrls()) {
            System.out.println("  QueueUrl: " + queueUrl);
        }
        System.out.println();
	}

	/**
	 * Send message.
	 *
	 * @param queueUrl the queue url
	 * @param messageContent the message content
	 */
	public void sendMessage(String queueUrl,String messageContent){
		// Send a message
	    System.out.println("Sending a message to " + queueUrl);
	    SendMessageRequest sendMessageRequest = new SendMessageRequest(TRANSCODER_QUEUE_URL, messageContent);
	    sqs.sendMessage(sendMessageRequest);
	}
	
	/**
	 * Display messages.
	 *
	 * @param queueUrl the queue url
	 */
	public void displayMessages(String queueUrl){
		System.out.println("Receiving messages from MyQueue.\n");
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            System.out.println("  Message");
            System.out.println("    MessageId:     " + message.getMessageId());
            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
            System.out.println("    Body:          " + message.getBody());
            for (Entry<String, String> entry : message.getAttributes().entrySet()) {
                System.out.println("  Attribute");
                System.out.println("    Name:  " + entry.getKey());
                System.out.println("    Value: " + entry.getValue());
            }
        }
	}
	
	/**
	 * Delete message.
	 *
	 * @param queueUrl the queue url
	 */
	public void deleteMessage(String queueUrl){
		System.out.println("Deleting a message.\n");
		
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
        	System.out.println("Message "+message.getBody() +"is deleted");
        	String messageRecieptHandle = message.getReceiptHandle();
        	System.out.println("Message receipt Handle:"+ messageRecieptHandle);
            sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messageRecieptHandle));
        }
        
	}
	
	/**
	 * Gets the message count.
	 *
	 * @param queueUrl the queue url
	 * @return the message count
	 */
	public int getMessageCount(String queueUrl){
		 // Receive messages
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        return messages.size();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
        /*
         * Important: Be sure to fill in your AWS access credentials in the
         *            AwsCredentials.properties file before you try to run this
         *            sample.
         * http://aws.amazon.com/security-credentials
         */
        AmazonSQS sqs = new AmazonSQSClient(new PropertiesCredentials(
                SQSConnector.class.getResourceAsStream("AwsCredentials.properties")));

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon SQS");
        System.out.println("===========================================\n");

        try {
            // Create a queue
            System.out.println("Creating a new SQS queue called MyQueue.\n");
            CreateQueueRequest createQueueRequest = new CreateQueueRequest("MyQueue");
            String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();

            // List queues
            System.out.println("Listing all queues in your account.\n");
            for (String queueUrl : sqs.listQueues().getQueueUrls()) {
                System.out.println("  QueueUrl: " + queueUrl);
            }
            System.out.println();

            // Send a message
            System.out.println("Sending a message to MyQueue.\n");
            sqs.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));

            // Receive messages
            System.out.println("Receiving messages from MyQueue.\n");
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
            for (Message message : messages) {
                System.out.println("  Message");
                System.out.println("    MessageId:     " + message.getMessageId());
                System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
                System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
                System.out.println("    Body:          " + message.getBody());
                for (Entry<String, String> entry : message.getAttributes().entrySet()) {
                    System.out.println("  Attribute");
                    System.out.println("    Name:  " + entry.getKey());
                    System.out.println("    Value: " + entry.getValue());
                }
            }
            System.out.println();

            // Delete a message
            System.out.println("Deleting a message.\n");
            String messageRecieptHandle = messages.get(0).getReceiptHandle();
            sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageRecieptHandle));

            // Delete a queue
            System.out.println("Deleting the test queue.\n");
            sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}

package aws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aws.SQSConnector;

public class SQSConnectorTest {

	private SQSConnector sqsConnector =  null;
	
	@Before
	public void setUp() throws Exception {
		sqsConnector = SQSConnector.getSQSConnector();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testCreateQueue() {
		sqsConnector.createQueue();
	}

	@Test
	public void testMessageCount() {
		int count = sqsConnector.getMessageCount(SQSConnector.TRANSCODER_QUEUE_URL);
		System.out.println("MessageCount:" + count);
		assertTrue(count > -1);
	}
	
	@Test
	public void testAddMessage(){
		sqsConnector.sendMessage(SQSConnector.TRANSCODER_QUEUE_URL, "testMessage2222");
	}
	
	
	@Test
	public void testDeleteMessage(){
		sqsConnector.deleteMessage(SQSConnector.TRANSCODER_QUEUE_URL);
	}
	
	@Test
	public void testMessageDisplay(){
		sqsConnector.displayMessages(SQSConnector.TRANSCODER_QUEUE_URL);
	}
	
	
	
	
}

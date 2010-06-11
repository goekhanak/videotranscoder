/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aws.SQSConnector;

// TODO: Auto-generated Javadoc
/**
 * The Class SQSConnectorTest.
 */
public class SQSConnectorTest {

	/** The sqs connector. */
	private SQSConnector sqsConnector =  null;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		sqsConnector = SQSConnector.getSQSConnector();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test create queue.
	 */
	@Ignore
	@Test
	public void testCreateQueue() {
		sqsConnector.createQueue();
	}

	/**
	 * Test message count.
	 */
	@Test
	public void testMessageCount() {
		int count = sqsConnector.getMessageCount(SQSConnector.TRANSCODER_QUEUE_URL);
		System.out.println("MessageCount:" + count);
		assertTrue(count > -1);
	}
	
	/**
	 * Test add message.
	 */
	@Test
	public void testAddMessage(){
		sqsConnector.sendMessage(SQSConnector.TRANSCODER_QUEUE_URL, "testMessage2222");
	}
	
	
	/**
	 * Test delete message.
	 */
	@Test
	public void testDeleteMessage(){
		sqsConnector.deleteMessage(SQSConnector.TRANSCODER_QUEUE_URL);
	}
	
	/**
	 * Test message display.
	 */
	@Test
	public void testMessageDisplay(){
		sqsConnector.displayMessages(SQSConnector.TRANSCODER_QUEUE_URL);
	}
	
	
	
	
}

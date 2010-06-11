/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package ws;

import static org.junit.Assert.*;

import java.util.Vector;

import model.Video;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TranscoderClientTest.
 */
public class TranscoderClientTest {

	/** The transcoder client. */
	TranscoderClient transcoderClient;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		//transcoderClient =  new TranscoderClient("ec2-79-125-60-15.eu-west-1.compute.amazonaws.com", "song.mp4",new Vector<Video>());
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
	 * Test execute service.
	 */
	@Ignore
	@Test
	public void testExecuteService() {
		transcoderClient.start();
	}

	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		/*
		TranscoderClient transcoderClient =  new TranscoderClient("ec2-79-125-60-15.eu-west-1.compute.amazonaws.com", "small.mp4",new Vector<Video>());
		//TranscoderClient transcoderClient =  new TranscoderClient("ec2-79-125-58-202.eu-west-1.compute.amazonaws.com", "small.mp4",new Vector<Video>());
		//TranscoderClient transcoderClient =  new TranscoderClient("ec2-79-125-28-154.eu-west-1.compute.amazonaws.com", "Barkovizyon2010-mobile.mp4");
		//transcoderClient.start();
		transcoderClient.executeService();
		*/
	}
}

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

// TODO: Auto-generated Javadoc
/**
 * The Class CloudWatchConnectorTest.
 */
public class CloudWatchConnectorTest {

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		
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
	 * Test display statics.
	 */
	@Test
	public void testDisplayStatics() {
		//CloudWatchConnector.getCloudWatchConnector().displayMetricStatics();
	}
	
	/**
	 * Test display metrics.
	 */
	@Ignore
	@Test
	public void testDisplayMetrics() {
		CloudWatchConnector.getCloudWatchConnector().displayMetrics();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
	//	CloudWatchConnector.getCloudWatchConnector().displayMetricStatics();
	}

}

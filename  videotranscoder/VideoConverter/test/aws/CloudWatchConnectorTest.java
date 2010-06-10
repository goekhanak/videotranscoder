package aws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CloudWatchConnectorTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testDisplayStatics() {
		CloudWatchConnector.getCloudWatchConnector().displayMetricStatics();
	}
	
	@Ignore
	@Test
	public void testDisplayMetrics() {
		CloudWatchConnector.getCloudWatchConnector().displayMetrics();
	}
	
	public static void main(String[] args){
		CloudWatchConnector.getCloudWatchConnector().displayMetricStatics();
	}

}

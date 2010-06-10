package ws;

import static org.junit.Assert.*;

import java.util.Vector;

import model.Video;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TranscoderClientTest {

	TranscoderClient transcoderClient;
	
	@Before
	public void setUp() throws Exception {
		//transcoderClient =  new TranscoderClient("ec2-79-125-60-15.eu-west-1.compute.amazonaws.com", "song.mp4",new Vector<Video>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testExecuteService() {
		transcoderClient.executeService();
	}

	
	
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

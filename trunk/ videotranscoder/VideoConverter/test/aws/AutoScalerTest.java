/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;

import static org.junit.Assert.*;

import model.CLEngine;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aws.AutoScaler;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoScalerTest.
 */
public class AutoScalerTest {

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
	 * Test directory script.
	 */
	@Test 
	public void testDirectoryScript(){
		CLEngine.shellRunner("pwd");
	}
	
	/**
	 * Test set number of instances.
	 */
	@Test
	public void testSetNumberOfInstances() {
		assertTrue(AutoScaler.setNumberOfInstances(3));
	}
	
	/**
	 * Test shell script.
	 */
	@Ignore
	@Test 
	public void testShellScript(){
		CLEngine.shellRunner("/bin/sh /home/goekhan/Desktop/AWS/setCapacity.sh");
	}

}

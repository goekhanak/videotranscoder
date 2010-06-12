/*
 * I. Gökhan Aksakallı
 * Informatik-5 RWTH Aachen
 * www.dbis.rwth-aachen.de
 */
package aws;

import model.CLEngine;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoScaler.
 */
public class AutoScaler {

	/** The Constant MINUMUM_CAPACITY. */
	public final static int MINUMUM_CAPACITY = 0;
	
	/** The Constant MAXIMUM_CAPACITY. */
	public final static int MAXIMUM_CAPACITY = 10;
	
	/** The Constant Auto_Scaling_Group_Name. */
	public final static String Auto_Scaling_Group_Name  = "asg";
	
	/**
	 * Sets the number of instances.
	 *
	 * @param desiredCapacity the desired capacity
	 * @return true, if successful
	 */
	public static boolean setNumberOfInstances(int desiredCapacity){
		if(desiredCapacity < MINUMUM_CAPACITY ){
			desiredCapacity = MINUMUM_CAPACITY;
		}
		else if (desiredCapacity > MAXIMUM_CAPACITY){
			desiredCapacity = MAXIMUM_CAPACITY;
		}	
		
		/* 
		 * Java Runtime can not see environmnet variables therefore instead of java runtime
		 * shell scirpting is used and java runtime is commented
		String cmd  = "/home/goekhan/work/AutoScaling-1.0.9.0/as-set-desired-capacity "+Auto_Scaling_Group_Name+"" +
				" --desired-capacity "+ desiredCapacity +" --region eu-west-1"; 
		
		return CLEngine.shellRunner(cmd);
		*/
		
		return CLEngine.shellRunner("/bin/sh "+CLEngine.SCRIPTS_DIR +"setCapacity.sh "+Auto_Scaling_Group_Name +
				" "+desiredCapacity);
	}
	
}

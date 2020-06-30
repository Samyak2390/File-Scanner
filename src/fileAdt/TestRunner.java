package fileAdt;

import org.junit.runner.Result;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

/**
 * This is the driver class that initiates testing of all the functionalities for
 * {@link FileScanner}.
 * 
 * @author Samyak Maharjan ID: 77202779
 *
 */

public class TestRunner {
	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(FileScanningTester.class);
			
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	   }
}

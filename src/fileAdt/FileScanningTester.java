package fileAdt;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods that tests functionalities for FileScanner.
 * @author Samyak Maharjan ID: 77202779
 *
 */

public class FileScanningTester {
	/**
	 * Simulated array list that stores the bytes for the loaded file(.txt, .png etc).
	 */
	ArrayList<Byte> fileBytes = new ArrayList<Byte>();
	
	/**
	 * Simulated array of byte pattern 
	 */
	byte[] bytes1 = {65, 66, 67};
	
	FileUtil fileUtilTest = new FileUtil();
	
	/**
	 * This method tests if the method scanFile from {@link FileUtil} is working correctly.
	 * If the method works as expected, then it should return a list of bytes. This is checked using 
	 * assertion - assertNotNull
	 */
	@Test
	public void testScanFile() {
		try {
			File file = new  File("test1.txt");
			List<Byte> bytesList = fileUtilTest.scanFile(file);
			assertNotNull(bytesList);
		}catch(Exception e) {
			System.out.println("File is not found or is corrupted");
		}
		
	}
	
	/**
	 * This method tests if the method loadPattern from {@link FileUtil} is working correctly.
	 * If the method works as expected, then it should convert every line from patterns.txt to
	 * bytes and store each line as an array in another array list. The nested array list has element or not is 
	 * confirmed using the assertion - assertNotNull
	 */
	@Test 
	public void testloadPattern(){
		try {
			File file = new File("patterns.txt");
			fileUtilTest.loadPattern(file);
			assertNotNull(fileUtilTest.bytePatterns.getByteArrays());
		}catch(Exception e) {
			System.out.println("File is not found or is corrupted");
		}
		
	}
	
	/**
	 * This method tests the parseLines method from {@link BytePatterns} class.
	 * parseLines method takes a string that is assumed to be two digit hex value separated
	 * by spaces. This passed string is then converted to array of bytes by splitting the string by
	 * spaces. Here, the result from the parseLine method is compared against the expected result from the 
	 * parseLines method using asserArrayEquals assertion.
	 */
	
	/*
	 * In this case, we are passing the string "41 42 43" to parseLines.
	 * And, we know the result after its conversion to array of bytes is [65, 66, 67].
	 * So we check if the result from parseLines is equal to the bytes1 array whose value
	 * is [65, 66, 67].
	 */
	@Test
	public void testParseLines() {
		String line = "41 42 43";
		BytePatterns bytePatternTest = new BytePatterns();
		bytePatternTest.parseLines(line);
		assertArrayEquals(bytePatternTest.getByteArrays().get(0), bytes1);
	}
	
	/**
	 * This method tests checkNext method from {@link BytePatterns} class.
	 * We have simulated an array list of bytes called fileBytes. This is compared
	 * against the array of bytes whose values we are aware of. We iterate through
	 * the fileBytes to call checkNext and find if we get what we expect.
	 * 
	 */
	/*
	 * In this case, we have an array list fileBytes with value = [12, 13, 65, 66, 67, 70]
	 * and bytes1 as [65, 66, 67]. We can clearly see there is a match for the pattern 65, 66 and 67.
	 * From the following method, we expect count to be 1 if checkNext is working fine.
	 * This is tested using assertTrue(count > 0).
	 */
	@Test
	public void testCheckNext() {
		fileBytes.add((byte)12);
		fileBytes.add((byte)13);
		fileBytes.add((byte)65);
		fileBytes.add((byte)66);
		fileBytes.add((byte)67);
		fileBytes.add((byte)70);
		BytePatterns bytePatternTest = new BytePatterns(bytes1);
		boolean matched = false;
		int count = 0;
		
		for(byte Byte : fileBytes) {
			matched = bytePatternTest.checkNext(Byte);
			if(matched) {
				count++;
			}
		}
		
		assertTrue(count > 0);
	}
	
}

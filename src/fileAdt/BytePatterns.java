package fileAdt;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * This class represents a nested array list of byte patterns.
 * This class provides method for comparing against these byte patterns along with other 
 * methods for getting array list of patterns, its size etc.
 * 
 * @author Samyak Maharjan ID: 77202779
 *
 */

public class BytePatterns {
	/**
	 * Variable to store a boolean value which denotes if the error has occurred while parsing 
	 * the lines from hex pattern file.
	 */
	boolean error = false;
	/**
	 * Array list that stores arrays of bytes.
	 */
	ArrayList<byte[]> byteArrays =new ArrayList<byte[]>();
	
	/**
	 * The bytes within the pattern.
	 */
	private byte[] bytes;
	
	/**
	 * The next position to be checked within the pattern's byte array.
	 */
	private int checkPos = 0;
	
	/**
	 * Index of the pattern array that is being checked.
	 */
	private int patternNumber = -1;
	
	/**
	 * Default constructor that does nothing.
	 */
    BytePatterns() {}
    
    /**
     * Parameterized constructor that initialize the array bytes;
     * 
     * @param bytes - an array of elements with byte datatype
     */
    BytePatterns(byte[] bytes) {
    	this.bytes=bytes;
    }
    
    /**
     * 
     * @return byteArrays - the nested array list of byte arrays.
     */
	
	public ArrayList<byte[]> getByteArrays() {
		return byteArrays;
	}
	
	/**
	 * This method empties the nested array list byteArrays
	 */
	
	public void resetByteArrays() {
		byteArrays.clear();
		error = false;
	}
	
	/**
	 * This method resets the index of byte array that is being checked.
	 */
	
	void reset() {
    	patternNumber =-1;
    	checkPos=0;
    }
	
	/**
	 * Gives the size or number of elements inside the array list byteArrays.
	 * 
	 * @return byteArrays.size() - the size or number of elements inside the array list byteArrays.
	 */
	
	public int getByteArraysSize() {
		return byteArrays.size();
	}
	
	/**
	 * This method increments the index of byte array that is being checked.
	 */
	
	public void nextPattern(){
    	checkPos=0;
    	patternNumber ++;
    	if(patternNumber < getByteArraysSize()) {
    		bytes=byteArrays.get(patternNumber);
    	}
    }
	
	/**
	 * This method converts a line of two digit hex patterns to array of bytes.
	 * 
	 * @param line - a string of probable two digit hex patterns that is converted to bytes and stored in byteArrays
	 */
	
	public void parseLines(String line){
		String[] hexPatterns = line.split(" ");
		bytes = new byte[hexPatterns.length];
		for(int i = 0; i<hexPatterns.length; i++){
		   if(hexPatterns[i].length() != 2) {
			   error=true;
		   }
		   try {
			   byte byteFromHex = (byte) Integer.parseInt(hexPatterns[i],16);
			   bytes[i] = byteFromHex;
		   }
		   catch(NumberFormatException e) {
			   error = true;
		   }
		}
	   
		if(error) {
	   		JOptionPane.showMessageDialog(null, "Pattern break down for line: " + line,"Error",-1);
	   		error = false;
	   		return;
		}
		byteArrays.add(bytes);
		bytes = null;
	}
	
	/**
     * Checks if the given value matches the next byte to be checked within the pattern.
     *
     * Each time this method is called it progresses to the next byte within the pattern, until the value does not match
     * or the end of the pattern is reached (in which case a match has been found).
     *
     * @param value the value to be checked against the next byte in the pattern.
     * @return true if the pattern has been matched, false if the pattern has not (yet) matched.
     */
	
	public boolean checkNext(byte value) {
    	if(value == bytes[checkPos]) {
    		if(checkPos == bytes.length-1) {
    			checkPos = 0;
    			return true;
    		}else {
    			checkPos ++;
    			return false;
    		}
    	}else {
    		checkPos = 0;
        	return false;
    	}
    }
	
	/**
	 * This method takes index of matched array pattern as parameter and uses it to get the array from byteArrays.
	 * Each element of this array is then converted to bytes and is concatenated with the preceding ones to get
	 * a string of hex pattern.
	 * 
	 * @param index - It is the index of the matched pattern from byteArrays.
	 * @return hexPattern - It is the hex String which was converted from the array of bytes that was matched.
	 */
	
	public String getHexPattern(int index) {
		String hexPattern = "";
		for(byte Byte: byteArrays.get(index)) {
			String hex = Integer.toHexString(Byte);
			if(hex.length()>2) {
				hex = hex.substring(6);
			}
			hexPattern += hex;
		}
		return hexPattern;
	}

}

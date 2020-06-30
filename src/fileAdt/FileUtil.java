package fileAdt;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The FileUtil class contains all the major methods that contributes to the functionality of this application.
 * 
 * This has methods for exiting from application, choosing file/directory, loading bytes of the chosen file into array list, 
 * choosing and loading pattern file and finally the method to check for matched patterns and display to window via JTextPane.
 * 
 * @author Samyak Maharjan ID: 77202779
 *
 * @param <T> -  Abstract Data Type object
 */


public class FileUtil<T> implements FileADT<T>{
	/**
	 * Array list that contains bytes of the file that is loaded. File may have any extension - .exe, .png, .jpg, .txt etc
	 */
	List<Byte> fileByteArray = new ArrayList<Byte>();
	BytePatterns bytePatterns = new BytePatterns();
	
	/**
	 * String that stores the name of the file that is being read.
	 */
	String fileName;
	
	/**
	 * String that stores the information about the patterns that were matched along with the offset at which the match was found.
	 */
	String fileInfo=" ";
	
	/**
	 * This method is used to exit or close the application. It has an action listener which is fired whenever the exit
	 * sub-menu is clicked. This function is called from method setupMenu of {@link Mainpanel} class.
	 * 
	 * @param exit - instance of JMenuitem from {@link Mainpanel}
	 */
	
	public void exitApplication(JMenuItem exit) {
		exit.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				System.exit(0);
  			}
  		});
	}
	
	/**
	 * This method allows the user to choose for file or directory using an instance of JFileChooser. Also it calls methods
	 * that stores the bytes of the chosen files to an array list which is then checked against already loaded byte pattern
	 * and the information about whether the match was found or not is displayed by calling the method displayInfo.
	 * 
	 * @param selectFileOrDir - instance of JMenuItem - to trigger action listener which uses JFileChooser to choose file or directory
	 * @param textPane -instance of JTextPane - same instance that is declared in {@link Mainpanel}, it is used to display information in GUI
	 */
	
	public void chooseFileOrDir(JMenuItem selectFileOrDir, JTextPane textPane) {
		selectFileOrDir.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				JFileChooser chooseFile = new JFileChooser();
  				chooseFile.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
				int returnVal = chooseFile.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File fileOrDir = chooseFile.getSelectedFile();
					
					/*
					 * If the fileOrDir is found to be file, scan it and load the bytes in array list.
					 * Using the method patternChecker, bytes from the loaded file is checked against already loaded pattern
					 * and information about matched pattern is displayed using the displayInfo method.
					 * 
					 */
					if(fileOrDir.isFile()) {
						fileInfo=" ";
						scanFile(fileOrDir);
						patternChecker();
						displayInfo(textPane);
						bytePatterns.reset();
					}
					
					/*
					 * If the fileOrDir is found to be a directory, store all the files inside an array of type File.
					 * Iterate this array to scan each file and load the bytes in array list.
					 * Using the method patternChecker, bytes from the loaded file is checked against already loaded pattern
					 * and information about matched pattern is displayed using the displayInfo method.
					 * For each file, reset method from the BytePatterns class is called to reinitialize the patternNumber to -1 so that the
					 * same bytes of pattern can be reused to check pattern in another file.
					 * 
					 * If the directory is empty, fileInfo is initialized with the information saying the directory is empty.
					 * .
					 */
					
					if(fileOrDir.isDirectory()) {
						File files[] = fileOrDir.listFiles();
						fileInfo=" ";
						fileInfo = "Directory: " + fileOrDir.getName() + " ("+files.length+ " files)<br>";
						if(files.length > 0) {
							for (File file : files) {
								if(file.isFile()) {
									scanFile(file);
									patternChecker();
									bytePatterns.reset();
								}
							}
						}else {
							fileInfo += "<br>Directory is empty or doesn't contain any files.";
						}
						displayInfo(textPane);
					}
				}
  			}
  		});
	}
	
	/**
	 * This method loops through the streams of bytes of the given file and store it in an array list.
	 * 
	 * @param file - an instance of File which is to be loaded into array list
	 * @return fileByteArray - the List type which has Byte type of elements stored.
	 */
	
	public List<Byte> scanFile(File file) {
		try {
			String filePath = file.getAbsolutePath();
			fileName = file.getName();
			InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
			int next;
			fileByteArray.clear();
			while ((next = inputStream.read()) != -1) {
				byte nextByte = (byte)next;
				fileByteArray.add(nextByte);
			}
			inputStream.close();
		}catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		}catch(IOException e) {
			System.out.println("IO Exception");
		}
		return fileByteArray;
	}
	
	/**
	 * This method allows to choose a text file.
	 * 
	 * @param PatternFile - instance of JMenuItem to trigger action listener which uses JFileChooser to choose 
	 * a text file of two digit hex patterns.
	 */
	
	public void choosePattern(JMenuItem PatternFile) {
		PatternFile.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				JFileChooser chooseFile = new JFileChooser();
  				 // restrict the user to select files of all types 
  				chooseFile.setAcceptAllFileFilterUsed(false); 
  	  
  	            // set a title for the dialog 
  				chooseFile.setDialogTitle("Select a .txt file"); 
  	  
  	            // only allow files of .txt extension 
  	            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt"); 
  	            chooseFile.addChoosableFileFilter(restrict); 
				int returnVal = chooseFile.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooseFile.getSelectedFile();
					loadPattern(file);
				}
  			}
  		});
	}
	
	/**
	 * This method uses Scanner class to read each line from the given file and sends this line
	 * as parameter to another method called parseLines from {@link BytePatterns} class.
	 * 
	 * @param file - instance of File
	 */
	
	public void loadPattern(File file) {
		try {
			bytePatterns.resetByteArrays();
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
			   String line = scanner.nextLine();
			   bytePatterns.parseLines(line);
			}
			scanner.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File Not Found");
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method iterates over two array lists fileByteArray and byteArrays from {@link BytePatterns} 
	 * and calls checkNext method to compare the bytes from these two array lists. If match is found, hex pattern 
	 * for the matched pattern is calculated along with the offset. These information are then concatenated to a 
	 * variable fileInfo. 
	 * When comparison of fileByteArray finishes for an array element inside byteArrays, nextPattern function is called
	 * to move to next element/pattern.
	 */
	
	public void patternChecker() {
		int patternNumber = bytePatterns.getByteArraysSize();
		if(bytePatterns.getByteArraysSize() > 0) {
			boolean patternsFound = false;
			fileInfo += "<br>File Name: "+ fileName + "<br>";
			bytePatterns.nextPattern();
			for(int i=0; i<patternNumber; i++) {
				for(int j=0; j<fileByteArray.size(); j++) {
					if(bytePatterns.checkNext(fileByteArray.get(j))) {
						patternsFound = true;
						String hexPattern = bytePatterns.getHexPattern(i);
						
						/*
						 * Offset is calculated using the difference between the index from fileByteArray where the matching finished
						 * and half the length of matched hex pattern minus one.
						 */
						
						int offset = j-((bytePatterns.getHexPattern(i).length()/2)-1);
						fileInfo += "Pattern found: " + hexPattern + " at offset: " + offset + " (0x" +Integer.toHexString(offset)+") within the file.<br>";
					}
				}
				bytePatterns.nextPattern();
			}
			if(!patternsFound) {
				fileInfo += "No Patterns Found.<br>";
			}
		}else {
			fileInfo = "You need to load pattern file first.";
		}
	}
	
	/**
	 * This method displays all the information stored in fileInfo variable using textPane.
	 * 
	 * @param textPane - instance of JTextPane from {@link Mainpanel}
	 */
	
	public void displayInfo(JTextPane textPane) {
		textPane.setEditable(false);
		if(!fileInfo.equals("")) {
			textPane.setText(""
					+"<html>"
		  				+ "<body style=\"font-size: 14px; background-color: #DCDCDC;\">"
		  					+"<div>"
		  						+ "<p style=\"text-align: left; padding: 15px\">" + fileInfo+ "<p>"
		  					+"</div>"
		  				+ "</body>"
	  				+ "</html>");
			
		}
	}
}

package fileAdt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;

/**
 * This is an interface with all the method declarations that needs to be overriden
 * by the class implementing it.
 * 
 * @author Samyak Maharjan ID: 77202779
 *
 * @param <T> - Abstract Data Type object
 */
public interface FileADT<T> {
	/**
	 * Method declaration that houses code for exiting the application.
	 * @param exit - an instance of JMenuItem which is used to fire the click event on respective menu item.
	 */
	public void exitApplication(JMenuItem exit);
	
	/**
	 * Method declaration that has code which allows the user to choose a file or files from a directory
	 * @param selectFileOrDir - an instance of JMenuItem
	 * @param textPane - an instance of JTextPane
	 */
	public void chooseFileOrDir(JMenuItem selectFileOrDir, JTextPane textPane);
	
	/**
	 * Method declaration that has code to scan streams from the file, convert each stream to byte
	 * and push this byte to an array list.
	 * 
	 * @param file - an instance of File
	 * @return list of elements with data type of byte
	 */
	public List<Byte> scanFile(File file);
	
	/**
	 * Method declaration that has code to choose a text file.
	 * 
	 * @param PatternFile - instance of JMenuItem to trigger action listener which uses JFileChooser to choose 
	 * a text file of two digit hex patterns.
	 */
	public void choosePattern(JMenuItem PatternFile);
	
	/**
	 * Method declaration that has code to check array of bytes(pattern) against the loaded files (.txt, .png, .exe).
	 */
	public void patternChecker();
	
	/**
	 * Method declaration that has code to display all information in GUI.
	 * @param textPane - an instance of JTextPane that is used to display all information in GUI.
	 */
	public void displayInfo(JTextPane textPane);
}

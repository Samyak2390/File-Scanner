package fileAdt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * This is the driver class that is executed first and is linked 
 * with all other required classes to form this whole application.
 * @author Samyak Maharjan ID: 77202779
 *
 */
public class FileScanner{
	/**
	 * This is the main method that is executed first. It sets up the required GUI by running {@link Mainpanel} methods
	 * and triggers to run other classes like {@link FileUtil} and {@link BytePatterns}.
	 * 
	 * @param args - supplied command-line arguments as an array of String objects
	 */
	public static void main(String[] args) {
	    JFrame frame = new JFrame("File Scanner");
	    //Add icon to the window
	    Image icon = Toolkit.getDefaultToolkit().getImage("icons/scanner.png");    
	    frame.setIconImage(icon);    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    Mainpanel panel = new Mainpanel();
	    frame.getContentPane().add(panel, BorderLayout.CENTER);
	    frame.setPreferredSize(new Dimension(600, 700));
		panel.setLayout(null);
	    frame.setJMenuBar(panel.setupMenu());
	    frame.setContentPane(panel.setupTextPane());
	    
	    frame.pack();
	    frame.setVisible(true);
	    
	    //Center the window to screen
	    frame.setLocationRelativeTo(null);
	}
}

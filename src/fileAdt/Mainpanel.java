package fileAdt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

/**
 * This class is associated with the {@link FileScanner} and extends Jpanel.<br>
 * {@link FileScanner} has an instance of this class to setup menu, text panel and about window. 
 * <br><br>
 * This class also has instances of JScrollPane, JTextPane and {@link FileUtil} which are used to display the information after scanning file.
 * {@link FileUtil} has all the logic from scanning the file to checking patterns of the files.
 * @author Samyak Maharjan ID: 77202779
 *
 */

public class Mainpanel extends JPanel {
	Mainpanel() {
	}
	
	FileUtil fileUtil = new FileUtil();
	JTextPane textPane = new JTextPane();
	JScrollPane scrollable = new JScrollPane(textPane);
	
	/**
	 * This method uses instances of JMenuBar and JMenuItem to place menu on the window.<br>
	 * Menu consists of File which has sub menus like Select File/Directory, Load Pattern, About and Exit.<br>
	 * This method also uses instance of {@link FileUtil} to call its methods, which provides functionality for 
	 * exiting from the application, selecting file or directory, pattern file etc.
	 * 
	 * @return the instance of JMenuBar which is used in {@link FileScanner} to setup menu.
	 */
	JMenuBar setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        
        menuBar.add(file);
        
  		JMenuItem exit = new JMenuItem("Exit");
  		//fileItem.setIcon(new ImageIcon("icons/exit.png"));
  		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
  		fileUtil.exitApplication(exit);
  		
  		JMenuItem selectFileOrDir = new JMenuItem("Select File/Directory");
  		selectFileOrDir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
  		fileUtil.chooseFileOrDir(selectFileOrDir, textPane);
  		
  		JMenuItem PatternFile = new JMenuItem("Load Pattern File");
  		PatternFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
  		fileUtil.choosePattern(PatternFile);
  		
  		JMenuItem about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        about.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				aboutDialogueBox();
  			}
  		});
  		
  		file.add(selectFileOrDir);
  		file.add(PatternFile);
  		file.add(about);
  		file.add(exit);
		
		return menuBar;
	}
	
	/**
	 * This method uses the instance of JTextPane to write html which is showed as default text when the application first
	 * loads.
	 * 
	 * @return the instance of JScrollPane which is used to set default text that is shown on the GUI when 
	 * the application is loaded for the first time.
	 */
	
	JScrollPane setupTextPane(){
		textPane.setEditable(false);
  		textPane.setContentType("text/html");
  		textPane.setText(""
				+"<html>"
	  				+ "<body style=\"font-size: 14px; background-color: #DCDCDC\">"
	  					+ "<p style=\"text-align: center\">"
	  						+ "Start by loading Pattern File. <br>"
	  						+ "File > Load Pattern File OR Ctrl + P"
	  					+ "<p>"
	  				+ "</body>"
  				+ "</html>");
  		
        return scrollable;
	}
	
	/**
	 * This method uses instances of JFrame, JPanel and JLabel which are used along with JOptionPane to construct an 
	 * About dialogue box. The about dialogue box displays the information about this application - available features, author etc.
	 */
	
	private void aboutDialogueBox(){
		JFrame aboutFrame = new JFrame();
		JPanel aboutPanel = new JPanel();
		
		JLabel info = new JLabel(""
				+ "<html>"
					+"<h2 style=\"text-align: center;\">File Scanner</h2>"
					+ "<p style=\"font-size: 12px;\">"
						+ "* This application allows a user to load a pattern text file having one or more <br> lines of two digit hexadecimal codes seperated by spaces."
						+ "Invalid lines are<br> reported as well. <br>"
						+ "<br>* Also, user can select either a file or directory and check if there is match of <br>patterns with hexadecimal codes of already loaded pattern file. <br>"
						+ "<br>* Then the information about whether the pattern match was found or not for <br>each file is displayed in GUI."
					+ "</p>"
					+ "<p style=\"text-align: center; font-size: 12px;\">Author : Samyak Maharjan<br/>@ Copyright 2020</p>"
				+ "</html>");
		
		aboutPanel.add(info);
		JOptionPane.showMessageDialog(aboutFrame,aboutPanel);
	}
}

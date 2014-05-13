/**
 * Description: class to handle upload and download through button 
 * listener interface.
 * @author Hardi Bhatt
 */
package ftp.gui.component;

import javax.swing.JTextField;

import ftp.gui.component.FileTable;

public interface CommandInterface {
	 public void processEvent(FileTable locTable, FileTable remTable, JTextField locText, JTextField remText);
}

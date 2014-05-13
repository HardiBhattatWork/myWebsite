/**
 * Description: Listener for handling button clicks on both tables
 * @author Hardi
 */
package ftp.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import ftp.gui.component.CommandInterface;
import ftp.gui.component.FileTable;

public class ButtonHandler implements ActionListener {
	// remote file display panel
	private FileTable remPanel;
	private JTextField remText;
	// local file display panel
	private FileTable locPanel;
	private JTextField locText;

	/**
	 * default constructor
	 * @param locTable
	 * @param remTable
	 * @param locText
	 * @param remText
	 */
	public ButtonHandler(FileTable locTable, FileTable remTable, JTextField locText, JTextField remText) {
		this.locPanel = locTable;
		this.remPanel = remTable;
		this.locText = locText;
		this.remText = remText;
	}

	/**
	 * Performing action on button click
	 */
	public void actionPerformed(ActionEvent e) {
		CommandInterface CommandObj = (CommandInterface) e.getSource();
		CommandObj.processEvent(locPanel, remPanel, locText, remText);
	}
}


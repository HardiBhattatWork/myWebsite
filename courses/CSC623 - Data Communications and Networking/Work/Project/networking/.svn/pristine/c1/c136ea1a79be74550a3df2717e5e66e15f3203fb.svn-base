/**
 * Description: Function to handle the left button clicked
 * @author Hardi Bhatt
 */
package ftp.gui.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import ftp.client.FtpCmdSession;
import ftp.client.FtpConnector;
import ftp.config.*;
import ftp.gui.component.FileTable;
import ftp.message.FtpLogger;
import ftp.message.JTextAreaOutput;

public class UploadButton extends JButton implements Settings, CommandInterface {

	private JTextAreaOutput log = FtpLogger.getLog();

	/**
	 * Post event to initiate the download once the session is connected
	 */
	@Override
	public void processEvent(FileTable locTable, FileTable remTable,
			JTextField locText, JTextField remText) {
		String sourceDir = locText.getText();
		FtpConnector conn = FtpCmdSession.getConnSession();
		if (conn == null) {
			log.error("You should connect first");
		} else {
			try {

				int rowCount = locTable.getSelectedRowCount();
				for (int i = 0; i < rowCount; i++) {
					int[] rows = locTable.getSelectedRows();
					String sourceFile = (String) locTable
							.getValueAt(rows[i], 1);
					if (locTable.getValueAt(rows[i], 3) == "folder"
							|| locTable.getValueAt(rows[i], 3) == "") {
						log.error("Cannot perform upload against a directory");
					} else {
						conn.upload(sourceFile, sourceDir);
						Thread.sleep(500);
					}

				}

			} catch (IndexOutOfBoundsException e) {
				System.out.println("Please select a directory ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Simple constructor
	 * @param imageIcon
	 */
	public UploadButton(ImageIcon imageIcon) {
		super(imageIcon);
	}
}

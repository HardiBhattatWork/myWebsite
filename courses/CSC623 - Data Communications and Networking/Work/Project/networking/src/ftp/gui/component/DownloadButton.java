/**
 * Description: Function to handle the right button clicked
 * action listener for download.
 * @author Hardi Bhatt
 */
package ftp.gui.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import ftp.client.FtpCmdSession;
import ftp.client.FtpConnector;
import ftp.config.Settings;
import ftp.gui.component.FileTable;
import ftp.message.FtpLogger;
import ftp.message.JTextAreaOutput;

public class DownloadButton extends JButton implements CommandInterface,
		Settings {

	private JTextAreaOutput log = FtpLogger.getLog();

	/**
	 * Post event to initiate the download once the session is connected
	 */
	@Override
	public void processEvent(FileTable locTable, FileTable remTable,
			JTextField locText, JTextField remText) {
		String destDir = locText.getText();
		FtpConnector conn = FtpCmdSession.getConnSession();
		if (conn == null) {
			log.error("You should connect first");
		} else {
			try {

				int rowCount = remTable.getSelectedRowCount();
				for (int i = 0; i < rowCount; i++) {
					int[] rows = remTable.getSelectedRows();
					String sourceFile = (String) remTable
							.getValueAt(rows[i], 1);
					if (remTable.getValueAt(rows[i], 3) == "folder"
							|| remTable.getValueAt(rows[i], 3) == "") {
						log.error("Cannot perform download against a directory");
					} else {
						conn.download(sourceFile, destDir);
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
	public DownloadButton(ImageIcon imageIcon) {
		super(imageIcon);
	}
}

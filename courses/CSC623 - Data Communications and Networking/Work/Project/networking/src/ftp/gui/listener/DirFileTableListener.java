/**
 * Description: Class action listener for drag and drop on mouse motion
 * @author Hardi Bhatt
 */
package ftp.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import ftp.client.FtpCmdSession;
import ftp.client.FtpConnector;
import ftp.file.LocalDirectory;
import ftp.file.RemoteDirectory;
import ftp.gui.component.FileTable;
import ftp.message.FtpLogger;
import ftp.message.JTextAreaOutput;

public class DirFileTableListener implements MouseMotionListener, MouseListener {

	private JTextAreaOutput log = FtpLogger.getLog();
	private FileTable locTable;
	private FileTable remTable;
	private JTextField locText;
	private JTextField remText;
	private HashMap<String, String> fileInfo;
	private boolean isLocalDragged;
	private boolean isRemoteDragged;
	private boolean readyForRelease;

	/**
	 * constructor to handle which file is being dragged
	 * 
	 * @param locTable
	 * @param remTable
	 * @param locText
	 * @param remText
	 */
	public DirFileTableListener(FileTable locTable, FileTable remTable,
			JTextField locText, JTextField remText) {
		this.locTable = locTable;
		this.remTable = remTable;
		this.locText = locText;
		this.remText = remText;
		fileInfo = new HashMap<String, String>();
	}

	// @Override
	public void mouseDragged(MouseEvent e) {
		if (e.getSource() == locTable) {
			isLocalDragged = true;
			isRemoteDragged = false;
		} else if (e.getSource() == remTable) {
			isLocalDragged = false;
			isRemoteDragged = true;
		}
		fileInfo.clear();
		if (isLocalDragged) {
			int rowCount = locTable.getSelectedRowCount();
			for (int i = 0; i < rowCount; i++) {
				int[] rows = locTable.getSelectedRows();
				String sourceFile = (String) locTable.getValueAt(rows[i], 1);
				if (locTable.getValueAt(rows[i], 3) == "folder"
						|| locTable.getValueAt(rows[i], 3) == "") {
					fileInfo.put(sourceFile, "folder");
				} else {
					fileInfo.put(sourceFile, "file");
				}
			}
		} else if (isRemoteDragged) {
			int rowCount = remTable.getSelectedRowCount();
			for (int i = 0; i < rowCount; i++) {
				int[] rows = remTable.getSelectedRows();
				String sourceFile = (String) remTable.getValueAt(rows[i], 1);
				if (remTable.getValueAt(rows[i], 3) == "folder"
						|| remTable.getValueAt(rows[i], 3) == "") {
					fileInfo.put(sourceFile, "folder");

				} else {
					fileInfo.put(sourceFile, "file");
				}
			}
		}

	}

	/**
	 * action listener for double clicking the directory on the table the
	 * current working directory changes
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (e.getSource() == locTable) {
				String curDir = locText.getText();
				int row = locTable.rowAtPoint(e.getPoint());
				curDir = LocalDirectory.winFormat(curDir);
				String newDir = curDir + (String) locTable.getValueAt(row, 1);
				curDir = LocalDirectory.cwd(newDir);
			} else if (e.getSource() == remTable) {
				String curDir = remText.getText();
				int row = remTable.rowAtPoint(e.getPoint());
				curDir = LocalDirectory.unixFormat(curDir);
				String newDir = curDir + (String) remTable.getValueAt(row, 1);
				curDir = RemoteDirectory.cwd(newDir);
			}
		}
	}

	/**
	 * Override function to handle mouse button event
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (isLocalDragged) {
			if (e.getSource() == remTable) {
				readyForRelease = true;
			} else {
				readyForRelease = false;
			}
		} else if (isRemoteDragged) {
			if (e.getSource() == locTable) {
				readyForRelease = true;
			} else {
				readyForRelease = false;

			}
		}

	}

	public void ChangeName(FileTable table, int col_index, String col_name) {
		table.getColumnModel().getColumn(col_index).setHeaderValue(col_name);
	}

	private JPopupMenu PopUp(FileTable table) {
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem renameItem = new JMenuItem("Rename");
		renameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e);
//				ChangeName(table,0,"");
			}
		});
		popup.add(renameItem);

		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Menu item Delete");
//				table.removeColumn(table.getColumnModel().getColumn(0));
			}
		});
		popup.add(deleteItem);

		return popup;
	}

	/**
	 * Function to handle mouse release and transfer of data when there is a
	 * drag action
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			int row = locTable.rowAtPoint(e.getPoint());
			if (row >= 0 && row < locTable.getRowCount()) {
				locTable.setRowSelectionInterval(row, row);
			} else {
				locTable.clearSelection();
			}
			int rowindex = locTable.getSelectedRow();
			if (rowindex < 0)
				return;

			if (e.isPopupTrigger()) {
				JPopupMenu popup = PopUp(locTable);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
		if (readyForRelease) {
			if (isLocalDragged) {
				// perform upload
				FtpConnector conn = FtpCmdSession.getConnSession();
				if (conn == null) {
					log.error("You should connect first");
				} else {
					String sourceDir = locText.getText();
					Iterator<Entry<String, String>> iter = fileInfo.entrySet()
							.iterator();
					while (iter.hasNext()) {
						Entry<String, String> next = iter.next();
						if (next.getValue().equals("folder")) {
							log.error("Cannot perform upload against a directory");
						} else {
							conn.upload(next.getKey(), sourceDir);
						}
					}
				}
			} else if (isRemoteDragged) {
				// perform download
				FtpConnector conn = FtpCmdSession.getConnSession();
				if (conn == null) {
					log.error("You should connect first");
				} else {
					String destDir = locText.getText();
					Iterator<Entry<String, String>> iter = fileInfo.entrySet()
							.iterator();
					while (iter.hasNext()) {
						Entry<String, String> next = iter.next();
						if (next.getValue().equals("folder")) {
							log.error("Cannot perform download against a directory");
						} else {
							conn.download(next.getKey(), destDir);
							try {
								Thread.sleep(800);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}

		}
		readyForRelease = false;
		fileInfo.clear();
	}

	// not support
	public void mouseExited(MouseEvent e) {

	}

	// not support
	public void mousePressed(MouseEvent e) {

	}

	// not support
	public void mouseMoved(MouseEvent e) {

	}
}

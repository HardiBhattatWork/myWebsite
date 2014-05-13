/**
 * Description: Initial GUI code which generates a GUI window on the client 
 * Machine for uploading and downloading code. Details below.
 * @author Hardi Bhatt
 */
package ftp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;

import ftp.config.Settings;
import ftp.file.LocalDirectory;
import ftp.file.RemoteDirectory;
import ftp.file.UnhideFileFilter;
import ftp.gui.component.DownloadButton;
import ftp.gui.component.FileTable;
import ftp.gui.component.FileTableModel;
import ftp.gui.component.UploadButton;
import ftp.gui.listener.ButtonHandler;
import ftp.gui.listener.ConnectListener;
import ftp.gui.listener.DirTextFieldListener;
import ftp.gui.listener.DirFileTableListener;
import ftp.message.FtpLogger;

public class FtpClientGui extends JFrame implements Settings {

	// connect panel
	private JPanel connPanel;
	private JLabel hostLabel;
	private JTextField hostText;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel pwdLabel;
	private JPasswordField pwdText;
	private JLabel portLabel;
	private JTextField portText;
	private JButton connBtn;

	// local file display panel
	private JPanel locPanel;
	private JLabel locLabel;
	private JTextField locText;
	private FileTableModel locModel;
	private FileTable locTable;

	private UploadButton btnUpload;
	private DownloadButton btnDownload;

	// remote file display panel
	private JPanel remPanel;
	private JLabel remLabel;
	private JTextField remText;
	private FileTableModel remModel;
	private FileTable remTable;

	// file display panel which includes local/remote file
	// display panels and file transfer mode panel.
	private JPanel filePanel;

	// status display panel
	private JPanel statPanel;
	private JTextArea statText;

	/**
	 * Initial Constructor for building the Client GUI
	 */
	public FtpClientGui() {
		setTitle("FTP Client");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// state panel
		buildStatPanel();
		add(statPanel, BorderLayout.SOUTH);

		// file display panel which includes local file,
		// remote file display panels and transfer mode panel
		buildFilePanel();
		add(filePanel, BorderLayout.CENTER);

		// server connection panel
		buildConnPanel();
		add(connPanel, BorderLayout.NORTH);

		// add MouseListener and MouseMotionListener
		// for local and remote file table
		DirFileTableListener listener = new DirFileTableListener(locTable,
				remTable, locText, remText);
		locTable.addMouseMotionListener(listener);
		remTable.addMouseMotionListener(listener);
		locTable.addMouseListener(listener);
		remTable.addMouseListener(listener);

		pack();
		setVisible(true);

	}
	
	/**
	 * building a connection panel section on the GUI IFrame
	 * with pre-loaded code for testing purposes
	 */
	private void buildConnPanel() {
		hostLabel = new JLabel("Host:");
		hostText = new JTextField(10);
//		hostText.setText("maxwell.sju.edu");
		hostText.setText("192.168.2.2");
//		hostText.setText("127.0.0.1");
		userLabel = new JLabel("Username:");
		userText = new JTextField(10);
		userText.setText("admin");
		pwdLabel = new JLabel("Password:");
		pwdText = new JPasswordField(10);
		pwdText.setText("zhu88jie");
		portLabel = new JLabel("Port:");
		portText = new JTextField(5);
		portText.setText("35086");
		connBtn = new JButton("Connect");
		connBtn.addActionListener(new ConnectListener(hostText, portText,
				userText, pwdText, remTable, remText));
		connPanel = new JPanel();

		connPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		connPanel.add(hostLabel);
		connPanel.add(hostText);
		connPanel.add(userLabel);
		connPanel.add(userText);
		connPanel.add(pwdLabel);
		connPanel.add(pwdText);
		connPanel.add(portLabel);
		connPanel.add(portText);
		connPanel.add(connBtn);
		setBorder(connPanel);
	}

	/**
	 * Build file Panel function for building the entire GUI
	 */
	private void buildFilePanel() {
		filePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		addLocPanel(gbc);
		addRemPanel(gbc);
		addLeftBtn(gbc);
		addRightBtn(gbc);



	}


	/**
	 * Creating a right button and applying a listener for uploading data
	 * @param gbc
	 */
	private void addRightBtn(GridBagConstraints gbc) {
		btnUpload = new UploadButton(new ImageIcon(RIGHT_IMG));
		btnUpload.setMnemonic(KeyEvent.VK_U);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 0.0;
		gbc.weighty = 0.5;
		gbc.insets = new Insets(2, 4, 0, 4);
		filePanel.add(btnUpload, gbc);

		ButtonHandler vf = new ButtonHandler(locTable, remTable, locText,
				remText);
		btnUpload.addActionListener(vf);
	}

	/**
	 * Creating a left button and applying applying a listener for downloading data
	 * @param gbc
	 */
	private void addLeftBtn(GridBagConstraints gbc) {
		btnDownload = new DownloadButton(new ImageIcon(LEFT_IMG));
		btnDownload.setMnemonic(KeyEvent.VK_N);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.weightx = 0.0;
		gbc.weighty = 0.5;
		gbc.insets = new Insets(0, 4, 2, 4);
		filePanel.add(btnDownload, gbc);

		ButtonHandler vf = new ButtonHandler(locTable, remTable, locText,
				remText);
		btnDownload.addActionListener(vf);
	}

	/**
	 * Creating a Remote Server Panel to view Remote Server directories and files
	 * @param gbc
	 */
	private void addRemPanel(GridBagConstraints gbc) {
		buildRemPanel();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		filePanel.add(remPanel, gbc);
	}

	/**
	 * Creating a Local Client Panel to view Local Client directories and files
	 * @param gbc
	 */
	private void addLocPanel(GridBagConstraints gbc) {
		buildLocPanel();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		filePanel.add(locPanel, gbc);
	}

	/**
	 * Building the Local panel by populating the panel with local system properties
	 */
	private void buildLocPanel() {
		locPanel = new JPanel(new BorderLayout());
		locLabel = new JLabel("Local site:");
		locText = new JTextField(36);
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		subPanel.add(locLabel);
		subPanel.add(locText);

		Object[][] data = null;
		locModel = new FileTableModel(data, COLUMNS);
		// gets the user's home directory
		String dftDir = System.getProperty("user.home");
		dftDir = LocalDirectory.winFormat(dftDir);
		locText.setText(dftDir);
		File dftFile = new File(dftDir);
		if (dftFile.exists()) {
			locModel.resetTableModel(dftFile.listFiles(new UnhideFileFilter()));
		}
		locTable = new FileTable(locModel);
		initColumnSizes(locTable);
		JScrollPane scrollPane = new JScrollPane(locTable);
		locTable.setFillsViewportHeight(true);
		locPanel.add(subPanel, BorderLayout.NORTH);
		locPanel.add(scrollPane, BorderLayout.CENTER);
		setBorder(locPanel);
		LocalDirectory.setCurDir(locText);
		LocalDirectory.setFileTable(locTable);
		
		// add ActionListener so that an action is triggered once the Enter is
		// pressed in locText JTextField
		locText.addActionListener(new DirTextFieldListener(true));

	}
	/**
	 * Building the Remote panel by populating the panel with remote system properties
	 */
	private void buildRemPanel() {
		remPanel = new JPanel(new BorderLayout());
		remLabel = new JLabel("Remot site:");
		remText = new JTextField(36);
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		subPanel.add(remLabel);
		subPanel.add(remText);

		remModel = new FileTableModel(0, COLUMNS);
		remTable = new FileTable(remModel);
		initColumnSizes(remTable);
		JScrollPane scrollPane = new JScrollPane(remTable);
		remTable.setFillsViewportHeight(true);

		remPanel.add(subPanel, BorderLayout.NORTH);
		remPanel.add(scrollPane, BorderLayout.CENTER);
		setBorder(remPanel);

		RemoteDirectory.setCurDir(remText);
		RemoteDirectory.setFileTable(remTable);
		
		// add ActionListener so that an action is triggered once the Enter is
		// pressed in locText JTextField
		remText.addActionListener(new DirTextFieldListener(false));

	}

	/**
	 * setting up the column size of the given table
	 * @param table
	 */
	private void initColumnSizes(JTable table) {
		TableColumn imgColumn = table.getColumnModel().getColumn(0);
		imgColumn.setMaxWidth(20);
		TableColumn nameColumn = table.getColumnModel().getColumn(1);
		nameColumn.setPreferredWidth(120);
		TableColumn sizeColumn = table.getColumnModel().getColumn(2);
		sizeColumn.setPreferredWidth(60);
		TableColumn typeColumn = table.getColumnModel().getColumn(3);
		typeColumn.setPreferredWidth(50);
		TableColumn dateColumn = table.getColumnModel().getColumn(3);
		dateColumn.setPreferredWidth(50);
	}

	/**
	 * Building a statPanel to dump the communication between client and server
	 */
	private void buildStatPanel() {

		statPanel = new JPanel(new BorderLayout());
		statText = new JTextArea(6, 90);
		statText.setEditable(false);
		FtpLogger.initLog(statText);
		JScrollPane scroll = new JScrollPane(statText);
		statPanel.add(scroll);
		setBorder(statPanel);
	}

	/**
	 * creating a panel border for ecstatic purposes
	 * @param comp
	 */
	private void setBorder(JComponent comp) {
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		comp.setBorder(BorderFactory.createCompoundBorder(raisedbevel,
				loweredbevel));
	}

	/**
	 * main file to run the client
	 * @param args
	 */
	public static void main(String[] args) {
		new FtpClientGui();
	}
}

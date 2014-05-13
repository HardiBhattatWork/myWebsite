/**
 * Description: This class is used to send and receive data 
 * from ftp server through data socket
 * @author Guojun Zhang
 */
package ftp.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ftp.config.Settings;
import ftp.file.RemoteDirectory;
import ftp.io.DataSocketOutputStream;
import ftp.message.FtpLogger;
import ftp.message.JTextAreaOutput;

public class DataSender extends Thread implements Settings {
	private JTextAreaOutput log = FtpLogger.getLog();
	private String sendFile;
	private static Object lock = new Object();

	/**
	 * constructor for the file that need to be sent
	 * @param sendFile
	 */
	public DataSender(String sendFile) {
		this.sendFile = sendFile;

	}

	/**
	 * function to synchronize the connection between the client and server 
	 * through socket and write to the server the data that needs to be
	 * transfered. Once the data is transfered it flushes and closes the 
	 * connection stream
	 */
	public void run() {
		try {
			Socket dSocket = null;
			synchronized (lock) {
				ServerSocket sSocket = new ServerSocket(DATASOCKET_PORT);
				dSocket = sSocket.accept();
				sSocket.close();
			}
			DataSocketOutputStream dataOut = new DataSocketOutputStream(dSocket
					.getOutputStream());
			FileInputStream fileReader = new FileInputStream(sendFile);
			byte[] data = new byte[BLOCK_SIZE];
			int size = -1;
			while ((size = fileReader.read(data)) > 0) {
				dataOut.write(data, 0, size);
			}
			dataOut.flush();
			dataOut.close();

			log
			.status("The file " + sendFile
					+ " is uploaded to the server");
			RemoteDirectory.refresh();

		} catch (IOException e) {
			log.error("exception occurs in data socket connection.");
		}

	}

}

/**
 * Description: This class maintain is used to start a thread
 * to receive data from ftp server through data socket
 * @author Guojun Zhang
 **/
package ftp.io;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import ftp.config.Settings;
import ftp.message.FtpLogger;
import ftp.message.JTextAreaOutput;

public class DataAccepter extends Thread implements Settings {

	private JTextAreaOutput log = FtpLogger.getLog();
	private Socket socket;
	private DataSocketObjectInputStream objIn;
	private ArrayList<File> files;
	private boolean running;

	/**
	 * Default constructor
	 */
	public DataAccepter() {
		running = true;
	}

	/**
	 * Functions that accepts and parses data through the data socket port.
	 */
	public void run() {
		try {
			ServerSocket sSocket = new ServerSocket(DATASOCKET_PORT);

			socket = sSocket.accept();
			sSocket.close();
			objIn = new DataSocketObjectInputStream(new ObjectInputStream(
					socket.getInputStream()));

			readFile();

			while (running) {
				Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * function to stop the thread
	 */
	public void stopThread() {
		running = false;
	}

	/**
	 * function that return a given file from the thread
	 * @return
	 */
	public ArrayList<File> getFiles() {
		return files;
	}

	/**
	 * reads file information from data socket input stream
	 * file information is transmitted through object stream
	 */
	private void readFile() throws IOException, ClassNotFoundException {

		files = new ArrayList<File>();
		File file;
		try {
			while ((file = (File) objIn.readObject()) != null){
				files.add(file);
			}
		} catch (EOFException e) {

		}
	}

}

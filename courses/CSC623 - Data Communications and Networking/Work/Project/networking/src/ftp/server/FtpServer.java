/**
 * Description: This class starts main thread for ftp server.
 * makes it starting to listening on CMDSOCKET_PORT
 * @author: Yikuan Zheng and Jing Zhao
 **/
package ftp.server;

import java.io.*;
import java.net.*;

import ftp.config.Settings;

public class FtpServer implements Settings {

	/**
	 * start server given the connection port
	 */
	public void startServer() {
		try {
			ServerSocket listener = new ServerSocket(CMDSOCKET_PORT);

			while (true) {
				Socket s = listener.accept();
				new ServerWorker(s);
			}
		} catch (IOException ioe) {
			System.out.println("ServerSocket error: " + ioe);
		}
	}

	/**
	 * main for starting the server demon.
	 * @param args
	 */
	public static void main(String[] args) {
		new FtpServer().startServer();


	}
}

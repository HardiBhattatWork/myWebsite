/**
 * Description: class to accept the data being transfered to server
 * @author Jing Zhao
 */
package ftp.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import ftp.config.Settings;
import ftp.io.CmdSocketWriter;
import ftp.io.DataSocketInputStream;

public class DataAccepter extends Thread implements Settings{
	private File pathName;
	private Socket dataSocket;
	private CmdSocketWriter cmdOut;

	/**
	 * constructor
	 * @param pathName
	 * @param dataSocket
	 * @param cmdOut
	 */
	public DataAccepter(File pathName, Socket dataSocket, CmdSocketWriter cmdOut){
		this.pathName = pathName;
		this.dataSocket = dataSocket;
		this.cmdOut = cmdOut;
	}
	
	/**
	 * function to upload files onto server
	 */
	public void run(){
		try {
			DataSocketInputStream dataIn = new DataSocketInputStream(
					dataSocket.getInputStream());
			
			FileOutputStream fileIn;
			if (pathName != null) {
				fileIn = new FileOutputStream(pathName);
				int size = -1;
				byte[] buffer = new byte[BLOCK_SIZE];
				while ((size = dataIn.read(buffer)) != -1) {
					fileIn.write(buffer, 0, size);
				}
				fileIn.flush();
				fileIn.close();
				dataSocket.close();
				cmdOut.write("260 uploaded file is stored.");
			}else {
				cmdOut.write("451 failed to store uploaded file.");
			}
			
		} catch (IOException e) {
			cmdOut.write("425 Can't open data connection.");
			e.printStackTrace();
		} 
		
	}

}

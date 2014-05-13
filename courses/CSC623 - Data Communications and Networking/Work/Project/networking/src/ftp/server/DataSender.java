/**
 * Description: Class to handle sending data through dataSocket
 * @author Jing Zhao
 */
package ftp.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import ftp.config.Settings;
import ftp.io.CmdSocketWriter;
import ftp.io.DataSocketOutputStream;

public class DataSender extends Thread implements Settings{
	private File sendFile;
	private Socket dataSocket;
	private CmdSocketWriter out;
	
	/**
	 * constructor to assign file, socket and write stream
	 * @param sendFile
	 * @param dataSocket
	 * @param cmdOut
	 */
	public DataSender(File sendFile, Socket dataSocket, CmdSocketWriter cmdOut){
		this.sendFile = sendFile;
		this.dataSocket = dataSocket;
		this.out = cmdOut;
		
	}
	
	/**
	 * function to write the data onto the stream flush and close the connection
	 */
	public void run(){
		try {
	
			DataSocketOutputStream dataOut = new DataSocketOutputStream(
					dataSocket.getOutputStream());
			FileInputStream fileReader = new FileInputStream(sendFile);
			byte[] data = new byte[BLOCK_SIZE];
			int size = -1;
			while((size = fileReader.read(data)) > 0){
				dataOut.write(data, 0, size);
			}
			dataOut.flush();
			dataOut.close();
			dataSocket.close();
			
		} catch (IOException e) {
			out.write("425 Can't open data connection.");
		}

	}

}

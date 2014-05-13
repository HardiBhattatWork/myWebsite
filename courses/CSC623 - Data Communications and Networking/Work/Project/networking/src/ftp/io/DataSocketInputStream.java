/**
 * Description: This class ftp server to manage the socket data connection
 * @author: Jing Zhao
 */
package ftp.io;

import java.io.IOException;
import java.io.InputStream;

import ftp.config.Settings;

public class DataSocketInputStream implements Settings{

	private InputStream dataIn;

	/**
	 * Constructor to set dataIn
	 * @param in
	 */
	public DataSocketInputStream(InputStream in){
		this.dataIn = in;
	}
	
	/**
	 * read data in from socket
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public int read() throws IOException, ClassNotFoundException {
		return dataIn.read();
	}
	
	public int read(byte[] buffer) throws IOException{
		return dataIn.read(buffer);
	}

}

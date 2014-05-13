/**
 * Description: Function to handle output stream objects
 * @author Jing Zhao
 */
package ftp.io;

import java.io.IOException;
import java.io.OutputStream;

import ftp.config.Settings;

public class DataSocketOutputStream implements Settings{
	
	private OutputStream dataOut;
	
	/**
	 * constructor to assign this objects dataOut to out
	 * @param out
	 */
	public DataSocketOutputStream(OutputStream out){
		this.dataOut = out;
	}
	
	/**
	 * write the dataOut
	 * @param arg
	 * @throws IOException
	 */
	public void write(byte[] arg) throws IOException{
		dataOut.write(arg);
	}
	
	public void write(byte[] arg, int offset, int size) throws IOException{
//		byte[] buffer = DESCrypto.encrypt(arg, offset, size);
		dataOut.write(arg, offset, size);
	}
	
	
	
	public void flush() throws IOException{
		dataOut.flush();
	}
	
	/**
	 * close the dataOut session
	 * @throws IOException
	 */
	public void close() throws IOException{
		dataOut.close();
	}

}

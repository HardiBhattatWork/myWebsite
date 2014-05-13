/**
 * This class encapsulates ObjectOnputStream and is
 * used by ftp client for receiving File from server
 * @author: Jing Zhao
 **/
package ftp.io;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataSocketObjectOutputStream {
	
private ObjectOutputStream dataOut;
	
	/**
	 * constructor to assign this objects dataOut to out
	 * @param out
	 */
	public DataSocketObjectOutputStream(ObjectOutputStream out){
		this.dataOut = out;
	}
	
	/**
	 * write the dataOut object
	 * @param arg
	 * @throws IOException
	 */
	public void writeObject(Object arg) throws IOException{

		dataOut.writeObject(arg);
	}
	
	/**
	 * flush the data object from the pipe
	 * @throws IOException
	 */
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

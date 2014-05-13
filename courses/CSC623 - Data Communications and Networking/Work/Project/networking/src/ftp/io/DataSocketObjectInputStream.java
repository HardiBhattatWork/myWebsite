/**
 * Description: This class encapsulates ObjectInputStream and is
 * used by ftp server for sending File to client
 * @author: Jing Zhao
 **/
package ftp.io;

import java.io.IOException;
import java.io.ObjectInputStream;

public class DataSocketObjectInputStream {

	private ObjectInputStream dataIn;

	/**
	 * Constructor to set the data in
	 * @param in
	 */
	public DataSocketObjectInputStream(ObjectInputStream in){
		this.dataIn = in;
	}
	
	/**
	 * read the dataIn Object
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object readObject() throws IOException, ClassNotFoundException {
		return dataIn.readObject();
	}

}

/**
 * Description: This class encapsulates PrintWriter and is
 * used by ftp server for sending command response to client
 * @author: Jing Zhao
 **/
package ftp.io;

import java.io.PrintWriter;

public class CmdSocketWriter {
	
	private PrintWriter out;
	
	/**
	 * constructor to set out socket for writing
	 * @param out
	 */
	public CmdSocketWriter(PrintWriter out){
		this.out = out;
	}
	
	/**
	 * writing to out socket
	 * @param arg
	 */
	public void write(String arg){
		out.write(arg);
		out.write('*');
		out.write('\n');
		out.flush();
		
	}
	

}

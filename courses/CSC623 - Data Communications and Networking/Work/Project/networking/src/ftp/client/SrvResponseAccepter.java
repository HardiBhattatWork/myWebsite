/**
 * Description: This class is used to receive ftp server's 
 * response from command socket
 * @author Guojun Zhang
 **/
package ftp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedWriter;


public class SrvResponseAccepter extends Thread{
	
	private BufferedReader replyReader;
	private int replyCode;
	private PipedWriter pipeOut;
	
	/**
	 * constructor that accepts the servers reply 
	 * @param replyReader
	 */
	public SrvResponseAccepter(BufferedReader replyReader){
		this.replyReader = replyReader;
		pipeOut = new PipedWriter();
		new Thread(this);

	}
	
	/**
	 * returns that is given by the server through the pipe
	 * userd for multithread communication
	 * @return
	 */
	public PipedWriter getPipeOut()
	{
		return pipeOut;
	}
	
	/**
	 * getting the response code "250" to get the proper response from server
	 * @return
	 */
	public int getReplyCode(){
		return replyCode;
	}
	
	/**
	 * starts a thread to receive ftp server's response
	 * for each command which is sent by client
	 */
	public void run() {
		String line = null;
		try {
			while ((line = replyReader.readLine()) != null) {
				char[] buffer = line.toCharArray();
				//write the server's response to other thread through pipe 
				pipeOut.write(buffer, 0, buffer.length);
				pipeOut.flush();
				replyCode = Integer.parseInt(line.substring(0, 3));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

/**
 * Description: This class maintain a command socket session between
 * ftp client and server. Once the connection is established
 * a single session is created.
 * @author Guojun Zhang
 **/
package ftp.client;

public class FtpCmdSession {
	
	private static FtpConnector connSession;
	/**
	 * returns the connected socket session
	 * @return
	 */
	public static FtpConnector getConnSession(){
		return connSession;
	}
	
	/**
	 * set the socket session with given ftp connection
	 * @param conn
	 */
	public static void setConnSession(FtpConnector conn){
		connSession = conn;
	}

}

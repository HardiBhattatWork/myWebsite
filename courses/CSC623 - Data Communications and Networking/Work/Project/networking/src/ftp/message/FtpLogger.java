/**
 * Description: This class logs the ftp information onto a text area 
 * for the client the see what is happening. And for debugging purposes
 * @author Jing Zhao
 */
package ftp.message;

import javax.swing.JTextArea;

import ftp.config.Settings;

public class FtpLogger implements Settings{

	private static JTextAreaOutput log;
	
	/**
	 * getting log for the text area
	 * @return
	 */
	public static JTextAreaOutput getLog(){
		return log;
	}
	
	/**
	 * setting log as testarea
	 * @param jta
	 */
	public static void initLog(JTextArea jta){
		if(log == null){
			log = new JTextAreaOutput(jta);
		}
	}
	
	
}

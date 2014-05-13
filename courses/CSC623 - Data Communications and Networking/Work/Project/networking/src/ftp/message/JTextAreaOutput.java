/**
 * Description: Class write message into JTextArea on client gui
 * @author Jing Zhao
 **/
package ftp.message;

import java.util.Scanner;

import javax.swing.JTextArea;

import ftp.config.Settings;

public class JTextAreaOutput implements Settings{
	
	private JTextArea jta;
	
	/**
	 * setting out the constructor
	 * @param jta
	 */
	public JTextAreaOutput(JTextArea jta){
		this.jta = jta;
	}
	
	/**
	 * writing message onto jtextarea
	 * @param msg
	 */
	public void write(String msg){
		jta.append(msg);
		jta.append("\n");
	}
	
	/**
	 * writing status and message on jtextarea 
	 * @param msg
	 */
	public void status(String msg){
		write(STATUS + msg);
	}
	
	/**
	 * writing error and message on jtextarea
	 * @param msg
	 */
	public void error(String msg){
		write(ERROR + msg);
	}
	
	/**
	 * writing command and message on jtextarea
	 * @param msg
	 */
	public void command(String msg){
		write(COMMAND + msg);
	}
	
	/**
	 * writing response on jtextarea
	 * @param msg
	 */
	public void response(String msg){	
		Scanner scanner = new Scanner(msg);
		while(scanner.hasNext()){
			write(RESPONSE + scanner.nextLine());
		}
		
	}

}

/**
 * @author Hardi Bhatt
 * @coursenumber CSC 623: Data Communications and Networking
 * @assignment Assignment #5 
 * @DueDate Wednesday 11/19/2011
 * @description 
 *
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MEPClient {

	static Socket sock;
	static PrintWriter pw;
	static BufferedReader br;
	static int Port = 20000;
	static int docPort = 20010;
	static int grumpyPort = 20011;
	static int sleepyPort = 20012;
	static int sneezyPort = 20013;
	static int dopeyPort = 20014;
	static int bashfulPort = 20015;
	static int happyPort = 20016;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// *** establish connection to remote server
		sock = new Socket("localhost", Port); // *** provide server name & port
		Scanner scan = null;
		
		// *** set up socket I/O streams
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				sock.getOutputStream())), true);
		br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		// *** wait for server question
		String r = br.readLine(); // *** THIS IS A BLOCKING CALL
		System.out.println("Server asks: " + r);

		// *** respond to server
		pw.println("Client dopey online");
		pw.println("Client bashful online");
		pw.println("send bashful > whats’ going on?");
		
		// *** wait for server question
		String response = br.readLine(); // *** THIS IS A BLOCKING CALL
		System.out.println("response: " + response);
		
		pw.flush();

		// *** close this socket connection
		sock.close();

	}

}

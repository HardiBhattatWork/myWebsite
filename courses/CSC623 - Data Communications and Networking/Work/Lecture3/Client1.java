//*** Client1.java

import java.io.*;
import java.net.*;

public class Client1
{
   static Socket          sock;
   static PrintWriter     pw;
   static BufferedReader  br;
   static String          response = "I am the Grinch";

   //************************************
   public static void main(String args[]) throws IOException
   {
	  //*** establish connection to remote server
	  sock = new Socket(args[0],
	                   Integer.parseInt(args[1]));  //*** provide server name & port


	  //*** set up socket I/O streams
	  pw = new PrintWriter (new BufferedWriter (new OutputStreamWriter(sock.getOutputStream())),true);
	  br  = new BufferedReader (new InputStreamReader (sock.getInputStream()));


	  //*** wait for server question
	  String r = br.readLine();    //*** THIS IS A BLOCKING CALL
	  System.out.println("Server asks: " + r);

	  //*** respond to server
	  pw.println(response);
	  pw.flush();

	  //*** close this socket connection
	  sock.close();
   }
}

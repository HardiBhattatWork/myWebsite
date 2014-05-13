//*** Server1.java

import java.io.*;
import java.net.*;

public class Server1
{
   static ServerSocket    ServSock;
   static Socket	      Sock;
   static PrintWriter     PW;
   static BufferedReader  DIS;
   static String          Question = "Who are you";

   //************************************
   public static void main(String args[]) throws IOException
   {
	  //*** establish server socket
	  ServSock = new ServerSocket(20000, 6);  //*** port & queue length

	  //*** server runs forever until killed
	  while (true)
	     {
	     //*** wait for the next client connection
	     Sock = ServSock.accept();


	     //*** set up socket I/O streams
	     PW = new PrintWriter (new BufferedWriter (new OutputStreamWriter(Sock.getOutputStream())),true);
		 DIS  = new BufferedReader (new InputStreamReader (Sock.getInputStream()));


	     //*** send server question
	     PW.println(Question);
	     PW.flush();

	     //*** wait for client response
	     String R = DIS.readLine();    //*** THIS IS A BLOCKING CALL
	     System.out.println("Client says: " + R);

	     //*** close this socket connection but not the
		//*** overall server connection
	    Sock.close();
	   }
   }
}

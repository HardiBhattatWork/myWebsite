	//***  Test2.java
	import java.io.*;
	import java.net.*;

	public class Test2
	{
	   //*** give 'Test' your SMTP server-name and the recipient's name
	   //*** e.g.    
	   //           java Test mailhost.sju.edu   someone@sju.edu
	   //***
	   public static void main(String args[])
	   {
	      Socket 		S;
	      PrintWriter	P;
		
	      try
		{
		S = new Socket(args[0], 25);
	        P = new PrintWriter(S.getOutputStream());

	        P.println("MAIL FROM: me@sju.edu");
	        P.println("RCPT TO:  " + args[1]);
		P.println("DATA"); 
	        P.println("This is my first J-mail!");
	        P.println(".");
	        P.println("QUIT");

		P.flush();
		S.close();
		}
	      catch (IOException e)
		{System.out.println(e);}
	   }	
	}

//*** Server2.java

import java.io.*;
import java.net.*;

//********************
public class Server2
{
   static ServerSocket  ServSock;
   static int           QLen = 6;

   //***
   public static void main(String args[])
   {
      try
      {
         //*** establish server socket
         ServSock = new ServerSocket(Integer.parseInt(args[0]), QLen);

         while (true)
         {
               //***  accept a client
               Socket Sock = ServSock.accept();

               //*** pass the client to a new thread
               new Worker(Sock).start();
          }
      }
      catch(IOException e)
          {System.out.println(e);}
    }
}





//*************************
class Worker extends Thread
{
   Socket          Sock;
   PrintWriter     PW;
   BufferedReader  DIS;
   String          Question = "Who are you?";


   //**************
   Worker(Socket S)
   {Sock=S;}


   //**************
   public void run()
   {
     try
     {
        //*** thread identifies itself
        System.out.println("Thread: " + getName());


        //*** auto-flush
	    //*** set up socket I/O streams
	    PW = new PrintWriter (new BufferedWriter (new OutputStreamWriter(Sock.getOutputStream())),true);
		DIS  = new BufferedReader (new InputStreamReader (Sock.getInputStream()));



        //*** send server question
        PW.println(Question);


        //*** wait for client response
        String R = DIS.readLine();
        System.out.println("Client says: " + R);


        //*** close this socket connection
        Sock.close();
        }

     catch(IOException e)
	    {System.out.println(e);}
   }
}

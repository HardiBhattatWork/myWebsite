//*** miniHTTP.java

import java.io.*;
import java.net.*;

public class miniHTTP
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
   Worker(Socket S) {
	Sock=S;
	}

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
			
			//**** handler fpr the http request
			http_handler(DIS, PW);
			
		} catch(EOFException e){
            System.out.println("EOF: " + e.getMessage());
        }
        catch(IOException e){
            System.out.println("IO at run: " + e.getMessage());
        }finally{
            try{
                //*** close this socket connection
				Sock.close();
            }catch(IOException e){
                System.out.println("Unable to close the socket");
            }
        }
	}
	
	//our implementation of the hypertext transfer protocol
//its very basic and stripped down
  private void http_handler(BufferedReader input, DataOutputStream output) {
    int method = 0; //1 get, 2 head, 0 not supported
    String http = new String(); //a bunch of strings to hold
    String path = new String(); //the various things, what http v, what path,
    String file = new String(); //what file
    String user_agent = new String(); //what user_agent
    try {
      //This is the two types of request we can handle
      //GET /index.html HTTP/1.0
      //HEAD /index.html HTTP/1.0
      String tmp = input.readLine(); //read from the stream
      String tmp2 = new String(tmp);
      tmp.toUpperCase(); //convert it to uppercase
      if (tmp.startsWith("GET")) { //compare it is it GET
        method = 1;
      } //if we set it to method 1
      if (tmp.startsWith("HEAD")) { //same here is it HEAD
        method = 2;
      } //set method to 2

      if (method == 0) { // not supported
        try {
          output.writeBytes(construct_http_header(501, 0));
          output.close();
          return;
        }
        catch (Exception e3) { //if some error happened catch it
          s("error:" + e3.getMessage());
        } //and display error
      }
      //}

      //tmp contains "GET /index.html HTTP/1.0 ......."
      //find first space
      //find next space
      //copy whats between minus slash, then you get "index.html"
      //it's a bit of dirty code, but bear with me...
      int start = 0;
      int end = 0;
      for (int a = 0; a < tmp2.length(); a++) {
        if (tmp2.charAt(a) == ' ' && start != 0) {
          end = a;
          break;
        }
        if (tmp2.charAt(a) == ' ' && start == 0) {
          start = a;
        }
      }
      path = tmp2.substring(start + 2, end); //fill in the path
    }
    catch (Exception e) {
      s("errorr" + e.getMessage());
    } //catch any exception

    //path do now have the filename to what to the file it wants to open
    s("\nClient requested:" + new File(path).getAbsolutePath() + "\n");
    FileInputStream requestedfile = null;

    try {
      //NOTE that there are several security consideration when passing
      //the untrusted string "path" to FileInputStream.
      //You can access all files the current user has read access to!!!
      //current user is the user running the javaprogram.
      //you can do this by passing "../" in the url or specify absoulute path
      //or change drive (win)

      //try to open the file,
      requestedfile = new FileInputStream(path);
    }
    catch (Exception e) {
      try {
        //if you could not open the file send a 404
        output.writeBytes(construct_http_header(404, 0));
        //close the stream
        output.close();
      }
      catch (Exception e2) {}
      ;
      s("error" + e.getMessage());
    } //print error to gui

    //happy day scenario
    try {
      int type_is = 0;
      //find out what the filename ends with,
      //so you can construct a the right content type
      if (path.endsWith(".zip"
             ) {
        type_is = 3;
      }
      if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
        type_is = 1;
      }
      if (path.endsWith(".gif")) {
        type_is = 2;
        //write out the header, 200 ->everything is ok we are all happy.
      }
      output.writeBytes(construct_http_header(200, 5));

      //if it was a HEAD request, we don't print any BODY
      if (method == 1) { //1 is GET 2 is head and skips the body
        while (true) {
          //read the file from filestream, and print out through the
          //client-outputstream on a byte per byte base.
          int b = requestedfile.read();
          if (b == -1) {
            break; //end of file
          }
          output.write(b);
        }
        
      }
//clean up the files, close open handles
      output.close();
      requestedfile.close();
    }

    catch (Exception e) {}

  }

  //this method makes the HTTP header for the response
  //the headers job is to tell the browser the result of the request
  //among if it was successful or not.
  private String construct_http_header(int return_code, int file_type) {
    String s = "HTTP/1.0 ";
    //you probably have seen these if you have been surfing the web a while
    switch (return_code) {
      case 200:
        s = s + "200 OK";
        break;
      case 400:
        s = s + "400 Bad Request";
        break;
      case 403:
        s = s + "403 Forbidden";
        break;
      case 404:
        s = s + "404 Not Found";
        break;
      case 500:
        s = s + "500 Internal Server Error";
        break;
      case 501:
        s = s + "501 Not Implemented";
        break;
    }

    s = s + "\r\n"; //other header fields,
    s = s + "Connection: close\r\n"; //we can't handle persistent connections
    s = s + "Server: SimpleHTTPtutorial v0\r\n"; //server name

    //Construct the right Content-Type for the header.
    //This is so the browser knows what to do with the
    //file, you may know the browser dosen't look on the file
    //extension, it is the servers job to let the browser know
    //what kind of file is being transmitted. You may have experienced
    //if the server is miss configured it may result in
    //pictures displayed as text!
    switch (file_type) {
      //plenty of types for you to fill in
      case 0:
        break;
      case 1:
        s = s + "Content-Type: image/jpeg\r\n";
        break;
      case 2:
        s = s + "Content-Type: image/gif\r\n";
      case 3:
        s = s + "Content-Type: application/x-zip-compressed\r\n";
      default:
        s = s + "Content-Type: text/html\r\n";
        break;
    }

    ////so on and so on......
    s = s + "\r\n"; //this marks the end of the httpheader
    //and the start of the body
    //ok return our newly created header!
    return s;
  }
}
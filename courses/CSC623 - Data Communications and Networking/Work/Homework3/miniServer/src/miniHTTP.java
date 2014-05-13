/**
 * 
 * @author Hardi Bhatt
 * @coursenumber CSC 623: Data Communications and Networking
 * @assignment Assignment #3 
 * @DueDate Wednesday 10/19/2011
 * @description a socket-based mini HTTP server in Java
 * 1.	The server runs on your PC off port 20000. So, to connect 
 * to it you would have to open your browser and type the 
 * following URL in the address bar: http://localhost:20000/index.html
 * 
 * 2.	Your server would then look for index.html in its directory and 
 * send it to the remote browser; otherwise, an error message such as 
 * File Not Found should be sent.
 * 
 * 3.	Text files (.txt) and HTML files (.html) are the only file types 
 * your server can handle.
 * 4.	Recall from our class discussions that to retrieve a document 
 * via HTTP the browser transmits the following request to the server:
 *  
 *         GET /request-URI    HTTP/version
 *         
 * where version tells the server which HTTP version is used. One important 
 * point here is that this request string is all the server ever sees. So 
 * the server doesn't care if the request came from a browser, a link checker, 
 * a search engine robot or if you typed it in manually. It just performs the 
 * request and returns the result.
 * 5.	When the server receives the HTTP request it locates the appropriate 
 * document and returns it. However, an HTTP response is required to have a 
 * particular form. It must look like this:
 * 
 * HTTP/[VER] [CODE] [TEXT] 
 * Field1: Value1 
 * Field2: Value2
 * 
 * ...Document content here...
 * 
 * The first line shows the HTTP version used, followed by a three-digit number 
 * (the HTTP status code) and a reason phrase meant for humans. Usually the code 
 * is 200 (which basically means that all is well) and the phrase "OK". The first 
 * line is followed by some lines called the header, which contains information 
 * about the document. The header ends with a blank line, followed by the document 
 * content. This is a typical header:
 * HTTP/1.0 200 OK 
 * Server: Netscape-Communications/1.1 
 * Date: Tuesday, 25-Nov-09 01:22:04 GMT 
 * Last-modified: Thursday, 20-Nov-09 10:44:53 GMT 
 * Content-length: 6372 
 * Content-type: text/html
 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
 * <HTML> 
 * ...followed by document content...
 *
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class miniHTTP {

	/**
	 * 
	 */
	static ServerSocket ServSock;
	static int QLen = 6;
	static int Port = 20000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// *** establish server socket
			ServSock = new ServerSocket(Port, QLen);

			while (true) {
				// *** accept a client
				Socket Sock = ServSock.accept();

				// *** pass the client to a new thread
				new Worker(Sock).start();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

/**
 * 
 * @author Hardi Bhatt
 * 
 */
class Worker extends Thread {
	Socket Sock;
	PrintWriter PW;
	BufferedReader DIS;
	OutputStreamWriter OSW;
	DataOutputStream DOS;
	String Question = "Who are you?";

	// **************
	Worker(Socket S) {
		Sock = S;
	}

	/**
	 * 
	 */
	public void run() {
		try {
			// *** thread identifies itself
			System.out.println("Thread: " + getName());

			// *** auto-flush
			// *** set up socket I/O streams
			OSW = new OutputStreamWriter(Sock.getOutputStream());
			PW = new PrintWriter(new BufferedWriter(OSW), true);
			DIS = new BufferedReader(new InputStreamReader(
					Sock.getInputStream()));

			// **** handler for the http request
			http_handler(DIS, PW);

		} catch (EOFException e) {
			System.out.println("EOF: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO at run: " + e.getMessage());
		} finally {
			try {
				// *** close this socket connection
				Sock.close();
			} catch (IOException e) {
				System.out.println("Unable to close the socket");
			}
		}
	}

	/**
	 * 
	 * @param input
	 * @param output
	 */
	private void http_handler(BufferedReader input, PrintWriter output) {
		int method = 0; // 1 get, 2 head, 0 not supported
		String path = new String(); // the various things, what http v, what
									// path,
		try {

			String tmp = input.readLine(); // read from the stream
			tmp.toUpperCase(); // convert it to uppercase
			// System.out.println(tmp);
			StringTokenizer st = new StringTokenizer(tmp); // StringTokenizer

			while (st.hasMoreElements()) {
				String nxtElm = (String) st.nextElement();
				// System.out.println(nxtElm);
				if (nxtElm.equals("GET")) { // compare it is it GET
					// System.out.println("Connection Made");
					method = 1;
					path = (String) st.nextElement();
					// System.out.println("Path: "+path);
				} // if we set it to method 1
				else if (nxtElm.equals("HEAD")) { // same here is it HEAD
					System.out.println("Yup!  Its starts with Head");
					method = 2;
					path = (String) st.nextElement();
					// System.out.println("Path: "+path);
				} // set method to 2
				if (method == 0) { // not supported
					try {
						output.println(construct_http_header(501, 0, output));
						output.close();
						return;
					} catch (Exception e) { // if some error happened catch it
						System.out.println("error:" + e.getMessage());
					} // and display error
				}
			}
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
		} // catch any exception

		try {
			int type_is = 0;
			// find out what the filename ends with,
			// so you can construct the right content type
			if (path.endsWith(".zip")) {
				type_is = 3;
			}
			if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
				type_is = 1;
			}
			if (path.endsWith(".gif")) {
				type_is = 2;
			}
			if (path.endsWith(".txt")) {
				type_is = 4;
			}
			if (path.endsWith(".html")) {
				type_is = 5;
			}
			if (path.endsWith(".xml")) {
				type_is = 6;
			}
			if (path.endsWith(".png")) {
				type_is = 7;
			}
			// System.out.println(construct_http_header(200, type_is, output));
			output.println(construct_http_header(200, type_is, output));
			output.flush();
			// clean up the files, close open handles
			output.close();
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
		}
	}

	/**
	 * this method makes the HTTP header for the response the headers job is to
	 * tell the browser the result of the request if it was successful or not.
	 * 
	 * @param return_code
	 * @param file_type
	 * @return
	 */
	private String construct_http_header(int return_code, int file_type,
			PrintWriter output) {
		String header = "HTTP/1.0 ";
		String file_resp = "File Type not found";
		switch (return_code) {
		case 200:
			header += "200 OK";
			// output.println("200 OK\r\n");
			break;
		case 400:
			header += "400 Bad Request";
			// output.println("400 Bad Request\r\n");
			break;
		case 403:
			header += "403 Forbidden";
			// output.println("403 Forbidden\r\n");
			break;
		case 404:
			header += "404 Not Found";
			// output.println("404 Not Found\r\n");
			break;
		case 500:
			header += "500 Internal Server Error";
			// output.println("500 Internal Server Error\r\n");
			break;
		case 501:
			header += "501 Not Implemented";
			// output.println("501 Not Implemented\r\n");
			break;
		}

		header += "\r\n"; // other header fields,
		header += "Connection: close\r\n"; // we can't handle persistent
											// connections
		header += "Server: SimpleHTTPminiServer v0\r\n"; // server name
		Calendar currentDate = Calendar.getInstance();
		header += "Date: " + currentDate.getTime() + "\r\n";
		header += "Content-length: N/A\r\n";

		// text/xml
		// Construct the right Content-Type for the header.
		switch (file_type) {
		case 0:
			break;
		case 1:
			file_resp = "";
			byte[] buffJPG = null;
			File testJPG = new File("jpgImg.jpg");
			ByteArrayOutputStream streamJPG = new ByteArrayOutputStream();

			try {
				Image imageJPG = ImageIO.read(testJPG);
				BufferedImage cpimg = bufferImage(imageJPG,
						BufferedImage.TYPE_INT_RGB);

				ImageIO.write(cpimg, "jpeg", streamJPG);
				buffJPG = streamJPG.toByteArray();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file_resp = new String(buffJPG);

			header += "Content-Type: image/jpeg\r\n";
			break;
		case 2:
			file_resp = "";
			byte[] buffGIF = null;
			File testGIF = new File("gifImg.gif");

			try {
				FileInputStream fis = new FileInputStream(testGIF);
				BufferedInputStream BIS = new BufferedInputStream(fis);
				try {
					buffGIF = new byte[BIS.available()];
					BIS.read(buffGIF);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < buffGIF.length; i++) {
				file_resp = file_resp + (char) buffGIF[i];
			}
			// header += "Content-Length: " + buffGIF.length + "\r\n";
			header += "Content-Type: image/gif\r\n";
			file_resp = "File type GIF";
			break;
		case 3:
			header += "Content-Type: application/x-zip-compressed\r\n";
			file_resp = "File type Zip";
			break;
		case 4:
			header += "Content-Type: text/plain\r\n";
			file_resp = "File type Text";
			break;
		case 5:
			header += "Content-Type: text/html\r\n";
			file_resp = "<html><body><h1>File type HTML</h1></body></html>";
			break;
		case 6:
			header += "Content-Type: text/xml\r\n";
			file_resp = "<?xml version='1.0' encoding='UTF-8'?>"
					+ "<note><body>File type XML</body></note>";
			break;
		case 7:
			file_resp = "";
			byte[] buffPNG = null;
			File testPNG = new File("pngImg.png");

			try {
				FileInputStream fis = new FileInputStream(testPNG);
				BufferedInputStream BIS = new BufferedInputStream(fis);
				try {
					buffPNG = new byte[BIS.available()];
					BIS.read(buffPNG);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < buffPNG.length; i++) {
				file_resp = file_resp + (char) buffPNG[i];
			}
			// header += "Content-Length: " + buffPNG.length + "\r\n";
			header += "Content-Type: image/png\r\n";
			break;
		default:
			break;
		}

		header += "\r\n"; // end of the httpheader
		// start of the body here...
		header += file_resp;

		// return newly created header!
		return header;
	}

	public static BufferedImage bufferImage(Image image, int type) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), type);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, null, null);
		// waitForImage(bufferedImage);
		return bufferedImage;
	}
}

/**  
 * @author Hardi Bhatt
 * @coursenumber CSC 623: Data Communications and Networking
 * @assignment Assignment #5 
 * @DueDate Wednesday 11/19/2011
 * @description 
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MEPServer {

	static ServerSocket ServSock;
	static ServerSocket DocServSock;
	static ServerSocket GrumpyServSock;
	static ServerSocket SleepyServSock;
	static ServerSocket SneezyServSock;
	static ServerSocket DopeyServSock;
	static ServerSocket BashfulServSock;
	static ServerSocket HappyServSock;

	static int QLen = 6;

	static int Port = 20000;
	static int docPort = 20010;
	static int grumpyPort = 20011;
	static int sleepyPort = 20012;
	static int sneezyPort = 20013;
	static int dopeyPort = 20014;
	static int bashfulPort = 20015;
	static int happyPort = 20016;

	static Socket Sock;
	static Socket DocSock;
	static Socket GrumpySock;
	static Socket SleepySock;
	static Socket SneezySock;
	static Socket DopeySock;
	static Socket BashfulSock;
	static Socket HappySock;

	static Socket DocClientSock;
	static Socket GrumpyClientSock;
	static Socket SleepyClientSock;
	static Socket SneezyClientSock;
	static Socket DopeyClientSock;
	static Socket BashfulClientSock;
	static Socket HappyClientSock;

	static PrintWriter PW;
	static BufferedReader DIS;
	static DateFormat dateFormat;
	static Date date;

	/**
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {

		// *** establish server socket
		ServSock = new ServerSocket(Port, QLen);

		System.out.println("Connected..\n");
		String strGET = "";
		String strClient = "";
		String command = "";
		String Question = "Who are you?";
		int count = 0;

		while (true) {
			try {
				// *** accept a client
				Sock = ServSock.accept();

				dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				date = new Date();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						Sock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						Sock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** send server question
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL

				StringTokenizer token = new StringTokenizer(R);

				// strGET: holds the first token (Client name).
				strGET = token.nextElement().toString();
				System.out.println(strGET);
				if (strGET.equals("Client")) {
					strClient = token.nextElement().toString();
					System.out.println(strClient);
					// str: holds the second token which is the command
					command = token.nextElement().toString();
					System.out.println(command);
				} else if (strGET.equals("send")) {
					strClient = token.nextElement().toString();
					System.out.println(strClient);
				}

				if (count <= 2 && command.equals("online")) {
					if (strClient.equals("doc")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							
							count = count + 1;
							// *** pass the client to a new port
							new Doc(DocServSock, docPort, DocSock, QLen);

							// *** provide server name & port
							DocClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											DocClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									DocClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("doc says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);
							}
							// *** respond to Doc
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else if (strClient.equals("grumpy")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							
							count = count + 1;
							// *** pass the client to a new port
							new Grumpy(GrumpyServSock, grumpyPort, GrumpySock,
									QLen);
							// *** provide server name & port
							GrumpyClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											GrumpyClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									GrumpyClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("grumpy says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);								
							}
							// *** respond to Grumpy
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else if (strClient.equals("sleepy")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							count = count + 1;
							// *** pass the client to a new port
							new Sleepy(SleepyServSock, sleepyPort, SleepySock,
									QLen);
							// *** provide server name & port
							SleepyClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											SleepyClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									SleepyClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("sleepy says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);								
							}
							// *** respond to sleepy
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else if (strClient.equals("sneezy")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							count = count + 1;
							// *** pass the client to a new port
							new Sneezy(SneezyServSock, sneezyPort, SneezySock,
									QLen);
							// *** provide server name & port
							SneezyClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											SneezyClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									SneezyClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("sneezy says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);
								
							}
							// *** respond to sneezy
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else if (strClient.equals("dopey")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							count = count + 1;
							// *** pass the client to a new port
							new Dopey(DopeyServSock, dopeyPort, DopeySock, QLen);
							// *** provide server name & port
							DopeyClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											DopeyClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									DopeyClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("dopey says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);
								
							}
							// *** respond to dopey
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else if (strClient.equals("bashful")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							count = count + 1;
							// *** pass the client to a new port
							new Bashful(BashfulServSock, bashfulPort,
									BashfulSock, QLen);
							// *** provide server name & port
							BashfulClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											BashfulClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									BashfulClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("bashful says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);
							}
							// *** respond to bashful
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else if (strClient.equals("happy")) {
						PrintWriter pw = null;
						BufferedReader br = null;
						if (command.equals("online")) {
							count = count + 1;
							// *** pass the client to a new port
							new Happy(HappyServSock, happyPort, HappySock, QLen);
							// *** provide server name & port
							HappyClientSock = new Socket("localhost", docPort);

							// *** set up socket I/O streams
							pw = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(
											HappyClientSock.getOutputStream())),
									true);
							br = new BufferedReader(new InputStreamReader(
									HappyClientSock.getInputStream()));

							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String r = br.readLine(); 
							System.out.println("doc says: " + r);
						} else {
							String respond = "";
							while (token.hasMoreTokens()) {
								respond += token.nextToken();
								System.out.println(respond);
								
							}
							// *** respond to happy
							pw.println(respond);
							// *** wait for server question
							// *** THIS IS A BLOCKING CALL
							String response = br.readLine();
							System.out.println("response: " + response);
						}
					} else {
						System.out.println("Client Not found");
					}
				} else {
					System.out.println("2 Clients currently online.");
				}

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				Sock.close();
				DocSock.close();
				GrumpySock.close();
				SleepySock.close();
				SneezySock.close();
				DopeySock.close();
				BashfulSock.close();
				HappySock.close();
				DocClientSock.close();
				GrumpyClientSock.close();
				SleepyClientSock.close();
				SneezyClientSock.close();
				DopeyClientSock.close();
				BashfulClientSock.close();
				HappyClientSock.close();
			}
		}
	}
}

class Doc {

	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Doc> sorry! I forgot";

	// ************************************
	public Doc(ServerSocket docServSock, int docPort, Socket docSock, int qLen)
			throws IOException {

		// *** establish server socket
		docServSock = new ServerSocket(docPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {
				// *** wait for the next client connection
				docSock = docServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						docSock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						docSock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				docSock.close();
			}
		}
	}
}

class Grumpy {
	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Grumpy> sorry! I forgot";

	// ************************************
	public Grumpy(ServerSocket grumpyServSock, int grumpyPort,
			Socket grumpySock, int qLen) throws IOException {

		// *** establish server socket
		grumpyServSock = new ServerSocket(grumpyPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {
				// *** wait for the next client connection
				grumpySock = grumpyServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						grumpySock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						grumpySock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				grumpySock.close();
			}
		}
	}
}

class Sleepy {
	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Sleepy> sorry! I forgot";

	// ************************************
	public Sleepy(ServerSocket sleepyServSock, int sleepyPort,
			Socket sleepySock, int qLen) throws IOException {

		// *** establish server socket
		sleepyServSock = new ServerSocket(sleepyPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {
				// *** wait for the next client connection
				sleepySock = sleepyServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						sleepySock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						sleepySock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				sleepySock.close();
			}
		}
	}
}

class Sneezy {
	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Sneezy> sorry! I forgot";

	// ************************************
	public Sneezy(ServerSocket sneezyServSock, int sneezyPort,
			Socket sneezySock, int qLen) throws IOException {

		// *** establish server socket
		sneezyServSock = new ServerSocket(sneezyPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {// *** wait for the next client connection
				sneezySock = sneezyServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						sneezySock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						sneezySock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				sneezySock.close();
			}
		}
	}
}

class Dopey {
	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Dopey> sorry! I forgot";

	// ************************************
	public Dopey(ServerSocket dopeyServSock, int dopeyPort, Socket dopeySock,
			int qLen) throws IOException {

		// *** establish server socket
		dopeyServSock = new ServerSocket(dopeyPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {// *** wait for the next client connection
				dopeySock = dopeyServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						dopeySock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						dopeySock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				dopeySock.close();
			}
		}
	}
}

class Bashful {
	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Bashful> sorry! I forgot";

	// ************************************
	public Bashful(ServerSocket bashfulServSock, int bashfulPort,
			Socket bashfulSock, int qLen) throws IOException {

		// *** establish server socket
		bashfulServSock = new ServerSocket(bashfulPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {// *** wait for the next client connection
				bashfulSock = bashfulServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						bashfulSock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						bashfulSock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				bashfulSock.close();
			}
		}
	}
}

class Happy {
	static PrintWriter PW;
	static BufferedReader DIS;
	static String Question = "Happy> sorry! I forgot";

	// ************************************
	public Happy(ServerSocket happyServSock, int happyPort, Socket happySock,
			int qLen) throws IOException {

		// *** establish server socket
		happyServSock = new ServerSocket(happyPort, qLen);

		// *** server runs forever until killed
		while (true) {
			try {
				// *** wait for the next client connection
				happySock = happyServSock.accept();

				// *** set up socket I/O streams
				PW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						happySock.getOutputStream())), true);
				DIS = new BufferedReader(new InputStreamReader(
						happySock.getInputStream()));

				// *** send server question
				PW.println(Question);

				// *** wait for client response
				String R = DIS.readLine(); // *** THIS IS A BLOCKING CALL
				System.out.println("Client says: " + R);

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				PW.flush();
				// *** close this socket connection but not the
				// *** overall server connection
				happySock.close();
			}
		}
	}
}

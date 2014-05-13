        //*** Test3.java

        import java.io.*;
        import java.net.*;

        public class Test3
        {
           public static void main(String args[]) throws IOException
           {
              Socket S = new Socket(args[0], 80);

              //*** set up socket I/O streams
              PrintWriter PW = new PrintWriter (new BufferedWriter (new OutputStreamWriter(S.getOutputStream())),true);
              BufferedReader DIS = new BufferedReader (new InputStreamReader (S.getInputStream()));

              PW.print("GET / HTTP/1.0 \n\n");
              PW.flush();

              String Line = DIS.readLine();
              while (Line != null)
                {
                System.out.println(Line);
                Line = DIS.readLine();
                }

              S.close();
           }
        }

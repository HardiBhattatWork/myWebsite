//***  Test1.java
import java.net.*;

public class Test1
{
   public static void main(String args[])
   {
   try
   {
       InetAddress IA = InetAddress.getByName(args[0]);
       System.out.println("Host address: " + IA.getHostAddress());
       System.out.println("Host name: " + IA.getHostName());
       System.out.println("Local host name: " + IA.getLocalHost());
   }
   catch (UnknownHostException e)
       {e.printStackTrace();}
   }
}



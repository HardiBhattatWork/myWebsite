	//*** Test4.java

	import java.io.*;
	import java.net.*;

	public class Test4
	{
	   public static void main(String args[])
	   {
	      try
		{
	         Socket S = new Socket("time-A.timefreq.bldrdoc.gov", 13);
                 BufferedReader DIS = new BufferedReader (new InputStreamReader (S.getInputStream()));
	      
	         String Line = DIS.readLine();
	         while (Line != null)
		   {
		   System.out.println(Line);
	           Line = DIS.readLine();
		   }

	         S.close();
		}
	      catch(IOException e)
	        {System.out.println(e);}
	   }
	}

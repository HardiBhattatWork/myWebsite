/*****************************************************************************/
/*                                                                           */
/* QuadTreeDriver.java                                                       */
/*                                                                           */
/* Author: Hardik Bhatt                                                      */
/* Date: June 8, 2005                                                        */
/*                                                                           */
/* A class that implements a QuadTree class using the array-                 */
/*                                                                           */
/*****************************************************************************/
import java.io.*;
import java.util.*;
import java.lang.Integer;
public class QuadtreeDriver
{
    public static void main (String[] args) throws IOException
    {
	Quadtree Tree = new Quadtree ();
	String line;                            // this holds the different commands from the file
	StringTokenizer token;                  // this holds the tokenized commands
	STNode temp;                            // temp node to store stuff

	FileReader file = new FileReader ("DataFile");  // used ot read in from a file
	BufferedReader input = new BufferedReader (file);

	
	do
	{
	    token = new StringTokenizer (input.readLine ());
	    line = token.nextToken ();
	    //System.out.println (line);
	    if (line.equals ("insert"))
	    {
	    QuadtreePoint point = new QuadtreePoint ();
	    Tree.insert (point);
	    Tree.display (Tree.Rootptr ());
    }
}

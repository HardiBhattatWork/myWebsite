/*****************************************************************************/
/*                                                                           */
/* QuadTreePoint.java                                                         */
/*                                                                           */
/* Author: Hardik Bhatt                                                      */
/* Date: June 8, 2005                                                        */
/*                                                                           */
/* A class that implements a QuadTree class using the array-                 */

/*****************************************************************************/
public class QuadtreePoint
{
    protected int x;
    protected int y;

    public QuadtreePoint ()
    {
	x = 0;
	y = 0;
    }


    public QuadtreePoint (QuadtreePoint point)
    {
	this.x = point.x;
	this.y = point.y;
    }


    public QuadtreePoint (int x, int y)
    {
	this.x = x;
	this.y = y;
    }


    public int getX ()
    {
	return x;
    }


    public int getY ()
    {
	return y;
    }


    public boolean equals (QuadtreePoint point)
    {
	if (this.getX () == point.getX () && this.getY () == point.getY ())
	    return true;
	else
	    return false;
    }
}

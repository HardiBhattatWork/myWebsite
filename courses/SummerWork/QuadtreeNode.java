/*****************************************************************************/
/*                                                                           */
/* QuadTreeNode.java                                                         */
/*                                                                           */
/* Author: Hardik Bhatt                                                      */
/* Date: June 8, 2005                                                        */
/*                                                                           */
/* A class that implements a QuadTree class using the array-                 */

/*****************************************************************************/

public class QuadtreeNode
{

    protected QuadtreeNode NWNode;
    protected QuadtreeNode NENode;
    protected QuadtreeNode SWNode;
    protected QuadtreeNode SENode;
    protected QuadtreeNode Center;
    protected QuadtreePoint Newpoint;


    public QuadtreeNode ()
    {
	Newpoint = new QuadtreePoint ();
	NWNode = null;
	NENode = null;
	SWNode = null;
	SENode = null;
    }


    public QuadtreeNode (QuadtreePoint point)
    {
	Newpoint = new QuadtreePoint (point);
	NWNode = null;
	NENode = null;
	SWNode = null;
	SENode = null;
    }


    public QuadtreeNode getNWNode ()
    {
	return NWNode;
    }


    public QuadtreeNode getNENode ()
    {
	return NENode;
    }


    public QuadtreeNode getSWNode ()
    {
	return SWNode;
    }


    public QuadtreeNode getSENode ()
    {
	return SENode;
    }


    public void setNWNode (QuadtreeNode newNode)
    {
	NWNode = newNode;
    }


    public void setNENode (QuadtreeNode newNode)
    {
	NENode = newNode;
    }


    public void setSWNode (QuadtreeNode newNode)
    {
	SWNode = newNode;
    }


    public void setSENode (QuadtreeNode newNode)
    {
	SENode = newNode;
    }


    public QuadtreeNode getQuadrent (QuadtreePoint Npoint)
    {
	if (Npoint.getX () - Newpoint.getX () > 0 && Npoint.getY () - Newpoint.getY () > 0)
	    return NWNode;
	else if (Npoint.getX () - Newpoint.getX () < 0 && Npoint.getY () - Newpoint.getY () < 0)
	    return SWNode;
	else if (Npoint.getX () - Newpoint.getX () < 0 && Npoint.getY () - Newpoint.getY () > 0)
	    return NENode;
	else if (Npoint.getX () - Newpoint.getX () > 0 && Npoint.getY () - Newpoint.getY () < 0)
	    return SENode;
	else if (Npoint.getX () - Newpoint.getX () == 0 && Npoint.getY () - Newpoint.getY () == 0)
	    return Center;
	else
	    return null;
    }


    public QuadtreeNode setNode (QuadtreePoint point)
    {
	QuadtreeNode Temppoint = new QuadtreeNode (point);
	return Temppoint;
    }
    
    public QuadtreePoint getPoint ()
    {
	return Newpoint;
    }

     
}

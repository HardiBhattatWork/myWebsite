/*****************************************************************************/
/*                                                                           */
/* QuadTree.java                                                             */
/*                                                                           */
/* Author: Hardik Bhatt                                                      */
/* Date: June 8, 2005                                                        */
/*                                                                           */
/* A class that implements a QuadTree class using the array-                 */
/*                                                                           */
/*****************************************************************************/

public class Quadtree
{
    QuadtreeNode root;

    public Quadtree ()
    {
	root = null;
    }


    public Quadtree (QuadtreeNode tree)
    {
	root = tree;
    }


    public void insert (QuadtreePoint point)
    {
	root = AuxInsert (point, root);
    }


    public QuadtreeNode AuxInsert (QuadtreePoint point, QuadtreeNode tempptr)
    {
	if (tempptr == null)
	{
	    tempptr = new QuadtreeNode (point);
	    System.out.println(tempptr.getPoint().getX());
	    System.out.println(tempptr.getPoint().getY());
	}
	else if (hasNorthWestNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.NWNode)
	{

	    tempptr = tempptr.NWNode;
	    tempptr = AuxInsert (point, tempptr);
	}
	else if (hasSouthWestNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.SWNode)
	{
	    tempptr = tempptr.SWNode;
	    tempptr = AuxInsert (point, tempptr);
	}
	else if (hasNorthEastNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.NENode)
	{
	    tempptr = tempptr.NENode;
	    tempptr = AuxInsert (point, tempptr);
	}
	else if (hasSouthEastNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.SENode)
	{
	    tempptr = tempptr.SENode;
	    tempptr = AuxInsert (point, tempptr);
	}
	else if (tempptr.getQuadrent (point) == tempptr.Center)
	{
	    System.out.println ("This point alredy exists");
	}
	else
	{
	    tempptr.setNode (point);
	}
	return tempptr;
    }


    public void delete (QuadtreeNode node)
    {
	QuadtreeNode parent;



    }


    public boolean search (QuadtreePoint point)
    {

	return AuxSearch (point, root);
    }


    public boolean AuxSearch (QuadtreePoint point, QuadtreeNode tempptr)
    {

	if (tempptr == null)
	    return false;
	else if (tempptr.equals (point))
	    return true;
	else if (hasNorthWestNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.NWNode)
	{
	    return AuxSearch (point, tempptr.NWNode);
	}
	else if (hasSouthWestNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.SWNode)
	{
	    return AuxSearch (point, tempptr.SWNode);
	}
	else if (hasNorthEastNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.NENode)
	{
	    return AuxSearch (point, tempptr.NENode);
	}
	else if (hasSouthEastNode (tempptr) == true && tempptr.getQuadrent (point) == tempptr.SENode)
	{
	    return AuxSearch (point, tempptr.SENode);
	}
	else if (tempptr.getQuadrent (point) == tempptr.Center)
	    return true;
	else
	    return false;

    }


    public QuadtreeNode Rootptr ()  //root method
    {
	return root; //returns the root
    } //end of root method


    public boolean hasNorthWestNode (QuadtreeNode node)
    {
	if (node.NWNode != null)
	    return true;
	else
	    return false;
    }


    public boolean hasNorthEastNode (QuadtreeNode node)
    {
	if (node.NENode != null)
	    return true;
	else
	    return false;
    }


    public boolean hasSouthWestNode (QuadtreeNode node)
    {
	if (node.SWNode != null)
	    return true;
	else
	    return false;
    }


    public boolean hasSouthEastNode (QuadtreeNode node)
    {
	if (node.SENode != null)
	    return true;
	else
	    return false;
    }


    public void display (QuadtreeNode tree)
    {
	QuadtreeNode rootptr = tree;
	if (rootptr == null)
	    System.out.println ("tree is empty");
	else
	{
	    System.out.println ("Root: " + rootptr);
	    if (rootptr.NENode != null)
	    {
		System.out.println ("NEChild: " + rootptr.NENode);
		display (rootptr.NENode);
	    }
	    if (rootptr.NWNode != null)
	    {
		System.out.println ("NWChild: " + rootptr.NWNode);
		display (rootptr.NWNode);
	    }
	    if (rootptr.SENode != null)
	    {
		System.out.println ("SEChild: " + rootptr.SENode);
		display (rootptr.SENode);
	    }
	    if (rootptr.SWNode != null)
	    {
		System.out.println ("SWChild: " + rootptr.SWNode);
		display (rootptr.SWNode);
	    }
	}
    }
}



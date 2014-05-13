//
// Module: Simulation.java
//
// Purpose: This module contains the definition of the Simulation
//          class which stores all information about the current state 
//          of the simulation and the simulated universe.
//

import java.awt.Graphics;
import java.awt.Color;
import java.applet.Applet;
import java.io.*;

class Simulation {

    int forceCount;
    
    static final double G =  6.67e-11;
    NBody _display;

    // The state of the simulation (running or stopped).
    boolean _running;

    // The current simulation time.
    double _time;

    // These determine the accuracy of the simulation.  The time step
    // is in units of seconds.
    double _time_step;
    double _max_error;
    boolean _runge_kutta = true;
    
    // The planets of our system.
    PlanetSet _planets;

    Color _velocity_color;
    Color _acceleration_color;
    Color _grid_color;

    Quadtree _quadtree;
    boolean _use_quad = false;

    Graphics _gc;
    Universe _univ;
    Rectangle _bounding_rect;

    // Initialize this Simulation.
    Simulation(NBody disp, double step, double error) { 
	_display = disp;
	_time_step = step;
	_max_error = error;
	_planets = new PlanetSet();
	_velocity_color = Color.yellow;
	_acceleration_color = Color.red;
	_grid_color = Color.white;
	_bounding_rect = new Rectangle(-250,-250,250,250);
	
	// Create an infinite rectangle to initialize the empty quadtree.
	// Rectangle rect = new Rectangle(-Double.MAX_VALUE,-Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
	
	// Create an empty quadtree.
	_quadtree = new Quadtree(this, _bounding_rect);
    }

    Simulation(NBody disp, double step, double error, String fileName) { 
	this(disp,step,error);
	load(fileName);
    }

    // Load this Simulation from the given file.  True is returned if
    // the file was loaded successfully.  False indicates that the
    // file format or contents were invalid.
    boolean load(String file_name)
    {
	try {
	    FileInput file = new FileInput(new FileInputStream(file_name));
	    
	    _planets = new PlanetSet();
	    int n = file.nextInt();
	    for (int i=0; i<n; i++) {
		Planet p = new Planet(file);
		insert(p);
		System.out.println("read " + i);
	    }
	    recreateQuadtree();
	    save("test");
	} catch (Exception e) { return false; }

	return true;
    }
    
    // Save this Simulation to the given file.  True is returned if
    // the file was written successfully.  False indicates that the
    // file could not be written to.
    boolean save(String file_name)
    {
	try {
	    // FileOutputStream file = new FileOutputStream(file_name);
	    int i=0;
	    for (PlanetIter Pptr = _planets.first(); 
		 !Pptr.isPastEnd();
		 Pptr.advance()) {
		Planet p = Pptr.element();
		p.output(System.out);
		System.out.println("wrote " + i);
		i++;
	    }
	} catch (Exception e) { return false;}
	return true;
    }

    // Is the simulation currently running?
    boolean running() {
	return _running;
    }
    
    // Start the simulation.
    void start() {
	_running = true;
    }

    // Stop the simulation.
    void stop() {
	_running = false;
    }

    // Start the simulation.
    void quad() {
	_use_quad = true;
    }

    // Stop the simulation.
    void unquad() {
	_use_quad = false;
    }

    // Retrieve the current simulation time.
    double time() {
	return _time;
    }
    
    // Retrieve the time step.
    double timeStep() {
	return _time_step;
    }

    // Retrieve the time step.
    void setTimeStep(double dt) {
	_time_step = dt;
    }

    // Retrieve the maximum error value for a 
    // single force calculation.
    double maxError() {
	return _max_error;
    }

    // Retrieve the time step.
    void setMaxError(double me) {
	_max_error = me;
    }

    // Retrieve the number of planets in this simulation.
    int numPlanets() {
	return _planets.numElements();
    }

    // Convert the given point in the simulation (a floating-point
    // number) to a pixel position (an integer).
    Point posToPixel(Vector p, Universe u) {

	// Scale the distance from the origin and shift to the origin on
	// the screen.
	return new Point((int)((p.x-u.origin.x) * u.scale)+(u.width/2),
			 (int)((u.origin.y-p.y) * u.scale)+(u.height/2));

    }
    
    // Convert the given pixel position (integer pair) to a point in the
    // simulation (floating-point pair).
    Vector pixelToPos(Point p, Universe u) {
	
	// Scale the distance from the origin on the screen and shift to
	// the origin.
	return new Vector(((p.x-(u.width/2)) / u.scale + u.origin.x),
			  (u.origin.y - (p.y-u.height/2)/ u.scale));
    }

    void highlightPlanet(Graphics gc, Planet p, Universe u) {

	// Map this simulation position to an (x,y) screen coordinate.
	Point loc = posToPixel(p.pos, u);
    
	// Map the simulation radius into a pixel distance.
	int r = (int)(p.radius * u.scale)+5;
    
	gc.setColor(Color.red);
	gc.drawLine(loc.x-r,loc.y,loc.x+r,loc.y);
	gc.drawLine(loc.x,loc.y-r,loc.x,loc.y+r);
	gc.drawOval(loc.x - r, loc.y - r, 2*r+1, 2*r+1);
    }    

    void drawPlanet(Graphics gc, Planet p, Universe u) {

	// Map this simulation position to an (x,y) screen coordinate.
	Point loc = posToPixel(p.pos, u);
    
	// Map the simulation radius into a pixel distance.
	int r = (int)(p.radius * u.scale);

	gc.setColor(_velocity_color);
	gc.drawLine(loc.x,loc.y,
		    loc.x + (int)(_time_step * p.vel.x),
		    loc.y - (int)(_time_step * p.vel.y));
	gc.setColor(_acceleration_color);
	double accnorm = p.acc.norm();
	gc.drawLine(loc.x,loc.y,
		    loc.x + (int)(10.0 * p.acc.x / accnorm),
		    loc.y - (int)(10.0 * p.acc.y / accnorm));
	// Draw this planet onto the screen
	gc.setColor(p.color);
	gc.fillOval(loc.x - r, loc.y - r, 2*r+1, 2*r+1);
	// System.out.println(p.name + ":" + loc.x + "," + loc.y);
    }    


    // Draw the current simulation universe onto the given GC, as
    // specified by the Universe.
    void draw(Graphics gc, Universe u) {
	PlanetIter Pptr;

	// Draw each existing planet.
	for (Pptr = _planets.first(); !Pptr.isPastEnd(); Pptr.advance()) {

	    Planet p = Pptr.element();

	    // Set the color of planet and draw it
	    gc.setColor(p.color);
	    drawPlanet(gc,p,u);
	}
    }

    // Highlight the set of planets by drawing them into the given GC as
    // specified by the Universe.
    void drawHighlight(Graphics gc, Universe u, PlanetSet set) {
	PlanetIter Pptr;

	// Draw each existing planet.
	for (Pptr = set.first(); !Pptr.isPastEnd(); Pptr.advance()) {

	    Planet p = Pptr.element();
	    highlightPlanet(gc,p,u);
	}
    }

    // Draw the Quadtree grid onto the given GC.  The other parameters
    // are as for draw.
    void drawGrid(Graphics gc, Universe u) {

	// Keep track of the drawing information so that we can service
	// the calls to the drawRectangle function below.
	_gc = gc;
	_univ = u;

	// Call the Quadtree to draw its grid.
	_quadtree.drawGrid();

    }

    // Draw the Quadtree grid onto the given GC.  The other parameters
    // are as for draw.
    void drawTree(Graphics gc, Universe u, PlanetSet hlt) {

	gc.clearRect(u.width,0,u.width,u.height);

	_quadtree.unmark();
	if (hlt != null) {
	    for (PlanetIter Pptr = hlt.first(); 
		 !Pptr.isPastEnd(); 
		 Pptr.advance()) {
		
		Planet p = Pptr.element();
		_quadtree.mark(p);
	    }
	}

	int d = _quadtree.depth();
	int n = _quadtree.size();
	
	if (n >= 1) {
	    int dy = u.height/d;
	    if (dy == 0) dy = 1;
	    
	    int dx = u.width/n;
	    if (dx == 0) dx = 1;
	
	    // Call the Quadtree to draw its grid.
	    _quadtree.drawTree(gc,u.width,0,dx,dy);
	}

    }

    // Highlight those bodies that lie in the given range.
    PlanetSet getRange(Rectangle rect)
    {
	// Perform a range query to get the list of the points in this
	// range.
	PlanetSet Ps = new PlanetSet();
	_quadtree.rangeQuery(Ps, rect);
	
	return Ps;
    }


    // Insert the given planet into the simulation.
    void insert(Planet p)
    {
	_planets.add(p);

	// If the newly inserted point is in the current bounding
	// rectangle, then we can just insert it.  Otherwise, we must
	// recreate the tree.
	if (_bounding_rect.contains(p.pos)) {
	    _quadtree.insert(p);
	} else {
	    recreateQuadtree();
	}
    }


    // remove all the bodies from the simulation
    void clear()
    {
	// delete each body found.
	for (PlanetIter Pptr = _planets.first(); !Pptr.isPastEnd(); Pptr.advance()) {

	    Planet p = Pptr.element();

	    _quadtree.remove(p);
	}
	_planets = new PlanetSet();
    }

    // Delete bodies from the given set
    void delete(PlanetSet Ps)
    {
	// delete each body found.
	for (PlanetIter Pptr = Ps.first(); !Pptr.isPastEnd(); Pptr.advance()) {

	    Planet p = Pptr.element();

	    // Remove the body from our collection and the quadtree
	    _planets.remove(p);
	    _quadtree.remove(p);
	}
    }

    // Compute the total energy of the system.
    double totalEnergy() {

	// Compute the total kinetic energy of the system.
	double kinetic_energy = 0.0;

	// Add in the kinetic energy of each valid planet.
	for (PlanetIter Pptr = _planets.first(); 
	     !Pptr.isPastEnd(); 
	     Pptr.advance()) {

	    Planet p = Pptr.element();
	    kinetic_energy += 0.5 * p.mass * p.vel.normSq();
	}

	// Compute the total potential energy of the system.
	double potential_energy = 0.0;

	for (PlanetIter Pptr = _planets.first(); 
	     !Pptr.isPastEnd(); 
	     Pptr.advance()) {

	    Planet p = Pptr.element();

	    for (PlanetIter Qptr = _planets.first(); 
		 !Qptr.isPastEnd(); 
		 Qptr.advance()) {

		Planet q = Qptr.element();

		// Find the distance between these planets.
		double r = (p.pos.minus(q.pos).norm());
	    
		if (p != q) {
		    // Add in the potential between these planets.
		    potential_energy += -G * p.mass * q.mass / r;
		}
	    }
	}

	// Return the total energy.
	return kinetic_energy + potential_energy;
       
    }


    // Compute the total angular momentum of the system.
    double totalAngularMomentum() {
	double angular_momentum = 0.0;

	for (PlanetIter Pptr = _planets.first(); 
	     !Pptr.isPastEnd(); 
	     Pptr.advance()) {

	    Planet p = Pptr.element();

	    angular_momentum += p.mass * (p.pos.cross(p.vel));
	}
    
	return angular_momentum;
    }


    // Advance the simulation by 1 time step.
    void advance()
    {
	// Only advance if the simulation is running.
	if (running())
	    {
		// Initialize the next values and
		// Save the current values
		for (PlanetIter Pptr = _planets.first(); 
		     !Pptr.isPastEnd(); 
		     Pptr.advance()) {
	    
		    Planet p = Pptr.element();
		    p.posNext.set(p.pos);
		    p.velNext.set(p.vel);
		    p.posPrev.set(p.pos);
		    p.velPrev.set(p.vel);
		}
	    
		// Compute the acceleration at the start time.
		computeAccel();

		// STEP 1:

		// System.out.println("advance!");
		if (!_runge_kutta) {

		    // Add these values into the accumulator.
		    for (PlanetIter Pptr = _planets.first(); !Pptr.isPastEnd(); 
			 Pptr.advance()) {
		
			Planet p = Pptr.element();
		
			// p(t+dt) = v(t) * dt
			p.posNext.add(p.vel.times(_time_step));
			// v(t+dt) = acc(p(t)) * dt
			p.velNext.add(p.acc.times(_time_step));
		
			p.pos.set(p.posNext);
			p.vel.set(p.velNext);
		    }


		} else {

		    // This is an implementation of the classic fourth-order
		    // Runge-Kutta method.

		    // Add these values into the accumulator.
		    for (PlanetIter Pptr = _planets.first(); !Pptr.isPastEnd(); 
			 Pptr.advance()) {
		
			Planet p = Pptr.element();
		
			// dp1 = v(t) * dt/6
			p.posNext.add(p.vel.times(_time_step/6.0));
			// dv1 = acc(p(t)) * dt/6
			p.velNext.add(p.acc.times(_time_step/6.0));
		
			// make a first step attempt
		
			// t1 = t + dt/2
			// p1 = p(t) + v(t) * dt/2
			p.pos = p.posPrev.plus(p.vel.times(_time_step/2.0));
			// v1 = v(t) + acc(p(t)) * dt/2
			p.vel = p.velPrev.plus(p.acc.times(_time_step/2.0));
		    }
	    
		    // Recreate the quadtree at the first step
		    // and then compute the acceleration there.
		    recreateQuadtree();
		    computeAccel();
	    
		    // STEP 2:
	    
		    for (PlanetIter Pptr = _planets.first(); !Pptr.isPastEnd(); 
			 Pptr.advance()) {
		
			Planet p = Pptr.element();
		
			// dp2 = 1/3  v1 * dt
			p.posNext.add(p.vel.times(_time_step/3.0));
			// dv2 = 1/3  a(p1) * dt
			p.velNext.add(p.acc.times(_time_step/3.0));
		
			// make a second step attempt
			// t2 = t + dt/2
			// p2 = p(t) + v1 * dt/2
			p.pos = p.posPrev.plus(p.vel.times(_time_step/2.0));
			// v2 = v(t) + acc(p1) * dt/2
			p.vel = p.velPrev.plus(p.acc.times(_time_step/2.0));
		    }
	    
		    // Recreate the quadtree at the first step
		    // and then compute the acceleration there.
		    recreateQuadtree();
		    computeAccel();
	    
		    // STEP 3:
		    for (PlanetIter Pptr = _planets.first(); !Pptr.isPastEnd(); 
			 Pptr.advance()) {
		
			Planet p = Pptr.element();
		
			// dp2 = 1/3  v2 * dt
			p.posNext.add(p.vel.times(_time_step/3.0));
			// dv2 = 1/3  a(p2) * dt
			p.velNext.add(p.acc.times(_time_step/3.0));
		
			// make a third step attempt
			// t3 = t + dt
			// p2 = p(t) + v2 * dt
			p.pos = p.posPrev.plus(p.vel.times(_time_step));
			// v2 = v(t) + acc(p2) * dt
			p.vel = p.velPrev.plus(p.acc.times(_time_step));
		    }
	    
		    // Recreate the quadtree at the first step
		    // and then compute the acceleration there.
		    recreateQuadtree();
		    computeAccel();
	    
		    // STEP 4:
		    for (PlanetIter Pptr = _planets.first(); !Pptr.isPastEnd(); 
			 Pptr.advance()) {
		
			Planet p = Pptr.element();
		
			// dp2 = 1/6  v3 * dt
			p.pos = p.posNext.plus(p.vel.times(_time_step/6.0));
			// dv2 = 1/6  acc(p3) * dt
			p.vel = p.velNext.plus(p.acc.times(_time_step/6.0));
		    }

		}
		// Recreate the quadtree with these new positions.
		recreateQuadtree();

		// Increment the simulation time by one time step.
		_time += _time_step;
	    }
    }        


    // Draw the given rectangle (whose corners are in simulation
    // coordinates) onto the screen.
    void drawRectangle(Rectangle rect)
    {
	// Switch to the appropriate color.
	_gc.setColor(_grid_color);

	Point min = posToPixel(rect.minCorner(),_univ);
	Point max = posToPixel(rect.maxCorner(),_univ);

	// draw the rectangle.
	_gc.drawRect(min.x, max.y, max.x-min.x, min.y-max.y);
    }


    // Compute some approximate measure of the error that would be
    // produced in computing the acceleration on the given body if we
    // approximated a group of points by their center of mass.  The 
    // radius input is an upper bound on the radius of a bounding sphere
    // for those points whose center is situated at the center of mass. 
    double approxError(Planet p, Vector center, double radius) {
	// This error should grow as radius grows, and it should shrink as
	// the distance between body and the center of mass grows.  It
	// should also grow as the mass of those points grows (since more
	// mass means more acceleration which means more error in the
	// calculation).

	// First, compute the squared distance between the given body and
	// the center of mass.
	double d2 = p.pos.minus(center).normSq();

	// If d2 is too small, we cannot divide by it, so say that the
	// error is infinite.
	if (d2 <= Double.MIN_VALUE) return Double.MAX_VALUE;

	// Otherwise, the following is a good appoximation of the error.
	return ((radius * radius) / d2);
    }


    // Draw the Quadtree grid onto the given GC.  The other parameters
    // are as for draw.
    void drawGravGrid(Planet p, Graphics gc, Universe u) {

	// Keep track of the drawing information so that we can service
	// the calls to the drawRectangle function below.
	_gc = gc;
	_univ = u;

	// Call the Quadtree to draw its grid.
	_quadtree.drawGravGrid(p,_max_error);

    }
    // Compute the approximate acceleration on the given body due to a
    // group of bodies whose center of mass and total mass are as
    // given.  This acceleration is added onto the given acceleration
    // parameter.
    void approxAccel(Planet p, Vector center, double mass, Vector accel) {
    
	forceCount++;

	// Find a vector pointing in the direction of the acceleration.
	Vector R = p.pos.minus(center);

	// Find the distance between those points, which is simply the
	// length of the vector that stretches between them.
	double r = R.norm();

	// If r is reallly small... well, bad things will happen.
	// we'll just ignore the interaction in that icky case.
	//
	// Otherwise, just compute the acceleration using Newton's brain
	if (r > Double.MIN_VALUE)
	    {
		// Add the appropriate acceleration of p1 due to p2
		accel.add(R.times(-(G * mass) / (r * r * r)));
	
	    }
    }

    // Compute the approximate acceleration on the given body due to a
    // group of bodies whose center of mass and total mass are as
    // given.  This acceleration is added onto the given acceleration
    // parameter.
    void computeAccel(Planet p1, Planet p2, Vector accel) {
	approxAccel(p1,p2.pos,p2.mass,accel);
    }

    // Compute the accelerations of every planet due to the gravity of
    // every other planet.
    void computeAccel()
    {
	forceCount=0;
	// Compute the acceleration of each planet.

	if (_use_quad) {
	    //
	    // do it with style
	    //
	    for (PlanetIter Pptr = _planets.first();
		 !Pptr.isPastEnd();
		 Pptr.advance()) {
	
		Planet p = Pptr.element();

		p.acc.zero();
		_quadtree.computeAccel(p,p.acc,_max_error);

	    } 
	} else {
	
	    //
	    // do it the ol' fashioned O(n^2) way
	    //
	    for (PlanetIter Pptr = _planets.first();
		 !Pptr.isPastEnd();
		 Pptr.advance()) {

		Planet p = Pptr.element();

		p.acc.zero();

		for (PlanetIter Qptr = _planets.first();
		     !Qptr.isPastEnd();
		     Qptr.advance()) {
	
		    Planet q = Qptr.element();
		    if (p != q) {
			computeAccel(p,q,p.acc);
		    }
		
		}
	    } 
	}
    }


    // Find a bounding rectangle for all of the bodies currently in
    // this simulation.
    void computeBoundingRect()
    {
	// Start with an empty rectangle.
	_bounding_rect.x_min = _bounding_rect.y_min = Double.MAX_VALUE;
	_bounding_rect.x_max = _bounding_rect.y_max = -Double.MAX_VALUE;

	// Check the components of each planet to see if they are outside
	// of the current rectangle.
	for (PlanetIter Pptr = _planets.first();
	     !Pptr.isPastEnd();
	     Pptr.advance()) {
	
	    Planet p = Pptr.element();
	    if (p.pos.x < _bounding_rect.x_min)
		_bounding_rect.x_min = p.pos.x;
	    if (p.pos.x > _bounding_rect.x_max)
		_bounding_rect.x_max = p.pos.x;
	    if (p.pos.y < _bounding_rect.y_min)
		_bounding_rect.y_min = p.pos.y;
	    if (p.pos.y > _bounding_rect.y_max)
		_bounding_rect.y_max = p.pos.y;
	}
    }

    // Recreate the quadtree with the new positions of the bodies.
    void recreateQuadtree()
    {
	// Recompute the bounding rectangle for these points.
	computeBoundingRect();

	// Create a new quadtree to store the points points that we just
	// loaded.  First, we must free the other one.
	_quadtree = new Quadtree(this, _bounding_rect);

	// Now, insert the planets into the Quadtree.
	for (PlanetIter Pptr = _planets.first();
	     !Pptr.isPastEnd();
	     Pptr.advance()) {
	
	    Planet p = Pptr.element();
	    _quadtree.insert(p);
	}
    }

}

/*****************************************************************************/
/*                                                                           */
/* QuadTree.java                                                             */
/*                                                                           */
/* Author: Hardik Bhatt                                                      */
/* Date: June 8, 2005                                                        */
/*                                                                           */
/* A class that implements a QuadTree class using the array-                 */

/*****************************************************************************/
import java.io.*;
public class Quadtree
{
    Rectangle bounds;
    int num; // # points within bounds
    Point point; // stores point for full leaf (num = 1)
    Quadtree sub[]; // subquadrants for non-leaf (num > 1)

    public static void query (Vector set, Rectangle range)
    {
	if (bounds.intersects (range))
	{
	    if (num == 1)
	    {
		if (range.contains (point))
		{
		    set.addElement (point);
		}
	    }
	    else if (num > 1)
	    {
		for (int i = 0 ; i < 4 ; i++)
		{
		    sub [i].query (set, range);
		}
	    }
	}
    }


    public static void insert (Point p)
    {
	if (num == 0)
	{
	    point = p;
	}
	else if (num == 1)
	{
	    split ();
	    sub [quadrant (point)].insert (point);
	    point = null;
	    sub [quadrant (p)].insert (p);
	}
	else
	{
	    sub [quadrant (p)].insert (p);
	}
	num++;
    }



}

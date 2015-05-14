//---------------------------------------------------------------------//
/* Hardik Bhatt                Assignment 4                            */
/*                                                                     */
/* Discription:  Essentially similar to the previous however this time */
/* instead of a wire world the world is using solid objects, Fog and   */
/* shadows.                                                            */
/*                                                                     */
/*                                                                     */
/*                                                                     */
/*                                                                     */
//---------------------------------------------------------------------//

#include <stdlib.h>			// standard definitions
#include <iostream.h>			// C++ I/O
#include <stdio.h>			// C I/O (for sprintf) 
#include <math.h>			// standard definitions

#include <GL/glut.h>			// GLUT
#include <GL/glu.h>			// GLU
#include <GL/gl.h>			// OpenGL

#define FOVY 60

#define NO_SHIPS 2           /*number of ships */
#define NO_CLOUDS 8          /*number of clouds */
#define SHIP_TVEL_INC 2      /*incremen/decrement amounts for ship velocities */
#define SHIP_TVEL_DEC -2
#define SHIP_RVEL_INC 0.2
#define SHIP_RVEL_DEC -0.2
#define INNER 1              /*inner radius of torus */
#define OUTER 4              /*outer radius of torus */ 
#define SPHERERADIUS 10            /*radius of sphere */
#define NEAR 1               /*clipping plane near and far */
#define FAR 10000
#define NO_LIGHTS 2
GLboolean MYPAUSE = false; //boolean that keep track of pause of game
GLboolean MYTRACE = false;

/* window dimensi0ns */
int wh=300;
int ww=400;

GLfloat Radious = 3.141598/180;
/* parameters for drawing sphere and torus */
GLint NO_STACKS=20;
GLint NO_SLICES=20;
GLint NO_SIDES=20;
GLint NO_RINGS=20;

/*variable that stores which ship is in use*/
GLint Ship = 0;

GLfloat ship_color[4]={1.0,0.627,0.478};  /*color of a ship */
GLfloat ground_color[4]={0.0, 1.0, 0.0}; 
 
/* The ground should be located at z=0 plane, you can use (0,0,0) as the lower left corner and (200, 200, 0) as the upper right corner. */

/*several initializations */
/* cloud coordinates(x,y,z) for each cloud */ 
GLfloat cloud_coords[NO_CLOUDS][3]={{30, 30, 30},
			   {30, 170, 15},
			   {80, 110, 25},
			   {70,  60, 12},
			   {90, 150, 13},
			   {120,  80, 17},
			   {150,  40, 15},
			   {160, 170, 22}};

/*cloud colors(rgb) for each cloud */
GLfloat cloud_colors[NO_CLOUDS][3]={{0.30, 0.30, 0.30},
			   {1.00, 0.00, 0.00},
			   {0.00, 1.00, 0.00},
			   {0.00, 0.00, 1.00},
			   {1.00, 1.00, 0.00},
			   {1.00, 0.00, 1.00},
			   {0.00, 1.00, 1.00},
			   {1.00, 1.00, 1.00}};
					 
/*ship coordinates for all ships, (x,y,z) for each ship*/
GLfloat ship_coords[NO_SHIPS][3]={{100.0,10.0,10.0},
			  {105.0,0.0,15.0}};


/*velocites (translational and rotational) for each ship */
GLfloat ship_velocities[NO_SHIPS][2]={{0.0,0.0},
				     {-2.0,0.0}};


/*heading initializations for each ship */
GLfloat ship_headings[NO_SHIPS]={0.0, 180.0};

/* NEW !!!! Light and Material Related */
/* spcular color component for an object */
GLfloat spec_color[4]={1.0, 1.0, 1.0, 1.0};

GLfloat global_ambient_intensity[4] = {0.2, 0.2, 0.2, 1.0};

/* light coordinates */
GLfloat light_coords[NO_LIGHTS][4]={{0.5,-1.0,1.5,0.0},
                           {-0.5,1.0,0.5,0.0}};

/* light components */
GLfloat light_ambient[NO_LIGHTS][4]={{0.2, 0.2, 0.2, 1.0},
                            {0.2, 0.2, 0.2, 1.0}};


GLfloat light_diffuse[NO_LIGHTS][4]={{1.0, 1.0, 1.0, 1.0},
                            {1.0, 1.0, 1.0, 1.0}};


GLfloat light_specular[NO_LIGHTS][4]={{2.0, 2.0, 2.0, 1.0},
                             {2.0, 2.0, 2.0, 1.0}}; 

GLfloat fog_color[]={ 0.5, 0.5, 0.5, 1.0}; /* fog color */

GLfloat shininess = 100.0;


/* initial viewer location and At location*/
static GLdouble viewer[]= {0.0, 0.0, 0.0}; 
static GLdouble at[]= {0.0, 0.0, 0.0};

void myInit();
void myReshape(int ww, int wh);
void myDisplay();
void myKeyboard(unsigned char c, int x, int y);
void myMouse(int btn, int state, int x, int y);
void myTimeOut(int id);

//-----------------------------------------------------------------------
// main program
//	
//-----------------------------------------------------------------------
int main(int argc, char **argv)
{
    				
    glutInit(&argc, argv);   //initialize glut and gl
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(ww, wh);   // window
    //glutInitWindowPosition(0,0);    // upper left
    glutCreateWindow("My OpenGL Program: Assignment 3"); 	// create the window

	//register callbacks
	glutReshapeFunc(myReshape);     // call myReshape if window is resized
    glutDisplayFunc(myDisplay);		//  call myDisplay to redraw window
    //keyboard entries
    glutKeyboardFunc(myKeyboard);   // call myKeyboard when key is hit
	glutMouseFunc(myMouse);         // call in mouse event
	
	glEnable(GL_DEPTH_TEST);
	glutTimerFunc(100, myTimeOut, 0);
    
    myInit();				// our own initializations

    glutMainLoop();			// pass control to GLUT, start it running
    return 0;               // ANSI C expects this
}

//-----------------------------------------------------------------------
//	Sets up some default OpenGL values, like the viewer and the at for
//  the look at function in myDisplay.
//-----------------------------------------------------------------------
void myInit()
{
    glClearColor(fog_color[0], fog_color[1], fog_color[2], fog_color[3]);		// background color
    glShadeModel(GL_SMOOTH);		// smooth shading

	viewer[0] = ship_coords[Ship][0] + OUTER * sin(ship_headings[Ship]*Radious);
	viewer[1] = ship_coords[Ship][1] + OUTER * cos(ship_headings[Ship]*Radious);
	viewer[2] = ship_coords[Ship][2];

	at[0] = viewer[0] + sin(ship_headings[Ship]*Radious);
	at[1] = viewer[1] + cos(ship_headings[Ship]*Radious);
	at[2] = viewer[2];

}

//-----------------------------------------------------------------------
// reshape callback function
//	This is called each time the window is reshaped
//-----------------------------------------------------------------------
void myReshape(int ww, int wh) 
{
	
   // update projection
  glViewport (0, 0, ww,  wh);  // update the viewport

/* Use a perspective view */

  glMatrixMode(GL_PROJECTION);              // update the projection
  glLoadIdentity();               

  gluPerspective(FOVY,(GLdouble) ww/wh, NEAR, FAR);
  
  glMatrixMode(GL_MODELVIEW);

  glutPostRedisplay();                      // request redisplay
}


//-----------------------------------------------------------------------
// display callback function
//	This is called each time application needs to redraw itself.
// This function draws the ship and the clouds for the world that is made
//-----------------------------------------------------------------------
void myDisplay()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);       // clear the window
    int i, j;
	/* Update viewer position in modelview matrix */

	glLoadIdentity();
	gluLookAt(viewer[0], viewer[1], viewer[2], at[0], at[1], at[2], 0.0, 0.0, 1.0);

	
	glFogi(GL_FOG_MODE, GL_EXP);		// Fog Mode
	glFogfv(GL_FOG_COLOR, fog_color);			// Set Fog Color
	glFogf(GL_FOG_DENSITY, 0.01f);				// How Dense Will The Fog Be
	glHint(GL_FOG_HINT, GL_DONT_CARE);			// Fog Hint Value
	glFogf(GL_FOG_START, 1.0f);				// Fog Start Depth
	glFogf(GL_FOG_END, 5.0f);				// Fog End Depth
	glEnable(GL_FOG);					// Enables GL_FOG
	
	glEnable(GL_LIGHTING);
	
	glEnable(GL_LIGHT0);
	glLightfv(GL_LIGHT0, GL_POSITION, light_coords[0]);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light_ambient[0]);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light_diffuse[0]);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light_specular[0]);

	glEnable(GL_LIGHT1);
	glLightfv(GL_LIGHT1, GL_POSITION, light_coords[1]);
	glLightfv(GL_LIGHT1, GL_AMBIENT, light_ambient[1]);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, light_diffuse[1]);
	glLightfv(GL_LIGHT1, GL_SPECULAR, light_specular[1]);

   	
	for (i =0; i < NO_CLOUDS; i++)
	{
		glPushMatrix();
		glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, cloud_colors[i]);
		glTranslatef(cloud_coords[i][0], cloud_coords[i][1], cloud_coords[i][2]);
		glutSolidSphere(SPHERERADIUS, NO_STACKS, NO_SLICES); // parameters: radius, number of slices (longitude), no stacks (latitude)
		glPopMatrix();
	}

	for (j =0; j < NO_SHIPS; j++)
	{
		glPushMatrix();
		glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ship_color);
		glTranslatef(ship_coords[j][0], ship_coords[j][1], ship_coords[j][2]);
		glutSolidTorus(INNER, OUTER, NO_SIDES, NO_RINGS); // inner radius, outer radis, no sides, no rings
		glRotatef(90, 0, 1, 0);
		glutSolidTorus(INNER, OUTER, NO_SIDES, NO_RINGS); // inner radius, outer radis, no sides, no rings
		glPopMatrix();
	}

	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ground_color);
	glRectf(0.0, 0.0, 200.0, 200.0);
    glFlush();				// force OpenGL to render now
    glutSwapBuffers();			// swap buffers

}

//-----------------------------------------------------------------------
// keyboard callback function
//	This is called whenever a keyboard key is hit.
//-----------------------------------------------------------------------

void myKeyboard(unsigned char c, int x, int y)
{
    switch (c)
    {
      case 'q':
		  exit(0);			// exit
		  break;
	  case 'a':
		  ship_velocities[Ship][0] += SHIP_TVEL_INC;    //increase by constant amount
		  break;
	  case 's':
		  ship_velocities[Ship][0] += SHIP_TVEL_DEC;    //decrease by constant amount
		  break;
	  case 'l':
		  ship_velocities[Ship][1] += SHIP_RVEL_INC;    //increase the ship's rightward rotation
		  break;
	  case 'k':
		  ship_velocities[Ship][1] += SHIP_RVEL_DEC;    //decrease the ship's rightward rotation
		  break;
	}
    glutPostRedisplay();		// request redisplay
}

//-----------------------------------------------------------------------
// mouse callback function
//	
//-----------------------------------------------------------------------
void myMouse(int btn, int state, int x, int y)
{
	if ((btn == GLUT_LEFT_BUTTON)&&(state == GLUT_DOWN))   //changes from ship1 ro ship 2
	{
	   if(Ship == 0)
	   {
		   Ship = 1;
		   viewer[0] = ship_coords[Ship][0] + OUTER * sin(ship_headings[Ship]*Radious);
		   viewer[1] = ship_coords[Ship][1] + OUTER * cos(ship_headings[Ship]*Radious);
		   viewer[2] = ship_coords[Ship][2];
		   at[0] = viewer[0] + sin(ship_headings[Ship]*Radious);
	       at[1] = viewer[1] + cos(ship_headings[Ship]*Radious);
	       at[2] = viewer[2];

	   }
	   else
	   {
		   Ship = 0;
		   viewer[0] = ship_coords[Ship][0] + OUTER * sin(ship_headings[Ship]*Radious);
		   viewer[1] = ship_coords[Ship][1] + OUTER * cos(ship_headings[Ship]*Radious);
		   viewer[2] = ship_coords[Ship][2];
		   at[0] = viewer[0] + sin(ship_headings[Ship]*Radious);
	       at[1] = viewer[1] + cos(ship_headings[Ship]*Radious);
	       at[2] = viewer[2];

	   }
	}
	
	if ((btn == GLUT_MIDDLE_BUTTON) && (state == GLUT_DOWN) && MYPAUSE == false)  //Pause
	{
		glutIdleFunc(NULL);
		MYPAUSE = true;
	}
	else if ((btn == GLUT_MIDDLE_BUTTON) && (state == GLUT_DOWN) && MYPAUSE == true)  //UnPause
	{
		glutTimerFunc(100, myTimeOut, 0);
		MYPAUSE = false;
	}

	if ((btn == GLUT_RIGHT_BUTTON) && (state == GLUT_DOWN))  //Step by step runtime after pause 
	{
		glutIdleFunc(NULL);
		MYTRACE = true;
	}

	glutPostRedisplay();		// request redisplay
}

//-----------------------------------------------------------------------
// myTimeOut callback function
//  essentially runs the whole program by updation the Heading, Location,
//  Velocity, as well as taking care of the Pause feature.
//-----------------------------------------------------------------------

void myTimeOut(int id) 
{

	if (MYPAUSE == false || MYTRACE == true)
	{
		//translational
		ship_coords[0][0] += ship_velocities[0][0]*sin(ship_headings[0]*Radious);
		ship_coords[0][1] += ship_velocities[0][0]*cos(ship_headings[0]*Radious);

		ship_coords[1][0] += ship_velocities[1][0]*sin(ship_headings[1]*Radious);
		ship_coords[1][1] += ship_velocities[1][0]*cos(ship_headings[1]*Radious);

	

		//ship_coords[Ship][0] += ship_velocities[Ship][1]*sin(ship_headings[Ship]*Radious);
		ship_headings[Ship] += ship_velocities[Ship][1];
		//ship_coords[Ship][1] += ship_velocities[Ship][1]*cos(ship_headings[Ship]*Radious);
		ship_headings[Ship] += ship_velocities[Ship][1];

		viewer[0] = ship_coords[Ship][0] + OUTER*sin(ship_headings[Ship]*Radious);
		viewer[1] = ship_coords[Ship][1] + OUTER*cos(ship_headings[Ship]*Radious);
		viewer[2] = ship_coords[Ship][2];
		at[0] = viewer[0] + sin(ship_headings[Ship]*Radious);
	    at[1] = viewer[1] + cos(ship_headings[Ship]*Radious);
	    at[2] = viewer[2];

        if(MYTRACE == true)
		{
			printf("Ships Heading = %i\n", ship_headings[0]);
			printf("Ships Heading = %i\n", ship_headings[1]);

			printf("Ships Translational Velocity = %i\n", ship_velocities[0][0]);
			printf("Ships Translational Velocity = %i\n", ship_velocities[1][0]);

			printf("Ships Rotational Velocity = %i\n", ship_velocities[0][1]);
			printf("Ships Rotational Velocity = %i\n", ship_velocities[1][1]);

			printf("Ship 1's coordinate x = %i and y = %i\n", ship_coords[0][0],ship_coords[0][1]);
			printf("Ship 2's coordinate x = %i and y = %i\n", ship_coords[1][0],ship_coords[1][1]);

			printf("\n");
		}

		MYTRACE = false;
	}

	// advance the state of animation incrementally
	glutPostRedisplay();			  // request redisplay
	glutTimerFunc(100, myTimeOut, 0);  // request next timer event
}
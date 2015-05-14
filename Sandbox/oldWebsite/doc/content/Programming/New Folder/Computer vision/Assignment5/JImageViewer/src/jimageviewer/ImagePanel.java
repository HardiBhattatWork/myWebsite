/**
    \file ImagePanel.java
    ImagePanel class.

    \author George J. Grevera, Ph.D., ggrevera@sju.edu

    Copyright (C) 2006, George J. Grevera

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
    USA or from http://www.gnu.org/licenses/gpl.txt.

    This General Public License does not permit incorporating this
    code into proprietary programs.  (So a hypothetical company such
    as GH (Generally Hectic) should NOT incorporate this code into
    their proprietary programs.)
 */
package jimageviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

//----------------------------------------------------------------------
/** \brief ImagePanel class.
 */
class ImagePanel
    extends JPanel implements MouseMotionListener, MouseListener {
  public void mouseExited(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
    //System.out.println( "released" );
  }

  public void mousePressed(MouseEvent e) {
    //System.out.println( "pressed" );
  }

  public void mouseClicked(MouseEvent e) {
    System.out.println("clicked " + e.getX() + "," + e.getY());
    //make a temp copy of the input image and make the foreground -1
    int temp[][] = new int[mParent.mImage.mH][mParent.mImage.mW];
    for (int row = 0, i = 0; row < mParent.mImage.mH; row++) {
      for (int col = 0; col < mParent.mImage.mW; col++) {
        if (mParent.mImage.mImage[i] == 0) {
          temp[row][col] = 0;
        }
        else {
          temp[row][col] = -1;
        }
        i++;
      }
    }

    int startR = e.getY(), startC = e.getX();
    startR /= mScale;
    startC /= mScale;
    //make sure that we are in bounds
    if (startR < 0) {
      startR = 0;
    }
    if (startC < 0) {
      startC = 0;
    }
    if (startR >= mParent.mImage.mH) {
      startR = mParent.mImage.mH - 1;
    }
    if (startC >= mParent.mImage.mW) {
      startC = mParent.mImage.mW - 1;
    }
    //make sure that the point the user clicked is within some object
    if (temp[startR][startC] == 0) {
      return;
    }

    area = 0;
    MouseX = 0;
    MouseY = 0;
    count = 0;
    ULx = startC;
    ULy = startR;
    LRx = startC;
    LRy = startR;
    search(temp, 5, startR, startC);
    JOptionPane.showMessageDialog(this,
                                  "The " + mIs4Connected + " Area is " + area +
                                  " The Centroid is X =" + (MouseX / count) +
                                  " Y =" + (MouseY / count) +
                                  " The Bounding box is between Upper Left X = " +
                                  ULx + " Upper Left Y =" + ULy +
                                  " Lower Right X =" + LRx + " Lower Right Y =" +
                                  LRy);
  }

  public int ULx, ULy, LRx, LRy;
  public boolean mIs4Connected = true;
  public int area = 0;
  public double MouseX = 0;
  public double MouseY = 0;
  public int count = 0;
  //----------------------------------------------------------------------
  /** \brief search
   */
  private void search(int temp[][], int label, int r, int c) {
    //are we out of bounds?
    if (r < 0 || c < 0) {
      return;
    }
    if (r >= mParent.mImage.mH || c >= mParent.mImage.mW) {
      return;
    }
    if (temp[r][c] != -1) {
      return;
    }
    //must be -1 so now we can label it
    temp[r][c] = label;

    if (ULx > c)     ULx = c;
    if (ULy > r)     ULy = r;
    if (LRx < c)     LRx = c;
    if (LRy < r)     LRy = r;

    //assume 4-connected
    if (mIs4Connected) {
      MouseX = MouseX + r;
      MouseY = MouseY + c;
      count++;
      area++;
      search(temp, label, r - 1, c); //up one row
      search(temp, label, r + 1, c); //down one row
      search(temp, label, r, c - 1); //left
      search(temp, label, r, c + 1); //right
    }
    else {
      MouseX = MouseX + r;
      MouseY = MouseY + c;
      count++;
      area++;
      search(temp, label, r - 1, c); //up one row
      search(temp, label, r + 1, c); //down one row
      search(temp, label, r, c - 1); //left
      search(temp, label, r, c + 1); //right
      search(temp, label, r - 1, c - 1); //top-left
      search(temp, label, r + 1, c + 1); //bottom-right
      search(temp, label, r + 1, c - 1); //bottom-left
      search(temp, label, r - 1, c + 1); //top-right
    }
  }

  JImageViewer mParent;
  private boolean mMouseMoveValid = false;
  private int mMouseX; ///< mouse movement x position
  private int mMouseY; ///< mouse movement y position
  private Image mDoubleBuffer = null; ///< to avoid flicker
  //----------------------------------------------------------------------
  /** \brief Ctor
   */
  ImagePanel(JImageViewer p) {
    mParent = p;
    this.addMouseMotionListener(this);
    this.addMouseListener(this);
  }

  //----------------------------------------------------------------------
  /** \brief simply call paint
   */
  public void update(Graphics g) {
    paint(g);
  }

  public double mScale = 1.0;
  //----------------------------------------------------------------------
  /** \brief redraw the panel contents.
   *
   * To avoid flicker (when rapidly redrawing), we will draw into a single
   * image and then draw that image to the panel.
   */
  public void paint(Graphics g) {
    //if the size of the panel has changed, we need a new doublebuffer of
    // the correct size.
    Dimension d = getSize();
    if (mDoubleBuffer == null || mDoubleBuffer.getWidth(null) != d.width
        || mDoubleBuffer.getHeight(null) != d.height) {
      mDoubleBuffer = createImage(d.width, d.height);
    }
    //draw into the doublebuffer
    Graphics dbg = mDoubleBuffer.getGraphics();
    dbg.setColor(Color.BLACK);
    dbg.fillRect(0, 0, d.width, d.height);
    dbg.setColor(Color.WHITE);
    if (mParent.mImage != null && mParent.mImage.mScreenImage != null) {
      dbg.drawImage(mParent.mImage.mScreenImage, 0, 0,
                    (int) (mParent.mImage.mW * mScale),
                    (int) (mParent.mImage.mH * mScale), null);
      if (mMouseMoveValid) {
        dbg.drawString("(" + mMouseX + "," + mMouseY + ")",
                       mParent.mImage.mScreenImage.getWidth() + 20, 100);
      }
    }
    else if (mMouseMoveValid) {
      dbg.drawString("(" + mMouseX + "," + mMouseY + ")", 100, 100);
    }
    //draw the doublebuffer on the panel
    //d.width = (int) (mParent.mImage.mW * mScale);
    //d.height = (int) (mParent.mImage.mH * mScale);
    g.drawImage(mDoubleBuffer, 0, 0, d.width, d.height, null);
  }

  //----------------------------------------------------------------------
  /** \brief track mouse movement when button is not down.
   */
  public void mouseMoved(MouseEvent e) {
    mMouseMoveValid = true;
    mMouseX = e.getX();
    mMouseY = e.getY();
    repaint();
  }

  //----------------------------------------------------------------------
  /** \brief track mouse movement when button is down.
   */
  public void mouseDragged(MouseEvent e) {
    mMouseMoveValid = true;
    mMouseX = e.getX();
    mMouseY = e.getY();
    repaint();
  }
}
//----------------------------------------------------------------------
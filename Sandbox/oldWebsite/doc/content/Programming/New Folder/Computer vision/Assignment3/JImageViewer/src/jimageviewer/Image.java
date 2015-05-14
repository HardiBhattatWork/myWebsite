/**
    \file Image.java
    Image class.

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

import java.awt.image.BufferedImage;
import javax.media.jai.PlanarImage;
import javax.media.jai.JAI;
//----------------------------------------------------------------------
/** \brief Image class
 *
 *  This class contains the actual image data.
 */
public class Image {
  BufferedImage  mOriginalImage = null; ///< original input image
  BufferedImage  mScreenImage   = null; ///< (possibly modified input) image drawn on screen
  boolean        mIsColor;              ///< true if color, false if grey
  boolean        mImageModified;        ///< true if image has been modified
  int            mW;                    ///< image width
  int            mH;                    ///< image height
  int            mMin;                  ///< min image value
  int            mMax;                  ///< max image value
  /** \brief actual image data.
   *
   *  Each array value contains either 16-bit gray data (displayed via mScreenImage
   *  above as a 24-bit rgb image which is, in effect, 8-bit gray data) or as 24-bit
   *  rgb data.
   */
  int[]          mImage;
  //----------------------------------------------------------------------
  public Image ( final String fileName ) {
      //load the image
      PlanarImage  pi = JAI.create( "fileload", fileName );
      mW = pi.getWidth();
      mH = pi.getHeight();
      //convert everything to one standard, sane, color image type
      mOriginalImage = pi.getAsBufferedImage();
      //16-bit grey images must be treated differently
      //if (mOriginalImage.getType() == BufferedImage.TYPE_USHORT_GRAY) {
      if(mOriginalImage.getType() == BufferedImage.TYPE_USHORT_GRAY ||
         mOriginalImage.getType() == BufferedImage.TYPE_BYTE_GRAY   ||
         mOriginalImage.getType() == BufferedImage.TYPE_BYTE_BINARY ) {

          mIsColor = false;
          int[]  empty = null;
          mImage = mOriginalImage.getData().getPixels( 0, 0, mW, mH, empty );
          mMin = mImage[0];
          mMax = mImage[0];
          for (int i=0; i<mImage.length; i++) {
              if (mImage[i] < mMin)    mMin = mImage[i];
              if (mImage[i] > mMax)    mMax = mImage[i];
          }

          mScreenImage = new BufferedImage( mW, mH, BufferedImage.TYPE_INT_RGB );
          //scale 16-bit gray data to 24-bit rgb data (which is, in effect,
          // only 8-bit gray data).
          int[]  tempImage = new int[mImage.length];
          double  diff = mMax-mMin;
          if (diff==0)    diff=1;
          for (int i=0; i<mImage.length; i++) {
              double  scaledGray = 255.0*(mImage[i]-mMin)/diff + 0.5;
              if (scaledGray<0)    scaledGray = 0;
              if (scaledGray>255)  scaledGray = 255;
              int  g = (int)scaledGray;
              tempImage[i] = (g<<16) | (g<<8) | g;
          }
          mScreenImage.setRGB( 0, 0, mW, mH, tempImage, 0, mW );
      } else {
          mIsColor = true;
          //format TYPE_INT_ARGB will be saved to mImage
          mImage = mOriginalImage.getRGB(0, 0, mW, mH, null, 0, mW);
          mMin = mMax = mImage[0] & 0xff;
          for (int i=0; i<mImage.length; i++) {
              mImage[i] &= 0xffffff;  //just to insure that we only have 24-bit rgb
              final int  r = (mImage[i] & 0xff0000) >> 16;
              final int  g = (mImage[i] & 0xff00) >> 8;
              final int  b =  mImage[i] & 0xff;
              if (r<mMin)    mMin = r;
              if (g<mMin)    mMin = g;
              if (b<mMin)    mMin = b;
              if (r>mMax)    mMax = r;
              if (g>mMax)    mMax = g;
              if (b>mMax)    mMax = b;
          }
          mScreenImage = new BufferedImage( mW, mH, BufferedImage.TYPE_INT_RGB );
          mScreenImage.setRGB( 0, 0, mW, mH, mImage, 0, mW );
      }
      System.out.println( "min=" + mMin + ", max=" + mMax );
  }

}
//----------------------------------------------------------------------
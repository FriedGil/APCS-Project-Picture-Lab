import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method to set the red and blue to 0 */
  public void greenOnly()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
        pixelObj.setRed(0);
      }
    }
  }

    
  
  /** Method to set the red,green,blue to (255 - its original value)  */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(255-pixelObj.getBlue());
        pixelObj.setRed(255-pixelObj.getRed());
        pixelObj.setGreen(255-pixelObj.getGreen());
      }
    }
  }
  
  /** Method to set all red,green,blue to the average of the three values  */
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        double avg = pixelObj.getAverage();
        pixelObj.setBlue((int) avg);
        pixelObj.setRed((int) avg);
        pixelObj.setGreen((int) avg);
      }
    }
  }
  
  /** Method to make the shape of the fish stand out  */
  public void enhanceFish()
  {
    Pixel[][] pixels = this.getPixels2D();
    Color fishColor = pixels[50][200].getColor();

    for (int i = 0; i <pixels.length-1; i++)
    {
      for (int j = 0; j < pixels[i].length-1; j++)
      {
        if (Pixel.colorDistance(pixels[i][j].getColor(), fishColor) > 20){
          pixels[i][j].setBlue((int) (pixels[i][j].getBlue()*.9));
          pixels[i][j].setGreen( (int) (pixels[i][j].getGreen()*.9));
          pixels[i][j].setRed((int) (pixels[i][j].getRed()*.9));

        }
      }
    }

  }
  
  /** Method to highlight the edges of object in a picture by checking large changes in color */
  public void edgeDetection()
  {
    
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i <pixels.length-1; i++)
    {
      for (int j = 0; j < pixels[i].length-1; j++)
      {
        if (Pixel.colorDistance(pixels[i][j].getColor(), pixels[i+1][j+1].getColor()) > 20){
          pixels[i][j].setColor(Color.black);
        }
        else if (Pixel.colorDistance(pixels[i][j].getColor(), Color.WHITE) > 200) pixels[i][j].setColor(Color.WHITE);
      }
    }
  }
  
  /** Method to mirror the picture around a vertical line in the center of the picture from left to right */

  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i <(pixels.length/2)-1; i++)
    {
      for (int j = 0; j < pixels[i].length-1; j++)
      {
        pixels[pixels.length-i-1][j].setColor(pixels[i][j].getColor());

      }
    }
  }

  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i <pixels.length-1; i++)
    {
      for (int j = 0; j < (pixels[i].length/2); j++)
      {
        pixels[i][pixels[i].length-j-1].setColor(pixels[i][j].getColor());

      }
    }
  }
  
  /** Method to mirror around a diagonal line from bottom left to top right */
  public void mirrorDiagonal()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i <(pixels.length/2); i++)
    {
      for (int j = 0; j < (pixels[i].length/2); j++)
      {
        pixels[i][j].setColor(pixels[(pixels.length/2)-i-1][(pixels[i].length/2)-j-1].getColor());

      }
    }

  }
    
  /** Method to mirror just part of a picture of a temple to fix the broken part of the temple */
  public void mirrorTemple()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i <pixels.length/4; i++)
    {
      for (int j = 0; j < (pixels[i].length/2); j++)
      {
        pixels[i][pixels[i].length-j-1].setColor(pixels[i][j].getColor());

      }
    }
  }
  

  /** Method to mirror just part of a picture of a snowman, so that it will have four arms instead of two */
  public void mirrorArms()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 158; i < 193; i++) for (int j = 103; j < 170; j++) pixels[i][j].setColor(pixels[i][j].getColor());
    }
  
  /** Method to copy the gull in the picture to another location of the picture */
  public void copyGull()
  {
    
  }
     
  
  /** Method to replace the blue background with the pixels in the newBack picture
    * @param newBack the picture to copy from
    */
  public void chromakey(Picture newBack)
  {
    Pixel[][] pixels = this.getPixels2D();
    
  }
  
  /** Method to decode a message hidden in the red value of the current picture */
  public void decode()
  {
    //add your code here
  }
  
  /** Hide a black and white message in the current picture by changing the green to even and then setting it to odd if the message pixel is black 
    * @param messagePict the picture with a message
    */
  public void encodeGreen(Picture messagePict)
  {
    //add your code here
  }

  /** Your own customized method*/
  public void customized()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i <pixels.length; i++)
    {
      for (int j = 0; j < pixels[i].length; j++)
      {
        Color squared = new Color(pixels[i][j].getBlue(),pixels[i][j].getRed(),pixels[i][j].getGreen());
        pixels[i][j].setColor(squared);
      }
    }

  }
} 

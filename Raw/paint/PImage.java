package paint;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class PImage extends Paintable
{
  public static Image IMG_ERROR_IMG = null;
  public static ImageObserver CANVAS = null;
  public static HashMap<String, Image> LOADED_IMAGES = new HashMap();
  public Image image;
  public String url;

  public static Image getImageFrom(String url)
  {
    if (LOADED_IMAGES.containsKey(url))
      return (Image)LOADED_IMAGES.get(url);
    BufferedImage img = null;
    URL imgURL = null;
    try {
      imgURL = new URL(url);
    } catch (MalformedURLException e) {
      System.out.println("Invalid image URL.");
      return null;
    }
    try {
      img = ImageIO.read(imgURL);
    } catch (IOException e) {
      System.out.println("Error reading image.");
      return IMG_ERROR_IMG;
    }
    LOADED_IMAGES.put(url, img);
    return img;
  }

  public PImage(String imageURL, int x, int y)
  {
    this.image = getImageFrom(imageURL);
    this.url = imageURL;
    this.x = x;
    this.y = y;
    this.w = this.image.getWidth(CANVAS);
    this.h = this.image.getHeight(CANVAS);
  }

  public PImage(Image img, String url, int x, int y) {
    this.image = img;
    this.url = url;
    this.x = x;
    this.y = y;
    this.w = this.image.getWidth(CANVAS);
    this.h = this.image.getHeight(CANVAS);
  }

  public String getListText()
  {
    return "Image (" + this.x + ", " + this.y + ")";
  }

  public void paintTo(Graphics2D g)
  {
    g.drawImage(this.image, this.x, this.y, CANVAS);
  }

  public Paintable duplicate()
  {
    return new PImage(this.image, this.url, this.x, this.y);
  }

  public String toOutput()
  {
    return "layer IMAGE" + PaintIO.NEWLINE + this.url + PaintIO.NEWLINE + this.x + PaintIO.NEWLINE + this.y;
  }

  public String getDrawCode()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public String getFillCode()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
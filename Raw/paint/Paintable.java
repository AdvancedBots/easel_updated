package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Paintable
{
  public BasicStroke stroke;
  public Color color;
  public Color strokeColor;
  public int x;
  public int y;
  public int w;
  public int h;
  public Paintable shadow;

  public abstract String getListText();

  public abstract void paintTo(Graphics2D paramGraphics2D);

  public abstract Paintable duplicate();

  public abstract String toOutput();

  public abstract String getDrawCode();

  public abstract String getFillCode();
}
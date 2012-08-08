package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class PLine extends Paintable
{
  public int x1;
  public int y1;
  public int x2;
  public int y2;

  public PLine(int x1, int y1, int x2, int y2, Color color, BasicStroke stroke)
  {
    this.stroke = stroke;
    this.strokeColor = color;
    this.x = Math.min(x1, x2);
    this.y = Math.min(y1, y2);
    this.w = Math.abs(x1 - x2);
    this.h = Math.abs(y1 - y2);
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  public void move(int xAmount, int yAmount) {
    this.x1 += xAmount;
    this.x2 += xAmount;
    this.y1 += yAmount;
    this.y2 += yAmount;
  }

  public String getListText() {
    return "Line (" + this.x1 + ", " + this.y1 + ", " + this.x2 + ", " + this.y2 + ")";
  }

  public String getDrawCode() {
    return "g.drawLine(" + this.x1 + ", " + this.y1 + ", " + this.x2 + ", " + this.y2 + ");";
  }

  public String getFillCode() {
    return "";
  }

  public String toOutput() {
    return "layer LINE" + PaintIO.NEWLINE + this.x1 + PaintIO.NEWLINE + this.y1 + PaintIO.NEWLINE + this.x2 + PaintIO.NEWLINE + this.y2 + PaintIO.NEWLINE + PaintIO.colorToString(this.strokeColor) + PaintIO.NEWLINE + PaintIO.strokeToString(this.stroke);
  }

  public Paintable duplicate()
  {
    return new PLine(this.x1, this.y1, this.x2, this.y2, this.color, this.stroke);
  }

  public void paintTo(Graphics2D g) {
    g.setStroke(this.stroke);
    g.setColor(this.strokeColor);
    g.drawLine(this.x1, this.y1, this.x2, this.y2);
  }
}
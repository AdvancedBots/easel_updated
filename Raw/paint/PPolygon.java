package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class PPolygon extends Paintable
{
  public int[] xs;
  public int[] ys;
  private GeneralPath poly;

  public PPolygon(int[] xs, int[] ys, Color color, Color strokeColor, BasicStroke stroke)
  {
    this.xs = xs;
    this.ys = ys;
    this.color = color;
    this.strokeColor = strokeColor;
    this.stroke = stroke;
    this.x = xs[0];
    this.y = ys[0];
    this.w = this.x;
    this.h = this.y;
    for (int i = 1; i < xs.length; i++) {
      if (xs[i] < this.x)
        this.x = xs[i];
      else if (xs[i] > this.w)
        this.w = xs[i];
      if (ys[i] < this.y)
        this.y = ys[i];
      else if (ys[i] > this.h)
        this.h = ys[i];
    }
    this.w -= this.x;
    this.h -= this.y;
    this.poly = new GeneralPath();
    update();
  }

  public String getListText() {
    return "Polygon (" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ")";
  }

  public Paintable duplicate() {
    return new PPolygon((int[])(int[])this.xs.clone(), (int[])(int[])this.ys.clone(), this.color, this.strokeColor, this.stroke);
  }

  public void move(int xAmount, int yAmount) {
    for (int i = 0; i < this.xs.length; i++) {
      this.xs[i] += xAmount;
      this.ys[i] += yAmount;
    }
    update();
  }

  public void update() {
    this.poly.reset();
    this.poly.moveTo(this.xs[0], this.ys[0]);
    for (int i = 1; i < this.xs.length; i++)
      this.poly.lineTo(this.xs[i], this.ys[i]);
    this.poly.closePath();
  }

  public String toOutput() {
    return "layer POLYGON" + PaintIO.NEWLINE + PaintIO.intArrayToString(this.xs) + PaintIO.NEWLINE + PaintIO.intArrayToString(this.ys) + PaintIO.NEWLINE + PaintIO.colorToString(this.color) + PaintIO.NEWLINE + PaintIO.colorToString(this.strokeColor) + PaintIO.NEWLINE + PaintIO.strokeToString(this.stroke);
  }

  public void paintTo(Graphics2D g)
  {
    if (this.color != null) {
      g.setPaint(this.color);
      g.fill(this.poly);
    }
    if ((this.stroke != null) && (this.strokeColor != null)) {
      g.setStroke(this.stroke);
      g.setPaint(this.strokeColor);
      g.draw(this.poly);
    }
  }

  public String getDrawCode() {
    throw new UnsupportedOperationException("Polygons should not use this!");
  }

  public String getFillCode() {
    throw new UnsupportedOperationException("Polygons should not use this!!");
  }
}
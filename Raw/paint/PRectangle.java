package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class PRectangle extends Paintable
{
  public PRectangle(int x, int y, int w, int h, Color color, Color strokeColor, BasicStroke stroke)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.stroke = stroke;
    this.color = color;
    this.strokeColor = strokeColor;
  }

  public String getListText() {
    return "Rectangle (" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ")";
  }

  public String getFillCode() {
    return "g.fillRect(" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ");";
  }

  public String getDrawCode() {
    return "g.drawRect(" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ");";
  }

  public String toOutput() {
    return "layer RECTANGLE" + PaintIO.NEWLINE + this.x + PaintIO.NEWLINE + this.y + PaintIO.NEWLINE + this.w + PaintIO.NEWLINE + this.h + PaintIO.NEWLINE + PaintIO.colorToString(this.color) + PaintIO.NEWLINE + PaintIO.colorToString(this.strokeColor) + PaintIO.NEWLINE + PaintIO.strokeToString(this.stroke);
  }

  public Paintable duplicate()
  {
    return new PRectangle(this.x, this.y, this.w, this.h, this.color, this.strokeColor, this.stroke);
  }

  public void paintTo(Graphics2D g) {
    if (this.color != null) {
      g.setPaint(this.color);
      g.fillRect(this.x, this.y, this.w, this.h);
    }
    if ((this.stroke != null) && (this.strokeColor != null)) {
      g.setStroke(this.stroke);
      g.setPaint(this.strokeColor);
      g.drawRect(this.x, this.y, this.w, this.h);
    }
  }
}
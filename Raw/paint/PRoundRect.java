package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class PRoundRect extends Paintable
{
  public int arcWidth;
  public int arcHeight;

  public PRoundRect(int x, int y, int w, int h, int arcWidth, int arcHeight, Color color, Color strokeColor, BasicStroke stroke)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.stroke = stroke;
    this.color = color;
    this.strokeColor = strokeColor;
    this.arcWidth = arcWidth;
    this.arcHeight = arcHeight;
  }

  public String getListText() {
    return "Round Rect(" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ")";
  }

  public Paintable duplicate() {
    return new PRoundRect(this.x, this.y, this.w, this.h, this.arcWidth, this.arcHeight, this.color, this.strokeColor, this.stroke);
  }

  public String getDrawCode() {
    return "g.drawRoundRect(" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ", " + this.arcWidth + ", " + this.arcHeight + ");";
  }

  public String getFillCode() {
    return "g.fillRoundRect(" + this.x + ", " + this.y + ", " + this.w + ", " + this.h + ", " + this.arcWidth + ", " + this.arcHeight + ");";
  }

  public void paintTo(Graphics2D g) {
    if (this.color != null) {
      g.setPaint(this.color);
      g.fillRoundRect(this.x, this.y, this.w, this.h, this.arcWidth, this.arcHeight);
    }
    if ((this.stroke != null) && (this.strokeColor != null)) {
      g.setStroke(this.stroke);
      g.setPaint(this.strokeColor);
      g.drawRoundRect(this.x, this.y, this.w, this.h, this.arcWidth, this.arcHeight);
    }
  }

  public String toOutput() {
    return "layer ROUNDRECT" + PaintIO.NEWLINE + this.x + PaintIO.NEWLINE + this.y + PaintIO.NEWLINE + this.w + PaintIO.NEWLINE + this.h + PaintIO.NEWLINE + this.arcWidth + PaintIO.NEWLINE + this.arcHeight + PaintIO.NEWLINE + PaintIO.colorToString(this.color) + PaintIO.NEWLINE + PaintIO.colorToString(this.strokeColor) + PaintIO.NEWLINE + PaintIO.strokeToString(this.stroke);
  }
}
package ui;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import paint.PCircle;
import paint.PImage;
import paint.PLine;
import paint.PPolygon;
import paint.PRectangle;
import paint.PRoundRect;
import paint.PString;
import paint.Paintable;

public class Canvas extends Applet
  implements Runnable, MouseMotionListener, MouseListener, KeyListener
{
  private ToolboxFrame parent;
  private Image background;
  private PaintableListModel layers;
  private boolean drawing;
  private ArrayList<Integer> Xs;
  private ArrayList<Integer> Ys;
  private GeneralPath polyToDraw;
  private Font f;
  private String mouseInfo = "X: 0, Y: 0";
  private TexturePaint tp;
  private final BasicStroke defaultStroke = new BasicStroke(1.0F);
  private final RenderingHints all = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); private final RenderingHints textOnly = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); private final RenderingHints off = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

  private final Color SELECTED1 = new Color(255, 255, 255, 150); private final Color SELECTED2 = new Color(0, 0, 0, 150); private final Color BACKGROUND = new Color(236, 233, 216);

  private Font defaultFont = null;
  private Image dbImage;
  private Graphics dbg;
  private int pressX;
  private int pressY;
  private int dragX;
  private int dragY;
  private int startWidth;
  private int startHeight;
  private int shadowWidth;
  private int shadowHeight;
  boolean shiftDown;

  public Canvas(ToolboxFrame parent)
  {
    this.parent = parent;
    this.layers = parent.layers;
    this.Xs = new ArrayList();
    this.Ys = new ArrayList();
    this.polyToDraw = new GeneralPath();
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
    new Thread(this).start();
  }

  public void run() {
    while (true) {
      repaint();
      try {
        Thread.sleep(25L); } catch (InterruptedException e) {
      }
    }
  }

  public void init() {
    Toolkit tk = Toolkit.getDefaultToolkit();
    this.background = tk.getImage(getClass().getResource("/images/preview.png"));
    PImage.IMG_ERROR_IMG = tk.getImage(getClass().getResource("/images/imageerror.png"));
    try {
      BufferedImage bi = ImageIO.read(getClass().getResourceAsStream("/images/pattern2.png"));
      this.tp = new TexturePaint(bi, new Rectangle(0, 0, 16, 16));
    } catch (IOException e) {
      this.tp = null;
    }
    PImage.CANVAS = this;
  }

  public void paint(Graphics g1)
  {
    if (this.defaultFont == null)
      this.defaultFont = g1.getFont();
    Graphics2D g = (Graphics2D)g1;
    if (this.parent.aaAllOp.isSelected())
      g.setRenderingHints(this.all);
    else if (this.parent.aaTextOp.isSelected())
      g.setRenderingHints(this.textOnly);
    else {
      g.setRenderingHints(this.off);
    }
    g.setColor(this.parent.getCanvasBackground());
    g.fillRect(0, 0, 765, 503);
    if (this.parent.drawInterfaceOp.isSelected())
      g.drawImage(this.background, null, this);
    for (Paintable p : this.layers.getArrayList())
      p.paintTo(g);
    Paintable selection = this.layers.getSelected();
    g.setStroke(this.defaultStroke);
    if (selection != null)
    {
      int y;
      int x;
      int w;
      int h;
      if ((selection instanceof PString)) {
        Rectangle2D r = g.getFontMetrics(((PString)selection).font).getStringBounds(((PString)selection).text, g);
        w = (int)r.getWidth();
        h = (int)r.getHeight();
        x = selection.x;
        y = selection.y - h;
      } else {
        x = selection.x;
        y = selection.y;
        w = selection.w;
        h = selection.h;
      }
      g.setColor(this.SELECTED1);
      g.drawRect(x - 1, y - 1, w + 2, h + 2);
      g.setColor(this.SELECTED2);
      g.drawRect(x - 2, y - 2, w + 4, h + 4);
    }
    int x = -1; int y = -1; int w = -1; int h = -1;
    if (this.drawing) {
      x = Math.min(this.dragX, this.pressX);
      y = Math.min(this.dragY, this.pressY);
      w = Math.abs(this.dragX - this.pressX);
      h = Math.abs(this.dragY - this.pressY);
      if (this.shiftDown) {
        w = Math.min(w, h);
        h = w;
      }
      Color fill = this.parent.getColor(); Color stroke = this.parent.getStrokeColor();
      switch (this.parent.getSelectedTool()) {
      case 4:
        g.setColor(this.parent.getStrokeColor());
        g.setStroke(this.parent.getStroke());
        g.drawLine(this.pressX, this.pressY, this.dragX, this.dragY);
        break;
      case 3:
        fill = this.parent.getColor();
        stroke = this.parent.getStrokeColor();
        if (fill != null) {
          g.setColor(fill);
          g.fillOval(x, y, w, h);
        }
        if (stroke == null) break;
        g.setColor(stroke);
        g.setStroke(this.parent.getStroke());
        g.drawOval(x, y, w, h); break;
      case 2:
        fill = this.parent.getColor();
        stroke = this.parent.getStrokeColor();
        if (fill != null) {
          g.setColor(fill);
          g.fillRect(x, y, w, h);
        }
        if (stroke == null) break;
        g.setColor(stroke);
        g.setStroke(this.parent.getStroke());
        g.drawRect(x, y, w, h); break;
      case 6:
        fill = this.parent.getColor();
        stroke = this.parent.getStrokeColor();
        if (fill != null) {
          g.setColor(fill);
          g.fillRoundRect(x, y, w, h, this.parent.getArcWidth(), this.parent.getArcHeight());
        }
        if (stroke == null) break;
        g.setColor(stroke);
        g.setStroke(this.parent.getStroke());
        g.drawRoundRect(x, y, w, h, this.parent.getArcWidth(), this.parent.getArcHeight()); break;
      case 5:
        fill = this.parent.getColor();
        stroke = this.parent.getStrokeColor();
        if ((fill != null) && (this.Xs.size() > 2)) {
          g.setColor(fill);
          g.fill(this.polyToDraw);
        }
        if ((stroke != null) && 
          (this.Xs.size() > 1)) {
          g.setColor(stroke);
          g.setStroke(this.parent.getStroke());
          g.draw(this.polyToDraw);
        }

        if (this.Xs.size() <= 0) break;
        if (this.parent.getStroke() != null)
          g.setStroke(this.parent.getStroke());
        else
          g.setStroke(this.defaultStroke);
        g.drawLine(this.dragX, this.dragY, ((Integer)this.Xs.get(this.Xs.size() - 1)).intValue(), ((Integer)this.Ys.get(this.Ys.size() - 1)).intValue());
        g.setStroke(this.defaultStroke);
        g.setColor(this.SELECTED1);
        x = ((Integer)this.Xs.get(0)).intValue();
        y = ((Integer)this.Ys.get(0)).intValue();
        g.drawRect(x - 3, y - 3, 6, 6);
        g.setColor(this.SELECTED2);
        g.drawRect(x - 4, y - 4, 8, 8); break;
      case 1:
        g.setColor(this.parent.getColor());
        g.setFont(this.f);
        g.drawString(this.parent.getString(), this.dragX, this.dragY);
        break;
      case 8:
        Image img = this.parent.getImage();
        if (img == null) break;
        g.drawImage(img, this.dragX, this.dragY, this);
      case 7:
      }
    }
    if (this.parent.showGridBox.isSelected()) {
      g.setColor(this.parent.getGridColor());
      for (int i = this.parent.getGridWidth(); i < getWidth(); i += this.parent.getGridWidth())
        g.drawLine(i, 0, i, 550);
      for (int i = this.parent.getGridHeight(); i < getWidth(); i += this.parent.getGridHeight())
        g.drawLine(0, i, 765, i);
    }
    g.setColor(this.BACKGROUND);
    g.fillRect(0, 550, 765, 25);
    g.setColor(Color.BLACK);
    g.setFont(this.defaultFont);
    if ((h >= 0) && (w >= 0))
      g.drawString(this.mouseInfo + " - W: " + w + ", H: " + h, 5, 565);
    else
      g.drawString(this.mouseInfo, 5, 565);
    g.setFont(this.parent.getPreviewFont());
    g.drawString("Preview Text", 400, 565);
  }

  public void update(Graphics g)
  {
    if (this.dbImage == null)
    {
      this.dbImage = createImage(getSize().width, getSize().height);
      this.dbg = this.dbImage.getGraphics();
    }

    this.dbg.setColor(getBackground());
    this.dbg.fillRect(0, 0, getSize().width, getSize().height);

    this.dbg.setColor(getForeground());
    paint(this.dbg);

    g.drawImage(this.dbImage, 0, 0, this);
  }

  public void reset()
  {
    this.polyToDraw.reset();
    this.Xs.clear();
    this.Ys.clear();
    this.shiftDown = false;
    this.drawing = false;
  }

  public void mouseDragged(MouseEvent e)
  {
    this.mouseInfo = ("X: " + e.getX() + ", Y: " + e.getY());
    Paintable p = this.layers.getSelected();
    this.dragX = e.getX();
    this.dragY = e.getY();
    if (this.parent.snap2GridBox.isSelected()) {
      this.dragX += (int)Math.round(this.parent.getGridWidth() / 2.0D);
      this.dragY += (int)Math.round(this.parent.getGridHeight() / 2.0D);
      this.dragX = (this.dragX / this.parent.getGridWidth() * this.parent.getGridWidth());
      this.dragY = (this.dragY / this.parent.getGridHeight() * this.parent.getGridHeight());
    }
    Paintable shadow;
    switch (this.parent.getSelectedTool()) {
    case 0:
      int oldX = p.x; int oldY = p.y;
      p.x = (this.dragX - this.pressX);
      p.y = (this.dragY - this.pressY);
      oldX = p.x - oldX;
      oldY = p.y - oldY;
      if ((p instanceof PLine))
        ((PLine)p).move(oldX, oldY);
      else if ((p instanceof PPolygon))
        ((PPolygon)p).move(oldX, oldY);
      shadow = p.shadow;
      if (shadow == null) break;
      shadow.x += oldX;
      shadow.y += oldY;
      if ((shadow instanceof PLine)) {
        ((PLine)shadow).move(oldX, oldY); } else {
        if (!(shadow instanceof PPolygon)) break;
        ((PPolygon)shadow).move(oldX, oldY); } break;
    case 7:
      int xdist = this.dragX - this.pressX;
      int ydist = this.dragY - this.pressY;
      p.w = (this.startWidth + xdist);
      p.h = (this.startHeight + ydist);
      if (this.shiftDown) {
        p.w = Math.min(p.w, p.h);
        p.h = p.w;
      }
      shadow = p.shadow;
      if (shadow == null) break;
      shadow.w = (this.shadowWidth + xdist);
      shadow.h = (this.shadowHeight + ydist);
    }
  }

  public void mouseMoved(MouseEvent e)
  {
    this.mouseInfo = ("X: " + e.getX() + ", Y: " + e.getY());
    if (this.parent.getSelectedTool() == 5) {
      this.dragX = e.getX();
      this.dragY = e.getY();
      if (this.parent.snap2GridBox.isSelected()) {
        this.dragX += (int)Math.round(this.parent.getGridWidth() / 2.0D);
        this.dragY += (int)Math.round(this.parent.getGridHeight() / 2.0D);
        this.dragX = (this.dragX / this.parent.getGridWidth() * this.parent.getGridWidth());
        this.dragY = (this.dragY / this.parent.getGridHeight() * this.parent.getGridHeight());
      }
    }
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public void mousePressed(MouseEvent e)
  {
    this.pressX = (this.dragX = e.getX());
    this.pressY = (this.dragY = e.getY());
    if (this.parent.snap2GridBox.isSelected()) {
      this.pressX += (int)Math.round(this.parent.getGridWidth() / 2.0D);
      this.pressY += (int)Math.round(this.parent.getGridHeight() / 2.0D);
      this.pressX = (this.pressX / this.parent.getGridWidth() * this.parent.getGridWidth());
      this.pressY = (this.pressY / this.parent.getGridHeight() * this.parent.getGridHeight());
    }
    switch (this.parent.getSelectedTool()) {
    case 0:
      this.pressX -= this.layers.getSelected().x;
      this.pressY -= this.layers.getSelected().y;
      break;
    case 7:
      this.startWidth = this.layers.getSelected().w;
      this.startHeight = this.layers.getSelected().h;
      Paintable shadow = this.layers.getSelected().shadow;
      if (shadow == null) break;
      this.shadowWidth = shadow.w;
      this.shadowHeight = shadow.h; break;
    case 1:
      this.f = this.parent.getSelectedFont();
      break;
    case 5:
      if (this.polyToDraw.getCurrentPoint() != null) break;
      this.Xs.clear();
      this.Ys.clear(); break;
    case 8:
      this.dragX = this.pressX;
      this.dragY = this.pressY;
    case 2:
    case 3:
    case 4:
    case 6: } if (this.parent.getSelectedTool() > 0)
      this.drawing = true;
  }

  public void mouseReleased(MouseEvent e) {
    int releaseX = e.getX(); int releaseY = e.getY();
    if (this.parent.snap2GridBox.isSelected()) {
      releaseX += (int)Math.round(this.parent.getGridWidth() / 2.0D);
      releaseY += (int)Math.round(this.parent.getGridHeight() / 2.0D);
      releaseX = releaseX / this.parent.getGridWidth() * this.parent.getGridWidth();
      releaseY = releaseY / this.parent.getGridHeight() * this.parent.getGridHeight();
    }
    this.drawing = (this.parent.getSelectedTool() == 5);
    int x = Math.min(this.pressX, releaseX); int y = Math.min(this.pressY, releaseY);
    int w = Math.abs(this.pressX - releaseX); int h = Math.abs(this.pressY - releaseY);
    if (this.shiftDown) {
      w = Math.min(w, h);
      h = w;
    }
    switch (this.parent.getSelectedTool()) {
    case 4:
      this.layers.insert(new PLine(this.pressX, this.pressY, releaseX, releaseY, this.parent.getStrokeColor(), this.parent.getStroke()));

      break;
    case 2:
      this.layers.insert(new PRectangle(x, y, w, h, this.parent.getColor(), this.parent.getStrokeColor(), this.parent.getStroke()));

      break;
    case 6:
      this.layers.insert(new PRoundRect(x, y, w, h, this.parent.getArcWidth(), this.parent.getArcHeight(), this.parent.getColor(), this.parent.getStrokeColor(), this.parent.getStroke()));

      break;
    case 3:
      this.layers.insert(new PCircle(x, y, w, h, this.parent.getColor(), this.parent.getStrokeColor(), this.parent.getStroke()));

      break;
    case 5:
      if (this.Xs.isEmpty()) {
        this.polyToDraw.moveTo(releaseX, releaseY);
        this.Xs.add(new Integer(releaseX));
        this.Ys.add(new Integer(releaseY));
      } else {
        int xdist = ((Integer)this.Xs.get(0)).intValue() - releaseX;
        int ydist = ((Integer)this.Ys.get(0)).intValue() - releaseY;
        double dist = Math.sqrt(xdist * xdist + ydist * ydist);
        if ((dist < 3.0D) && (this.Xs.size() > 1)) {
          this.drawing = false;
          this.polyToDraw.reset();
          this.layers.insert(new PPolygon(integerALToIntArray(this.Xs), integerALToIntArray(this.Ys), this.parent.getColor(), this.parent.getStrokeColor(), this.parent.getStroke()));
        }
        else
        {
          this.polyToDraw.lineTo(releaseX, releaseY);
          this.Xs.add(new Integer(releaseX));
          this.Ys.add(new Integer(releaseY));
        }
      }
      break;
    case 1:
      this.layers.add(new PString(this.parent.getString(), releaseX, releaseY, this.parent.getColor(), this.f));
      break;
    case 8:
      Image img = this.parent.getImage();
      if (img == null) break;
      this.layers.add(new PImage(img, this.parent.getImageURL(), releaseX, releaseY));
    case 7:
    }
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }

  public int[] integerALToIntArray(ArrayList<Integer> integers)
  {
    int[] asInts = new int[integers.size()];
    for (int i = 0; i < asInts.length; i++)
      asInts[i] = ((Integer)integers.get(i)).intValue();
    return asInts;
  }

  public void keyTyped(KeyEvent e)
  {
  }

  public void keyPressed(KeyEvent e)
  {
    switch (e.getKeyCode()) {
    case 16:
      this.shiftDown = true;
    }
  }

  public void keyReleased(KeyEvent e)
  {
    switch (e.getKeyCode()) {
    case 16:
      this.shiftDown = false;
      break;
    case 8:
      if ((!this.drawing) || (this.parent.getSelectedTool() != 5)) break;
      this.Xs.remove(this.Xs.size() - 1);
      this.Ys.remove(this.Ys.size() - 1);
      this.polyToDraw.reset();
      if (this.Xs.size() == 0) {
        this.drawing = false;
      } else {
        this.polyToDraw.moveTo(((Integer)this.Xs.get(0)).intValue(), ((Integer)this.Ys.get(0)).intValue());
        for (int i = 1; i < this.Xs.size(); i++)
          this.polyToDraw.lineTo(((Integer)this.Xs.get(i)).intValue(), ((Integer)this.Ys.get(i)).intValue());
      }
      break;
    case 27:
      if ((!this.drawing) || (this.parent.getSelectedTool() != 5)) break;
      this.Xs.clear();
      this.Ys.clear();
      this.polyToDraw.reset();
      this.drawing = false; break;
    case 127:
      if (this.drawing) break;
      this.layers.removeSelected();
    }
  }
}
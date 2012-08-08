package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class JavaConverter
{
  public static final int AA_ALL = 1;
  public static final int AA_TEXT = 2;
  private static final String NEWLINE = "\r\n";
  private static final String TAB = "    ";

  public static String colorToJava(Color c)
  {
    return "new Color(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + (c.getAlpha() < 255 ? ", " + c.getAlpha() : "") + ")";
  }

  public static String strokeToJava(BasicStroke bs)
  {
    return "new BasicStroke(" + (int)bs.getLineWidth() + ")";
  }

  public static String fontToJava(Font f) {
    return "new Font(\"" + f.getName() + "\", " + f.getStyle() + ", " + f.getSize() + ")";
  }

  public static String tab(int amount) {
    String tabs = "";
    for (int i = 0; i < amount; i++)
      tabs = tabs + "    ";
    return tabs;
  }

  public static String newline(int amount) {
    String lines = "";
    for (int i = 0; i < amount; i++)
      lines = lines + "\r\n";
    return lines;
  }

  public static String[] convertLayersToJava(ArrayList<Paintable> layers, int antialiasingType) {
    String code = "    //START: Code generated using Enfilade's Easel\r\n";
    String imports = "import java.awt.*;";
    int[] colorIndexes = new int[layers.size()];
    int[] strokeColorIndexes = new int[layers.size()];
    int[] strokeIndexes = new int[layers.size()];
    int[] fontIndexes = new int[layers.size()];
    int[] imageIndexes = new int[layers.size()];
    ArrayList colors = new ArrayList();
    ArrayList strokes = new ArrayList();
    ArrayList fonts = new ArrayList();
    ArrayList polygons = new ArrayList();
    ArrayList images = new ArrayList();

    for (int i = 0; i < layers.size(); i++) {
      Paintable p = (Paintable)layers.get(i);
      if ((p instanceof PPolygon))
        polygons.add((PPolygon)p);
      if ((p instanceof PImage)) {
        PImage pi = (PImage)p;
        int index = images.indexOf(pi.url);
        if (index < 0) {
          images.add(pi.url);
          index = images.size() - 1;
        }
        imageIndexes[i] = index;
      } else {
        imageIndexes[i] = -1;
        if (p.color != null) {
          int index = colors.indexOf(p.color);
          if (index < 0) {
            colors.add(p.color);
            index = colors.size() - 1;
          }
          colorIndexes[i] = index;
        } else {
          colorIndexes[i] = -1;
        }if (p.strokeColor != null) {
          int index = colors.indexOf(p.strokeColor);
          if (index < 0) {
            colors.add(p.strokeColor);
            index = colors.size() - 1;
          }
          strokeColorIndexes[i] = index;
        } else {
          strokeColorIndexes[i] = -1;
        }if (p.stroke != null) {
          int index = strokes.indexOf(p.stroke);
          if (index < 0) {
            strokes.add(p.stroke);
            index = strokes.size() - 1;
          }
          strokeIndexes[i] = index;
        } else {
          strokeIndexes[i] = -1;
        }if ((p instanceof PString)) {
          PString ps = (PString)p;
          int index = fonts.indexOf(ps.font);
          if (index < 0) {
            fonts.add(ps.font);
            index = fonts.size() - 1;
          }
          fontIndexes[i] = index;
        } else {
          fontIndexes[i] = -1;
        }
      }
    }
    if (antialiasingType == 1) {
      code = code + tab(1) + "private final RenderingHints antialiasing = new RenderingHints(" + "\r\n" + tab(3) + "RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);" + newline(2);
    }
    else if (antialiasingType == 2) {
      code = code + tab(1) + "private final RenderingHints antialiasing = new RenderingHints(" + "\r\n" + tab(3) + "RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);" + newline(2);
    }
    if (polygons.size() > 0) {
      imports = imports + "\r\nimport java.awt.geom.GeneralPath;";
      code = code + tab(1) + "private GeneralPath pathFrom(int[] xs, int[] ys) {" + "\r\n";
      code = code + tab(2) + "GeneralPath gp = new GeneralPath();" + "\r\n";
      code = code + tab(2) + "gp.moveTo(xs[0], ys[0]);" + "\r\n";
      code = code + tab(2) + "for(int i = 1; i < xs.length; i++)" + "\r\n";
      code = code + tab(3) + "gp.lineTo(xs[i], ys[i]);" + "\r\n";
      code = code + tab(2) + "gp.closePath();" + "\r\n";
      code = code + tab(2) + "return gp;" + "\r\n";
      code = code + tab(1) + "}" + newline(2);
    }
    if (images.size() > 0) {
      imports = imports + "\r\nimport javax.imageio.ImageIO;";
      imports = imports + "\r\nimport java.io.IOException;";
      imports = imports + "\r\nimport java.net.URL;";
      code = code + tab(1) + "private Image getImage(String url) {" + "\r\n";
      code = code + tab(2) + "try {" + "\r\n";
      code = code + tab(3) + "return ImageIO.read(new URL(url));" + "\r\n";
      code = code + tab(2) + "} catch(IOException e) {" + "\r\n";
      code = code + tab(3) + "return null;" + "\r\n";
      code = code + tab(2) + "}" + "\r\n" + tab(1) + "}" + newline(2);
    }
    for (int i = 0; i < colors.size(); i++)
      code = code + "    private final Color color" + (i + 1) + " = " + colorToJava((Color)colors.get(i)) + ";" + "\r\n";
    if (colors.size() > 0)
      code = code + "\r\n";
    for (int i = 0; i < strokes.size(); i++)
      code = code + "    private final BasicStroke stroke" + (i + 1) + " = " + strokeToJava((BasicStroke)strokes.get(i)) + ";" + "\r\n";
    if (strokes.size() > 0)
      code = code + "\r\n";
    for (int i = 0; i < fonts.size(); i++)
      code = code + "    private final Font font" + (i + 1) + " = " + fontToJava((Font)fonts.get(i)) + ";" + "\r\n";
    if (fonts.size() > 0)
      code = code + "\r\n";
    for (int i = 0; i < polygons.size(); i++) {
      code = code + "    private final GeneralPath polygon" + (i + 1) + " = pathFrom(new int[]{" + PaintIO.intArrayToString(((PPolygon)polygons.get(i)).xs) + "}," + "\r\n";
      code = code + tab(3) + "new int[]{" + PaintIO.intArrayToString(((PPolygon)polygons.get(i)).ys) + "});" + "\r\n";
    }
    if (polygons.size() > 0)
      code = code + "\r\n";
    for (int i = 0; i < images.size(); i++)
      code = code + "    private final Image img" + (i + 1) + " = getImage(\"" + (String)images.get(i) + "\");" + "\r\n";
    if (images.size() > 0)
      code = code + "\r\n";
    int lastColor = -2;
    int lastFont = -2;
    int lastStroke = -2;
    int polygon = 0;
    code = code + "    public void onRepaint(Graphics g1) {\r\n";
    code = code + tab(2) + "Graphics2D g = (Graphics2D)g1;" + "\r\n";
    if (antialiasingType > 0)
      code = code + tab(2) + "g.setRenderingHints(antialiasing);" + newline(2);
    for (int i = 0; i < layers.size(); i++) {
      Paintable p = (Paintable)layers.get(i);
      if ((p instanceof PImage)) {
        PImage pi = (PImage)p;
        code = code + tab(2) + "g.drawImage(img" + (imageIndexes[i] + 1) + ", " + pi.x + ", " + pi.y + ", null);" + "\r\n";
      } else {
        if ((fontIndexes[i] >= 0) && (fontIndexes[i] != lastFont)) {
          lastFont = fontIndexes[i];
          code = code + tab(2) + "g.setFont(font" + (lastFont + 1) + ");" + "\r\n";
        }
        if (colorIndexes[i] >= 0) {
          if (lastColor != colorIndexes[i]) {
            lastColor = colorIndexes[i];
            code = code + tab(2) + "g.setColor(color" + (lastColor + 1) + ");" + "\r\n";
          }
          if ((p instanceof PPolygon))
            code = code + tab(2) + "g.fill(polygon" + (polygon + 1) + ");" + "\r\n";
          else
            code = code + tab(2) + p.getFillCode() + "\r\n";
        }
        if ((strokeColorIndexes[i] >= 0) && (strokeIndexes[i] >= 0)) {
          if (lastColor != strokeColorIndexes[i]) {
            lastColor = strokeColorIndexes[i];
            code = code + tab(2) + "g.setColor(color" + (lastColor + 1) + ");" + "\r\n";
          }
          if (lastStroke != strokeIndexes[i]) {
            lastStroke = strokeIndexes[i];
            code = code + tab(2) + "g.setStroke(stroke" + (lastStroke + 1) + ");" + "\r\n";
          }
          if ((p instanceof PPolygon))
            code = code + tab(2) + "g.draw(polygon" + (polygon + 1) + ");" + "\r\n";
          else
            code = code + tab(2) + p.getDrawCode() + "\r\n";
        }
        if ((p instanceof PPolygon))
          polygon++;
      }
    }
    code = code + "    }";
    return new String[] { code + "\r\n" + "    " + "//END: Code generated using Enfilade's Easel", imports };
  }
}
package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CanvasFrame extends JFrame
{
  public CanvasFrame(ToolboxFrame toolbox)
  {
    setTitle("Preview");
    setDefaultCloseOperation(0);
    JFrame t = this;
    addWindowListener(new WindowAdapter() {
      private Component val$t;

	public void windowClosing(WindowEvent evt) {
        if (JOptionPane.showConfirmDialog(this.val$t, "Do you really want to exit?", "Confirm", 0) == 0)
          System.exit(0);
      }
    });
    toolbox.canvas = new Canvas(toolbox);
    toolbox.canvas.setPreferredSize(new Dimension(765, 570));
    toolbox.canvas.init();
    setResizable(false);
    add(toolbox.canvas);
    pack();
  }
}
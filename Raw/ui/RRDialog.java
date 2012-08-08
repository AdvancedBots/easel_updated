package ui;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

public class RRDialog extends JDialog
{
  public JSpinner arcHeight;
  public JSpinner arcWidth;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;

  public RRDialog(ToolboxFrame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
  }

  private void initComponents()
  {
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.arcWidth = new JSpinner();
    this.jLabel3 = new JLabel();
    this.jLabel4 = new JLabel();
    this.arcHeight = new JSpinner();

    setTitle("Rounded Rectangle");
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        RRDialog.this.formWindowClosing(evt);
      }
    });
    this.jLabel1.setText("Rounded Rectangle options:");

    this.jLabel2.setText("Arc Width:");

    this.arcWidth.setModel(new SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

    this.jLabel3.setText("Arc Height:");

    this.jLabel4.setText("<html>These values change how large the rounded<br>\nrectangle's edges will be, in pixels.");

    this.arcHeight.setModel(new SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 73, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.arcHeight).addComponent(this.arcWidth, -1, 76, 32767))).addComponent(this.jLabel4, GroupLayout.Alignment.TRAILING)).addContainerGap()));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.arcWidth, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.arcHeight, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel4).addContainerGap(-1, 32767)));

    pack();
  }

  private void formWindowClosing(WindowEvent evt) {
    ((ToolboxFrame)getParent()).roundRectDialogClosed();
  }
}
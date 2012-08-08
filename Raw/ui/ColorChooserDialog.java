package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooserDialog extends JDialog
  implements ChangeListener
{
  private JPanel target;
  private ToolboxFrame parent;
  private JLabel alphaLabel;
  private JSlider alphaSlider;
  private JButton cancelButton;
  private JColorChooser colorChooser;
  private JLabel jLabel1;
  private JButton setButton;

  public ColorChooserDialog(ToolboxFrame parent, boolean modal)
  {
    super(parent, modal);
    this.parent = parent;
    initComponents();
  }

  public void setTarget(JPanel target) {
    this.target = target;
    setLocationRelativeTo(this.parent);
    this.colorChooser.setColor(target.getBackground());
    this.colorChooser.getSelectionModel().addChangeListener(this);
    this.alphaSlider.setValue(target.getBackground().getAlpha());
    this.alphaLabel.setText("" + this.alphaSlider.getValue());
  }

  private void initComponents()
  {
    this.colorChooser = new JColorChooser();
    this.jLabel1 = new JLabel();
    this.alphaSlider = new JSlider();
    this.alphaLabel = new JLabel();
    this.setButton = new JButton();
    this.cancelButton = new JButton();

    this.jLabel1.setText("Alpha:");

    this.alphaSlider.setMaximum(255);
    this.alphaSlider.setOrientation(1);
    this.alphaSlider.setValue(255);
    this.alphaSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        ColorChooserDialog.this.alphaSliderStateChanged(evt);
      }
    });
    this.alphaLabel.setText("255");

    this.setButton.setText("Ok");
    this.setButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ColorChooserDialog.this.setButtonActionPerformed(evt);
      }
    });
    this.cancelButton.setText("Cancel");
    this.cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ColorChooserDialog.this.cancelButtonActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 486, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.colorChooser, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.alphaSlider, -2, -1, -2).addComponent(this.alphaLabel))).addGroup(layout.createSequentialGroup().addComponent(this.setButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton))).addContainerGap(-1, 32767)));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 391, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.alphaSlider, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.alphaLabel)).addComponent(this.colorChooser, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.setButton).addComponent(this.cancelButton)).addContainerGap(-1, 32767)));

    pack();
  }

  private void alphaSliderStateChanged(ChangeEvent evt) {
    Color c = this.colorChooser.getColor();
    this.colorChooser.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), this.alphaSlider.getValue()));
    this.alphaLabel.setText("" + this.alphaSlider.getValue());
  }

  private void cancelButtonActionPerformed(ActionEvent evt) {
    setVisible(false);
  }

  public void stateChanged(ChangeEvent e) {
    this.alphaSlider.setValue(this.colorChooser.getColor().getAlpha());
  }

  private void setButtonActionPerformed(ActionEvent evt) {
    this.target.setBackground(this.colorChooser.getColor());
    this.parent.colorChosen(this.target);
    setVisible(false);
  }
}
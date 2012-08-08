package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

public class GridDialog extends JDialog
{
  private ColorChooserDialog colorChooser;
  public JSpinner cellHeight;
  public JSpinner cellWidth;
  public JPanel gridColorPanel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;

  public GridDialog(Frame parent, boolean modal, ColorChooserDialog colorChooser)
  {
    super(parent, modal);
    this.colorChooser = colorChooser;
    initComponents();
  }

  private void initComponents()
  {
    this.cellWidth = new JSpinner();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jLabel3 = new JLabel();
    this.cellHeight = new JSpinner();
    this.jLabel4 = new JLabel();
    this.gridColorPanel = new JPanel();

    setTitle("Grid Options");

    this.cellWidth.setModel(new SpinnerNumberModel(Integer.valueOf(25), Integer.valueOf(1), null, Integer.valueOf(1)));

    this.jLabel1.setText("Grid Options:");

    this.jLabel2.setText("Cell Width:");

    this.jLabel3.setText("Cell Height:");

    this.cellHeight.setModel(new SpinnerNumberModel(Integer.valueOf(25), Integer.valueOf(1), null, Integer.valueOf(1)));

    this.jLabel4.setText("Color:");

    this.gridColorPanel.setBackground(new Color(255, 0, 0, 150));
    this.gridColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    this.gridColorPanel.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent evt) {
        GridDialog.this.gridColorPanelMouseReleased(evt);
      }
    });
    GroupLayout gridColorPanelLayout = new GroupLayout(this.gridColorPanel);
    this.gridColorPanel.setLayout(gridColorPanelLayout);
    gridColorPanelLayout.setHorizontalGroup(gridColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 25, 32767));

    gridColorPanelLayout.setVerticalGroup(gridColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 25, 32767));

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 91, 32767).addComponent(this.cellWidth, -2, 80, -2)).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.gridColorPanel, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 88, 32767).addComponent(this.cellHeight, -2, 80, -2))))).addContainerGap()));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.cellWidth, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.cellHeight, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.gridColorPanel, -2, -1, -2)).addContainerGap(-1, 32767)));

    pack();
  }

  private void gridColorPanelMouseReleased(MouseEvent evt) {
    this.colorChooser.setTarget(this.gridColorPanel);
    this.colorChooser.setVisible(true);
  }
}
package ui;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CodeDialog extends JDialog
{
  private JScrollPane codeBox;
  private JTextArea codeText;
  private JButton copyCodeButton;
  private JButton copyImportsButton;
  private JTextArea importBox;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JScrollPane jScrollPane1;

  public CodeDialog(Frame parent, boolean modal, String code, String imports)
  {
    super(parent, modal);
    initComponents();
    this.codeText.setText(code);
    this.importBox.setText(imports);
  }

  private void initComponents()
  {
    this.jScrollPane1 = new JScrollPane();
    this.importBox = new JTextArea();
    this.codeBox = new JScrollPane();
    this.codeText = new JTextArea();
    this.copyImportsButton = new JButton();
    this.copyCodeButton = new JButton();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();

    setDefaultCloseOperation(2);

    this.importBox.setColumns(20);
    this.importBox.setRows(2);
    this.jScrollPane1.setViewportView(this.importBox);

    this.codeText.setColumns(20);
    this.codeText.setRows(5);
    this.codeBox.setViewportView(this.codeText);

    this.copyImportsButton.setText("Copy Imports to Clipboard");
    this.copyImportsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        CodeDialog.this.copyImportsButtonActionPerformed(evt);
      }
    });
    this.copyCodeButton.setText("Copy Code to Clipboard");
    this.copyCodeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        CodeDialog.this.copyCodeButtonActionPerformed(evt);
      }
    });
    this.jLabel1.setText("Needed imports:");

    this.jLabel2.setText("onRepaint method (and variable declarations):");

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 380, 32767).addComponent(this.jLabel2).addComponent(this.codeBox, -1, 380, 32767).addComponent(this.copyImportsButton).addComponent(this.copyCodeButton)).addContainerGap()));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.copyImportsButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.codeBox, -2, 173, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.copyCodeButton).addContainerGap()));

    pack();
  }

  private void copyImportsButtonActionPerformed(ActionEvent evt) {
    this.importBox.selectAll();
    this.importBox.copy();
  }

  private void copyCodeButtonActionPerformed(ActionEvent evt) {
    this.codeText.selectAll();
    this.codeText.copy();
  }
}
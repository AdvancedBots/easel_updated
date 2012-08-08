package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import paint.JavaConverter;
import paint.PCircle;
import paint.PImage;
import paint.PLine;
import paint.PPolygon;
import paint.PRectangle;
import paint.PRoundRect;
import paint.PString;
import paint.PaintIO;
import paint.Paintable;

public class ToolboxFrame extends JFrame
{
  public PaintableListModel layers;
  public Canvas canvas;
  private CanvasFrame canvasFrame;
  private JToggleButton tool;
  private ColorChooserDialog colorChooser;
  private GridDialog gridOptions;
  private RRDialog roundRectOptions;
  private JFileChooser saveDialog;
  private JFileChooser openDialog;
  private File file;
  private Image loadedImage;
  private String imageURL;
  private BufferedImage background;
  private JPanel dummy;
  private FileFilter fileFilter = new FileFilter()
  {
    public boolean accept(File pathname) {
      return (pathname.isDirectory()) || (pathname.getName().toLowerCase().endsWith(".eep"));
    }

    public String getDescription() {
      return "Enfilade's Easel Project File (.eep)"; }  } ;
  private boolean editing;
  public static final int MOVE = 0;
  public static final int TEXT = 1;
  public static final int RECT = 2;
  public static final int CIRCLE = 3;
  public static final int LINE = 4;
  public static final int POLY = 5;
  public static final int ROUND = 6;
  public static final int RESIZE = 7;
  public static final int IMAGE = 8;
  public JRadioButtonMenuItem aaAllOp;
  public JRadioButtonMenuItem aaTextOp;
  private ButtonGroup antialiasingGroup;
  private JMenuItem backgroundOp;
  private JToggleButton boldButton;
  private JToggleButton circleButton;
  private JMenuItem clearOption;
  private JButton deleteLayerButton;
  public JCheckBoxMenuItem drawInterfaceOp;
  private JButton duplicateButton;
  private JToggleButton editButton;
  private JMenu fileMenu;
  private JCheckBox fillBox;
  private JPanel fillColorPanel;
  private JComboBox fontDropDown;
  private JSpinner fontSize;
  private JMenuItem generateOption;
  private JMenuItem gridOptionsOption;
  private JToggleButton imageButton;
  private JToggleButton italicsButton;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JLabel jLabel6;
  private JMenuBar jMenuBar1;
  private JScrollPane jScrollPane1;
  private JSeparator jSeparator1;
  private JSeparator jSeparator2;
  private JSeparator jSeparator3;
  private JSeparator jSeparator4;
  private JButton layerDownButton;
  private JList layerList;
  private JButton layerUpButton;
  private JToggleButton lineButton;
  private JToggleButton moveButton;
  private JRadioButtonMenuItem noneOp;
  private JMenuItem openOption;
  private JToggleButton polygonButton;
  private JToggleButton rectButton;
  private JToggleButton resizeButton;
  private JToggleButton roundRectButton;
  private JButton rroptionsButton;
  private JMenuItem saveAsOption;
  private JMenuItem saveOption;
  private JButton shadowButton;
  private JPanel shadowColorPanel;
  private JSpinner shadowX;
  private JSpinner shadowY;
  public JCheckBoxMenuItem showGridBox;
  public JCheckBoxMenuItem snap2GridBox;
  private JToggleButton stringButton;
  private JCheckBox strokeBox;
  private JPanel strokeColorPanel;
  private JSpinner strokeSpinner;
  private JTextField textBox;
  private ButtonGroup toolsGroup;
  private JMenu viewMenu;

  public ToolboxFrame() { this.background = new BufferedImage(16, 16, 1);
    Graphics g = this.background.getGraphics();
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, 16, 16);
    this.dummy = new JPanel();
    this.dummy.setBackground(Color.LIGHT_GRAY);
    initComponents();
    this.fontDropDown.setSelectedItem("Arial");
    updateFont();
    this.canvasFrame = new CanvasFrame(this);
    this.canvasFrame.setVisible(true);
    this.canvasFrame.setLocation(10, 10);
    Point loc = this.canvasFrame.getLocation();
    setLocation(loc.x + this.canvasFrame.getWidth(), loc.y);
    this.colorChooser = new ColorChooserDialog(this, true);
    this.gridOptions = new GridDialog(this, true, this.colorChooser);
    this.saveDialog = new JFileChooser();
    this.saveDialog.setApproveButtonText("Save");
    this.saveDialog.setFileFilter(this.fileFilter);
    this.openDialog = new JFileChooser();
    this.openDialog.setApproveButtonText("Open");
    this.openDialog.setMultiSelectionEnabled(false);
    this.openDialog.setFileFilter(this.fileFilter);
    this.roundRectOptions = new RRDialog(this, true);
  }

  private void initComponents()
  {
    this.toolsGroup = new ButtonGroup();
    this.antialiasingGroup = new ButtonGroup();
    this.jScrollPane1 = new JScrollPane();
    this.layerList = new JList();
    this.layerUpButton = new JButton();
    this.layerDownButton = new JButton();
    this.lineButton = new JToggleButton();
    this.deleteLayerButton = new JButton();
    this.polygonButton = new JToggleButton();
    this.stringButton = new JToggleButton();
    this.rectButton = new JToggleButton();
    this.circleButton = new JToggleButton();
    this.moveButton = new JToggleButton();
    this.fillBox = new JCheckBox();
    this.fillColorPanel = new JPanel();
    this.strokeColorPanel = new JPanel();
    this.strokeBox = new JCheckBox();
    this.strokeSpinner = new JSpinner();
    this.jLabel1 = new JLabel();
    this.fontDropDown = new JComboBox();
    this.boldButton = new JToggleButton();
    this.italicsButton = new JToggleButton();
    this.fontSize = new JSpinner();
    this.editButton = new JToggleButton();
    this.textBox = new JTextField();
    this.jLabel2 = new JLabel();
    this.duplicateButton = new JButton();
    this.jLabel3 = new JLabel();
    this.shadowButton = new JButton();
    this.shadowColorPanel = new JPanel();
    this.jLabel4 = new JLabel();
    this.shadowX = new JSpinner();
    this.shadowY = new JSpinner();
    this.jLabel5 = new JLabel();
    this.jLabel6 = new JLabel();
    this.roundRectButton = new JToggleButton();
    this.rroptionsButton = new JButton();
    this.resizeButton = new JToggleButton();
    this.imageButton = new JToggleButton();
    this.jMenuBar1 = new JMenuBar();
    this.fileMenu = new JMenu();
    this.clearOption = new JMenuItem();
    this.jSeparator3 = new JSeparator();
    this.openOption = new JMenuItem();
    this.saveOption = new JMenuItem();
    this.saveAsOption = new JMenuItem();
    this.jSeparator2 = new JSeparator();
    this.generateOption = new JMenuItem();
    this.viewMenu = new JMenu();
    this.noneOp = new JRadioButtonMenuItem();
    this.aaAllOp = new JRadioButtonMenuItem();
    this.aaTextOp = new JRadioButtonMenuItem();
    this.jSeparator1 = new JSeparator();
    this.showGridBox = new JCheckBoxMenuItem();
    this.snap2GridBox = new JCheckBoxMenuItem();
    this.gridOptionsOption = new JMenuItem();
    this.jSeparator4 = new JSeparator();
    this.drawInterfaceOp = new JCheckBoxMenuItem();
    this.backgroundOp = new JMenuItem();

    setDefaultCloseOperation(0);
    setTitle("Toolbox");
    setResizable(false);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        ToolboxFrame.this.formWindowClosing(evt);
      }
    });
    this.layers = new PaintableListModel(this.layerList, this);
    this.layerList.setModel(this.layers);
    this.layerList.setSelectionMode(0);
    this.layerList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        ToolboxFrame.this.layerListValueChanged(evt);
      }
    });
    this.layerList.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent evt) {
        ToolboxFrame.this.layerListKeyReleased(evt);
      }
    });
    this.jScrollPane1.setViewportView(this.layerList);

    this.layerUpButton.setIcon(new ImageIcon(getClass().getResource("/images/layerup.png")));
    this.layerUpButton.setToolTipText("Move selected layer up.");
    this.layerUpButton.setEnabled(false);
    this.layerUpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.layerUpButtonActionPerformed(evt);
      }
    });
    this.layerDownButton.setIcon(new ImageIcon(getClass().getResource("/images/layerdown.png")));
    this.layerDownButton.setToolTipText("Move selected layer down.");
    this.layerDownButton.setEnabled(false);
    this.layerDownButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.layerDownButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.lineButton);
    this.lineButton.setIcon(new ImageIcon(getClass().getResource("/images/line.png")));
    this.lineButton.setToolTipText("Draw a line.");
    this.lineButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.lineButtonActionPerformed(evt);
      }
    });
    this.deleteLayerButton.setIcon(new ImageIcon(getClass().getResource("/images/trashcan.png")));
    this.deleteLayerButton.setToolTipText("Delete the selected layer.");
    this.deleteLayerButton.setEnabled(false);
    this.deleteLayerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.deleteLayerButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.polygonButton);
    this.polygonButton.setIcon(new ImageIcon(getClass().getResource("/images/polygon.png")));
    this.polygonButton.setToolTipText("Draw a polygon.");
    this.polygonButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.polygonButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.stringButton);
    this.stringButton.setIcon(new ImageIcon(getClass().getResource("/images/string.png")));
    this.stringButton.setToolTipText("Draw a string.");
    this.stringButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.stringButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.rectButton);
    this.rectButton.setIcon(new ImageIcon(getClass().getResource("/images/rect.png")));
    this.rectButton.setToolTipText("Draw a rectangle.");
    this.rectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.rectButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.circleButton);
    this.circleButton.setIcon(new ImageIcon(getClass().getResource("/images/circle.png")));
    this.circleButton.setToolTipText("Draw a circle or oval.");
    this.circleButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.circleButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.moveButton);
    this.moveButton.setIcon(new ImageIcon(getClass().getResource("/images/move.png")));
    this.moveButton.setToolTipText("Move selected layer.");
    this.moveButton.setEnabled(false);
    this.moveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.moveButtonActionPerformed(evt);
      }
    });
    this.fillBox.setSelected(true);
    this.fillBox.setText("Fill");
    this.fillBox.setToolTipText("Toggles whether or not this layer has a fill.");
    this.fillBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.fillBoxActionPerformed(evt);
      }
    });
    this.fillColorPanel.setBackground(new Color(255, 255, 255));
    this.fillColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    this.fillColorPanel.setToolTipText("Choose a fill color. This is also used when drawing text.");
    this.fillColorPanel.setPreferredSize(new Dimension(26, 25));
    this.fillColorPanel.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent evt) {
        ToolboxFrame.this.fillColorPanelMouseReleased(evt);
      }
    });
    GroupLayout fillColorPanelLayout = new GroupLayout(this.fillColorPanel);
    this.fillColorPanel.setLayout(fillColorPanelLayout);
    fillColorPanelLayout.setHorizontalGroup(fillColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

    fillColorPanelLayout.setVerticalGroup(fillColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

    this.strokeColorPanel.setBackground(new Color(0, 0, 0));
    this.strokeColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    this.strokeColorPanel.setToolTipText("Choose a stroke color. This is also used when drawing lines.");
    this.strokeColorPanel.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent evt) {
        ToolboxFrame.this.strokeColorPanelMouseReleased(evt);
      }
    });
    GroupLayout strokeColorPanelLayout = new GroupLayout(this.strokeColorPanel);
    this.strokeColorPanel.setLayout(strokeColorPanelLayout);
    strokeColorPanelLayout.setHorizontalGroup(strokeColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

    strokeColorPanelLayout.setVerticalGroup(strokeColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

    this.strokeBox.setSelected(true);
    this.strokeBox.setText("Stroke");
    this.strokeBox.setToolTipText("Toggles whether or not this layer has a stroke.");
    this.strokeBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.strokeBoxActionPerformed(evt);
      }
    });
    this.strokeSpinner.setModel(new SpinnerNumberModel(1, 1, 25, 1));
    this.strokeSpinner.setToolTipText("Changes how thick the stroke around this layer is.");
    this.strokeSpinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        ToolboxFrame.this.strokeSpinnerStateChanged(evt);
      }
    });
    this.jLabel1.setText("Font:");

    this.fontDropDown.setModel(new DefaultComboBoxModel(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
    this.fontDropDown.setToolTipText("Choose a font.");
    this.fontDropDown.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.fontDropDownActionPerformed(evt);
      }
    });
    this.boldButton.setFont(new Font("Tahoma", 1, 11));
    this.boldButton.setText("B");
    this.boldButton.setToolTipText("Toggles bold text.");
    this.boldButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.boldButtonActionPerformed(evt);
      }
    });
    this.italicsButton.setFont(new Font("Tahoma", 2, 11));
    this.italicsButton.setText("I");
    this.italicsButton.setToolTipText("Toggles italicized text.");
    this.italicsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.italicsButtonActionPerformed(evt);
      }
    });
    this.fontSize.setModel(new SpinnerNumberModel(9, 7, 32, 1));
    this.fontSize.setToolTipText("Change the size of your font. (Will not be shown in the preview)");
    this.fontSize.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        ToolboxFrame.this.fontSizeStateChanged(evt);
      }
    });
    this.toolsGroup.add(this.editButton);
    this.editButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
    this.editButton.setToolTipText("Edit selected layer's colors, stroke, font, and text.");
    this.editButton.setEnabled(false);
    this.editButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.editButtonActionPerformed(evt);
      }
    });
    this.textBox.setToolTipText("Text here will be drawn with the text tool.");
    this.textBox.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent evt) {
        ToolboxFrame.this.textBoxKeyReleased(evt);
      }
    });
    this.jLabel2.setText("Text:");

    this.duplicateButton.setIcon(new ImageIcon(getClass().getResource("/images/duplicate.png")));
    this.duplicateButton.setToolTipText("Duplicate selected layer.");
    this.duplicateButton.setEnabled(false);
    this.duplicateButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.duplicateButtonActionPerformed(evt);
      }
    });
    this.jLabel3.setText("Layers:");

    this.shadowButton.setIcon(new ImageIcon(getClass().getResource("/images/shadow.png")));
    this.shadowButton.setToolTipText("<html>Creates a shadow for the selected layer.<br>\nThe new shadow layer will be moved as you move the selected layer.");
    this.shadowButton.setEnabled(false);
    this.shadowButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.shadowButtonActionPerformed(evt);
      }
    });
    this.shadowColorPanel.setBackground(new Color(0, 0, 0));
    this.shadowColorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    this.shadowColorPanel.setToolTipText("Choose a stroke color. This is also used when drawing lines.");
    this.shadowColorPanel.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent evt) {
        ToolboxFrame.this.shadowColorPanelMouseReleased(evt);
      }
    });
    GroupLayout shadowColorPanelLayout = new GroupLayout(this.shadowColorPanel);
    this.shadowColorPanel.setLayout(shadowColorPanelLayout);
    shadowColorPanelLayout.setHorizontalGroup(shadowColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

    shadowColorPanelLayout.setVerticalGroup(shadowColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, 32767));

    this.jLabel4.setText("Shadow:");

    this.shadowX.setModel(new SpinnerNumberModel(Integer.valueOf(3), null, null, Integer.valueOf(1)));

    this.shadowY.setModel(new SpinnerNumberModel(Integer.valueOf(3), null, null, Integer.valueOf(1)));

    this.jLabel5.setText("X:");

    this.jLabel6.setText("Y:");

    this.toolsGroup.add(this.roundRectButton);
    this.roundRectButton.setIcon(new ImageIcon(getClass().getResource("/images/roundrect.png")));
    this.roundRectButton.setToolTipText("Draw a rounded rectangle.");
    this.roundRectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.roundRectButtonActionPerformed(evt);
      }
    });
    this.rroptionsButton.setIcon(new ImageIcon(getClass().getResource("/images/roundrectops.png")));
    this.rroptionsButton.setToolTipText("Change the arc width and height of rounded rectangles.");
    this.rroptionsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.rroptionsButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.resizeButton);
    this.resizeButton.setIcon(new ImageIcon(getClass().getResource("/images/resize.png")));
    this.resizeButton.setToolTipText("<html>Resize selected layer.<br>\n(Only usable on rectangles, rounded rectangles, and ovals)");
    this.resizeButton.setEnabled(false);
    this.resizeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.resizeButtonActionPerformed(evt);
      }
    });
    this.toolsGroup.add(this.imageButton);
    this.imageButton.setIcon(new ImageIcon(getClass().getResource("/images/image.png")));
    this.imageButton.setToolTipText("Load and draw an image from a URL.");
    this.imageButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.imageButtonActionPerformed(evt);
      }
    });
    this.fileMenu.setText("File");

    this.clearOption.setText("Clear");
    this.clearOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.clearOptionActionPerformed(evt);
      }
    });
    this.fileMenu.add(this.clearOption);
    this.fileMenu.add(this.jSeparator3);

    this.openOption.setText("Open...");
    this.openOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.openOptionActionPerformed(evt);
      }
    });
    this.fileMenu.add(this.openOption);

    this.saveOption.setText("Save");
    this.saveOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.saveOptionActionPerformed(evt);
      }
    });
    this.fileMenu.add(this.saveOption);

    this.saveAsOption.setText("Save As...");
    this.saveAsOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.saveAsOptionActionPerformed(evt);
      }
    });
    this.fileMenu.add(this.saveAsOption);
    this.fileMenu.add(this.jSeparator2);

    this.generateOption.setText("Generate Code");
    this.generateOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.generateOptionActionPerformed(evt);
      }
    });
    this.fileMenu.add(this.generateOption);

    this.jMenuBar1.add(this.fileMenu);

    this.viewMenu.setText("View");

    this.antialiasingGroup.add(this.noneOp);
    this.noneOp.setSelected(true);
    this.noneOp.setText("No anti-aliasing");
    this.viewMenu.add(this.noneOp);

    this.antialiasingGroup.add(this.aaAllOp);
    this.aaAllOp.setText("Anti-alias Shapes and Text");
    this.viewMenu.add(this.aaAllOp);

    this.antialiasingGroup.add(this.aaTextOp);
    this.aaTextOp.setText("Anti-alias Text");
    this.viewMenu.add(this.aaTextOp);
    this.viewMenu.add(this.jSeparator1);

    this.showGridBox.setText("Show Grid");
    this.viewMenu.add(this.showGridBox);

    this.snap2GridBox.setText("Snap-to Grid");
    this.viewMenu.add(this.snap2GridBox);

    this.gridOptionsOption.setText("Grid Options...");
    this.gridOptionsOption.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.gridOptionsOptionActionPerformed(evt);
      }
    });
    this.viewMenu.add(this.gridOptionsOption);
    this.viewMenu.add(this.jSeparator4);

    this.drawInterfaceOp.setSelected(true);
    this.drawInterfaceOp.setText("Draw RS Interface");
    this.viewMenu.add(this.drawInterfaceOp);

    this.backgroundOp.setIcon(new ImageIcon(this.background));
    this.backgroundOp.setText("Change Background Color");
    this.backgroundOp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        ToolboxFrame.this.backgroundOpActionPerformed(evt);
      }
    });
    this.viewMenu.add(this.backgroundOp);

    this.jMenuBar1.add(this.viewMenu);

    setJMenuBar(this.jMenuBar1);

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.stringButton, -2, 27, -2).addComponent(this.rectButton, -2, 27, -2)).addComponent(this.roundRectButton, -2, 27, -2)).addComponent(this.circleButton, -2, 27, -2).addComponent(this.lineButton, -2, 27, -2).addComponent(this.polygonButton, -2, 27, -2).addComponent(this.moveButton, -2, 27, -2).addComponent(this.resizeButton, -2, 27, -2).addComponent(this.imageButton, -2, 27, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.shadowColorPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.shadowX, -2, 40, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.shadowY, -2, 40, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.strokeColorPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.strokeBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.strokeSpinner, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.rroptionsButton, -2, 28, -2)).addGroup(layout.createSequentialGroup().addComponent(this.fillColorPanel, -2, 25, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fillBox)).addComponent(this.jLabel1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.textBox, -1, 170, 32767)).addComponent(this.fontDropDown, 0, -1, 32767)).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.boldButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.italicsButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fontSize, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jScrollPane1, 0, 0, 32767).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.layerUpButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.layerDownButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.editButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.duplicateButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.shadowButton, -2, 28, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteLayerButton, -2, 28, -2)).addComponent(this.jLabel3))).addContainerGap(20, 32767)));

    layout.linkSize(0, new Component[] { this.circleButton, this.lineButton, this.moveButton, this.polygonButton, this.rectButton, this.stringButton });

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.fillColorPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.strokeBox).addComponent(this.strokeSpinner, -2, -1, -2).addComponent(this.rroptionsButton)).addComponent(this.strokeColorPanel, -2, -1, -2))).addComponent(this.fillBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.textBox, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fontDropDown, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.boldButton).addComponent(this.italicsButton).addComponent(this.fontSize, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.editButton, GroupLayout.Alignment.LEADING).addComponent(this.shadowButton).addComponent(this.duplicateButton).addComponent(this.deleteLayerButton)).addComponent(this.layerUpButton).addComponent(this.layerDownButton))).addGroup(layout.createSequentialGroup().addComponent(this.stringButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.rectButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.roundRectButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.circleButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.lineButton).addGap(5, 5, 5).addComponent(this.polygonButton).addGap(5, 5, 5).addComponent(this.imageButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.moveButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.resizeButton))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.shadowX, -2, -1, -2).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.shadowY, -2, -1, -2)).addComponent(this.shadowColorPanel, -2, -1, -2).addComponent(this.jLabel4)).addContainerGap(22, 32767)));

    layout.linkSize(1, new Component[] { this.circleButton, this.lineButton, this.moveButton, this.polygonButton, this.rectButton, this.stringButton });

    pack();
  }

  private void formWindowClosing(WindowEvent evt) {
    if (JOptionPane.showConfirmDialog(this, "Do you really want to exit?", "Confirm", 0) == 0)
      System.exit(0);
  }

  private void layerUpButtonActionPerformed(ActionEvent evt) {
    this.layers.moveSelectedUp();
  }

  private void deleteLayerButtonActionPerformed(ActionEvent evt) {
    this.layers.removeSelected();
    checkLayerTools();
  }

  private void layerDownButtonActionPerformed(ActionEvent evt) {
    this.layers.moveSelectedDown();
  }

  private void fillBoxActionPerformed(ActionEvent evt) {
    if ((!this.strokeBox.isSelected()) || (this.stringButton.isSelected()))
      this.fillBox.setSelected(true);
    if ((this.editing) && (!(this.layers.getSelected() instanceof PLine)) && (!(this.layers.getSelected() instanceof PImage)))
      this.layers.getSelected().color = (this.fillBox.isSelected() ? this.fillColorPanel.getBackground() : null);
  }

  private void strokeBoxActionPerformed(ActionEvent evt)
  {
    if ((!this.fillBox.isSelected()) || (this.lineButton.isSelected()))
      this.strokeBox.setSelected(true);
    if ((this.editing) && (!(this.layers.getSelected() instanceof PString)) && (!(this.layers.getSelected() instanceof PImage))) {
      this.layers.getSelected().strokeColor = (this.strokeBox.isSelected() ? this.strokeColorPanel.getBackground() : null);
      this.layers.getSelected().stroke = getStroke();
    }
  }

  private void fontDropDownActionPerformed(ActionEvent evt) {
    updateFont();
  }

  private void boldButtonActionPerformed(ActionEvent evt) {
    updateFont();
  }

  private void italicsButtonActionPerformed(ActionEvent evt) {
    updateFont();
  }

  private void editButtonActionPerformed(ActionEvent evt) {
    this.editing = this.editButton.isSelected();
    if (this.editing)
      setOptions(this.layers.getSelected());
  }

  private void fillColorPanelMouseReleased(MouseEvent evt) {
    this.colorChooser.setTarget(this.fillColorPanel);
    this.colorChooser.setVisible(true);
  }

  private void strokeSpinnerStateChanged(ChangeEvent evt) {
    if ((this.editing) && (!(this.layers.getSelected() instanceof PImage)) && (!(this.layers.getSelected() instanceof PString)))
      this.layers.getSelected().stroke = new BasicStroke(((Integer)this.strokeSpinner.getValue()).intValue());
  }

  private void strokeColorPanelMouseReleased(MouseEvent evt) {
    this.colorChooser.setTarget(this.strokeColorPanel);
    this.colorChooser.setVisible(true);
  }

  private void layerListValueChanged(ListSelectionEvent evt) {
    boolean enabled = this.layers.selectedIndexIsValid();
    checkLayerTools();
    if (this.editing)
      setOptions(this.layers.getSelected());
  }

  private void duplicateButtonActionPerformed(ActionEvent evt) {
    Paintable p = this.layers.getSelected();
    if (p != null)
      this.layers.insert(p.duplicate());
  }

  private void fontSizeStateChanged(ChangeEvent evt) {
    if ((this.editing) && ((this.layers.getSelected() instanceof PString)))
      updateFont();
  }

  private void textBoxKeyReleased(KeyEvent evt) {
    if ((this.editing) && ((this.layers.getSelected() instanceof PString)))
      ((PString)this.layers.getSelected()).text = this.textBox.getText();
  }

  private void moveButtonActionPerformed(ActionEvent evt)
  {
    this.editing = false;
    this.canvas.setCursor(Cursor.getPredefinedCursor(13));
    this.canvas.reset();
  }

  private void stringButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.fillBox.setSelected(true);
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    this.canvas.reset();
  }

  private void rectButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    this.canvas.reset();
  }

  private void circleButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    this.canvas.reset();
  }

  private void lineButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.strokeBox.setSelected(true);
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    this.canvas.reset();
  }

  private void polygonButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    this.canvas.reset();
  }

  private void clearOptionActionPerformed(ActionEvent evt) {
    if ((this.layers.getSize() > 0) && (JOptionPane.showConfirmDialog(this, "Do you really want to clear the screen?", "Confirm", 0) == 0))
      this.layers.clear();
  }

  private void layerListKeyReleased(KeyEvent evt) {
    switch (evt.getKeyCode()) {
    case 127:
      this.layers.removeSelected();
      break;
    case 85:
      this.layers.moveSelectedUp();
      break;
    case 68:
      this.layers.moveSelectedDown();
    }
  }

  private void shadowButtonActionPerformed(ActionEvent evt)
  {
    Paintable selection = this.layers.getSelected();
    if (selection != null)
    {
      Paintable p;
      if ((selection instanceof PImage))
        p = new PRectangle(selection.x, selection.y, selection.w, selection.h, Color.BLACK, null, null);
      else
        p = selection.duplicate();
      int moveX = ((Integer)this.shadowX.getValue()).intValue();
      int moveY = ((Integer)this.shadowY.getValue()).intValue();
      p.x += moveX;
      p.y += moveY;
      if ((p instanceof PPolygon))
        ((PPolygon)p).move(moveX, moveY);
      else if ((p instanceof PLine))
        ((PLine)p).move(moveX, moveY);
      if (p.color != null)
        p.color = this.shadowColorPanel.getBackground();
      if (p.strokeColor != null)
        p.strokeColor = this.shadowColorPanel.getBackground();
      selection.shadow = p;
      this.layers.add(this.layers.getSelectedIndex(), p);
    }
  }

  private void shadowColorPanelMouseReleased(MouseEvent evt) {
    this.colorChooser.setTarget(this.shadowColorPanel);
    this.colorChooser.setVisible(true);
  }

  private void gridOptionsOptionActionPerformed(ActionEvent evt) {
    this.gridOptions.setLocationRelativeTo(this);
    this.gridOptions.setVisible(true);
  }

  private void roundRectButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    this.canvas.reset();
  }

  private void rroptionsButtonActionPerformed(ActionEvent evt) {
    this.roundRectOptions.setLocationRelativeTo(this);
    this.roundRectOptions.setVisible(true);
  }

  private void resizeButtonActionPerformed(ActionEvent evt) {
    this.canvas.setCursor(Cursor.getPredefinedCursor(6));
    this.canvas.reset();
  }

  private void openOptionActionPerformed(ActionEvent evt) {
    if ((this.layers.getSize() > 0) && (JOptionPane.showConfirmDialog(this, "Opening a project will clear the screen. Are you sure?", "Confirm", 0) != 0))
      return;
    open();
  }

  private void saveAsOptionActionPerformed(ActionEvent evt) {
    saveAs();
  }

  private void saveOptionActionPerformed(ActionEvent evt) {
    save();
  }

  private void generateOptionActionPerformed(ActionEvent evt) {
    int aa = 0;
    if (this.aaAllOp.isSelected())
      aa = 1;
    else if (this.aaTextOp.isSelected())
      aa = 2;
    String[] gen = JavaConverter.convertLayersToJava(this.layers.getArrayList(), aa);
    new CodeDialog(this, true, gen[0], gen[1]).setVisible(true);
  }

  private void imageButtonActionPerformed(ActionEvent evt) {
    this.editing = false;
    this.canvas.setCursor(Cursor.getPredefinedCursor(1));
    String enteredURL;
    Image loaded = PImage.getImageFrom(enteredURL = JOptionPane.showInputDialog(this, "Enter the image's URL."));
    if (loaded == null) {
      JOptionPane.showMessageDialog(this, "Invalid URL!");
      return;
    }if (loaded == PImage.IMG_ERROR_IMG)
      JOptionPane.showMessageDialog(this, "Could not load the image, but you can still add it to your paint.");
    this.loadedImage = loaded;
    this.imageURL = enteredURL;
  }

  private void backgroundOpActionPerformed(ActionEvent evt) {
    this.colorChooser.setTarget(this.dummy);
    this.colorChooser.setVisible(true);
  }

  public Image getImage() {
    return this.loadedImage;
  }

  public String getImageURL() {
    return this.imageURL;
  }

  public void open() {
    if (this.openDialog.showOpenDialog(this) == 0) {
      File f = this.openDialog.getSelectedFile();
      if (!f.exists()) {
        JOptionPane.showMessageDialog(this, "File does not exist!");
        return;
      }
      ArrayList newLayers = PaintIO.open(f, this);
      if (newLayers == null) {
        JOptionPane.showMessageDialog(this, "Error reading file!");
        return;
      }
      this.file = f;
      this.canvasFrame.setTitle("Preview of " + f.getName());
      this.layers.clear();
      this.layers.getArrayList().addAll(newLayers);
      this.layerList.updateUI();
      checkLayerTools();
    }
  }

  public void save() {
    if (this.file == null)
      saveAs();
    else
      PaintIO.saveLayersAs(this.layers.getArrayList(), this.file, this);
  }

  public void saveAs() {
    if (this.saveDialog.showSaveDialog(this) == 0) {
      File f = this.saveDialog.getSelectedFile();
      if (!f.getName().endsWith(".eep"))
        f = new File(f.getPath() + ".eep");
      if ((f.exists()) && (JOptionPane.showConfirmDialog(this, "Do you really want to overwrite " + f.getName() + "?", "Confirm", 0) != 0))
        return;
      if (PaintIO.saveLayersAs(this.layers.getArrayList(), f, this)) {
        this.file = f;
        this.canvasFrame.setTitle("Preview of " + f.getName());
      }
    }
  }

  public void roundRectDialogClosed() {
    if ((this.editing) && ((this.layers.getSelected() instanceof PRoundRect))) {
      PRoundRect p = (PRoundRect)this.layers.getSelected();
      p.arcWidth = getArcWidth();
      p.arcHeight = getArcHeight();
    }
  }

  public void checkLayerTools() {
    Paintable selection = this.layers.getSelected();
    boolean enabled = selection != null;

    boolean resizable = ((selection instanceof PRectangle)) || ((selection instanceof PRoundRect)) || ((selection instanceof PCircle));

    this.moveButton.setEnabled(enabled);
    this.layerUpButton.setEnabled(enabled);
    this.layerDownButton.setEnabled(enabled);
    this.deleteLayerButton.setEnabled(enabled);
    this.editButton.setEnabled(enabled);
    this.duplicateButton.setEnabled(enabled);
    this.shadowButton.setEnabled(enabled);
    this.resizeButton.setEnabled((enabled) && (resizable));
    if (!this.fillBox.isEnabled())
      this.fillBox.setSelected(false);
    this.editing = ((enabled) && (this.editing));
  }

  public Color getGridColor() {
    return this.gridOptions.gridColorPanel.getBackground();
  }

  public int getArcWidth() {
    return ((Integer)this.roundRectOptions.arcWidth.getValue()).intValue();
  }

  public int getArcHeight() {
    return ((Integer)this.roundRectOptions.arcHeight.getValue()).intValue();
  }

  public Color getCanvasBackground() {
    return this.dummy.getBackground();
  }

  public void colorChosen(JPanel p) {
    if (p == this.dummy) {
      Graphics g = this.background.getGraphics();
      g.setColor(this.dummy.getBackground());
      g.fillRect(0, 0, 16, 16);
      return;
    }
    if (!this.editing)
      return;
    if ((p == this.fillColorPanel) && (this.fillBox.isSelected())) {
      this.layers.getSelected().color = this.fillColorPanel.getBackground();
    } else if ((p == this.strokeColorPanel) && (this.strokeBox.isSelected())) {
      this.layers.getSelected().strokeColor = this.strokeColorPanel.getBackground();
    } else if ((p == this.shadowColorPanel) && (this.layers.selectedIndexIsValid())) {
      Paintable shadow = this.layers.getSelected().shadow;
      if (shadow.strokeColor != null)
        shadow.strokeColor = this.shadowColorPanel.getBackground();
      if (shadow.color != null)
        shadow.color = this.shadowColorPanel.getBackground();
    }
  }

  private void setOptions(Paintable p) {
    if ((p.stroke != null) && (p.strokeColor != null)) {
      this.strokeColorPanel.setBackground(p.strokeColor);
      this.strokeSpinner.setValue(new Integer((int)p.stroke.getLineWidth()));
      this.strokeBox.setSelected(true);
    } else {
      this.strokeBox.setSelected(false);
    }if (p.color != null) {
      this.fillColorPanel.setBackground(p.color);
      this.fillBox.setSelected(true);
    } else {
      this.fillBox.setSelected(false);
    }if ((p instanceof PString)) {
      this.textBox.setText(((PString)p).text);
      Font f = ((PString)p).font;
      this.fontDropDown.setSelectedItem(f.getName());
      int flags = f.getStyle();
      this.boldButton.setSelected((flags & 0x1) > 0);
      this.italicsButton.setSelected((flags & 0x2) > 0);
      this.fontSize.setValue(new Integer(f.getSize()));
    } else if ((p instanceof PRoundRect)) {
      PRoundRect prr = (PRoundRect)p;
      this.roundRectOptions.arcWidth.setValue(Integer.valueOf(prr.arcWidth));
      this.roundRectOptions.arcHeight.setValue(Integer.valueOf(prr.arcHeight));
    }
  }

  public int getSelectedTool()
  {
    if (this.moveButton.isSelected())
      return 0;
    if (this.stringButton.isSelected())
      return 1;
    if (this.rectButton.isSelected())
      return 2;
    if (this.circleButton.isSelected())
      return 3;
    if (this.lineButton.isSelected())
      return 4;
    if (this.polygonButton.isSelected())
      return 5;
    if (this.roundRectButton.isSelected())
      return 6;
    if (this.resizeButton.isSelected())
      return 7;
    if (this.imageButton.isSelected())
      return 8;
    return -1;
  }

  public int getGridWidth() {
    return ((Integer)this.gridOptions.cellWidth.getValue()).intValue();
  }

  public int getGridHeight() {
    return ((Integer)this.gridOptions.cellHeight.getValue()).intValue();
  }

  public Font getSelectedFont() {
    int flags = 0;
    if (this.boldButton.isSelected())
      flags |= 1;
    if (this.italicsButton.isSelected())
      flags |= 2;
    return new Font((String)this.fontDropDown.getSelectedItem(), flags, ((Integer)this.fontSize.getValue()).intValue());
  }

  private void updateFont() {
    if ((this.editing) && ((this.layers.getSelected() instanceof PString)))
      ((PString)this.layers.getSelected()).font = getSelectedFont();
  }

  public Font getPreviewFont()
  {
    int flags = 0;
    if (this.boldButton.isSelected())
      flags |= 1;
    if (this.italicsButton.isSelected())
      flags |= 2;
    return new Font((String)this.fontDropDown.getSelectedItem(), flags, 11);
  }

  public BasicStroke getStroke() {
    if (this.strokeBox.isSelected())
      return new BasicStroke(((Integer)this.strokeSpinner.getValue()).intValue());
    return null;
  }

  public Color getStrokeColor() {
    if (this.strokeBox.isSelected())
      return this.strokeColorPanel.getBackground();
    return null;
  }

  public String getString() {
    return this.textBox.getText();
  }

  public Color getColor() {
    if (this.fillBox.isSelected())
      return this.fillColorPanel.getBackground();
    return null;
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        new ToolboxFrame().setVisible(true);
      }
    });
  }
}
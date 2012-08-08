package ui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import paint.Paintable;

public class PaintableListModel extends AbstractListModel
{
  private ArrayList<Paintable> list;
  private JList parent;
  private ToolboxFrame frame;

  public PaintableListModel(JList parent, ToolboxFrame frame)
  {
    this.list = new ArrayList();
    this.parent = parent;
    this.frame = frame;
  }
  public ArrayList<Paintable> getArrayList() {
    return this.list;
  }
  public void add(Paintable p) {
    this.list.add(p);
    this.parent.updateUI();
    this.frame.checkLayerTools();
  }

  public void clear() {
    this.list.clear();
    this.parent.updateUI();
    this.frame.checkLayerTools();
  }

  public int getSize() {
    return this.list.size();
  }

  public Object getElementAt(int index) {
    return ((Paintable)this.list.get(convert(index))).getListText();
  }

  public Paintable get(int index) {
    return (Paintable)this.list.get(index);
  }

  public Paintable getSelected() {
    int index = getSelectedIndex();
    if (!validIndex(index))
      return null;
    return (Paintable)this.list.get(index);
  }

  public Paintable remove(int index) {
    if (!validIndex(index))
      return null;
    Paintable removed = (Paintable)this.list.remove(index);
    this.parent.updateUI();
    this.frame.checkLayerTools();
    return removed;
  }

  public void add(int index, Paintable p) {
    this.list.add(index, p);
    this.parent.updateUI();
    this.frame.checkLayerTools();
  }

  public Paintable removeSelected() {
    return remove(getSelectedIndex());
  }

  public void moveSelectedUp() {
    int index = getSelectedIndex() + 1;
    if (index >= this.list.size())
      return;
    Paintable selected = removeSelected();
    if (selected != null) {
      add(index, selected);
      this.parent.setSelectedIndex(convert(index));
    }
  }

  public void insert(Paintable p) {
    int index = getSelectedIndex() + 1;
    if (validIndex(index))
      add(index, p);
    else
      add(p);
  }

  public void moveSelectedDown() {
    int index = getSelectedIndex() - 1;
    if (index < 0)
      return;
    Paintable selected = removeSelected();
    if (selected != null) {
      add(index, selected);
      this.parent.setSelectedIndex(convert(index));
    }
  }

  public int getSelectedIndex() {
    return convert(this.parent.getSelectedIndex());
  }

  public int convert(int index) {
    return this.list.size() - 1 - index;
  }

  public boolean validIndex(int index) {
    return (index >= 0) && (index < this.list.size());
  }

  public boolean selectedIndexIsValid() {
    return validIndex(getSelectedIndex());
  }

  public void paintAllTo(Graphics2D g) {
    for (Paintable p : this.list)
      p.paintTo(g);
  }
}
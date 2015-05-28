/**
 * Author: Piotr Kordy (piotr.kordy@uni.lu <mailto:piotr.kordy@uni.lu>)
 * Date:   06/06/2013
 * Copyright (c) 2013,2012 University of Luxembourg -- Faculty of Science,
 *     Technology and Communication FSTC
 * All rights reserved.
 * Licensed under GNU Affero General Public License 3.0;
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as
 *    published by the Free Software Foundation, either version 3 of the
 *    License, or (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lu.uni.adtool.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventObject;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import lu.uni.adtool.domains.rings.Bool;
import lu.uni.adtool.domains.rings.BoundedInteger;
import lu.uni.adtool.domains.rings.LMHEValue;
import lu.uni.adtool.domains.rings.LMHValue;
import lu.uni.adtool.domains.rings.RealG0;
import lu.uni.adtool.domains.rings.RealZeroOne;
import lu.uni.adtool.domains.rings.Ring;

/**
 * Class showing the map between node names and its valuations.
 *
 * @author Piot Kordy
 */
public class ValuationView extends JPanel implements KeyListener,ListSelectionListener
{
  static final long serialVersionUID = 94565646654411328L;
  private final String proText = "Proponent";
  private JLabel message;
  private JTable table;
  private JScrollPane scrollPane;
  private ValuationTableModel tableModel;
  private ListSelectionModel listSelectionModel;
  /**
   * Constructs a new instance.
   * @param newCanvas associated domain canvas
   */
  public ValuationView()
  {
    super(new BorderLayout());
    message = createMessage();
    add(message);
    tableModel = new ValuationTableModel();
    tableModel.setCanvas(null);
    table=new JTable(tableModel);
    listSelectionModel = table.getSelectionModel();
    listSelectionModel.addListSelectionListener(this);
    table.setSelectionModel(listSelectionModel);
    scrollPane = null;//new JScrollPane(createTable());
  }
  private JLabel createMessage(){
    JLabel result;
    result = new JLabel("No attribute domain chosen.");
    return result;
  }

  private JPanel createTable(){
    JPanel result = new JPanel();
    result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
    table = new JTable(tableModel)
    {
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      public boolean editCellAt(int row, int column, EventObject e)
      {
    //scrollPane.setVisible(false);
        if(!isCellSelected(row,column)){
          return false;
        };
        String type = (String)getValueAt(row,0);
        String key = (String)getValueAt(row,1);
        boolean editable = false;

        int[] selection = table.getSelectedRows();
        for (int i = 0; (i < selection.length)&&!editable; i++) {
           type = (String)getValueAt(selection[i],0);
           key = (String)getValueAt(selection[i],1);
          if(getCanvas().getDomain().isValueModifiable(type.equals(proText))){
            editable = true;
          }
        }
        if (editable){
          Ring value = editValue(type,key);
          if (value != null){
           selection = table.getSelectedRows();
           for (int i = 0; i < selection.length; i++) {
             type = (String)getValueAt(selection[i],0);
             key = (String)getValueAt(selection[i],1);
             if(getCanvas().getDomain().isValueModifiable(type.equals(proText))){
               getCanvas().putNewValue(type.equals(proText),key,value);
             }
           }
           getCanvas().valuesUpdated();
          }
        }
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            //table.requestFocusInWindow();
            table.requestFocus();
          }
        });
        return false;
      }
    };
    table.setSelectionModel(listSelectionModel);
    table.setAutoCreateRowSorter(false);
    table.setDefaultRenderer(Ring.class, new ValuationRenderer());
    table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
    table.setFillsViewportHeight(true);
    result.add(new JLabel(getCanvas().getDomain().getName()));
    result.add(table.getTableHeader());
    result.add(table);
    return result;
  }
  /**
   * Shows the dialog to edit a value in the tree canvas.
   *
   * @param type can be proponent or opponent
   * @param key
   */

  public Ring editValue(String type, String key)
  {
    Ring value;
    InputDialog dialog;
    if (type.equals(proText)) {
      value = getCanvas().getValueAssPro().get(key);
    }
    else {
      value = getCanvas().getValueAssOpp().get(key);
    }

   if (value instanceof Bool) {
      value = (Bool) Bool.not((Bool) value);
    }
    else if (value instanceof RealG0) {
      dialog = new RealG0Dialog((Frame) getCanvas().getMainWindow());
      value = (RealG0) (dialog.showInputDialog(value));
    }
    else if (value instanceof RealZeroOne) {
       dialog = new RealZeroOneDialog(getCanvas().getMainWindow());
       value = (RealZeroOne) (dialog.showInputDialog(value));
     }
     else if (value instanceof BoundedInteger) {
       dialog = new BoundedIntegerDialog(getCanvas().getMainWindow());
       value = (BoundedInteger) (dialog.showInputDialog(value));
     }
     else if (value instanceof LMHValue) {
       dialog = new LMHDialog(getCanvas().getMainWindow());
       value = (LMHValue) (dialog.showInputDialog(value));
     }
     else if (value instanceof LMHEValue) {
       dialog = new LMHEDialog(getCanvas().getMainWindow());
       value = (LMHEValue) (dialog.showInputDialog(value));
     }
     else{
       System.out.println("Unknown type of ring for class"+value.getClass()+" and value "+value);
     }
    return value;
  }
  public void keyPressed(KeyEvent e)
  {
  }

  public void keyReleased(KeyEvent e)
  {
  }

  public void keyTyped(KeyEvent e)
  {
  }

  @SuppressWarnings("unchecked")
  public void assignCanvas(ADTreeCanvas canvas)
  {
    removeAll();
    if (canvas instanceof DomainCanvas){
      table.setRowSorter(null);
      tableModel.setCanvas((DomainCanvas<Ring>)canvas);
      message=null;
      scrollPane=new JScrollPane(createTable());
      add(scrollPane);
      ((JComponent) scrollPane.getParent()).revalidate();
      table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
    }
    else{
      tableModel.setCanvas(null);
      table.setRowSorter(null);
      scrollPane=null;
      message=createMessage();
      add(message);
    }
  }
  /**
   * Return associated canvas.
   *
   * @return
   */
  public DomainCanvas<Ring> getCanvas()
  {
    return tableModel.getCanvas();
  }

  /**
   * Renderer for table cells.
   */
  class ValuationRenderer extends DefaultTableCellRenderer implements TableCellRenderer
  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public ValuationRenderer()
    {
      super();
    }
    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
      if(value instanceof Ring){
        return super.getTableCellRendererComponent(table,((Ring)value).toUnicode(), isSelected,hasFocus,row,column);
      }
      else{
        return super.getTableCellRendererComponent(table,value, isSelected,hasFocus,row,column);
      }
    }
  }
  public void valueChanged(ListSelectionEvent e) {
    if (getCanvas()==null) return;
    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
    getCanvas().unmarkAll();
    if (!lsm.isSelectionEmpty()) {
      // Find out which indexes are selected.
      int minIndex = lsm.getMinSelectionIndex();
      int maxIndex = lsm.getMaxSelectionIndex();
      for (int i = minIndex; i <= maxIndex; i++) {
        if (lsm.isSelectedIndex(i)) {
          int j =table.convertRowIndexToModel(i);
          String key =(String)(table.getModel().getValueAt(j,1));
          String type =(String)(table.getModel().getValueAt(j,0));
          if (type.equals("Proponent")){
            getCanvas().markPro(key);
          }
          else{
            getCanvas().markOpp(key);
          }
        }
      }
    }
    getCanvas().repaint();
  }

  class ValuationTableModel extends DefaultTableModel{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private DomainCanvas<Ring> canvas;
    public ValuationTableModel(){
      super();
      super.addColumn("Node Type");
      super.addColumn("Node Label");
      super.addColumn("Assigned Value");
    }
    /**
     * {@inheritDoc}
     * @see TableModel#getColumnClass(int)
     */
    public Class<?> getColumnClass(int columnIndex)
    {
      if(columnIndex==2) {
        return Ring.class;
      }
      else{
        return getValueAt(0, columnIndex).getClass();
      }
    }
    /**
     * {@inheritDoc}
     * @see TableModel#isCellEditable(int,int)
     */
    public boolean isCellEditable(int row, int col)
    {
      if(col>1){
        return true;
      }
      else{
        return false;
      }
    }

    /**
     * {@inheritDoc}
     * @see DefaultTableModel#setValueAt(Object value, int row, int col)
     */
     public void setValueAt(Object value, int row, int col)
    {
      super.setValueAt(value,row,col);
      fireTableCellUpdated(row, col);
    }

    public void setCanvas(DomainCanvas<Ring> canvas)
    {
      this.canvas = canvas;
      if (canvas!=null){
        updateRowData();
        fireTableDataChanged();
      }
    }

    @SuppressWarnings("rawtypes")
    private void updateRowData(){
      if(canvas == null){
        return;
      }
      Set<String> keys = canvas.getValueAssPro().keySet();
      setRowCount(0);
      for (String key:keys){
        Vector<Comparable> v = new Vector<Comparable>();
        v.add(proText);
        v.add(key);
        v.add(canvas.getValueAssPro().get(key));
        addRow(v);
      }
      keys = canvas.getValueAssOpp().keySet();
      for (String key:keys){
        Vector<Comparable> v = new Vector<Comparable>();
        v.add("Opponent");
        v.add(key);
        v.add(canvas.getValueAssOpp().get(key));
        addRow(v);
      }
    }

    /**
     * Gets the canvas for this instance.
     *
     * @return The canvas.
     */
    public DomainCanvas<Ring> getCanvas()
    {
      return this.canvas;
    }
  }
}

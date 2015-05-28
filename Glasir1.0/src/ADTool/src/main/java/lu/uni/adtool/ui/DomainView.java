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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * A view of an dynamically added attiribute domain.
 *
 * @author Piot Kordy
 * @version
 */
public class DomainView<Type> extends JPanel implements ItemListener
{
  static final long serialVersionUID = 152365011570211223L;
//   private DomainCanvas<Type> canvas;
  private int id; //id of canvas and a view
  private DomainCanvas<Type> canvas;
  private MainWindow parent;
  private JCheckBox labelBox;
  private JCheckBox allLabelBox;
  private JCheckBox nodeSizeBox;
  private JCheckBox markEditableBox;
  private JButton closeDomain;
  /**
   * Constructs a new instance.
   *
   * @param newParent main window of the application
   * @param newId id of the view and of a canvas
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public DomainView(final MainWindow newParent, DomainCanvas newCanvas, final int newId)
  {
    super(new BorderLayout());
    id = newId;
    canvas=newCanvas;
    parent=newParent;
    initLayout();
  }
  /**
   * Initialize the layout of a panel.
   * @param domain associated domain
   *
   */
  private void initLayout()
  {
    labelBox = new JCheckBox("Show Labels");
    labelBox.setMnemonic(KeyEvent.VK_L);
    labelBox.setSelected(true);
    labelBox.addItemListener(this);
    allLabelBox = new JCheckBox("Show Computed Values for Subtrees");
    allLabelBox.setMnemonic(KeyEvent.VK_V);
    allLabelBox.setSelected(true);
    allLabelBox.addItemListener(this);
    markEditableBox = new JCheckBox("Mark Editable Nodes");
    markEditableBox.setMnemonic(KeyEvent.VK_M);
    markEditableBox.setSelected(canvas.isMarkEditable());
    markEditableBox.addItemListener(this);
    canvas.setLocalExtentProvider(false);
    canvas.setShowLabels(true);
    canvas.setShowAllLabels(true);
    nodeSizeBox = new JCheckBox("Local Node Size");
    nodeSizeBox.setMnemonic(KeyEvent.VK_S);
    nodeSizeBox.setSelected(false);
    nodeSizeBox.addItemListener(this);
    final CollapsiblePanel optionsPanel = new CollapsiblePanel("Options");
    final JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel,BoxLayout.LINE_AXIS));
    closeDomain=new JButton("Remove Domain");
    final MainWindow window= parent;
    closeDomain.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        //Execute when button is pressed
        final int result = JOptionPane.showConfirmDialog(parent.getFrame(),
        "Do you really want to remove the domain? If you select \"Yes\", all domain values and computations will be lost.",
         "New Tree", JOptionPane.YES_NO_OPTION);
         if (result == JOptionPane.YES_OPTION){
           window.removeDomain(id);
         }
      }
    });
    checkBoxPanel.add(closeDomain);
    checkBoxPanel.add(labelBox);
    checkBoxPanel.add(nodeSizeBox);
    checkBoxPanel.add(markEditableBox);
    checkBoxPanel.add(allLabelBox);
    optionsPanel.add(checkBoxPanel);
    optionsPanel.toggleVisibility(true);
    final JScrollPane scrollPane = new JScrollPane(canvas);
    scrollPane.add(new JLabel("test"));
    canvas.setVisible(true);
    scrollPane.setAutoscrolls(true);
    canvas.setScrollPane(scrollPane);
    add(scrollPane);
    add(optionsPanel,BorderLayout.PAGE_END);
  }

  /** Listens to the check boxes.
   * @param e event.
   */
  public final void itemStateChanged(final ItemEvent e)
  {
    if (canvas==null){
      getMainWindow().getStatusBar().reportError("Associated canvas is null");
      return;
    }
    final Object source = e.getItemSelectable();
    if (source == labelBox) {
      if (e.getStateChange() == ItemEvent.DESELECTED) {
        canvas.setShowLabels(false);
      }
      else {
        canvas.setShowLabels(true);
      }
    }
    if (source == allLabelBox) {
      if (e.getStateChange() == ItemEvent.DESELECTED) {
        canvas.setShowAllLabels(false);
      }
      else {
        canvas.setShowAllLabels(true);
      }
    }
    if (source == markEditableBox) {
      if (e.getStateChange() == ItemEvent.DESELECTED) {
        canvas.setMarkEditable(false);
      }
      else {
        canvas.setMarkEditable(true);
      }
    }
    if (source == nodeSizeBox) {
      if (e.getStateChange() == ItemEvent.DESELECTED) {
        canvas.setLocalExtentProvider(false);
      }
      else {
        canvas.setLocalExtentProvider(true);
      }
    }
  }

  /**
   * Retuns associated canvas.
   *
   * @return canvas.
   */
  public final DomainCanvas<Type> getCanvas()
  {
    return canvas;
  }

  /**
   * Gets the id for this instance.
   *
   * @return The id.
   */
  public final int getId()
  {
    return this.id;
  }

  /**
   * Gets the mainWindow for this instance.
   *
   * @return The mainWindow.
   */
  public MainWindow getMainWindow()
  {
    return parent;
  }


  /**
   * Deregister canvas when windows is closed.
   *
   */
  public final void onClose()
  {
    canvas.getTree().removeTreeChangeListener(canvas);
    canvas.getTree().deregisterSizeCanvas(canvas);
  }
  /**
   * Returns an icon for the view.
   *
   * @return default icon
   */
  public final Icon getIcon()
  {
    return new ImageIcon(this.getClass().getResource("/icons/new.png"));
  }

}

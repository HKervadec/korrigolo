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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import lu.uni.adtool.Main;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.domains.Domain;

/**
 * A mouse and keyboard handler for DomainCanvas.
 *
 * @author Piot Kordy
 */
public class DomainCanvasHandler<Type> extends AbstractCanvasHandler
{
  private JPopupMenu   pmenu;
  private ADTreeNode   menuNode;
  private JMenuItem    editValueItem;
  private JMenuItem    toggleAboveFold;
  private JSeparator   separator;
  private JMenuItem    toggleFold;

  /**
   * Constructs a new instance.
   */
  public DomainCanvasHandler(final DomainCanvas<Type> canvas)
  {
    super(canvas);
    initPopupMenu();
  }
  /**
   * {@inheritDoc}
   * @see java.awt.event.KeyListener#keyPressed(KeyEvent)
   */
  public void keyPressed(final KeyEvent e)
  {
    boolean consume = true;
    final ADTreeNode node = canvas.getFocused();
    if (node == null) {
      return;
    }
    else{
      menuNode = node;
    }
    if(e.isControlDown()) {
      consume = false;
    }
    else {
      if (e.isShiftDown()) {

        switch (e.getKeyCode()) {
          case KeyEvent.VK_SPACE:
            if (node != null) {
              menuNode = node;
              canvas.toggleAboveFold(menuNode);
            }
            break;
          default:
            consume = false;
            break;
        }
      }
      else{
        switch (e.getKeyCode()) {
          case KeyEvent.VK_ENTER:
            if (node != null) {
              menuNode = node;
              changeValueActionPerformed();
            }
            break;
          case KeyEvent.VK_PLUS:
          case KeyEvent.VK_ADD:
          case KeyEvent.VK_EQUALS:
            canvas.zoomIn();
            break;
          case KeyEvent.VK_MINUS:
          case KeyEvent.VK_SUBTRACT:
            canvas.zoomOut();
            break;
          case KeyEvent.VK_O:
            canvas.resetZoom();
            break;
          case KeyEvent.VK_SPACE:
            if (node != null) {
              menuNode = node;
              canvas.toggleFold(menuNode);
            }
          case KeyEvent.VK_Z:
        	  canvas.undo();
        	  break;
          default:
            consume = false;
        }
      }
    }
    if (!consume){
      super.keyPressed(e);
    }
  }

  /**
   * {@inheritDoc}
   * @see MouseListener#mouseClicked(MouseEvent)
   */
  @SuppressWarnings("unchecked")
  public void mouseClicked(final MouseEvent e)
  {
	if (Main.viewmodeIsOn)
	{
		return;
	}
    canvas.requestFocusInWindow();
    final ADTreeNode node = this.canvas.getNode(e.getX(), e.getY());
    if (node != null) {
      if (e.getModifiers() == InputEvent.BUTTON3_MASK
          || e.getModifiers() == InputEvent.CTRL_MASK) {
        menuNode = node;
        this.pmenu.show(e.getComponent(), e.getX(), e.getY());
        setFocus(node);
      }
      else {
        if (node.equals(canvas.getFocused())) {
          menuNode = node;
         ((DomainCanvas<Type>)canvas).editValue(menuNode);
          // canvas.toggleExpandNode(node);
          // this.canvas.repaint();
        }
        else {
          setFocus(node);
        }
      }
    }
  }
  /**
   * Set new focus and update context menu visibility.
   *
   * @param node
   */
  @SuppressWarnings("unchecked")
  public void setFocus(final ADTreeNode node)
  {
    if (node != null){
//       final ADTNode.Type t = node.getTerm().getType();
      Domain<Type> d = ((DomainCanvas<Type>)canvas).getDomain();
      editValueItem.setEnabled(node.getTerm().isEditable(d));
      boolean canFold;
      boolean canFoldAbove;
      if (node.isFolded()) {
        toggleFold.setText("Expand Below");
        canFold = true;
      }
      else {
        toggleFold.setText("Fold Below");
        canFold = canvas.getTree().getChildrenList(node, false).size() > 0;
      }
      if (node.isAboveFolded()) {
        toggleAboveFold.setText("Expand Above");
        canFoldAbove = true;
      }
      else {
        toggleAboveFold.setText("Fold Above");
        canFoldAbove = (canvas.getTree().getParent(node, false) != null);
      }
      toggleAboveFold.setVisible(canFoldAbove);
      toggleFold.setVisible(canFold);
      separator.setVisible(canFold&&canFoldAbove);
      pmenu.pack();
    }
    super.setFocus(node);
  }
  /**
   * Initialise context menu.
   *
   */
  private void initPopupMenu()
  {
    menuNode = null;
    this.pmenu = new JPopupMenu();
    editValueItem = new JMenuItem("Edit Assigned Value");
    editValueItem.setAccelerator(KeyStroke.getKeyStroke('\n'));
    editValueItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        changeValueActionPerformed();
      }
    });
    pmenu.add(editValueItem);
    separator = new JSeparator();
    pmenu.add(separator);
    toggleAboveFold = new JMenuItem("Fold Above");
    toggleAboveFold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
        InputEvent.SHIFT_MASK));
    toggleAboveFold.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.toggleAboveFold(menuNode);
        }
      }
    });
    pmenu.add(toggleAboveFold);
    toggleFold = new JMenuItem("Fold Below");
    toggleFold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,true));
    toggleFold.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.toggleFold(menuNode);
        }
      }
    });
    pmenu.add(toggleFold);
  }
  /**
   * Displays a dialog to change the value of the node
   *
   */
  @SuppressWarnings("unchecked")
  private void changeValueActionPerformed()
  {
    if (menuNode != null) {
     ((DomainCanvas<Type>)canvas).editValue(menuNode);
    }
  }
}


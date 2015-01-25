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
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import lu.uni.adtool.adtree.ADTreeNode;

/**
 * A mouse handler for ADTreeCanvas.
 *
 * @author Piot Kordy
 */
public class ADTreeCanvasHandler extends AbstractCanvasHandler
{
  //private ADTreeCanvas canvas;
  private JPopupMenu   pmenu;
  private JMenuItem    toggleAboveFold;
  private JMenuItem    toggleFold;
  private JMenuItem    addCounter;
  private JMenuItem    addLeft;
  private JMenuItem    addRight;
  private JMenuItem    remove;
  private JMenuItem    removeChild;
  private ADTreeNode   menuNode;
  //private Point2D      dragStart;
  //private boolean      dragScroll;

  /**
   * Constructs a new instance.
   *
   * @param canvas parent canvas
   */
  public ADTreeCanvasHandler(final ADTreeCanvas canvas)
  {
    super(canvas);
    initPopupMenu();
  }

  /**
   * {@inheritDoc}
   * @see KeyListener#keyPressed(KeyEvent)
   */
  public void keyPressed(final KeyEvent e)
  {
    boolean consume = true;
    final ADTreeNode node = canvas.getFocused();
    if(e.isControlDown()) {
      if(node!=null){
        switch(e.getKeyCode()) {
          case KeyEvent.VK_J:
            canvas.changeOp(node);
            break;
          case KeyEvent.VK_I:
            canvas.addCounter(node);
            break;
          case KeyEvent.VK_N:
            canvas.addChild(node);
            break;
          case KeyEvent.VK_L:
            menuNode = node;
            changeLabelActionPerformed();
            break;
          case KeyEvent.VK_R:
            canvas.removeTree(node);
            break;
          case KeyEvent.VK_S:
            canvas.addSibling(node, !e.isShiftDown());
            break;
          default:
            consume = false;
        }
      }
      else {
        consume = false;
      }
    }
    else
      if (e.isShiftDown()) {
        if (node != null) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
              if (node != null) {
                menuNode = node;
                canvas.toggleAboveFold(menuNode);
              }
              break;
            case KeyEvent.VK_R:
              if (node != null) {
                menuNode = node;
                canvas.removeChildren(node);
              }
              break;
            default:
              consume = false;
              break;
          }
        }
        else {
          consume = false;
        }
      }
      else {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_ENTER:
            if (node != null) {
              menuNode = node;
              changeLabelActionPerformed();
              canvas.setFocus(menuNode);
            }
            break;
          case KeyEvent.VK_SPACE:
            if (node != null) {
              menuNode = node;
              canvas.toggleFold(menuNode);
            }
            break;
          default:
            consume = false;
        }
      }
    if (!consume) {
      super.keyPressed(e);
    }
  }

  /**
   * {@inheritDoc}
   * @see MouseListener#mouseClicked(MouseEvent)
   */
  public final void mouseClicked(final MouseEvent e)
  {
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
          changeLabelActionPerformed();
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
   * @param node node to which we set focus.
   */
  public void setFocus(final ADTreeNode node)
  {
    if (node != null){
      final ADTreeNode parent = canvas.getParentNode(node);
      boolean canAddSibling;
      if (parent == null){
        canAddSibling = false;
      }
      else {
        canAddSibling = parent.getType() == node.getType();
      }
      boolean canFold;
      boolean canFoldAbove;
      if(node.isFolded()){
        toggleFold.setText("Expand Below");
        canFold = true;
      }
      else{
        toggleFold.setText("Fold Below");
        canFold = canvas.getTree().getChildrenList(node,false).size()>0;
      }
      if(node.isAboveFolded()){
        toggleAboveFold.setText("Expand Above");
        canFoldAbove = true;
      }
      else{
        toggleAboveFold.setText("Fold Above");
        canFoldAbove = canvas.getTree().getParent(node,false)!=null;
      }
      toggleAboveFold.setVisible(canFoldAbove);
      toggleFold.setVisible(canFold);
      addCounter.setVisible(!node.isCountered());
      addLeft.setVisible(canAddSibling);
      addRight.setVisible(canAddSibling);
      remove.setVisible(parent!=null);
      removeChild.setVisible(canvas.getMiddleChild(node)!=null);
        //removeNode.setVisible(parent!=null);
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
    this.pmenu = new JPopupMenu();
    menuNode = null;
    JMenuItem menuItem = new JMenuItem("Change Label");
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
        InputEvent.CTRL_MASK));
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        changeLabelActionPerformed();
      }
    });
    pmenu.add(menuItem);
    menuItem = new JMenuItem("Change Operator");
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
        InputEvent.CTRL_MASK));
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.changeOp(menuNode);
        }
      }
    });
    pmenu.add(menuItem);
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


    pmenu.addSeparator();

    menuItem = new JMenuItem("Add Child");
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
        InputEvent.CTRL_MASK));
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.addChild(menuNode);
        }
      }
    });
    pmenu.add(menuItem);

    addCounter = new JMenuItem("Add Countermeasure");
    addCounter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
        InputEvent.CTRL_MASK));
    addCounter.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.addCounter(menuNode);
        }
      }
    });
    pmenu.add(addCounter);

    addLeft = new JMenuItem("Add Left Sibling");
    addLeft.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        InputEvent.CTRL_MASK));
    addLeft.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.addSibling(menuNode, true);
        }
      }
    });
    pmenu.add(addLeft);

    addRight = new JMenuItem("Add Right Sibling");
    addRight.setAccelerator(KeyStroke.getKeyStroke("shift control S"));
    addRight.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.addSibling(menuNode, false);
        }
      }
    });
    pmenu.add(addRight);
    pmenu.addSeparator();

    //removeNode = new JMenuItem("Remove Node");
    //removeNode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
        //0));
    //removeNode.addActionListener(new ActionListener()
    //{
      //public void actionPerformed(final ActionEvent evt)
      //{
        //if (menuNode != null) {
          //canvas.removeNode(menuNode);
        //}
      //}
    //});
    //pmenu.add(removeNode);
    remove = new JMenuItem("Remove Subtree");
    remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
        InputEvent.CTRL_MASK));
    remove.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.removeTree(menuNode);
        }
      }
    });
    pmenu.add(remove);
    removeChild = new JMenuItem("Remove Children");
    removeChild.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
        InputEvent.SHIFT_MASK));
    removeChild.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
          canvas.removeChildren(menuNode);
        }
      }
    });
    pmenu.add(removeChild);
    //pmenu.addSeparator();

    // menuItem = new JMenuItem("Change Basic Assignment");
    // menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,
    // InputEvent.CTRL_MASK));
    // menuItem.addActionListener(new ActionListener() {
    // public void actionPerformed(ActionEvent evt) {
    // changeBAActionPerformed(evt);
    // }
    // });
    // pmenu.add(menuItem);
    // pmenu.addSeparator();

    // menuItem = new JMenuItem("Collapse/Expand Node");
    // menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
    // InputEvent.CTRL_MASK));
    // menuItem.addActionListener(new ActionListener() {
    // public void actionPerformed(ActionEvent evt) {
    // collapseActionPerformed();
    // }
    // });
    // pmenu.add(menuItem);
    // pmenu.addSeparator();

    // menuItem = new JMenuItem("Properties");
    // //menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
    // InputEvent.CTRL_MASK));
    // menuItem.addActionListener(new ActionListener() {
    // public void actionPerformed(ActionEvent evt) {
    // propertiesActionPerformed(evt);
    // }
    // });
    // pmenu.add(menuItem);
  }
  /**
   * Checks if a string is a valid label
   *
   * @param s
   * @return
   */
  private boolean validLabel(String s)
  {
    return canvas.validLabel(s);
  }

  /**
   * Displays a dialog to change the label of the node
   *
   */
  private void changeLabelActionPerformed()
  {
    if (menuNode != null) {
        String s = (String) MultilineInput.showInputDialog("Enter new label:","Change Label Dialog",menuNode.getLabel());
      if (s == null) {
        return;
      }
      // If a string was returned, say so.
      while (!validLabel(s.replaceAll("^ +| +$| +\n|\n +|(\n)[ \t]*\n|( )+","$1"))){
          s = (String) MultilineInput.showInputDialog("<html><body><font color=\"red\">Invalid Label Format.</font> Enter new label:</body></html>","Change Label Dialog",s.trim());
        if (s==null) {
          return;
        }
      }
      s=s.replaceAll("(?m)^ +| +$|^[ \t]*\r?\n|( )+","$1");//replaceAll("(?m)^[ \t]*\r?\n", "").replaceAll("^ +| +$| +\n|( )+","$1");
      menuNode.setLabel(s);
      canvas.setLabel(menuNode,s);
      canvas.setFocus(menuNode);
    }
  }
}
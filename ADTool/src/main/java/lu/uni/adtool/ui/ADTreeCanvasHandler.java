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

import lu.uni.adtool.Main;
import lu.uni.adtool.adtree.ADTreeNode;

/**
 * A mouse handler for ADTreeCanvas.
 *
 * @author Piot Kordy
 */
public class ADTreeCanvasHandler extends AbstractCanvasHandler
{
  private JPopupMenu	pmenu;
  private JMenuItem    	toggleAboveFold;
  private JMenuItem    	toggleFold;
  private JMenuItem    	addCounter;
  private JMenuItem    	addLeft;
  private JMenuItem    	addRight;
  private JMenuItem    	remove;
  private JMenuItem    	removeChild;
  private JMenuItem		cut;
  private JMenuItem		copy;
  private JMenuItem 	paste;
  private ADTreeNode   	menuNode;
  
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
	if (Main.viewmodeIsOn)
	{
	  return;
	}
    boolean consume = true;
    final ADTreeNode node = canvas.getFocused();
    if(e.isControlDown()) {
      if(node!=null){
        switch(e.getKeyCode()) {
          case KeyEvent.VK_J:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  canvas.changeOp(node);
        	  break;
          case KeyEvent.VK_I:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  canvas.addCounter(node);
        	  break;
          case KeyEvent.VK_N:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  canvas.addChild(node);
        	  break;
          case KeyEvent.VK_L:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  menuNode = node;
        	  changeLabelActionPerformed();
        	  break;
          case KeyEvent.VK_R:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  canvas.removeTree(node);
        	  break;
          case KeyEvent.VK_S:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  canvas.addSibling(node, !e.isShiftDown());
        	  break;
          case KeyEvent.VK_X:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  super.transferHandler.copy(canvas.getFocused());
        	  this.canvas.removeTree(canvas.getFocused());
        	  System.out.println("Cut: "+canvas.getFocused().getLabel());
        	  break;
          case KeyEvent.VK_C:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  super.transferHandler.copy(canvas.getFocused());
        	  System.out.println("Copied: "+canvas.getFocused().getLabel());
        	  break;
          case KeyEvent.VK_V:
        	  undoExecutor.saveTreeState(canvas.getRoot(true));
        	  super.transferHandler.paste(canvas);
        	  break;
          case KeyEvent.VK_Z:
        	  super.undoExecutor.undo(canvas);
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
      paste.setVisible(transferHandler.hasClonedNode());
      remove.setVisible(parent!=null);
      removeChild.setVisible(canvas.getMiddleChild(node)!=null);
        //removeNode.setVisible(parent!=null);
      pmenu.pack();
    }
    super.setFocus(node);
  }
  /**
   * Initialize context menu.
   *
   */
  private void initPopupMenu()
  {
    this.pmenu = new JPopupMenu();
    menuNode = null;
    
    // Change Label
    JMenuItem menuItem = new JMenuItem("Change Label");
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
        InputEvent.CTRL_MASK));
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
    	  undoExecutor.saveTreeState(canvas.getRoot(true));
    	  changeLabelActionPerformed();
      }
    });
    pmenu.add(menuItem);
    
    // Change Operator 
    menuItem = new JMenuItem("Change Operator");
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
        InputEvent.CTRL_MASK));
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
    	undoExecutor.saveTreeState(canvas.getRoot(true));
        if (menuNode != null) {
          canvas.changeOp(menuNode);
        }
      }
    });
    pmenu.add(menuItem);
    
    // Fold Above
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
    
    // Fold Below
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

    // separator
    pmenu.addSeparator();

    // Add Child
    menuItem = new JMenuItem("Add Child");
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
        InputEvent.CTRL_MASK));
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
        	undoExecutor.saveTreeState(canvas.getRoot(true));
        	canvas.addChild(menuNode);
        }
      }
    });
    pmenu.add(menuItem);

    // Add Countermeasure
    addCounter = new JMenuItem("Add Countermeasure");
    addCounter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
        InputEvent.CTRL_MASK));
    addCounter.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
        	undoExecutor.saveTreeState(canvas.getRoot(true));
        	canvas.addCounter(menuNode);
        }
      }
    });
    pmenu.add(addCounter);
    
    // Add Left Sibling
    addLeft = new JMenuItem("Add Left Sibling");
    addLeft.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        InputEvent.CTRL_MASK));
    addLeft.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
        	undoExecutor.saveTreeState(canvas.getRoot(true));
        	canvas.addSibling(menuNode, true);
        }
      }
    });
    pmenu.add(addLeft);

    // Add Right Sibling
    addRight = new JMenuItem("Add Right Sibling");
    addRight.setAccelerator(KeyStroke.getKeyStroke("shift control S"));
    addRight.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
        	undoExecutor.saveTreeState(canvas.getRoot(true));
        	canvas.addSibling(menuNode, false);
        }
      }
    });
    pmenu.add(addRight);
    
    // Separator
    pmenu.addSeparator();

    // Remove Subtree
    remove = new JMenuItem("Remove Subtree");
    remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
        InputEvent.CTRL_MASK));
    remove.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
        	undoExecutor.saveTreeState(canvas.getRoot(true));
        	canvas.removeTree(menuNode);
        }
      }
    });
    pmenu.add(remove);
    
    // Remove Children
    removeChild = new JMenuItem("Remove Children");
    removeChild.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
        InputEvent.SHIFT_MASK));
    removeChild.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent evt)
      {
        if (menuNode != null) {
        	undoExecutor.saveTreeState(canvas.getRoot(true));
        	canvas.removeChildren(menuNode);
        }
      }
    });
    pmenu.add(removeChild);
    
    // Separator
    pmenu.addSeparator();
    
    // Cut Subtree
    cut = new JMenuItem("Cut Subtree");
    cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
    cut.addActionListener(new ActionListener()
    {
    	public void actionPerformed(final ActionEvent evt)
    	{
    		if (menuNode != null)
    		{
    			undoExecutor.saveTreeState(canvas.getRoot(true));
    			transferHandler.copy(menuNode);
    			canvas.removeTree(menuNode);
    			System.out.println("Cut : "+menuNode.getLabel());
    		}
    	}
    });
    pmenu.add(cut);
    
    // Copy Subtree
    copy = new JMenuItem("Copy Subtree");
    copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
    copy.addActionListener(new ActionListener()
    {
    	public void actionPerformed(final ActionEvent evt)
    	{
    		if (menuNode != null)
    		{
    			transferHandler.copy(menuNode);
    			System.out.println("Copied : "+menuNode.getLabel());
    		}
    	}
    });
    pmenu.add(copy);
    
    // Paste as Child
    paste = new JMenuItem("Paste Subtree as Child");
    paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
    paste.addActionListener(new ActionListener()
    {
    	public void actionPerformed(final ActionEvent evt)
    	{
    		if (menuNode != null)
    		{
    			undoExecutor.saveTreeState(canvas.getRoot(true));
    			transferHandler.paste(canvas);
    		}
    	}
    });
    pmenu.add(paste);
    paste.setVisible(false);
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

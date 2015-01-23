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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lu.uni.adtool.domains.Domain;

/**
 * Dialog for adding new attribute domain.
 *
 * @author Piot Kordy
 */
@SuppressWarnings("rawtypes")
public class AddDomainDialog extends JDialog  implements ActionListener, ListSelectionListener, Comparator<Domain>
{
  private static final long serialVersionUID = 1848945612200055156L;
  private MainWindow frame;
  private JList list;
  private JButton setButton;
  private JLabel description;
  private Domain<?> chosenDomain;
  private boolean selectPressed;
  private Vector<Domain<?>> ds;
  /**
   * Constructs a new instance.
   * @param newParent parent.
   */
  public AddDomainDialog(final MainWindow newParent)
  {
    super(newParent,"Add Attribute Domain",true);
    // Create and initialize the buttons.
    chosenDomain = null;
    selectPressed = false;
    frame = newParent;
    setAlwaysOnTop(true);
    this.setLocationRelativeTo(frame);
    setLocation(60,60);
    //setSize(1900, 600);
  }

  /**
   * Show dialog where user chooses a predefined domain.
   *
   * @return chosen Domain or null if cancel button was pressed.
   */
  public Domain showDomainDialog(Vector<Domain<?>> domains)
  {
    ds = domains;
    Collections.sort(ds,this);
    createLayout();
    this.setVisible(true);
    if(!selectPressed){
      setValue(-1);
    }
    return chosenDomain;
  }
  /**
   * {@inheritDoc}
   * @see Comparator#compare(Domain,Domain)
   */
  public int compare(Domain d1,  Domain d2)
  {
    return d1.getName().compareTo(d2.getName());
  }
  /**
   * {@inheritDoc}
   * @see ListSelectionListener#valueChanged(ListSelectionEvent)
   */
  public void valueChanged(ListSelectionEvent e)
  {
    if (e.getValueIsAdjusting() == false) {

      if (list.getSelectedIndex() == -1) {
        // No selection, disable fire button.
        setButton.setEnabled(false);
      }
      else {
        // Selection, enable the fire button.
        setValue(list.getSelectedIndex());
        setButton.setEnabled(true);
      }
    }
  }

  /**
   * {@inheritDoc}
   * @see ActionListener#actionPerformed(ActionEvent)
   */
  public void actionPerformed(ActionEvent e)
  {
    if ("Add".equals(e.getActionCommand())) {
      selectPressed = true;
    }
    dispose();
  }
//   private Domain getIndexToDomain(int i)
//   {
//     if (i ==-1) {
//       return null;
//     }
//     else {
//       return ds.elementAt(i);
//     }
//   }
  private void createLayout()
  {
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(this);
    setButton = new JButton("Add");
    setButton.setActionCommand("Add");
    setButton.addActionListener(this);
    getRootPane().setDefaultButton(setButton);
    list = new JList(getDomainsAsArray(ds))
    {
      /**
       *
       */
      private static final long serialVersionUID = 3976580603641311787L;

      // Subclass JList to workaround bug 4832765, which can cause the
      // scroll pane to not let the user easily scroll up to the beginning
      // of the list. An alternative would be to set the unitIncrement
      // of the JScrollBar to a fixed value. You wouldn't get the nice
      // aligned scrolling, but it should work.
      public int getScrollableUnitIncrement(Rectangle visibleRect,
          int orientation, int direction)
      {
        int row;
        if (orientation == SwingConstants.VERTICAL && direction < 0
            && (row = getFirstVisibleIndex()) != -1) {
          Rectangle r = getCellBounds(row, row);
          if ((r.y == visibleRect.y) && (row != 0)) {
            Point loc = r.getLocation();
            loc.y--;
            int prevIndex = locationToIndex(loc);
            Rectangle prevR = getCellBounds(prevIndex, prevIndex);

            if (prevR == null || prevR.y >= r.y) {
              return 0;
            }
            return prevR.height;
          }
        }
        return super.getScrollableUnitIncrement(visibleRect, orientation,
            direction);
      }
    };
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setVisibleRowCount(15);
    list.setSelectedIndex(0);
    list.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() == 2) {
          setButton.doClick(); // emulate button click
        }
      }
    });
    list.addListSelectionListener(this);
    JScrollPane listScroller = new JScrollPane(list);
    listScroller.setPreferredSize(new Dimension(500, 80));
    listScroller.setAlignmentX(LEFT_ALIGNMENT);
    // Create a container so that we can add a title around
    // the scroll pane. Can't add a title directly to the
    // scroll pane because its background would be white.
    // Lay out the label and scroll pane from top to bottom.
    JPanel listPane = new JPanel();
    listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
    JLabel label = new JLabel("Domain Name:");
    label.setLabelFor(list);
    listPane.add(label);
    listPane.add(Box.createRigidArea(new Dimension(0, 5)));
    listPane.add(listScroller);
    listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    description = new JLabel("Not initialised!")
    {
      /**
       *
       */
      private static final long serialVersionUID = -1965044192759316872L;

      public Dimension getPreferredSize()
      {
        return new Dimension(450, 350);
      }

      public Dimension getMinimumSize()
      {
        return new Dimension(450, 350);
      }

      public Dimension getMaximumSize()
      {
        return new Dimension(450, 350);
      }
    };
    description.setVerticalAlignment(SwingConstants.TOP);
    description.setFont(new Font("Sans", Font.TRUETYPE_FONT, 13));
    description.setHorizontalAlignment(SwingConstants.LEFT);

    JScrollPane descPane = new JScrollPane(description);
    descPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createTitledBorder("Details:")));

    // Lay out the buttons from left to right.
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    buttonPane.add(Box.createHorizontalGlue());
    buttonPane.add(cancelButton);
    buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
    buttonPane.add(setButton);
 //Put everything together, using the content pane's BorderLayout.
    Container contentPane = getContentPane();
    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.LINE_AXIS));
    //GridBagConstraints c = new GridBagConstraints();
    ////content.setBackground(Options.printview_background);
    //c.insets = new Insets(0,8,0,0);
    //c.gridx = 0;
    //c.gridy = 0;
    content.add(listPane);
    //c.gridx = 1;
    content.add(descPane);
    contentPane.add(content, BorderLayout.CENTER);
    contentPane.add(buttonPane, BorderLayout.PAGE_END);
    // Initialize values.
    setValue(0);

    pack();
  }
  private void setValue(int i)
  {
    if(0<=i && i<ds.size()){
      chosenDomain = ds.elementAt(i);
      list.setSelectedValue(chosenDomain,true);
      description.setText(chosenDomain.getDescription().replaceAll("2147483647","k"));
    }
    else{
      chosenDomain = null;
      description.setText("Nothing is chosen.");
    }
  }
  private String[] getDomainsAsArray(Vector<Domain<?>> ds2){
    String[] result = new String[ds2.size()];
    for (int i =0; i<ds2.size();i++){
      result[i] = ds2.elementAt(i).getName();
    }
    return result;
  }

  /**
   * {@inheritDoc}
   * @see JDialog#createRootPane()
   */
  protected JRootPane createRootPane()
  {
    ActionListener actionListener = new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        setValue(-1);
        dispose();
      }
    };
    KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    JRootPane rootPane = new JRootPane();
    rootPane.registerKeyboardAction(actionListener, stroke,
        JComponent.WHEN_IN_FOCUSED_WINDOW);
    return rootPane;
  }
}


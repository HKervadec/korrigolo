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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Dialog with list of all examples.
 *
 * @author Piot Kordy
 */
public class LoadExampleDialog extends JDialog implements ActionListener, ListSelectionListener
{
  private static final long serialVersionUID = 1223325877545646416L;
  private MainWindow frame;
  private JButton setButton;
  private JList list;
  private JLabel description;
  private final String[] examplesList = {"Auction Fraud",
                                         "RFID Dos Attack",
                                         "RFID Communication Block",
                                         "Breaking into a Warehouse",
                                         "RFID Dos Attack in Warehouse",
                                         "Data Confidentiality",
                                         "Bank Account"};
  private final String[] examplesFileName = {"/examples/AuctionFraud.adt",
                                             "/examples/RFIDDos.adt",
                                             "/examples/RFIDBlock.adt",
                                             "/examples/BreakingWarehouse.adt",
                                             "/examples/RFIDWarehouse.adt",
                                             "/examples/DataConfidentiality.adt",
                                             "/examples/BankAccount.adt"};
  private final String[] examplesDesc = {"An online auction fraud scenario.",
                                         "Dos attack on an RFID system.",
                                         "How to block communication between RFID reader and tag?",
                                         "Breaking and entering into a warehouse.",
                                         "Dos attack on an RFID system in a warehouse (large example).",
                                         "How to protect data confidentiality?",
                                         "How to steal the money from a bank account?"};
  private String result;
  /**
   * Constructs a new instance.
   */
  public LoadExampleDialog(final MainWindow parentFrame)
  {
    super(parentFrame,"Choose example to load",true);
    frame = parentFrame;
    setAlwaysOnTop(true);
    this.setLocationRelativeTo(frame);
    setLocation(60,60);
  }
  /**
   * Show dialog where user chooses a predefined domain.
   * @return return file name from which to read the tree.
   *
   */
  public String showDialog()
  {
    createLayout();
    this.setVisible(true);
    return result;
    //return chosenDomain;
  }
  /**
   * {@inheritDoc}
   * @see ActionListener#actionPerformed(ActionEvent)
   */
  public void actionPerformed(ActionEvent e)
  {
    if ("Cancel".equals(e.getActionCommand())) {
      setValue(-1);
    }
    dispose();
  }
  private void createLayout()
  {
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(this);
    setButton = new JButton("Load");
    setButton.setActionCommand("Load");
    setButton.addActionListener(this);
    getRootPane().setDefaultButton(setButton);
    list = new JList(examplesList)
    {
      /**
       *
       */
      private static final long serialVersionUID = -5216109258424750213L;

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
    listScroller.setPreferredSize(new Dimension(350, 80));
    listScroller.setAlignmentX(LEFT_ALIGNMENT);
    // Create a container so that we can add a title around
    // the scroll pane. Can't add a title directly to the
    // scroll pane because its background would be white.
    // Lay out the label and scroll pane from top to bottom.
    JPanel listPane = new JPanel();
    listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
    JLabel label = new JLabel("Attack Defense Trees Examples:");
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
      private static final long serialVersionUID = -6683205667737229246L;

      public Dimension getPreferredSize()
      {
        return new Dimension(400, 300);
      }

      public Dimension getMinimumSize()
      {
        return new Dimension(400, 300);
      }

      public Dimension getMaximumSize()
      {
        return new Dimension(400, 300);
      }
    };
    description.setVerticalAlignment(SwingConstants.TOP);
    description.setFont(new Font("Sans", Font.TRUETYPE_FONT, 13));
    description.setHorizontalAlignment(SwingConstants.LEFT);

    JScrollPane descPane = new JScrollPane(description);
    descPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createTitledBorder("Description:")));

    // Lay out the buttons from left to right.
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    buttonPane.add(Box.createHorizontalGlue());
    buttonPane.add(new JLabel("<html><b>Warning! Current tree will be discarded!</b></html>"));
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

  private void setValue(int i)
  {
    if(0<=i && i<examplesFileName.length){
      result = examplesFileName[i];
      list.setSelectedValue(examplesList[i],true);
      description.setText("<html>"+examplesDesc[i]+"<br></html>");
    }
    else{
      result = null;
      description.setText("No example is chosen.");
    }
  }
}



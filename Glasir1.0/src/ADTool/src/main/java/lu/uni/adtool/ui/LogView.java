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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import lu.uni.adtool.Options;

/**
 * Class showing the window with the log of messages.
 *
 * @author Piot Kordy
 */
public class LogView extends JPanel
{
  /**
   *
   */
  private static final long  serialVersionUID = -1627291937521951134L;
  private LinkedList<String> messages;
  private JTextPane log;
  private JScrollPane scrollPane;
  /**
   * {@inheritDoc}
   * @see JPanel#LogView()
   */
  public LogView()
  {
    super(new BorderLayout());
    messages = new LinkedList<String>();
    messages.clear();
    initLayout();
  }

  /**
   * Initialize layout.
   *
   */
  private void initLayout()
  {
    log = new JTextPane();
    log.setEditable(false);
    log.setContentType("text/html");
    scrollPane =new JScrollPane(log);
    scrollPane.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Message Log:"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
    add(scrollPane);
    final JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
    JButton clearLog=new JButton("Clear Log");
    clearLog.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        clearLog();
      }
    });
    buttonPanel.add(clearLog);
    add(buttonPanel,BorderLayout.PAGE_END);
  }

  /**
   * Transfer text from private vector messages to the JTextPane log.
   *
   */
  private void updateText()
  {
    String text="<html>";
    for (String s:messages){
      text+=s+"<br>";
    }
    log.setText(text+"</html>");
//     ((JComponent) scrollPane.getParent()).revalidate();  
  }
  /**
   * Clear all messages
   *
   */
  private void clearLog()
  {
    messages.clear();
    updateText();
  }
  /**
   * Clear all messages
   *
   */
  private String getTimeStamp()
  {
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }
  public void addMessage(String s)
  {
    if (messages.size()>Options.log_noLinesSaved){
      messages.removeFirst();
    }
    messages.add("<font color='gray'>"+getTimeStamp()+" - </font>"+s);
    updateText();
  }
}

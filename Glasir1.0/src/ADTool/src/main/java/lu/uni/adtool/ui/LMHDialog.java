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
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;


import java.text.ChoiceFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import lu.uni.adtool.domains.rings.LMHValue;

/**
 * Dialog to edit values for the attribute domains of values from the set {L,M,H}
 *
 * @author Piot Kordy
 */
public class LMHDialog extends InputDialog
{
  static final long serialVersionUID = 66458869112819776L;
  /**
   * Constructs a new instance.
   * @param frame parent frame.
   */
  public LMHDialog(final Frame frame)
  {
    super(frame,"Enter values from the set {L, M, H}");
  }
  /**
   * {@inheritDoc}
   * @see ActionListener#actionPerformed(ActionEvent)
   */
  public final void actionPerformed(final ActionEvent e)
  {
    if ("L".equals(e.getActionCommand())) {
      setValue(1);
    }
    else if ("M".equals(e.getActionCommand())) {
      setValue(2);
    }
    else if ("H".equals(e.getActionCommand())) {
      setValue(3);
    }
    else if ("Infinity".equals(e.getActionCommand())) {
      setValue(LMHValue.INF);
    }
    else if ("\u221E".equals(e.getActionCommand())) {
      setValue(LMHValue.INF);
    }
    else{
      super.actionPerformed(e);
    }

  }
  /**
   * {@inheritDoc}
   * @see InputDialog#isValid(double)
   */
  protected final boolean isValid(final double d)
  {
    if(d >= 1 && d <= 3){
      LMHValue v = new LMHValue((int)d);
      if (v.getValue() == d){
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   * @see InputDialog#setValue(double)
   */
  protected final void setValue(final double d)
  {
      value = new LMHValue((int)d);
    valueField.setValue(d);
  }

  protected final void createLayout(boolean showDefault)
  {
    errorMsg.setText("You must enter value from the set {L, M, H}!");
    double[] choices = {1,2,3,4};
    String[] choiceNames = {"L","M","H","\u221E"};
    final ChoiceFormat f = new ChoiceFormat(choices,choiceNames);
    valueField = new JFormattedTextField(f);
    valueField.addKeyListener(this);
    if (showDefault){
      valueField.setValue(new Double(((LMHValue)value).getValue()));
    }
    valueField.setColumns(15);
    valueField.addPropertyChangeListener("value",this);
    final JPanel inputPane = new JPanel();
    inputPane.setLayout(new GridBagLayout());
    final GridBagConstraints c = new GridBagConstraints();
    JButton button;
    c.insets = new Insets(0,8,0,0);
    c.gridy=0;
    c.gridx=0;
    c.gridwidth = 3;
    inputPane.add(valueField,c);
    c.gridwidth = 1;
    c.gridy=1;
    button = new JButton("L");
    button.setActionCommand("L");
    button.addActionListener(this);
    inputPane.add(button,c);
    c.gridy=1;
    c.gridx=1;
    button = new JButton("M");
    button.setActionCommand("M");
    button.addActionListener(this);
    inputPane.add(button,c);
    c.gridx=2;
    button = new JButton("H");
    button.setActionCommand("H");
    button.addActionListener(this);
    inputPane.add(button,c);
    contentPane.add(inputPane, BorderLayout.CENTER);
    pack();
  }
}



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

import java.awt.Frame;
import javax.swing.JButton;

import lu.uni.adtool.domains.rings.BoundedInteger;

public class BoundedIntegerInfDialog extends BoundedIntegerDialog
{
  static final long serialVersionUID = 35393957497521214L;

  /**
   * {@inheritDoc}
   * @see BoundedIntegerDialog#BoundedIntegerInfDialog(Frame)
   */
  public BoundedIntegerInfDialog(final Frame frame)
  {
    super(frame,"Enter a non-negative integer number");
  }
  /**
   * {@inheritDoc}
   * @see BoundedIntegerDialog#BoundedIntegerInfDialog(Frame,String)
   */
  public BoundedIntegerInfDialog(final Frame frame,final String title)
  {
    super(frame,title);
  }
  /**
   * {@inheritDoc}
   * @see BoundedIntegerDialog#createLayout(boolean)
   */
  protected final void createLayout(final boolean showDefault)
  {
    super.infButton = new JButton("Infinity");
    super.createLayout(showDefault);
    errorMsg.setText(errorMsg.getText()+" Infinity value is also allowed.");
  }
  /**
   * {@inheritDoc}
   * @see InputDialog#isValid(double)
   */
  protected final boolean isValid(final double d)
  {
    boolean isValid = false;
    final int bound = ((BoundedInteger)value).getBound();
    if(new Double(d).isInfinite()){
      isValid = true;
    }
    else if(d >= 0 && d<=bound){
      isValid = true;
    }
    return isValid;
  }
}


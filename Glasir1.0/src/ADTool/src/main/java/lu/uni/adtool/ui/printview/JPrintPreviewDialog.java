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
package lu.uni.adtool.ui.printview;

import java.awt.BorderLayout;
import java.awt.Frame;

import java.awt.print.Pageable;

import javax.swing.JDialog;

public class JPrintPreviewDialog extends JDialog
{

  /**
   *
   */
  private static final long serialVersionUID = -8452285246945202151L;

  /**
   * Constructs a new instance.
   */
  public JPrintPreviewDialog(Frame frame,final Pageable pageable)
  {
    super(frame,true);
    JPrintPreviewPane preview = new JPrintPreviewPane(pageable,this);
    setAlwaysOnTop(true);
    setTitle("Print preview");
    this.setLocationRelativeTo(frame);

    getContentPane().add(preview,BorderLayout.CENTER);
    pack();
    setLocation(50,50);
    setSize(800, 600);
  }


}


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
package lu.uni.adtool;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lu.uni.adtool.ui.MainWindow;

/**
 * The Class Main of ADTool application.
 *
 * @author Piotr Kordy
 * @version 0.1
 */
public final class Main
{

  private static final int MIN_JAVA_NUM = 6;


  /**
   * Hidden private constructor
   */
  private Main()
  {
  }
  /** The main method.
   *
   * @param args the arguments
   */
  public static void main(final String[] args)
  {
    final String vendor = System.getProperty("java.vendor");
    final String version = System.getProperty("java.version");
    boolean correctversion = false;
    final String[] versnbr = version.split("\\.");
    if (versnbr.length > 2) {
      final int vers = Integer.valueOf(versnbr[1]).intValue();
      if (vers >= MIN_JAVA_NUM) {
        correctversion = true;
      }
    }
    if (!correctversion) {
      // JOptionPane.showMessageDialog(null,
      System.out.println("The system has: " + vendor + " " + version + "\n" + "You should at least have: 1.6.0_26 " +
          "from Sun Microsytems Inc.\n" + "Not all features will be supported.");
      // JOptionPane.WARNING_MESSAGE);
      // System.exit(1);
    }
    try {
      // Set System Default Look and Feel
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
      System.err.println("Could not set up a default system Look and " + "Feel:" + e.getLocalizedMessage());
    } catch (ClassNotFoundException e) {
      System.err.println("Could not set up a default system Look and " + "Feel:" + e.getLocalizedMessage());
    } catch (InstantiationException e) {
      System.err.println("Could not set up a default system Look and " + "Feel:" + e.getLocalizedMessage());
    } catch (IllegalAccessException e) {
      System.err.println("Could not set up a default system Look and " + "Feel:" + e.getLocalizedMessage());
    }

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        new MainWindow(args);
      }
    });
  }
}

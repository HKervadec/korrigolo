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
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

/**
 * Provides static methods for choosing file to open or save.
 *
 * @author Piot Kordy
 */
public class JWSFileHandler extends FileHandler
{
  /**
   * Constructs a new instance.
   */
  public JWSFileHandler(Frame window)
  {
    this(null, window);
  }

  /**
   * Constructs a new instance.
   *
   * @param status
   *          status line to which we report.
   * @param window parent window
   */
  public JWSFileHandler(final StatusLine status, final Frame window)
  {
    super(status, window);
  }

  public ObjectInputStream getLoadLayoutStream()
  {
    ObjectInputStream in = null;
    FileOpenService fos = null;
    FileContents fileContents = null;
    try {
      fos = (FileOpenService) ServiceManager
          .lookup("javax.jnlp.FileOpenService");
    }
    catch (UnavailableServiceException exc) {
      statusLine.reportError("Open command failed: " + exc.getLocalizedMessage());
    }

    if (fos != null) {
      try {
        fileContents = fos.openFileDialog(null, null);
      }
      catch (Exception exc) {
        statusLine.reportError("Open command failed: " + exc.getLocalizedMessage());
      }
    }

    if (fileContents != null) {
      try {
        // This is where a real application would do something
        // with the file.
        in = new ObjectInputStream(fileContents.getInputStream());
        statusLine.reportError("Opened file: " + fileContents.getName() + ".");
      }
      catch (IOException exc) {
        statusLine.reportError("Problem opening file: " + exc.getLocalizedMessage());
      }
    }
    else {
      statusLine.reportWarning("User canceled open request.");
    }
    return in;
  }

}


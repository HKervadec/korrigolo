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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import lu.uni.adtool.Options;

/**
 * Provides static methods for choosing file to open or save.
 *
 * @author Piot Kordy
 */
public class FileHandler
{
  protected StatusLine statusLine;
  protected String treeFileName;
  protected Frame mainWindow;
  protected JFileChooser fc;
  protected JCheckBox saveLayout;
  protected JCheckBox exportDomains;
  protected JCheckBox exportCalculatedValues;
  private String tempFileName;
  /**
   * Constructs a new instance.
   */
  public FileHandler(Frame window)
  {
    this(null,window);
  }

  /**
   * Constructs a new instance.
   * @param status status line to which we report.
   */
  public FileHandler(final StatusLine status,final Frame window)
  {
    treeFileName = null;
    tempFileName = "";
    tempFileName = "";
    statusLine = status;
    mainWindow = window;
    initFileChooser();
  }
  /** Shows save dialog.
   *
   * @return stream to wchich we can write or null.
   */
  public ObjectOutputStream getSaveTreeStream()
  {
    fc.setAcceptAllFileFilterUsed(true);
    FileFilter filter = new FileNameExtensionFilter("ADTree file", "adt", "ADT", "Adt");
    saveLayout.setVisible(true);
    exportDomains.setVisible(false);
    exportCalculatedValues.setVisible(false);
    fc.setSelectedFile(new File(getTreeFileNameWithExt("adt")));
    fc.setDialogTitle("Save Attack Defence Tree...");
    ObjectOutputStream result = getSaveStream(filter);
    if (result != null){
      setTreeFileName(tempFileName);
    }
    return result;
  }
  /**
   * Shows export dialog.
   *
   * @return stream to which we can write or null.
   */
  public FileOutputStream getExportTreeStream(String extension)
  {
    FileFilter filter = null;
    exportDomains.setVisible(false);
    exportCalculatedValues.setVisible(false);
    saveLayout.setVisible(false);
    if (extension.equals("pdf")){
      filter = new FileNameExtensionFilter("Pdf file", "pdf", "PDF", "Pdf");
    }
    else if (extension.equals("xml")){
      filter = new FileNameExtensionFilter("XML file", "xml", "XML", "Xml");
      if(((MainWindow)mainWindow).getValuations().size()>0){
        exportDomains.setVisible(true);
        exportCalculatedValues.setVisible(true);
      }
    }
    else if (extension.equals("png")){
      filter = new FileNameExtensionFilter("PNG image", "png", "PNG", "Png");
    }
    else if (extension.equals("jpg")){
      filter = new FileNameExtensionFilter("JPEG image", "jpg", "JPG", "Jpg","jpeg", "JPEG", "Jpeg");
    }
    else if (extension.equals("tex")){
      filter = new FileNameExtensionFilter("LaTeX file", "tex", "TEX", "Tex", "latex", "LATEX", "LaTeX", "Latex");
    }
    fc.setDialogTitle("Export tree...");
    FileOutputStream out = null;
    fc.setSelectedFile(new File(getTreeFileNameWithExt(extension)));
    fc.resetChoosableFileFilters();
    fc.setAcceptAllFileFilterUsed(false);
    fc.setFileFilter(filter);
    int returnVal = fc.showSaveDialog(this.mainWindow);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      try {
        out =new FileOutputStream(file);
        tempFileName = file.getName();
        setTreeFileName(tempFileName);
        statusLine.report("Exported tree to: " + file.getName() + ".");
      }
      catch (FileNotFoundException e) {
        statusLine.reportError("File not found:\""+file.getName()+"\".");
      }
//       catch (IOException e) {
//         statusLine.reportError("There was IO problem opening a file:\""+file.getName()+"\".");
//       }
    }
    else {
      statusLine.reportWarning("Export command cancelled by the user.");
    }
    return out;
  }
  /**
   * Shows import dialog.
   *
   * @return stream from which we can read or null.
   */
  public FileInputStream getImportTreeStream(String extension)
  {
    FileInputStream in = null;
    FileFilter filter = null;
    if (extension.equals("xml")){
      filter = new FileNameExtensionFilter("Xml file", "xml", "XML", "Xml");
    }
    exportDomains.setVisible(false);
    exportCalculatedValues.setVisible(false);
    saveLayout.setVisible(false);
    fc.setDialogTitle("Import tree...");
    fc.setSelectedFile(new File(getTreeFileNameWithExt(extension)));
    fc.resetChoosableFileFilters();
    fc.setFileFilter(filter);
    int returnVal = fc.showOpenDialog(this.mainWindow);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      tempFileName = file.getName();
      setTreeFileName(tempFileName);
      // This is where a real application would open the file.
      try {
        in = new FileInputStream(file);
        statusLine.report("Imported tree from: " + file.getName() + ".");
      }
      catch (FileNotFoundException e) {
        statusLine.reportError(e.getLocalizedMessage());
      }
//       catch (IOException e) {
//         statusLine.reportError("There was IO problem opening a file:\""+file.getName()+"\"");
//       }
    }
    else {
      statusLine.reportWarning("Open command cancelled by the user.");
    }
    return in;
  }

  /**
   * Shows save dialog.
   *
   * @return stream to which we can write or null.
   */
  public ObjectOutputStream getSaveLayoutStream()
  {
    fc.setAcceptAllFileFilterUsed(true);
    FileFilter filter = new FileNameExtensionFilter("Layout file", "adl", "ADL", "Adl");
    exportDomains.setVisible(false);
    exportCalculatedValues.setVisible(false);
    saveLayout.setVisible(false);
    fc.setSelectedFile(new File(getTreeFileNameWithExt("adl")));
    fc.setDialogTitle("Save Layout...");
    return getSaveStream(filter);
  }
  /**
   * Shows save dialog.
   *
   * @return stream to wchich we can write or null.
   */
  public ObjectOutputStream getSaveStream(FileFilter filter)
  {
    ObjectOutputStream out = null;
    fc.resetChoosableFileFilters();
    fc.setFileFilter(filter);
    int returnVal = fc.showSaveDialog(this.mainWindow);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      // This is where a real application would open the file.
      try {
        out = new ObjectOutputStream(new FileOutputStream(file));
        tempFileName = file.getName();
        statusLine.report("Saved to: " + file.getName() + ".");
      }
      catch (FileNotFoundException e) {
        statusLine.reportError("File not found:\""+file.getName()+"\".");
      }
      catch (IOException e) {
        statusLine.reportError("There was IO problem opening a file:\""+file.getName()+"\".");
      }
    }
    else {
      statusLine.reportWarning("Save command cancelled by the user.");
    }
    return out;
  }
  /**
   * Shows open file dialog.
   *
   * @return Stream from which we can read or null if no file was open.
   */
  public ObjectInputStream getLoadTreeStream()
  {
    FileFilter filter = new FileNameExtensionFilter("Tree file", "adt", "ADT", "Adt");
    exportDomains.setVisible(false);
    exportCalculatedValues.setVisible(false);
    saveLayout.setVisible(false);
    fc.setSelectedFile(new File(getTreeFileNameWithExt("adt")));
    fc.setDialogTitle("Load Atack Defence Tree...");
    ObjectInputStream result = getLoadStream(filter);
    if (result != null){
      setTreeFileName(tempFileName);
    }
    return result;
  }
  /**
   * Shows open file dialog.
   *
   * @return Stream from which we can read or null if no file was open.
   */
  public ObjectInputStream getLoadLayoutStream()
  {
    FileFilter filter = new FileNameExtensionFilter("Layout file", "adl", "ADL", "Adl");
    fc.setDialogTitle("Load Layout...");
    fc.setSelectedFile(new File(getTreeFileNameWithExt("adl")));
    exportDomains.setVisible(false);
    exportCalculatedValues.setVisible(false);
    saveLayout.setVisible(false);
    return getLoadStream(filter);
  }
  /**
   * Shows open file dialog.
   *
   * @return Stream from which we can read or null if no file was open.
   */
  private ObjectInputStream getLoadStream(FileFilter filter)
  {
    ObjectInputStream in = null;
    fc.resetChoosableFileFilters();
    fc.setFileFilter(filter);
    int returnVal = fc.showOpenDialog(this.mainWindow);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      tempFileName = file.getName();
      // This is where a real application would open the file.
      try {
        in = new ObjectInputStream(new FileInputStream(file));
        statusLine.report("Loaded tree from file: " + file.getName() + ".");
      }
      catch (FileNotFoundException e) {
        statusLine.reportError("File not found.");
      }
      catch (IOException e) {
        statusLine.reportError("There was IO problem opening a file:\""+file.getName()+"\"");
      }
    }
    else {
      statusLine.reportWarning("Open command cancelled by the user.");
    }
    return in;
  }
  /**
   * Initializes fileChooser
   *
   * @return new checkbox
   */
  private void initFileChooser()
  {
    JPanel jp = new JPanel();
    fc = new JFileChooser()
    {
      /**
       *
       */
      private static final long serialVersionUID = 7266914590804770955L;

      public void approveSelection()
      {
        File f = getSelectedFile();
        if (f.exists() && getDialogType() == SAVE_DIALOG) {
          int result = JOptionPane.showConfirmDialog(this,
              "The file exists, overwrite?", "Existing file",
              JOptionPane.YES_NO_CANCEL_OPTION);
          switch (result) {
            case JOptionPane.YES_OPTION:
              super.approveSelection();
              return;
            case JOptionPane.NO_OPTION:
              return;
            case JOptionPane.CLOSED_OPTION:
              return;
            case JOptionPane.CANCEL_OPTION:
              cancelSelection();
              return;
          }
        }
        super.approveSelection();
      };
    };

    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
// Add "show hidden files" CheckBoxMenuItem
    JCheckBox showHiddenCheckBox = new JCheckBox();
    exportDomains = new JCheckBox();
    exportDomains.setText("Include Domains");
    exportDomains.setSelected(Options.main_saveDomains);
    exportDomains.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JCheckBox source = (JCheckBox) e.getSource();
        Options.main_saveDomains = source.isSelected();
        if(!Options.main_saveDomains){
          Options.main_saveDerivedValues=false;
          exportCalculatedValues.setSelected(Options.main_saveDerivedValues);
        }
        exportCalculatedValues.setEnabled(Options.main_saveDomains);
      }
    });
    exportCalculatedValues = new JCheckBox();
    exportCalculatedValues.setText("Add derived values");
    exportCalculatedValues.setSelected(true);
    exportCalculatedValues.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JCheckBox source = (JCheckBox) e.getSource();
        Options.main_saveDerivedValues = source.isSelected();
      }
    });
    saveLayout = new JCheckBox();
    saveLayout.setText("Include Layout");
    saveLayout.setSelected(Options.main_saveLayout);
    saveLayout.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JCheckBox source = (JCheckBox) e.getSource();
        Options.main_saveLayout = source.isSelected();
      }
    });

    showHiddenCheckBox.setText("Show hidden files");
    showHiddenCheckBox.setMnemonic(KeyEvent.VK_H);
    showHiddenCheckBox.setSelected(!fc.isFileHidingEnabled());
    showHiddenCheckBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JCheckBox source = (JCheckBox) e.getSource();
        boolean showHidden = source.isSelected();
        //fc.firePropertyChange(JFileChooser.FILE_HIDING_CHANGED_PROPERTY, showHidden, !showHidden);
        fc.setFileHidingEnabled(!showHidden);
      }
    });
    //fc.setFileHidingEnabled(false);
    jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
    saveLayout.setVisible(true);
    jp.add(saveLayout);
    jp.add(showHiddenCheckBox);
    jp.add(exportDomains);
    jp.add(exportCalculatedValues);
    fc.setAccessory(jp);
  }

  /**
   * Gets the treeFileName for this instance.
   *
   * @return The treeFileName.
   */
  public String getTreeFileName()
  {
    return this.treeFileName;
  }
  /**
   * Assigns new value to the proposed file name of the tree
   * @param s new value of the name
   */
  public void setTreeFileName(String s){
    treeFileName=s;
  }
  private String getTreeFileNameWithExt(String ext){
    String s = getTreeFileNameForStream();
    if (s.contains(".")){
      return s.substring(0, s.lastIndexOf('.'))+'.'+ext;
    }
    else{
      return s+'.'+ext;
    }
  }

  /**
   * Get the name of the file that is used in load/save/export dialogs.
   * If treeFileName is null then return the name of the root
   * @return name used in load/save dialogs
   */
  private String getTreeFileNameForStream(){
    if (treeFileName!=null) {
      return treeFileName;
    }
    else{
      String s= ((ADTreeView)((MainWindow)mainWindow).getViews(0).getComponent()).getTree().getRoot(true).getLabel();
//       s=s.replace(' ','_')+".adt";
      s=s.replace(' ','_');
//       return s.replace('\n','_')+".adt";
      return s.replace('\n','_');
    }
  }
}

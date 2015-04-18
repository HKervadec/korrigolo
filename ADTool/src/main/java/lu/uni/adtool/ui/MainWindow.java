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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;

import lu.uni.adtool.Main;
import lu.uni.adtool.Options;
import lu.uni.adtool.adtree.ADTSerializer;
import lu.uni.adtool.adtree.ADTXmlImport;
import lu.uni.adtool.adtree.ADTreeForGui;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.domains.Domain;
import lu.uni.adtool.domains.DomainFactory;
import lu.uni.adtool.domains.ValuationDomain;
import lu.uni.adtool.domains.predefined.MinSkill;
import lu.uni.adtool.domains.predefined.Parametrized;
import lu.uni.adtool.domains.rings.BoundedInteger;
import lu.uni.adtool.domains.rings.Ring;
import lu.uni.adtool.ui.printview.JPrintPreviewDialog;
import lu.uni.adtool.ui.texts.ButtonTexts;
import lu.uni.adtool.ui.texts.ToolTipTexts;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowAdapter;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.BlueHighlightDockingTheme;
import net.infonode.docking.theme.ClassicDockingTheme;
import net.infonode.docking.theme.DefaultDockingTheme;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.GradientDockingTheme;
import net.infonode.docking.theme.LookAndFeelDockingTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.theme.SlimFlatDockingTheme;
import net.infonode.docking.theme.SoftBlueIceDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.MixedViewHandler;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
/**
 * A class containing the main window of application.
 *
 * @author Piot Kordy
 * @version
 */
public class MainWindow extends Frame
{
  /**
   *
   */
  private static final long                 serialVersionUID = -5414047637435066736L;
  /**
   * This adapter handles Mouse over messages on toolbar buttons and
   * menu items.
   */
  private MouseHandler                      mouseHandler;
  private static ADAction                   fileNew;
  private static ADAction                   fileOpen;
  private static ADAction                   fileSave;
  private static ADAction                   fileExample;
  private static ADAction                   fileExportToPdf;
  private static ADAction                   fileExportToLatex;
  private static ADAction                   fileExportToPng;
  private static ADAction                   fileExportToJpg;
  private static ADAction                   fileExportToXml;
  private static ADAction                   fileImportFromXml;
  private static ADAction                   filePrint;
  private static ADAction                   filePrintPreview;
  private static ADAction                   fileExit;
  /**
   * Status bar
   */
  private StatusLine                        status;
  /**
   * Saved layout in memory.
   */
  private byte[]                            layout;
  private HashMap<Integer, ValuationDomain<Ring>> valuations = new HashMap<Integer, ValuationDomain<Ring>>();
  /**
   * Class for handling displaying dialogs for saving/loading files.
   */
  private FileHandler                       fh;

  /**
   * The one and only root window.
   */
  private RootWindow                        rootWindow;

  /**
   * The application frame.
   */
  private JFrame                            frame    = new JFrame("Attack Defense Tree Tool");
  /**
   * An array of the static views.
   */
  private View[]                            views     = new View[5];

  /**
   * Contains the dynamic views that has been added to the root window.
   */
  private HashMap<Integer, DynamicView>     dynamicViews= new HashMap<Integer, DynamicView>();

  /**
   * Contains all the static views.
   */
  private ViewMap                           viewMap     = new ViewMap();

  /**
   * The windows menu items.
   */
  private JMenuItem[]                       windowsItems = new JMenuItem[views.length];
  /**
   * The Attribute Domains menu.
   */
  private JMenu                             attrDomainsMenu;
  /**
   * The currently applied docking windows theme
   */
  private DockingWindowsTheme               currentTheme  = new ShapedGradientDockingTheme();
  private ADTreeCanvas                      lastFocused;
  private SplitWindow                       splitWindow;
  /**
   * In this properties object the modified property values for close buttons
   * etc. are stored. This object is cleared when the theme is changed.
   */
  private RootWindowProperties              properties    = new RootWindowProperties();

  public ValuationDomain<Ring> getValuation(int id)
  {
    return valuations.get(new Integer(id));
  }

  /**
   * Return list of DomainValuations used in main window
   *
   */
  public HashMap<Integer, ValuationDomain<Ring>> getValuations()
  {
    return valuations;
  }

  /**
   * Sets the lastFocused for this instance.
   *
   * @param lastFocused
   *          The lastFocused.
   */
  public void setLastFocused(ADTreeCanvas lastFocused)
  {
    this.lastFocused = lastFocused;
    if (lastFocused == null || lastFocused instanceof DomainCanvas) {
      ((ValuationView) views[2].getComponent()).assignCanvas(lastFocused);
      ((DetailsView) views[3].getComponent()).assignCanvas(lastFocused);
    }
  }

  @SuppressWarnings("rawtypes")
  public void removeDomain(int i)
  {
    DynamicView view = dynamicViews.get(new Integer(i));
    Component c = view.getComponent();
    if (c instanceof DomainView) {
      if (lastFocused instanceof DomainCanvas
          && ((DomainCanvas) lastFocused).getId() == i) {
        setLastFocused(null);
      }
      ((DomainView<?>) c).onClose();
      view.close();
      getRootWindow().removeView(view);
    }
    valuations.remove(new Integer(i));
    dynamicViews.remove(new Integer(i));
    createAttrDomainMenu();
  }

  public void removeDomains()
  {
    if (lastFocused instanceof DomainCanvas) {
      setLastFocused(null);
    }
    Collection<DynamicView> dynViews = dynamicViews.values();
    Vector<DynamicView> set = new Vector<DynamicView>(dynViews);
    for (DynamicView view : set) {
      Component c = view.getComponent();
      if (c instanceof DomainView) {
        ((DomainView<?>) c).onClose();
        view.close();
        getRootWindow().removeView(view);
      }
    }
    valuations.clear();
    dynamicViews.clear();
    createAttrDomainMenu();
  }

  /**
   * Gets the views for this instance.
   *
   * @return The views.
   */
  public View[] getViews()
  {
    return this.views;
  }

  /**
   * Gets the root window for this instance.
   *
   * @return The rootWindow
   */
  public RootWindow getRootWindow()
  {
    return this.rootWindow;
  }

  /**
   * @return JLabel that holds the text of a status bar.
   */
  public StatusLine getStatusBar()
  {
    return status;
  }

  /**
   * Gets the views for this instance.
   *
   * @param index
   *          The index to get.
   * @return The views.
   */
  public View getViews(int index)
  {
    return this.views[index];
  }

  /**
   * Gets the lastFocused for this instance.
   *
   * @return The lastFocused.
   */
  public ADTreeCanvas getLastFocused()
  {
    return this.lastFocused;
  }

  /**
   * A dynamically created view containing an id.
   */
  public static class DynamicView extends View
  {
    static final long serialVersionUID = 4127190623311867764L;
    private int       id;

    /**
     * Constructor.
     *
     * @param title
     *          the view title
     * @param icon
     *          the view icon
     * @param component
     *          the view component
     * @param id
     *          the view id
     */
    DynamicView(String title, Icon icon, Component component, int id)
    {
      super(title, icon, component);
      this.id = id;
    }

    /**
     * Returns the view id.
     *
     * @return the view id
     */
    public int getId()
    {
      return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
      this.id = id;
    }
  }

   @SuppressWarnings("rawtypes")
  public void updateDynamicViewTitles()
  {
    Collection<DynamicView> dynViews = dynamicViews.values();
    Vector<DynamicView> set = new Vector<DynamicView>(dynViews);
    int i = 0;
    for (DynamicView view : set) {
      i++;
      DomainView dv = (DomainView) view.getComponent();
      view.getViewProperties().setTitle(
          i + ". " + (dv.getCanvas().getDomain().getName()));
    }
  }

  /**
   * Constructs a new instance.
   */
  public MainWindow(final String args[])
  {
    createActions();
    createRootWindow();
    restoreDefaultLayout();
    showFrame();
    if(args.length > 0) {
      String ext = args[0].substring(args[0].lastIndexOf('.') + 1);
      try {
        if(ext.equalsIgnoreCase("xml")){
          File file = new File(args[0]);
          FileInputStream in = new FileInputStream(file);
          if (in != null) {
            ADTXmlImport importer = new ADTXmlImport(this);
            importer.importFrom(in);
          }
        }
        else{
          if(ext.equalsIgnoreCase("adt")){
            File file = new File(args[0]);
            FileInputStream fin = new FileInputStream(file);
            if (fin != null) {
              ObjectInputStream in = new ObjectInputStream(fin);
              ADTSerializer as = new ADTSerializer(this);
              as.loadFromStreamTree(in);
              createAttrDomainMenu();
            }
          }
          else{
            System.out.println("Unknown file type:"+args[0]);
          }
        }
      }
      catch(FileNotFoundException e){
        System.out.println("File not found:"+args[0]);
      }
      catch(Exception e){
        System.out.println("Error opening file:"+args[0]);
        if (args[0].equals("--viewmode") || args[0].equals("-viewmode"))
        {
        	System.out.println("Viewmode usage : --viewmode filename.xml");
        }
      }
    }
  }

  /**
   * Creates the status bar.
   *
   * @return JLabel that holds the text of a status bar.
   */
  private StatusLine createStatusBar()
  {
    StatusLine newStatus = new StatusLine();
    newStatus.setLog((LogView)views[4].getComponent());
    newStatus.setBorder(BorderFactory.createEtchedBorder());
    return newStatus;
  }

//   /**
//    * Creates a view component containing the specified text.
//    *
//    * @param text
//    *          the text
//    * @return the view component
//    */
// 
//   private static JComponent createViewComponent(final String text)
//   {
//     final StringBuffer sb = new StringBuffer();
// 
//     for (int j = 0; j < 100; j++) {
//       sb.append(text + ". This is line " + j + "\n");
//     }
// 
//     return new JScrollPane(new JTextArea(sb.toString()));
//   }

  /**
   * Update the floating window by adding a menu bar and a status label if menu
   * option is choosen.
   *
   * @param fw
   *          the floating window
   */
  private void updateFloatingWindow(FloatingWindow fw)
  {
    // Only update with if menu is selected
    // if (enableMenuAndStatusLabelMenuItem.isSelected()) {
    // Create a dummy menu bar as example
    // JMenuBar bar = new JMenuBar();
    // bar.add(new JMenu("Menu 1")).add(new JMenuItem("Menu 1 Item 1"));
    // bar.add(new JMenu("Menu 2")).add(new JMenuItem("Menu 2 Item 1"));

    // // Set it in the root pane of the floating window
    // fw.getRootPane().setJMenuBar(bar);

    // // Create and add a status label
    // JLabel statusLabel = new JLabel("I'm a status label!");

    // // Add it as the SOUTH component to the root pane's content pane. Note
    // that the actual floating
    // // window is placed in the CENTER position and must not be removed.
    // fw.getRootPane().getContentPane().add(statusLabel, BorderLayout.SOUTH);
    // }
  }

  /**
   * Returns the next available dynamic view id.
   *
   * @return the next available dynamic view id
   */
  private int getDynamicViewId()
  {
    int id = 0;

    while (dynamicViews.containsKey(new Integer(id)))
      id++;

    return id;
  }

  private void createRootWindow()
  {
    ADTreeForGui tree = new ADTreeForGui(new ADTreeNode());
    views[0] = new View("ADTree Edit",
    					new ImageIcon(this.getClass().getResource("/icons/tree_16x16.png")),
    					new ADTreeView(tree, this));
    ADTreeCanvas canvas = ((ADTreeView) views[0].getComponent()).getCanvas();
    
    views[1] = new View("ADTerm Edit",
    					new ImageIcon(this.getClass().getResource("/icons/viewTree_16x16.png")),
    					new ADTermView(canvas));
    
    views[2] = new View("Valuations View",
    					new ImageIcon(this.getClass().getResource("/icons/table_16x16.png")),
    					new ValuationView());
    
    views[3] = new View("Domain Details View",
    					new ImageIcon(this.getClass().getResource("/icons/eyes_16x16.png")),
    					new DetailsView());
    views[4] = new View("Message Log",
    					new ImageIcon(this.getClass().getResource("/icons/messageLog.png")),
    					new LogView());
    
    for (int i = 0; i < views.length; i++) 
    {
      viewMap.addView(i, views[i]);
    }
    MixedViewHandler handler = new MixedViewHandler(viewMap,
        new ViewSerializer()
        {
          @SuppressWarnings({ "rawtypes", "unchecked" })
          public void writeView(View view, ObjectOutputStream out)
              throws IOException
          {
            int id = ((DynamicView) view).getId();
            out.writeInt(id);
            if (view.getComponent() instanceof DomainView) {
              Domain d = ((DomainCanvas) ((DomainView) view.getComponent())
                  .getCanvas()).getDomain();
              out.writeObject(DomainFactory.updateDomainName(DomainFactory
                  .getClassName(d)));
            }
          }
          @SuppressWarnings({ "rawtypes", "unchecked" })
          public View readView(ObjectInputStream in) throws IOException
          {
            int id = in.readInt();
            try {
              if (Options.currentSaveVer == 1) {

//                 String domainName = (String)in.readObject();
                String domainName = DomainFactory.updateDomainName((String) in.readObject());
                ValuationDomain<Ring> vd = new ValuationDomain(DomainFactory.createFromString(domainName));
                valuations.put(new Integer(id), vd);
                View view = getDynamicView(id);
                return view;
              }
              else {
                if (Options.currentSaveVer == -1) {
                  String domainName = DomainFactory.updateDomainName((String) in.readObject());
                  if (valuations.get(new Integer(id))!=null){
                    View view = getDynamicView(id);
                    return view;
                  }
                }
              }
            }
            catch (ClassNotFoundException e) {
              return null;
            }
            return null;
          }
        });
    rootWindow = DockingUtil.createRootWindow(viewMap, handler, true);
    // Set gradient theme. The theme properties object is the super object of
    // our properties object, which
    // means our property value settings will override the theme values
    properties.addSuperObject(currentTheme.getRootWindowProperties());

    // Our properties object is the super object of the root window properties
    // object, so all property values of the
    // theme and in our property object will be used by the root window
    rootWindow.getRootWindowProperties().addSuperObject(properties);

    // Enable the bottom window bar
    rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);
    // Add a listener which shows dialogs when a window is closing or closed.
    rootWindow.addListener(new DockingWindowAdapter()
    {
      public void windowAdded(DockingWindow addedToWindow,
          DockingWindow addedWindow)
      {
        // If the added window is a floating window, then update it
        if (addedWindow instanceof FloatingWindow) {
          updateFloatingWindow((FloatingWindow) addedWindow);
        }
        else {
          updateWindowsMenu();
        }
      }

      public void windowRemoved(DockingWindow removedFromWindow,
          DockingWindow removedWindow)
      {
        updateWindowsMenu();
      }

      public void windowClosing(DockingWindow window)
          throws OperationAbortedException
      {
        // Confirm close operation
        // if (window.getChildWindow(0)instanceof DynamicView){
        // if (JOptionPane.showConfirmDialog(frame, "Really close window '" +
        // window +
        // "'? Note that closing window with domain removes the domain.") !=
        // JOptionPane.YES_OPTION){
        // throw new OperationAbortedException("Window close was aborted!");
        // }
        // Component c = ((DynamicView)window.getChildWindow(0)).getComponent();
        // if(c instanceof DomainView) {
        // // ((DomainView<?>)c).onClose();
        // }
        // }
      }

      public void windowUndocking(DockingWindow window)
          throws OperationAbortedException
      {
        // Confirm undock operation
        // if (JOptionPane.showConfirmDialog(frame, "Really undock window '" +
        // window + "'?") != JOptionPane.YES_OPTION)
        // throw new OperationAbortedException("Window undock was aborted!");
      }

      @SuppressWarnings("rawtypes")
      public void viewFocusChanged(View previouslyFocusedView, View focusedView)
      {
        if (focusedView != null && focusedView.getComponent() != null) {
          Component c = focusedView.getComponent();
          if (c instanceof ADTreeView) {
            setLastFocused(((ADTreeView) c).getCanvas());
          }
          else
            if (c instanceof DomainView) {
              setLastFocused(((DomainView) c).getCanvas());
            }
            else
              if (!(c instanceof ValuationView || c instanceof DetailsView)) {
                setLastFocused(null);
              }
        }
        else
          if (focusedView != null) {
            setLastFocused(null);
          }
      }
    });

    // Add a mouse button listener that closes a window when it's clicked with
    // the middle mouse button.
    rootWindow
        .addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);
  }

  /**
   * Create all actions used in application.
   */
  private void createActions()
  {

    fileNew = new ADAction(ButtonTexts.NEW)
    {
      /**
       *
       */
      private static final long serialVersionUID = -1139040302346025737L;

      public void actionPerformed(final ActionEvent e)
      {
        final int result = JOptionPane.showConfirmDialog(frame,
            "Are you sure you want replace current tree with new one?",
            "New Tree", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
          restoreDefaultLayout();
          ADTreeCanvas can = ((ADTreeView) views[0].getComponent()).getCanvas();
          removeDomains();
          can.newTree();
          fh.setTreeFileName(null);
          status.report("New empty tree created.");
        }
      }
    };
    fileNew.setMnemonic(KeyEvent.VK_N);
    fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
        InputEvent.ALT_MASK));
    fileNew.setSmallIcon(new ImageIcon(this.getClass().getResource(
        "/icons/new.png")));
    fileNew.setToolTip(ToolTipTexts.NEW);

    fileOpen = new ADAction(ButtonTexts.OPEN)
    {
      /**
       *
       */
      private static final long serialVersionUID = -1735715946035939568L;

      public void actionPerformed(final ActionEvent e)
      {
        loadTree();
      }
    };
    fileOpen.setMnemonic(KeyEvent.VK_O);
    fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
        InputEvent.ALT_MASK));
    fileOpen.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/open.png")));

    fileSave = new ADAction(ButtonTexts.SAVE)
    {
      /**
       *
       */
      private static final long serialVersionUID = -8810941869644307469L;

      public void actionPerformed(final ActionEvent e)
      {
        saveTree();
      }
    };
    fileSave.setMnemonic(KeyEvent.VK_S);
    fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        InputEvent.ALT_MASK));
    fileSave.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/save.png")));

    fileExample = new ADAction("Load Example Tree")
    {
      /**
       *
       */
      private static final long serialVersionUID = -4300803966363076614L;

      public void actionPerformed(final ActionEvent e)
      {
        loadExample();
      }
    };
    fileExample.setMnemonic(KeyEvent.VK_L);
    fileExample.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
        InputEvent.ALT_MASK));
    fileExample.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/treeEx_16x16.png")));

    fileExportToPdf = new ADAction("Pdf")
    {
      /**
       *
       */
      private static final long serialVersionUID = 4325025687838671271L;

      public void actionPerformed(final ActionEvent e)
      {
        exportTo("pdf");
      }
    };
    fileExportToPdf.setMnemonic(KeyEvent.VK_P);
    // fileExportToPdf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
    // InputEvent.ALT_MASK));
    fileExportToPdf.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/pdf_16x16.png")));

    fileExportToLatex = new ADAction("LaTeX")
    {
      /**
       *
       */
      private static final long serialVersionUID = -6909764599614352161L;

      public void actionPerformed(final ActionEvent e)
      {
        exportTo("tex");
      }
    };
    fileExportToLatex.setMnemonic(KeyEvent.VK_L);
    // fileExportToLatex.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
    // InputEvent.ALT_MASK));
    fileExportToLatex.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/tex_16x16.png")));

    fileExportToPng = new ADAction("PNG image")
    {
      /**
       *
       */
      private static final long serialVersionUID = 2398600083840742200L;

      public void actionPerformed(final ActionEvent e)
      {
        exportTo("png");
      }
    };
    fileExportToPng.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/png_16x16.png")));
    fileExportToPng.setMnemonic(KeyEvent.VK_N);

    fileExportToJpg = new ADAction("JPEG image")
    {
      /**
       *
       */
      private static final long serialVersionUID = 8409590777160375107L;

      public void actionPerformed(final ActionEvent e)
      {
        exportTo("jpg");
      }
    };
    fileExportToJpg.setMnemonic(KeyEvent.VK_J);
    // fileExportToPng.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
    // InputEvent.ALT_MASK));
    fileExportToJpg.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/jpg_16x16.png")));

    fileExportToXml = new ADAction("Xml")
    {
      /**
       *
       */
      private static final long serialVersionUID = 2724287904022882599L;

      public void actionPerformed(final ActionEvent e)
      {
        exportTo("xml");
      }
    };
    fileExportToXml.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/xml_16x16.png")));
    fileExportToXml.setMnemonic(KeyEvent.VK_X);

    fileImportFromXml = new ADAction("Xml")
    {
      /**
       *
       */
      private static final long serialVersionUID = -3605440604743377670L;

      public void actionPerformed(final ActionEvent e)
      {
        importFrom("xml");
      }
    };
    fileImportFromXml.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/xml_16x16.png")));
    fileImportFromXml.setMnemonic(KeyEvent.VK_X);

    filePrintPreview = new ADAction(ButtonTexts.PRINTPREVIEW)
    {
      /**
       *
       */
      private static final long serialVersionUID = -8710097506678812443L;

      public void actionPerformed(final ActionEvent e)
      {
        printPreview();
      }
    };
    // filePrintPreview.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
    // InputEvent.ALT_MASK));
    filePrintPreview.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/preview.png")));
    filePrintPreview.setMnemonic(KeyEvent.VK_V);
    filePrintPreview.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
        InputEvent.CTRL_MASK));

    filePrint = new ADAction(ButtonTexts.PRINT)
    {
      /**
       *
       */
      private static final long serialVersionUID = 7365498990462507356L;

      public void actionPerformed(final ActionEvent e)
      {
        printCanvas();
      }
    };
    filePrint.setMnemonic(KeyEvent.VK_P);
    filePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
        InputEvent.ALT_MASK));
    filePrint.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/print.png")));

    fileExit = new ADAction(ButtonTexts.EXIT)
    {
      /**
       *
       */
      private static final long serialVersionUID = -6586817922511469697L;

      public void actionPerformed(final ActionEvent e)
      {
        WindowEvent windowClosing = new WindowEvent(frame,
            WindowEvent.WINDOW_CLOSING);
        frame.dispatchEvent(windowClosing);
      }
    };
    fileExit.setMnemonic(KeyEvent.VK_X);
    fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
        InputEvent.CTRL_MASK));
    fileExit.setSmallIcon(new ImageIcon(getClass().getResource(
        "/icons/exit.png")));
  }

  private void importFrom(String type)
  {
    FileInputStream in = fh.getImportTreeStream(type);
    if (in != null) {
      ADTXmlImport importer = new ADTXmlImport(this);
      importer.importFrom(in);
    }

    // System.out.println("Exporting "+type);
  }

  private void exportTo(String type)
  {
    ADTreeCanvas canvas = ((ADTreeView) views[0].getComponent()).getCanvas();
    if (lastFocused != null) {
      canvas = lastFocused;
    }
    ADTreeNode tempFocus = canvas.getFocused();
    canvas.setFocus(null);
    FileOutputStream out = fh.getExportTreeStream(type);
    if (out != null) {
      if (type.equals("pdf")) {
        canvas.createPdf(out);
      }
      else
        if (type.equals("tex")) {
          canvas.createLatex(out);
        }
        else
          if (type.equals("png") || type.equals("jpg")) {
            canvas.createImage(out, type);
          }
          else
            if (type.equals("xml")) {
              canvas.createXml(out);
            }
    }
    canvas.setFocus(tempFocus);
  }

  private void restoreDefaultLayout()
  {
	  if(Main.viewmodeIsOn)
	  {
		  rootWindow.setWindow(views[0]);
	  }
	  else
	  {
		  splitWindow=new SplitWindow(false, 0.37103593f, views[1], views[0]);
		  rootWindow.setWindow(splitWindow);
	  }
	  for (View v : dynamicViews.values()) 
	  {
		  addDomainWindow(v);
	  }
    
  }

  private void printPreview()
  {
    ADTreeCanvas canvas = ((ADTreeView) views[0].getComponent()).getCanvas();
    if (lastFocused != null) {
      canvas = lastFocused;
    }
    // int id = getDynamicViewId();
    JPrintPreviewDialog pp = new JPrintPreviewDialog(this, canvas);
    pp.setVisible(true);

  }

  private void printCanvas()
  {
    ADTreeCanvas canvas = ((ADTreeView) views[0].getComponent()).getCanvas();
    if (lastFocused != null) {
      canvas = lastFocused;
    }
    canvas.showPrintDialog(true);
  }

  private void showFrame()
  {
    status = createStatusBar();
    if (isRunningJavaWebStart()) {
      fh = new JWSFileHandler(status, this);
    }
    else {
      fh = new FileHandler(status, this);
    }
    mouseHandler = new MouseHandler();
    if (!Main.viewmodeIsOn)
    {
    	frame.getContentPane().add(createToolBar(), BorderLayout.NORTH);
    	frame.getContentPane().add(status, BorderLayout.SOUTH);
    }
    
    frame.getContentPane().add(getRootWindow(), BorderLayout.CENTER);
    
    frame.setJMenuBar(createMenuBar());
    Dimension dim = getScreenSize(0.8, 0.4);
    frame.setSize(dim.width, dim.height);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter()
    {
      public final void windowClosing(final WindowEvent e)
      {
    	  if (Main.viewmodeIsOn)
    	  {
    		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  }
    	  else
    	  {
    		  final JFrame localFrame = (JFrame) e.getSource();

    	        final int result = JOptionPane.showConfirmDialog(localFrame,
    	            "Are you sure you want to exit the application?",
    	            "Exit Application", JOptionPane.YES_NO_OPTION);

    	        if (result == JOptionPane.YES_OPTION) {
    	          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	        }
    		  
    	  }
      }
    });
    frame.setVisible(true);
  }

  /**
   * Creates the frame tool bar.
   *
   * @return the frame tool bar
   */
  private JToolBar createToolBar()
  {
    ADToolBar toolbar = new ADToolBar();

    ToolBarButton button;

    button = toolbar.add(fileNew);
    button.addMouseListener(mouseHandler);
    button = toolbar.add(fileOpen);
    button.addMouseListener(mouseHandler);
    button = toolbar.add(fileSave);
    button.addMouseListener(mouseHandler);
    toolbar.addSeparator();
    button = toolbar.add(filePrintPreview);
    button.addMouseListener(mouseHandler);
    toolbar.setTextLabels(true);
    return toolbar;
  }

  /**
   * Creates the frame menu bar.
   *
   * @return the menu bar
   */
  private JMenuBar createMenuBar()
  {
    final JMenuBar menu = new JMenuBar();
    
    attrDomainsMenu = new JMenu("Domains");
    attrDomainsMenu.setMnemonic(KeyEvent.VK_D);
    
    createAttrDomainMenu();
    
    if (!Main.viewmodeIsOn)
    {
    	menu.add(createFileMenu());
        menu.add(createEditMenu());
        menu.add(createViewMenu());
        menu.add(createWindowsMenu());
        menu.add(attrDomainsMenu);
        menu.add(createHelpMenu());
    }
    return menu;
  }

  /**
   * Create Attribute Domains menu
   *
   * @return the domain menu
   */
  public void createAttrDomainMenu()
  {
    JMenu menu=attrDomainsMenu;
    menu.removeAll();
    JMenuItem menuItem = new JMenuItem("Add Attribute Domain");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        chooseAttributeDomain();
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_A);
    menu.add(menuItem);
    // if (dynViews.size()>0){
    if (valuations.size() > 0) {
      menu.addSeparator();
      for (Integer i : valuations.keySet()) {
        // for (DynamicView dv : dynViews) {
        final int j = i.intValue();
        final View view = getDynamicView(j);
        menuItem = new JMenuItem(view.getTitle(), view.getIcon());
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if (view.getRootWindow() != null) {
              view.restoreFocus();
            }
            else {
              addDomainWindow(view);
//               DockingUtil.addWindow(view, rootWindow);
            }
          }
        });
        menu.add(menuItem);
      }
    }
  }
  public void addDomainWindow(DockingWindow window){
    DockingWindow wr =splitWindow.getRightWindow();
    DockingWindow wl =splitWindow.getLeftWindow();
    if (wr==null||wr==null){
      DockingUtil.addWindow(window, getRootWindow());
    }
    else if (wr instanceof TabWindow){
      ((TabWindow) wr).addTab(window);
    }
    else{
      splitWindow.setWindows(wl,new TabWindow(new DockingWindow[]{wr, window}));
    }
  }
  private void chooseAttributeDomain()
  {
    Vector<Domain<?>> domains = DomainFactory.getPredefinedDomains();
    AddDomainDialog addDialog = new AddDomainDialog(this);
    Domain<?> d = addDialog.showDomainDialog(domains);
    if (d instanceof Parametrized) {
      if (((Parametrized) d).getParameter() instanceof Integer) {
        Integer value = (Integer) ((Parametrized) d).getParameter();
        BoundedIntegerDialog dialog;
        if (d instanceof MinSkill) {
          dialog = new BoundedIntegerDialog(this,
              "Choose maximal skill level k for the proponent");
        }
        else {
          dialog = new BoundedIntegerDialog(this, "Choose value for k");
        }
        BoundedInteger result = (BoundedInteger) (dialog.showInputDialog(
            new BoundedInteger(value, Integer.MAX_VALUE), false));
        if (result != null) {
          value = new Integer(result.getValue());
          if (value == BoundedInteger.INF) {
            value = Integer.MAX_VALUE;
          }
          ((Parametrized) d).setParameter(value);
        }
        else {
          d = null;
        }
      }
    }
    if (d == null) {
      return;
    }
    else {
      addAttributeDomain(d);
      createAttrDomainMenu();
      // if(s.equals("Custom")){
      // addCustomDomain(s);
      // }
      // else {
      // }
    }
  }

//   private void addCustomDomain(String s)
//   {
//     status.reportError("Custom domains not implemented yet!");
//   }

  public HashMap<Integer, DynamicView> getDynamicViews()
  {
    return dynamicViews;
  }

  /**
   * Add a predefined domain window.
   *
   * @param s
   */
  public Dimension getScreenSize(double scaleY, double scaleX)
  {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gs = ge.getScreenDevices();
    DisplayMode dm = gs[0].getDisplayMode();
    return new Dimension((int) (dm.getWidth() * scaleX),
        (int) (dm.getHeight() * scaleY));
  }

  /**
   * Adds new attribute domain.
   *
   * @param d
   *          attribute domain to be added.
   */
  @SuppressWarnings("rawtypes")
  public View addAttributeDomain(Domain<?> d)
  {
    @SuppressWarnings("unchecked")
    ValuationDomain<Ring> vd = new ValuationDomain(d);
    int id = getDynamicViewId();
    valuations.put(new Integer(id), vd);
    View view = getDynamicView(id);
    addDomainWindow(view);
    setLastFocused(((DomainView) view.getComponent()).getCanvas());
    status.report("Added a new domain: "+d.getName());
    return view;
  }

  public void closeDynamicViews()
  {
    for (View v : dynamicViews.values()) {
      v.close();
    }
  }

  /**
   * Retrieves a DynamicView with domain.
   *
   * @param id
   *          id of the view and associated canvas
   * @return created or retreived DynamicView.
   */
  @SuppressWarnings("rawtypes")
  public View getDynamicView(int id)
  {
    View view = dynamicViews.get(new Integer(id));
    if (view == null) {
      if (getValuation(id) != null) {
        ADTreeForGui tree = ((ADTreeView) views[0].getComponent()).getTree();
        DomainCanvas canvas = new DomainCanvas(tree, this, id);
        DomainView dv = new DomainView(this, canvas, id);
        view = new DynamicView((1 + id) + ". " + canvas.getDomain().getName(),
            dv.getIcon(), dv, id);
        dynamicViews.put(new Integer(id), (DynamicView) view);
      }
      else {
        status.reportError("Dynamic View with id " + id
            + " has no associated valuations");
        return null;
      }
    }
    else {
    }
    return view;
  }

  private void updateWindowsMenu()
  {
    for (int i = 0; i < windowsItems.length; i++) {
      if (windowsItems[i] != null) {
        windowsItems[i].setEnabled(views[i].getRootWindow() == null);
      }
    }
  }

  /**
   * Creates the windows menu where not shown views can be shown.
   *
   * @return the windows menu
   */
  private JMenu createWindowsMenu()
  {
    JMenu menu = new JMenu("Windows");
    menu.setMnemonic(KeyEvent.VK_W);

    for (int i = 0; i < views.length; i++) {
      final View view = views[i];
      windowsItems[i] = new JMenuItem(view.getTitle(), view.getIcon());
      windowsItems[i].setEnabled(views[i].getRootWindow() == null);
      menu.add(windowsItems[i]).addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (view.getRootWindow() != null)
            view.restoreFocus();
          else {
            DockingUtil.addWindow(view, getRootWindow());
          }
        }
      });
    }
    windowsItems[0].setMnemonic('r');
    windowsItems[1].setMnemonic('e');
    windowsItems[2].setMnemonic('V');
    windowsItems[3].setMnemonic('D');
    windowsItems[4].setMnemonic('M');
    return menu;
  }

  /**
   * Creates the help menu
   *
   * @return the help menu
   */
  private JMenu createHelpMenu()
  {
    JMenuItem menuItem;
    JMenu menu = new JMenu("Help");
    menu.setMnemonic(KeyEvent.VK_H);
    menuItem = new JMenuItem("About");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        AboutDialog ad = new AboutDialog();
        ad.setVisible(true);
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_A);
    menu.add(menuItem);
    return menu;
  }

  /**
   * Creates the windows menu where not shown views can be shown.
   *
   * @return the menu menu
   */
  private JMenu createEditMenu()
  {
    JMenuItem menuItem;
    final JMenu editMenu = new JMenu();
    editMenu.setText(ButtonTexts.EDIT);
    editMenu.setMnemonic('E');
    menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
    menuItem.setText("Cut");
    menuItem.setMnemonic(KeyEvent.VK_T);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
        InputEvent.CTRL_MASK));
    editMenu.add(menuItem);

    menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
    menuItem.setText("Copy");
    menuItem.setMnemonic(KeyEvent.VK_C);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
        InputEvent.CTRL_MASK));
    editMenu.add(menuItem);

    menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
    menuItem.setText("Paste");
    menuItem.setMnemonic(KeyEvent.VK_P);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
        InputEvent.CTRL_MASK));
    editMenu.add(menuItem);
    editMenu.addSeparator();
    menuItem = new JMenuItem("Switch Attacker/Defender Roles");
    menuItem.addActionListener(new ActionListener()
    {
      @SuppressWarnings("rawtypes")
      public void actionPerformed(ActionEvent e)
      {
        Options.canv_Defender = Options.canv_Defender == ADTreeNode.Type.PROPONENT ? ADTreeNode.Type.OPPONENT
            : ADTreeNode.Type.PROPONENT;
        ((ADTreeView) views[0].getComponent()).getCanvas().getTree()
            .updateAllSizes();
        // ((ADTreeView)views[0].getComponent()).getCanvas().getTree().notifyTreeChanged();
        ((ADTreeView) views[0].getComponent()).getCanvas().repaint();
        for (View v : dynamicViews.values()) {
          ((DomainView) v.getComponent()).getCanvas().getTree()
              .updateAllSizes();
          ((DomainView) v.getComponent()).getCanvas().repaint();
        }
        status.report("Assigned a role of a defender to the "+(Options.canv_Defender == ADTreeNode.Type.PROPONENT ? "opponent." 
                                                       : "proponent."));
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_S);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
        InputEvent.CTRL_MASK));
    editMenu.add(menuItem);
    menuItem = new JMenuItem("Validate Terms");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ((ADTermView) views[1].getComponent()).parse();
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_L);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
        InputEvent.CTRL_MASK));
    editMenu.add(menuItem);
    return editMenu;
  }

  /**
   * Creates the file menu.
   *
   * @return the file menu
   */
  private JMenu createFileMenu()
  {
    JMenuItem menuItem;
    final JMenu fileMenu = new JMenu();
    fileMenu.setText(ButtonTexts.FILE);
    fileMenu.setMnemonic('F');

    menuItem = fileMenu.add(fileNew);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);
    fileMenu.addSeparator();

    menuItem = fileMenu.add(fileExample);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);

    menuItem = fileMenu.add(fileOpen);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);

    menuItem = fileMenu.add(fileSave);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);

    JMenu exportTo = new JMenu("Export to");
    exportTo.setMnemonic(KeyEvent.VK_E);
    menuItem = exportTo.add(fileExportToPdf);
    menuItem.addMouseListener(mouseHandler);
    exportTo.add(menuItem);
    menuItem = exportTo.add(fileExportToLatex);
    menuItem.addMouseListener(mouseHandler);
    exportTo.add(menuItem);
    menuItem = exportTo.add(fileExportToPng);
    menuItem.addMouseListener(mouseHandler);
    exportTo.add(menuItem);
    menuItem = exportTo.add(fileExportToJpg);
    menuItem.addMouseListener(mouseHandler);
    exportTo.add(menuItem);
    menuItem = exportTo.add(fileExportToXml);
    menuItem.addMouseListener(mouseHandler);
    exportTo.add(menuItem);
    fileMenu.add(exportTo);

    JMenu importFrom = new JMenu("Import from");
    importFrom.setMnemonic(KeyEvent.VK_I);
    menuItem = importFrom.add(fileImportFromXml);
    menuItem.addMouseListener(mouseHandler);
    importFrom.add(menuItem);
    fileMenu.add(importFrom);

    menuItem = fileMenu.add(filePrint);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);

    menuItem = fileMenu.add(filePrintPreview);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);

    menuItem = fileMenu.add(fileExit);
    menuItem.addMouseListener(mouseHandler);
    fileMenu.add(menuItem);
    return fileMenu;
  }

  /**
   * Create view menu.
   *
   * @return
   */
  private JMenu createViewMenu()
  {
    JMenu viewMenu = new JMenu("View");
    viewMenu.setMnemonic(KeyEvent.VK_V);
    JMenu themesMenu = new JMenu("Themes");
    themesMenu.setMnemonic(KeyEvent.VK_T);
    JMenu layoutMenu = new JMenu("Layouts");
    layoutMenu.setMnemonic(KeyEvent.VK_L);

    final RootWindowProperties titleBarStyleProperties = PropertiesUtil
        .createTitleBarStyleRootWindowProperties();

    final JCheckBoxMenuItem titleBarStyleItem = new JCheckBoxMenuItem(
        "Title Bar Style Theme");
    titleBarStyleItem.setSelected(false);
    titleBarStyleItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (titleBarStyleItem.isSelected())
          properties.addSuperObject(titleBarStyleProperties);
        else
          properties.removeSuperObject(titleBarStyleProperties);
      }
    });

    themesMenu.add(titleBarStyleItem);
    themesMenu.addSeparator();

    DockingWindowsTheme[] themes = { new DefaultDockingTheme(),
        new LookAndFeelDockingTheme(), new BlueHighlightDockingTheme(),
        new SlimFlatDockingTheme(), new GradientDockingTheme(),
        new ShapedGradientDockingTheme(), new SoftBlueIceDockingTheme(),
        new ClassicDockingTheme() };

    ButtonGroup group = new ButtonGroup();

    for (int i = 0; i < themes.length; i++) {
      final DockingWindowsTheme theme = themes[i];

      JRadioButtonMenuItem item = new JRadioButtonMenuItem(theme.getName());
      item.setSelected(i == 4);
      group.add(item);

      themesMenu.add(item).addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // Clear the modified properties values
          properties.getMap().clear(true);

          setTheme(theme);
        }
      });
    }
    viewMenu.add(themesMenu);
    JMenuItem menuItem;
    menuItem = new JMenuItem("Restore Default");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        restoreDefaultLayout();
        status.report("Restored default layout");
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_D);
    layoutMenu.add(menuItem);
    layoutMenu.addSeparator();
    menuItem = new JMenuItem("Store in Memory");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
          ObjectOutputStream out = new ObjectOutputStream(bos);
          getRootWindow().write(out, true);
          out.close();
          status.report("Stored window layout in memory.");
        }
        catch (IOException err) {
          status.reportError(err.getMessage());
        }
        layout = bos.toByteArray();
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_M);
    layoutMenu.add(menuItem);
    menuItem = new JMenuItem("Restore Layout");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            if (layout != null) {
              try {
                // Load the layout from a byte array
                ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(layout));
                getRootWindow().read(in, true);
                in.close();
//                 Collection<DynamicView> dynViews = dynamicViews.values();
//                 Vector<DynamicView> set = new Vector<DynamicView>(dynViews);
//                 for (DynamicView view : set) {
//                   DockingUtil.addWindow(view,getRootWindow());
// //                   view.restoreFocus();
//                 }
//                 views[0].restoreFocus();
                status.report("Restored window layout from memory.");
              }
              catch (IOException e1) {
                throw new RuntimeException(e1);
              }
            }
          }
        });
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_R);
    layoutMenu.add(menuItem);
    layoutMenu.addSeparator();
    menuItem = new JMenuItem("Save to File");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            ObjectOutputStream out = fh.getSaveLayoutStream();
            if (out != null) {
              try {
                rootWindow.write(out, true);
                out.close();
                status.report("Stored window layout in file.");
              }
              catch (final IOException e2) {
                status.reportError(e2.getLocalizedMessage());
              }
            }
          }
        });
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_S);
    layoutMenu.add(menuItem);
    menuItem = new JMenuItem("Load from File");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            ObjectInputStream in = fh.getLoadLayoutStream();
            if (in != null) {
              try {
                rootWindow.read(in, true);
                in.close();
                status.report("Restored window layout from file.");
              }
              catch (final IOException e2) {
                status.reportError(e2.getLocalizedMessage());
              }
            }
          }
        });
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_L);
    layoutMenu.add(menuItem);
    viewMenu.add(layoutMenu);
    menuItem = new JMenuItem("Expand All Nodes");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ADTreeCanvas canvas = ((ADTreeView) views[0].getComponent())
            .getCanvas();
        canvas.expandAllNodes();
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_E);
    viewMenu.add(menuItem);
    menuItem = new JMenuItem("Fit to Window");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ADTreeCanvas canvas = ((ADTreeView) views[0].getComponent())
            .getCanvas();
        if (lastFocused != null) {
          canvas = lastFocused;
        }
        canvas.fitToWindow();
      }
    });
    menuItem.setMnemonic(KeyEvent.VK_F);
    viewMenu.add(menuItem);
    return viewMenu;
  }

  /**
   * ADAction represents an action that is used in application.
   */
  public abstract class ADAction extends AbstractAction
  {
    /**
     *
     */
    private static final long serialVersionUID = 8109471079193338016L;

    /**
     * Defines an ADAction object with the specified descripiton and a default
     * icon.
     *
     * @param text
     *          text to be displayed
     */
    public ADAction(final String text)
    {
      super(text);
    }

    /**
     * Sets accelerator for the action.
     *
     * @param accelerator
     *          new accelerator
     */
    public final void setAccelerator(final KeyStroke accelerator)
    {
      putValue(ACCELERATOR_KEY, accelerator);
    }

    /**
     * Sets the new small icon for the action.
     *
     * @param icon
     *          new icon
     */
    public final void setSmallIcon(final Icon icon)
    {
      putValue(SMALL_ICON, icon);
    }

    /**
     * Sets tooltip for the action.
     *
     * @param text
     *          new tooltip text
     */
    public final void setToolTip(final String text)
    {
      putValue(SHORT_DESCRIPTION, text);
    }

    /**
     * Sets long description for the action.
     *
     * @param text
     */
    public final void setDescription(final String text)
    {
      putValue(LONG_DESCRIPTION, text);
    }

    /**
     * Sets mnemonic for the action.
     *
     * @param mnemonic
     *          new mnemonic
     */
    public final void setMnemonic(final Integer mnemonic)
    {
      putValue(MNEMONIC_KEY, mnemonic);
    }

    /**
     * {@inheritDoc}
     *
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    public abstract void actionPerformed(final ActionEvent e);
  }

  public JFrame getFrame()
  {
    return frame;
  }

  /**
   * Sets the docking windows theme.
   *
   * @param theme
   *          the docking windows theme
   */
  private void setTheme(DockingWindowsTheme theme)
  {
    properties.replaceSuperObject(currentTheme.getRootWindowProperties(),
        theme.getRootWindowProperties());
    currentTheme = theme;
  }

  /**
   * This adapter is constructed to handle mouse over component events.
   */
  private class MouseHandler extends MouseAdapter
  {

//     private StatusLine label;

    /**
     * ctor for the adapter.
     *
     * @param label
     *          the JLabel which will recieve value of the
     *          Action.LONG_DESCRIPTION key.
     */
    public MouseHandler()
    {
    }

    public void mouseEntered(MouseEvent evt)
    {
    }
  }

  /**
   * Loads the example tree and attribute domains.
   *
   */
  private void loadExample()
  {
    LoadExampleDialog dialog = new LoadExampleDialog(this);
    String fileName = dialog.showDialog();
    if (fileName != null) {
      try {
        URL url = this.getClass().getResource(fileName);
        InputStream in = url.openStream();
        ADTSerializer as = new ADTSerializer(this);
        as.loadFromStreamTree(new ObjectInputStream(in));
        status.report("Loaded example tree");
      }
      catch (IOException e) {
        status.reportError(e.getMessage());
      }
    }
  }

  /**
   * Loads the tree and attribute domains.
   *
   */
  private void loadTree()
  {
    ObjectInputStream in = fh.getLoadTreeStream();
    ADTSerializer as = new ADTSerializer(this);
    as.loadFromStreamTree(in);
    createAttrDomainMenu();
  }

  /**
   * Saves the tree and attribute domains.
   *
   */
  private void saveTree()
  {
    ObjectOutputStream out = fh.getSaveTreeStream();
    ADTSerializer as = new ADTSerializer(this);
    as.saveToStreamTree(out);
  }

  /**
   * Detect if application is run using JWS.
   *
   * @return true if we run under JWS.
   */
  private boolean isRunningJavaWebStart()
  {
    return System.getProperty("javawebstart.version", null) != null;
  }
}

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
package lu.uni.adtool.adtree;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import lu.uni.adtool.Options;
import lu.uni.adtool.domains.Domain;
import lu.uni.adtool.domains.DomainFactory;
import lu.uni.adtool.domains.ValuationDomain;
import lu.uni.adtool.domains.ValueAssignement;
import lu.uni.adtool.domains.rings.Ring;
import lu.uni.adtool.ui.ADTermView;
import lu.uni.adtool.ui.ADTreeCanvas;
import lu.uni.adtool.ui.ADTreeView;
import lu.uni.adtool.ui.DetailsView;
import lu.uni.adtool.ui.DomainCanvas;
import lu.uni.adtool.ui.DomainView;
import lu.uni.adtool.ui.MainWindow;
import lu.uni.adtool.ui.ValuationView;

import net.infonode.docking.RootWindow;
import net.infonode.docking.View;

public class ADTSerializer
{
  private MainWindow mainWindow;
  private View[] views;

  public ADTSerializer(MainWindow mw)
  {
    mainWindow=mw;
    views=mainWindow.getViews();
  }

  private void writeOptions(ObjectOutputStream out) throws IOException
  {
     out.writeObject(Options.canv_BackgroundColor);
     out.writeObject(Options.canv_EdgesColor);
     out.writeObject(Options.canv_TextColorAtt);
     out.writeObject(Options.canv_TextColorDef);
     out.writeObject(Options.canv_FillColorAtt);
     out.writeObject(Options.canv_FillColorDef);
     out.writeObject(Options.canv_BorderColorAtt);
     out.writeObject(Options.canv_BorderColorDef);
     out.writeObject(Options.canv_EditableColor);
     out.writeObject(Options.canv_ShapeAtt);
     out.writeObject(Options.canv_ShapeDef);
     out.writeObject(Options.canv_Font);
     out.writeObject(Options.canv_Defender);
     out.writeObject(Options.canv_ArcSize);
     out.writeObject(Options.canv_ArcPadding);
     out.writeObject(Options.canv_LineWidth);
     out.writeObject(Options.canv_DoAntialiasing);
  }

  private void readOptions(ObjectInputStream in) throws IOException,ClassNotFoundException
  {
    Options.canv_BackgroundColor = (Color)in.readObject();
    Options.canv_EdgesColor    = (Color)in.readObject();
    Options.canv_TextColorAtt  = (Color)in.readObject();
    Options.canv_TextColorDef  = (Color)in.readObject();
    Options.canv_FillColorAtt  = (Color)in.readObject();
    Options.canv_FillColorDef  = (Color)in.readObject();
    Options.canv_BorderColorAtt= (Color)in.readObject();
    Options.canv_BorderColorDef= (Color)in.readObject();
    Options.canv_EditableColor = (Color)in.readObject();
    Options.canv_ShapeAtt      = (Options.ShapeType)in.readObject();
    Options.canv_ShapeDef      = (Options.ShapeType)in.readObject();
    Options.canv_Font          = (Font)in.readObject();
    Options.canv_Defender      = (ADTreeNode.Type)in.readObject();
    Options.canv_ArcSize       = (Integer)in.readObject();
    Options.canv_ArcPadding    = (Integer)in.readObject();
    Options.canv_LineWidth     = (Integer)in.readObject();
    Options.canv_DoAntialiasing= (Boolean)in.readObject();
  }

  public ADTreeForGui loadVer1(ADTreeNode root,ObjectInputStream in)
  {
    ADTreeForGui newTree=null;
    try {
      Options.currentSaveVer=1;
      RootWindow rootWindow=mainWindow.getRootWindow();
      @SuppressWarnings("unchecked")
      Map<ADTreeNode, List<ADTreeNode>> childrenMap =(Map<ADTreeNode, List<ADTreeNode>>)in.readObject();
      @SuppressWarnings("unchecked")
      Map<ADTreeNode, ADTreeNode>       parents     = (Map<ADTreeNode, ADTreeNode>)in.readObject();
      int maxCounter = 0;
      for(ADTreeNode node:parents.keySet()){
        maxCounter = Math.max(maxCounter,node.getId());
        if(node.isAboveFolded()){
          node.setAboveFolded(false);
        }
      }
      ADTreeNode.resetCounter(maxCounter+1);
      newTree = new ADTreeForGui(root,childrenMap,parents);
      views[0].setComponent(new ADTreeView(newTree,mainWindow));
      ADTreeCanvas canvas = ((ADTreeView)views[0].getComponent()).getCanvas();
      views[1].setComponent(new ADTermView(canvas));
      views[2].setComponent(new ValuationView());
      views[3].setComponent(new DetailsView());
      readOptions(in);
      Boolean savedLayout = (Boolean)in.readObject();
      mainWindow.removeDomains();
      Vector<Integer>domainsIds=null;
      if (savedLayout){
        rootWindow.read(in, true);
        domainsIds=new Vector<Integer>();
        for (View v : mainWindow.getDynamicViews().values()) {
          domainsIds.add(new Integer(((DomainView<?>) v.getComponent()).getId()));
        }
      }
      ((ADTreeView)views[0].getComponent()).getCanvas().setFocus(null);
      ((ValuationView)views[2].getComponent()).assignCanvas(null);
      ((DetailsView)views[3].getComponent()).assignCanvas(null);
      Integer noDomains = (Integer)in.readObject();
      mainWindow.getValuations().clear();
      for(int i = 0; i < noDomains; i++){
        @SuppressWarnings("unchecked")
        Domain<Ring>  d = DomainFactory.updateDomain((Domain<Ring>)in.readObject());//in case domain class is obsolete
        ValuationDomain<Ring> vd= new ValuationDomain<Ring>(d);
        @SuppressWarnings("unchecked")
        ValueAssignement<Ring> vass=(ValueAssignement<Ring>)in.readObject();
        vd.setValueAssPro(vass,root);
        @SuppressWarnings("unchecked")
        ValueAssignement<Ring> vass2=(ValueAssignement<Ring>)in.readObject();
        vd.setValueAssOpp(vass2,root);
        if (!savedLayout){
          mainWindow.getValuations().put(new Integer(i),vd);
          View view = mainWindow.getDynamicView(i);
          System.out.println("Adding domain window when loading");
          mainWindow.addDomainWindow(view);
        }
        else{
          mainWindow.getValuations().put(domainsIds.elementAt(i),vd);
        }
      }
      newTree.updateAllSizes();
      Options.currentSaveVer=-1;
    }
    catch (final IOException e) {
      mainWindow.getStatusBar().reportError(e.getMessage());
    }
    catch(ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    return newTree;
  }

  public void loadVerN(Integer verNo,ObjectInputStream in) throws IOException,ClassNotFoundException
  {
    if (verNo==2){
      String description = (String)in.readObject();
      @SuppressWarnings("unchecked")
      HashMap<ADTreeNode, String> comments = (HashMap<ADTreeNode, String>)in.readObject();
      Object o = in.readObject();
      if (o instanceof ADTreeNode){//old version of save
        ADTreeForGui tree =loadVer1((ADTreeNode)o,in);
        if (tree!=null){
          tree.setDescription(description);
          tree.setComments(comments);
        }
      }
    }
    else{
      mainWindow.getStatusBar().reportError("Loading not implemented for version of the save: "+verNo);
    }
  }

  public void loadFromStreamTree(ObjectInputStream in)
  {
    if (in != null) {
      try {
        Object o = in.readObject();
        if (o instanceof ADTreeNode){
          loadVer1((ADTreeNode)o,in);//old non versioned save type assumed to be version 1
          mainWindow.updateDynamicViewTitles();

        }
        else if (o instanceof Integer){
          loadVerN((Integer)o,in); //version no 2
        }
        //((ADTreeView)views[0].getComponent()).getCanvas().newTree();
        in.close();
      }
      catch (final IOException e) {
        mainWindow.getStatusBar().reportError(e.getMessage());
      }
      catch(ClassNotFoundException ex)
      {
        ex.printStackTrace();
      }
    }
  }

  @SuppressWarnings("unchecked")
  public void saveToStreamTree(ObjectOutputStream out)
  {
    if (out != null) {
      try {
        HashMap<Integer,MainWindow.DynamicView> dynamicViews = mainWindow.getDynamicViews();
        ADTreeForGui tree = ((ADTreeView) views[0].getComponent()).getCanvas().getTree();
        out.writeObject(Options.saveVersion);
        out.writeObject(tree.getDescription());
        out.writeObject(tree.getComments());
        out.writeObject(tree.getRoot(true));
        out.writeObject(tree.getChildrenMap());
        out.writeObject(tree.getParents());
        writeOptions(out);
        out.writeObject(new Boolean(Options.main_saveLayout));
        if (Options.main_saveLayout) {
          mainWindow.getRootWindow().write(out, true);
        }
        out.writeObject(new Integer(dynamicViews.size()));
        for (View v : dynamicViews.values()) {
          out.writeObject(((DomainView<Ring>) v.getComponent()).getCanvas()
              .getDomain());
          out.writeObject(((DomainView<Ring>) v.getComponent()).getCanvas()
              .getValueAssPro());
          out.writeObject(((DomainView<Ring>) v.getComponent()).getCanvas()
              .getValueAssOpp());
        }
        out.close();
      }
      catch (final IOException e2) {
        mainWindow.getStatusBar().reportError(e2.getMessage());
      }
    }
  }
}


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
package lu.uni.adtimport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringReader;

import lu.uni.adtool.Options;
import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTParser;
import lu.uni.adtool.adtree.ADTreeForGui;
import lu.uni.adtool.adtree.ADTreeNode;
import lu.uni.adtool.adtree.ParseException;
import lu.uni.adtool.adtree.TokenMgrError;

public class Main {
  public static ADTParser parser = null;

  private void printUsage()
  {
    System.out.println("  Provide two names of the files as parameter.\n"+
        "  First file is the input file and the second\n  name is the output file.");
  }

  public Main(String[] args)
  {
    ADTNode terms;
    if(args.length > 1) {
      try{
        terms = parse(readFile(args[0]));
        ADTreeForGui tree = new ADTreeForGui(new ADTreeNode());
        tree.createFromTerms(terms);
        saveTree(tree,args[1]);
      }
      catch(IOException e){
        System.err.println(e);
      }
    }
    else{
      printUsage();
    }
  }

  public static void main (String[] args) {
    new Main(args);
  }

  public ADTNode parse(String str)
  {
    ADTNode result = null;
    if (parser == null){
      parser = new ADTParser(new StringReader(str));
    }
    ADTParser.ReInit(new StringReader(str));
    try {
      result = ADTParser.parse();
    }
    catch (ParseException e) {
      System.err.println(e.getMessage());
    }
    catch (TokenMgrError e) {
      System.err.println(e.getMessage());
    }
    return result;
  }

  private void saveTree(ADTreeForGui tree,String fileName)
  {
    File file = new File(fileName);
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(new FileOutputStream(file));
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found:\""+file.getName()+"\".");
    }
    catch (IOException e) {
      System.out.println("There was IO problem opening a file:\""+file.getName()+"\".");
    }

    if (out != null) {
      try {
        out.writeObject(tree.getRoot(true));
        out.writeObject(tree.getChildrenMap());
        out.writeObject(tree.getParents());
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
        out.writeObject(new Boolean(false));//no window layout
        out.writeObject(new Integer(0)); //no dynamic views
        out.close();
      }
      catch (final IOException e2) {
        System.err.println("IO error:" + e2);
      }
    }
  }

  private String readFile( String file ) throws IOException {
    BufferedReader reader = new BufferedReader( new FileReader (file));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");

    while( ( line = reader.readLine() ) != null ) {
        stringBuilder.append( line );
        stringBuilder.append( ls );
    }
    reader.close();
    return stringBuilder.toString();
  }
}

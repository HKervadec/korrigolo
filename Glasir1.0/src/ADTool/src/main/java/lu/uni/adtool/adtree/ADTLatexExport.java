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

import java.io.IOException;

import lu.uni.adtool.Options;

import lu.uni.adtool.ui.ADTreeCanvas;
import java.io.FileOutputStream;

public class ADTLatexExport
{

  private ADTreeCanvas canvas;
  /**
   * Construct new instance.
   * 
   * @param canvas associated canvas for which we do export.
   */
  public ADTLatexExport(ADTreeCanvas canvas)
  {
    this.canvas = canvas;
  }
  public static String escape(String s)
  {
    String result = s.replace("\\","\\textbackslash");
    result = result.replace("&","\\&");
    result = result.replace("%","\\%");
    result = result.replace("$","\\$");
    result = result.replace("#","\\#");
    result = result.replace("_","\\_");
    result = result.replace("{","\\{");
    result = result.replace("}","\\}");
    result = result.replace("~","\\textasciitilde");
    result = result.replace("\u221E","$\\infty$");
    result = result.replace("^","\\textasciicircum");
    final String[] lines = result.split("\n");
    if(lines.length>1){
      result = "\\vbox{";
      for(String l:lines){
        result += "\\hbox{\\strut "+l+"}";
      }
      result += "}";
    }
    else{
      result = "{\\strut "+result+"}";
    }
    return result;
  }
  /**
   * Exports tree into the latex file.
   * 
   * @param fileStream stream to which we write - we assume stream is open and
   * we close it.
   */
  public void exportTo(FileOutputStream fileStream)
  {
    String content = getContent();
    try {
      byte[] contentInBytes = content.getBytes();
      fileStream.write(contentInBytes);
      fileStream.flush();
      fileStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileStream != null) {
          fileStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
//canv_gapBetweenLevels, Options.canv_gapBetweenNodes)
//\psellipticarc*[options]{arrows}(x0,y0)(x1,y1){angleA}{angleB}
  private String getHeader()
  {
    String shapeDef ="";
    switch (Options.canv_ShapeDef) {
      case ROUNDRECT:
      case RECTANGLE:
         shapeDef = "\\Tr[name=#2]{\\psframebox[linecolor=defender,linestyle=solid,framesep=10]{\\psDefBoxNodes{#2}{#3}}}\n";
        break;
      case OVAL:
         shapeDef =  "\\Toval[name=#2,linecolor=defender,linestyle=solid]{\\psDefBoxNodes{#2}{#3}}\n";
        break;
    }
    String shapeAtt = ""; 
    switch (Options.canv_ShapeAtt) {
      case ROUNDRECT:
      case RECTANGLE:
        shapeAtt = "\\Tr[name=#2]{\\psframebox[linecolor=attacker,linestyle=solid,framesep=10]{\\psDefBoxNodes{#2}{#3}}}\n";
        break;
      case OVAL:
         shapeAtt = "\\Toval[name=#2,linecolor=attacker,linestyle=solid]{\\psDefBoxNodes{#2}{#3}}\n";
        break;
    }
    final String extraHeader = "\\documentclass[a4paper]{article}"
         + "\\usepackage{pstricks}\n"
         + "\\usepackage{pst-tree}\n"
         + "\\usepackage{graphicx}\n"
         + "\\usepackage{color}\n"
         + "\\definecolor{attacker}" + getDefineColor(Options.canv_BorderColorAtt) + "\n"
         + "\\definecolor{defender}" + getDefineColor(Options.canv_BorderColorDef) + "\n"
         + "\\definecolor{attFill}" + getDefineColor(Options.canv_FillColorAtt) + "\n"
         + "\\definecolor{defFill}" + getDefineColor(Options.canv_FillColorDef) + "\n"
         + "\\definecolor{editableFill}" + getDefineColor(Options.canv_EditableColor) + "\n"
         + "\\definecolor{edges}" + getDefineColor(Options.canv_EdgesColor) + "\n"
         + "\\definecolor{attText}" + getDefineColor(Options.canv_TextColorAtt) + "\n"
         + "\\definecolor{defText}" + getDefineColor(Options.canv_TextColorDef) + "\n"
         + "\\newcommand{\\NNodeA}[3][]{%\n"
         + "\\psset{linewidth=" + Options.canv_LineWidth + ",linestyle=solid,fillcolor=attFill,linecolor=edges}%\n"
         + "\\psset{#1}%\n" + shapeDef 
         + "}\n"
         + "\\newcommand{\\NNodeAC}[3][]{%\n"
         + "\\NNodeA[linestyle=dotted,#1]{#2}{#3}}%\n"
         + "\n"
         + "\\newcommand{\\NNodeB}[3][]{%\n"
         + "\\psset{linewidth=" + Options.canv_LineWidth + ",linestyle=solid,fillcolor=defFill,linecolor=edges}%\n"
         + "\\psset{#1}%\n" + shapeAtt
         + "}\n"
         + "\\newcommand{\\NNodeBC}[3][]{%\n"
         + "\\NNodeB[linestyle=dotted,#1]{#2}{#3}}%\n"
         + "\n"
         + "\\newcommand{\\Bogen}[4][]{\n%"
         + "\\psset{linecolor=edges,linestyle=solid}\n"
         + "\\psset{#1}%\n"
         + "\\psellipticarc(!\\psGetNodeCenter{#2} #2.x #2.y)\n"
         + "     (!\\psGetNodeCenter{#2:tr} \\psGetNodeCenter{#2:bl} #2:tr.x #2:bl.x sub 10 sub 1.8\n"
         + "     mul\n"
        + "     #2:tr.y #2:bl.y sub 2.0 mul)\n"
         + "     {!\\psGetNodeCenter{#3}\n"
         + "       #3.y #2.y sub #3.x #2.x sub atan}\n"
         + "     {!\\psGetNodeCenter{#4}\n"
         + "       #4.y #2.y sub #4.x #2.x sub atan}\n"
         + "}\n"
         + "\\begin{document}\n";

    return extraHeader
         + "\\begin{figure}[htb]\n"
         + "\\centering\n"
         //+ "\\newlength{\\pbs}\n"
         //+ "\\setlength{\\pbs}{0.2pt}"
         + "\\psset{unit=0.5pt,"
         + "levelsep="+2.5*Options.canv_gapBetweenLevels+","
         + "fillstyle=solid,nodesep=0}\n";

         //+ "\\psscalebox{"+canvas.getScale()+"}{\n";
  }
  private String getDefineColor(Color color)
  {
    return "{RGB}{"+color.getRed()+","+color.getGreen()+","+color.getBlue()+"}"; 
  }
  private String getFooter()
  {
    final String extraEnding = "\\end{document}\n";
    return "\\end{figure}\n"
         + extraEnding;
  }
  /**
   * Converts tree into latex.
   * 
   * @return String containing latex representation
   */
  private String getContent()
  {
    String result = getHeader();
    final String[]r = canvas.getLatex(canvas.getTree().getRoot(),0);
    result += r[0] +r[1];
    result += getFooter();
    return result;
  }
}


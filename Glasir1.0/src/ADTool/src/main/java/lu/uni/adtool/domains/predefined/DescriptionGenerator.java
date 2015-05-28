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
package lu.uni.adtool.domains.predefined;

import lu.uni.adtool.domains.Domain;

import lu.uni.adtool.domains.rings.Ring;

public class DescriptionGenerator
{
  public static String generateDescription(Domain<?> d, String name,String valueDomain, String[] oper)
  {
    return  "<html><table align=\"left\" border=0>"
        + "<tr><th align=\"left\">"+name+"</th></tr>"
        + "<tr align=\"left\"><table border=0 cellpadding=7>"
        + "<tr border=0><th align=\"left\">Value&nbsp;domain:</th>"
        + "    <td colspan=1><nbr>"+valueDomain+"</nbr></td></tr></table></tr>"
        + "<tr><table border=0>"
        + "<tr><th></th><th border=0>proponent</th><td border=0>&nbsp;&nbsp;&nbsp;&nbsp;</td><th border=0>opponent</th></tr>"
        + "<tr><th align=\"left\">or </th><td colspan=2 border=0><b>op&nbsp; </b>" +   oper[0]
        + "</td><td border=0><b>oo&nbsp; </b>" + oper[2] + "</td></tr>"
        + "<tr><th align=\"left\">and</th><td colspan=2 border=0><b>ap&nbsp; </b>" +   oper[1]
        + "</td><td border=0><b>ao&nbsp; </b>" + oper[3] + "</td></tr>"
        + "<tr><th align=\"left\">counter</th><td colspan=2 border=0><b>cp&nbsp; </b>"+oper[4]
        + "</td><td border=0><b>co&nbsp; </b>" + oper[5] + "</td></tr>"
        + "<tr><th align=\"left\" border=0>default value&nbsp;&nbsp;</th><td colspan=2 border=0>"+((Ring)d.getDefaultValue(true)).toUnicode()
        + "</td><td border=0>"+((Ring)d.getDefaultValue(false)).toUnicode()+"</td></tr>"
        + "<tr><th align=\"left\" border=0>modifiable</th><td colspan=2 border=0>"+(d.isValueModifiable(true)?"Yes":"No")+"</td>"
        + "<td border=0>"+(d.isValueModifiable(false)?"Yes":"No")+"</td></tr>"
        + "</table></tr>"
        + "<tr align=\"left\"><table border=0 cellpadding=1>"
        + "<tr border=0><th align=\"left\">Class:</th>"
        + "<td colspan=1><nbr>"+d.getClass().getName()+"</nbr></td></tr></table></tr>"
        + "</table>"
        + "</html>";
  }
}

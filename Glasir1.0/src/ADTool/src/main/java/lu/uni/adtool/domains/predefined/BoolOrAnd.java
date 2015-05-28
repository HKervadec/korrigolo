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

import lu.uni.adtool.domains.rings.Bool;

/**
 * A Domain defined on booleans.
 * 
 * @author Piot Kordy
 */
public class BoolOrAnd implements Domain<Bool>
{
  //number 2
  static final long serialVersionUID = 268474778914366456L;
  /**
   * A default constructor.
   * 
   */
  public BoolOrAnd()
  {
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#getDefaultValue()
   */
  public final Bool getDefaultValue(final boolean proponent)
  {
    if  (proponent){
      return new Bool(false);
    }
    else{
      return new Bool(false);
    }
  }
  /**
   * {@inheritDoc}
   * @see Domain#isValueModifiable(boolean)
   */
  public final boolean isValueModifiable(final boolean proponent)
  {
    return proponent;
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#getName()
   */
  public final String getName()
  {
    return "Satisfiability for the proponent";
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#getDescription()
   */
  public final String getDescription()
  {
    final String name = "Satisfiability for the proponent, "
        + "assuming that all opponent's actions are in place";
    final String vd = "{true,&nbsp;false}";
    final String[] operators = { "<i>x</i>&nbsp;&or;&nbsp;<i>y</i>", 
                                "<i>x</i>&nbsp;&and;&nbsp;<i>y</i>",
                                "<i>x</i>&nbsp;&and;&nbsp;<i>y</i>",
                                "<i>x</i>&nbsp;&or;&nbsp;<i>y</i> ",
                                "<i>x</i>&nbsp;&and;&nbsp;<i>y</i>",
                                "<i>x</i>&nbsp;&or;&nbsp;<i>y</i>",};
    return DescriptionGenerator.generateDescription(this, name, vd, operators);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#op(Bool,Bool)
   */
  public final Bool op(final Bool a, final Bool b)
  {
    return Bool.or(a,b);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#ap(Bool,Bool)
   */
  public final Bool ap(final Bool a, final Bool b)
  {
    return Bool.and(a,b);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#oo(Bool,Bool)
   */
  public final Bool oo(final Bool a, final Bool b)
  {
    return Bool.and(a,b);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#ao(Bool,Bool)
   */
  public final Bool ao(final Bool a, final Bool b)
  {
    return Bool.or(a,b);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#cp(Bool,Bool)
   */
  public final Bool cp(final Bool a, final Bool b)
  {
    return Bool.and(a,b);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Domain#co(Bool,Bool)
   */
  public final Bool co(final Bool a, final Bool b)
  {
    return Bool.or(a,b);
  }
}


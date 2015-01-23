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

import lu.uni.adtool.domains.rings.LMHEValue;

/**
 * A domain to calculate minimal skill level needed for the proponent.
 * 
 * @author Piot Kordy
 */
public class DiffLMHE implements Domain<LMHEValue>
{
  static final long serialVersionUID = 14682694465266848L;
  /**
   * Constructs a new instance.
   */
  public DiffLMHE()
  {
  }
  /**
   * {@inheritDoc}
   * @see Domain#getDefaultValue()
   */
  public final LMHEValue getDefaultValue(final boolean proponent)
  {
    if(proponent){
      return new LMHEValue(4);
    }
    else{
      return new LMHEValue(LMHEValue.INF);
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
   * @see Domain#getName()
   */
  public String getName()
  {
      return "Difficulty for the proponent (L,M,H,E)";
  }
  /**
   * {@inheritDoc}
   * @see Domain#getDescription()
   */
  public final String getDescription()
  {
    final String name = 
        "Minimal difficulty level for the proponent, on the scale "
        +"Low-Medium-High-Extreme, assuming that all opponent’s actions are in "
        +"place and that the set of difficulty levels {L, M, H, E} is linearly "
        +"ordered L&lt;M&lt;H&lt;E";

    final String vd = "<nobr>{L,M,H,E,\u221E}</nobr>";
    final String[] operators = {"min(<i>x</i>,<i>y</i>)",
                                "max(<i>x</i>,<i>y</i>)",
                                "max(<i>x</i>,<i>y</i>)",
                                "min(<i>x</i>,<i>y</i>)",
                                "max(<i>x</i>,<i>y</i>)",
                                "min(<i>x</i>,<i>y</i>)",};
    return DescriptionGenerator.generateDescription(this, name, vd, operators);
  }
  /**
   * {@inheritDoc}
   * @see Domain#op(BoundedInteger,BoundedInteger)
   */
  public final LMHEValue op(final LMHEValue a, final LMHEValue b)
  {
    return LMHEValue.min(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#ap(LMHEValue,LMHEValue)
   */
  public final LMHEValue ap(final LMHEValue a, final LMHEValue b)
  {
    return LMHEValue.max(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#oo(LMHEValue,LMHEValue)
   */
  public final LMHEValue oo(final LMHEValue a,final  LMHEValue b)
  {
    return LMHEValue.max(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#ao(LMHEValue,LMHEValue)
   */
  public final LMHEValue ao(final LMHEValue a, final LMHEValue b)
  {
    return LMHEValue.min(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#cp(LMHEValue,LMHEValue)
   */
  public final LMHEValue cp(final LMHEValue a,final  LMHEValue b)
  {
    return LMHEValue.max(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#co(LMHEValue,LMHEValue)
   */
  public final LMHEValue co(final LMHEValue a, final LMHEValue b)
  {
    return LMHEValue.min(a,b);
  }
}


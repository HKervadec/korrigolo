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

import lu.uni.adtool.domains.rings.RealG0;

/**
 * A Domain defined on booleans.
 * 
 * @author Piot Kordy
 */
public class MinTimePar implements Domain<RealG0>
{
  //number 5
  static final long serialVersionUID = 565945232556446855L;
  /**
   * Constructs a new instance.
   */
  public MinTimePar()
  {
  }

  /**
   * {@inheritDoc}
   * @see Domain#getDefaultValue()
   */
  public final RealG0 getDefaultValue(final boolean proponent)
  {
    if  (proponent){
      return new RealG0(Double.POSITIVE_INFINITY);
    }
    else{
      return new RealG0(Double.POSITIVE_INFINITY);
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
    return "Minimal time for the proponent (in parallel)";
  }
  /**
   * {@inheritDoc}
   * @see Domain#getDescription()
   */
  public String getDescription()
  {
    final String name = "Minimal time for the proponent,"
      + " assuming that all opponent's actions are in place and "
      + "that actions are executed in parallel";
    final String vd = "&#x211D;\u208A\u222A{\u221E}";
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
   * @see Domain#op(RealG0,RealG0)
   */
  public final RealG0 op(final RealG0 a, final RealG0 b)
  {
    return RealG0.min(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#ap(RealG0,RealG0)
   */
  public final RealG0 ap(final RealG0 a, final RealG0 b)
  {
    return RealG0.max(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#oo(RealG0,RealG0)
   */
  public final RealG0 oo(final RealG0 a,final  RealG0 b)
  {
    return RealG0.max(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#ao(RealG0,RealG0)
   */
  public final RealG0 ao(final RealG0 a, final RealG0 b)
  {
    return RealG0.min(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#cp(RealG0,RealG0)
   */
  public final RealG0 cp(final RealG0 a,final  RealG0 b)
  {
    return RealG0.max(a,b);
  }

  /**
   * {@inheritDoc}
   * @see Domain#co(RealG0,RealG0)
   */
  public final RealG0 co(final RealG0 a, final RealG0 b)
  {
    return RealG0.min(a,b);
  }
}




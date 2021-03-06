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
package lu.uni.adtool.domains.rings;
import java.io.Serializable;
/**
 * Class representing values in the domain {L,M,H, Infinity} L=Low M=Medium H=High.
 * 
 * @author Piot Kordy
 */
public class LMHValue implements Serializable, Ring
{
  static final long serialVersionUID = 94246634254424462L;
  public static int INF = 4;
  private int value;
  /**
   * Constructs a new instance.
   * @param k new value
   */
  public LMHValue(final int k)
  {
    value = k;
    if (value <1) {
      value = 1;
    }
    if (value >4) {
      value = 4;
    }
  }
  public Object clone(){
    return new LMHValue(value);
  }
  /**
   * Constructs a new instance.
   * @param k new value as a string
   */
  public LMHValue(final String s)
  {
    value = translate(s);
  }
  /**
   * Set the new value reading from the string
   * 
   */
  public final boolean updateFromString(String s)
  {
    value = translate(s);
    if (value==-1){
      return false;
    }
    return true;
  }
  /**
   * Gets the value for this instance.
   *
   * @return The value.
   */
  public final int getValue()
  {
    return this.value;
  }
  /**
   * Sum with upper bound of two integers.
   * 
   * @param a first bounded integer.
   * @param b second bounded integer.
   * @return sum or upper bound if sum is larger than the bound.
   */
  public static LMHValue sum(final LMHValue a,
      final LMHValue b)
  {
    if(a.getValue()==INF||b.getValue()==INF) {
      return new LMHValue(INF);
    }
    return new LMHValue(Math.min(a.getValue() + b.getValue(),3));
  }
  /**
   * Max of two integers.
   * 
   * @param a first bounded integer.
   * @param b second bounded integer.
   * @return bigger of the two integers.
   */
  public static LMHValue max(final LMHValue a,
      final LMHValue b)
  {
    return new LMHValue(Math.max(a.getValue(), b.getValue()));
  }
  /**
   * Min of two integers.
   * 
   * @param a first bounded integer.
   * @param b second bounded integer.
   * @return smaller of the two integers.
   */
  public static LMHValue min(final LMHValue a,
      final LMHValue b)
  {
    return new LMHValue(Math.min(a.getValue(), b.getValue()));
  }
  /**
   * {@inheritDoc}
   * @see Object#toString()
   */
  public final String toString()
  {
    if (getValue()==INF){
      return "Infinity";
    }
    else{
      if (getValue()==1){
        return "L";
      }
      else{
        if (getValue()==2){
          return "M";
        }
        else{
          if (getValue()==3){
            return "H";
          }
        }
      }
    }
    return "Unknown";
  }
  /**
   * Unicode representation of a number.
   * 
   * @return string with unicode representation.
   */
  public final String toUnicode()
  {
    if (getValue()==INF){
      return "\u221E";
    }
    else{
      if (getValue()==1){
        return "L";
      }
      else{
        if (getValue()==2){
          return "M";
        }
        else{
          if (getValue()==3){
            return "H";
          }
        }
      }
    }
    return "Unknown";
  }

  private int translate(String s)
  {
    if (s.equals("L")){
      return 1;
    }
    else{
      if (s.equals("M")){
        return 2;
      }
      else{
        if (s.equals("H")){
          return 3;
        }
        else{
          if (s.equals("\u221E") || s.equals("Infinity") ){
            return INF;
          }
        }
      }
    }
    return -1;
  }
  public int compareTo(Object o)
  {
    if(o instanceof LMHValue){
      int val2=((LMHValue)o).getValue();
      if(value==val2){
        return 0;
      }
      if(value<val2){
        return -1;
      }
      if(value>val2){
        return 1;
      }
    }
    throw new ClassCastException("Unable to compare LMHValue class with "+o);
  }
}

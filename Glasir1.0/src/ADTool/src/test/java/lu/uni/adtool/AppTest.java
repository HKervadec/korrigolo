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
package lu.uni.adtool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Vector;

//import lu.uni.adtool.adtconverter.EulerTree;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest( String testName )
  {
    super( testName );
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite()
  {
    return new TestSuite( AppTest.class );
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp()
  {
    Vector<Integer> v1,v2;
    v1=new Vector<Integer>();
    v2=new Vector<Integer>();
//     cdnnncna
//     1123345 vs
//     6 233257
    v1.add(1);
    v1.add(1);
    v1.add(2);
    v1.add(3);
    v1.add(3);
    v1.add(4);
    v1.add(5);

    v2.add(6);
    v2.add(2);
    v2.add(3);
    v2.add(3);
    v2.add(2);
    v2.add(5);
    v2.add(7);
    /* EulerTree e = new EulerTree();
    Vector<EulerTree.Operation> r = e.levenshteinPath(v1,v2);
    System.out.println(r);
    assertEquals("They should be equal", r.elementAt(0),EulerTree.Operation.DEL);
    assertEquals("They should be equal", r.elementAt(1),EulerTree.Operation.CHANGE);
    assertEquals("They should be equal", r.elementAt(2),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(3),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(4),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(5),EulerTree.Operation.CHANGE);
    assertEquals("They should be equal", r.elementAt(6),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(7),EulerTree.Operation.ADD);
    assertEquals("Size of the path should be 8", r.size(),8 );*/
    v1=new Vector<Integer>();
    v2=new Vector<Integer>();
    v1.add(1);
    v1.add(1);
    v1.add(2);
    v1.add(3);
    v1.add(3);
    v1.add(4);
    v1.add(5);
    v2.add(1);
    v2.add(1);
    v2.add(2);
    v2.add(3);
    v2.add(3);
    v2.add(4);
    v2.add(5);
    /*r = e.levenshteinPath(v1,v2);
    assertEquals("They should be equal", r.elementAt(0),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(1),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(2),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(3),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(4),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(5),EulerTree.Operation.NONE);
    assertEquals("They should be equal", r.elementAt(6),EulerTree.Operation.NONE);*/
  }
}

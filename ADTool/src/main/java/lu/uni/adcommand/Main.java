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
package lu.uni.adcommand;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import lu.uni.adtool.adtree.ADTNode;
import lu.uni.adtool.adtree.ADTParser;
import lu.uni.adtool.adtree.ParseException;
import lu.uni.adtool.adtree.TokenMgrError;
import lu.uni.adtool.domains.Domain;
import lu.uni.adtool.domains.Evaluator;
import lu.uni.adtool.domains.ValueAssignement;
import lu.uni.adtool.domains.predefined.SatProp;
import lu.uni.adtool.domains.rings.Bool;

public class Main {
  public static ADTParser parser = null;
  private Evaluator<Bool> evaluator;
  private Domain<Bool> domain;
  private ValueAssignement<Bool> valueAssPro;
  private ValueAssignement<Bool> valueAssOpp;
  private ADTNode terms;

  public Main()
  {
    this.domain = new SatProp();
    this.evaluator = new Evaluator<Bool>(domain);
    this.valueAssPro = new ValueAssignement<Bool>();
    this.valueAssOpp = new ValueAssignement<Bool>();
    try{
      String str = readFile("terms.txt");
      fillAssigments();
      parse(str);
      evaluator.reevaluate(terms,valueAssPro,valueAssOpp);
      System.out.println("Value for root:"+evaluator.getValue(terms));
    }
    catch(IOException e){
      System.err.println(e);
    }
  }

  public static void main (String[] args) {
    new Main();
  }
  public void parse(String str)
  {
    if (parser == null){
      parser = new ADTParser(new StringReader(str));
    }
    ADTParser.ReInit(new StringReader(str));
    try {
      terms = ADTParser.parse();
    }
    catch (ParseException e) {
      System.err.println(e.getMessage());
    }
    catch (TokenMgrError e) {
      System.err.println(e.getMessage());
    }
  }
  private void fillAssigments()
  {
    valueAssPro.put("N_1",new Bool(false));
    valueAssOpp.put("N_2",new Bool(false));
    //valueAssOpp.put("N_3",new Bool(false));
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

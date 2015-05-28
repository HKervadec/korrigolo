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
package lu.uni.adtool.domains;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

import lu.uni.adtool.domains.predefined.DiffLMH;
import lu.uni.adtool.domains.predefined.DiffLMHE;
import lu.uni.adtool.domains.predefined.MinCost;
import lu.uni.adtool.domains.predefined.MinSkill;
import lu.uni.adtool.domains.predefined.MinTimePar;
import lu.uni.adtool.domains.predefined.MinTimeSeq;
import lu.uni.adtool.domains.predefined.PowerCons;
import lu.uni.adtool.domains.predefined.ProbSucc;
import lu.uni.adtool.domains.predefined.ReachPar;
import lu.uni.adtool.domains.predefined.ReachSeq;
import lu.uni.adtool.domains.predefined.SatOpp;
import lu.uni.adtool.domains.predefined.SatProp;
import lu.uni.adtool.domains.predefined.SatScenario;
import lu.uni.adtool.domains.rings.Ring;

import org.reflections.Reflections;

/**
 * Creates Domains.
 *
 * @author Piot Kordy
 */
public abstract class DomainFactory
{
  private static final String[] oldNames={
    "BoolOrAndStar",
    "BoolAndOr",
    "BoolOrAnd",
    "IntMinMinMax",
    "IntMinMax",
    "IntMinSum",
    "ProbPlusTimes",
    "RealG0MinSumCost",
    "RealG0MinSumParallel",
    "RealG0MinSum",
    "RealG0MaxSum",
    "SkillDomainLMHE",
    "SkillDomain"
  };
  private static final String[] newNames={
    "SatScenario",
    "SattOpp",
    "SatProp",
    "ReachPar",
    "MinSkill",
    "ReachSeq",
    "ProbSucc",
    "MinCost",
    "MinTimePar",
    "MinTimeSeq",
    "PowerCons",
    "DiffLMHE",
    "DiffLMH"
  };
  private static final String domainsPrefix
    = "lu.uni.adtool.domains.predefined";

  /**
   * Constructs a new instance.
   */
  public DomainFactory()
  {
  }
  public static Boolean isObsolete(Domain<Ring> domain)
  {
    return isObsolete(domain.getClass().getSimpleName());
  }

  public static Boolean isObsolete(String domainName)
  {
    if (Arrays.asList(oldNames).contains(domainName)){
      return true;
    }
    else{
      return false;
    }
  }
  public static Domain<Ring> updateDomain(Domain<Ring> d){
    if (isObsolete(d)){
      String newName=updateDomainName(d.getClass().getSimpleName());
      return createFromString(updateDomainName(newName));
    }
    else return d;
  }
  public static String updateDomainName(String name)
  {
    String result = name;
    for(int i=0; i<oldNames.length; i++){
      result=result.replace(oldNames[i],newNames[i]);
    }
    return result;
  }

  /**
   * Creates predefined domain from string name.
   *
   * @param domainName domain class name
   * @return created domain.
   */
  @SuppressWarnings("unchecked")
  public static Domain<Ring> createFromString(String domainName)
  {
    String name=domainName;
    if(!domainName.startsWith(domainsPrefix)){
      name=domainsPrefix+"."+domainName;
    }
    Constructor<Domain<Ring>>[] ct=null;
    try{
      final Class<?> c = Class.forName(name);
      ct = (Constructor<Domain<Ring>>[]) c.getDeclaredConstructors();
    }
    catch(ClassNotFoundException e){
      System.err.println("Class with name "+name+" not found");
      return null;
    }
    Domain<Ring> d = null;
    if (ct.length == 1) {
      try{
        d = ct[0].newInstance();
      }
      catch(InstantiationException e){
        System.err.println(e);
        return null;
      }
      catch(IllegalAccessException e){
        System.err.println(e);
        return null;
      }
      catch(InvocationTargetException e){
        System.err.println(e);
        return null;
      }
    }
    return d;
  }
  /**
   * Get domain class name as string.
   *
   * @param d domain.
   * @return domain class name.
   */
  public static String getClassName(Domain<Ring> d){
    return d.getClass().getSimpleName();
  }

  /**
   * Returns string list of predefined domains.
   *
   * @return array of strings with domain names.
   */
  @SuppressWarnings("all")
  public static Vector<Domain<?>> getPredefinedDomains()
  {
    Vector<Domain<?>> result = new Vector<Domain<?>>();
    Reflections reflections = new Reflections(domainsPrefix);
    Set<Class<? extends Domain>> m = reflections.getSubTypesOf(Domain.class);
    for (Class<? extends Domain> c : m) {
      Domain<Ring> d = null;
      Constructor<Domain<Ring>>[] ct = (Constructor<Domain<Ring>>[]) c
          .getDeclaredConstructors();
      try {
        if (ct.length == 1) {
          d = ct[0].newInstance();
          if(!DomainFactory.isObsolete(d)){
            result.add((Domain<Ring>)d);
          }
        }
      }
      catch (InstantiationException e) {
        System.err.println(e);
        return null;
      }
      catch (IllegalAccessException e) {
        System.err.println(e);
        return null;
      }
      catch (InvocationTargetException e) {
        System.err.println(e);
        return null;
      }
    }
    // fixing not loading classes under webstart
    if (result.size()==0){
      result.add(new SatProp());
      result.add(new ReachPar());
      result.add(new MinTimeSeq());
      result.add(new SatOpp());
      result.add(new MinTimePar());
      result.add(new SatScenario());
      result.add(new DiffLMH());
      result.add(new PowerCons());
      result.add(new MinSkill());
      result.add(new DiffLMHE());
      result.add(new ProbSucc());
      result.add(new ReachSeq());
      result.add(new MinCost());
    }
    return result;
  }
}


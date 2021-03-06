﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Windows;
using System.Xml.Linq;

namespace Glasir
{
    public class FunctionEditor : Module
    {

        public String FunctionName
        {
            get;
            private set;
        }

        public String Function
        {
            get;
            private set;
        }

        public string FirstParam
        {
            get;
            private set;
        }

        public string SecondParam
        {
            get;
            private set;
        }

        public XMLFile File
        {
            get;
            private set;
        }

        public String L1
        {
            get;
            private set;
        }

        public String M1
        {
            get;
            private set;
        }

        public String H1
        {
            get;
            private set;
        }

        public String E1
        {
            get;
            private set;
        }

        public String L2
        {
            get;
            private set;
        }

        public String M2
        {
            get;
            private set;
        }

        public String H2
        {
            get;
            private set;
        }

        public String E2
        {
            get;
            private set;
        }

        public FunctionEditor()
        {
        }

        /// <summary>
        /// Create a new FunctionEditor object with the given properties
        /// </summary>
        /// <param name="f"></param>
        /// <param name="name"></param>
        /// <param name="fn"></param>
        /// <param name="firstP"></param>
        /// <param name="secondP"></param>
        /// <param name="l1"></param>
        /// <param name="m1"></param>
        /// <param name="h1"></param>
        /// <param name="e1"></param>
        /// <param name="l2"></param>
        /// <param name="m2"></param>
        /// <param name="h2"></param>
        /// <param name="e2"></param>
        public FunctionEditor(XMLFile f, String name, String fn, string firstP, string secondP, String l1, String m1, String h1, String e1, String l2, String m2, String h2, String e2)
        {
            L1 = l1;
            M1 = m1;
            H1 = h1;
            E1 = e1;
            L2 = l2;
            M2 = m2;
            H2 = h2;
            E2 = e2;
            File = f;
            FunctionName = name;
            fn = fn.Replace('.', ',');
            Function = fn;
            FirstParam = firstP;
            SecondParam = secondP;
        }

        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public XMLFile createResultingFile()
        {
            XMLFile codeXML = new XMLFile(File);
            XElement fn = (XElement) codeXML.XmlCode.FirstNode;
            IEnumerable<XElement> domains = fn.Elements("domain");
            String domainClass=null;
            foreach (XElement dom in domains)
            {
                
                if ((String)dom.Attribute("id") == FirstParam)
                {
                    domainClass = dom.Element("class").Value;
                }
            }
            fn.Add(new XElement("domain", 
                new XAttribute("id", FunctionName), 
                new XElement("class", domainClass), 
                new XElement("tool", "ADTool")));
            XElement elemRoot = fn.Element("node");
            searchAndChange(elemRoot);
            XMLFile resultFile = codeXML.createResultFile(1);
            return resultFile;
        }

        /// <summary>
        /// Send the resultat of the mathematical operation within the expression 
        /// </summary>
        /// <param name="expression"></param>
        /// <returns></returns>
        public static double Evaluate(string expression)
        {
            var loDataTable = new DataTable();
            var loDataColumn = new DataColumn("Eval", typeof(double), expression);
            loDataTable.Columns.Add(loDataColumn);
            loDataTable.Rows.Add(0);
            return (double)(loDataTable.Rows[0]["Eval"]);
        }

        /// <summary>
        /// Change the valuations of the given tree following the given formula. 
        /// If the given tree isn't a leaf but a simple node, call recursively the same algorithm on the subtrees of the given tree to search for leafs
        /// </summary>
        /// <param name="code"></param>
        /// <param name="swittcccchhhhh"></param>
        public void searchAndChange(XElement code, int swittcccchhhhh=0)
        {
            if (swittcccchhhhh == 1)
            {
                IEnumerable<XElement> elementsNode = code.Elements("node");
                foreach (XElement el in elementsNode)
                {
                    if ((String)el.Attribute("switchRole") == "yes")
                    {
                        searchAndChange(el, 0);
                    }
                    else{
                        searchAndChange(el, 1);
                    }
                }
                return;
            }

            if (code.Element("node") == null)
            {
                IEnumerable<XElement> elements = code.Elements("parameter");
                string c = null;
                string d = null;
                foreach (XElement par in elements)
                {
                    if ((string)par.Attribute("category") == "basic" && (string)par.Attribute("domainId") == FirstParam)
                    {
                        c = par.Value;
                    }
                    else if ((string)par.Attribute("category") == "basic" && (string)par.Attribute("domainId") == SecondParam)
                    {
                        d = par.Value;
                    }
                }

                if (c == "true") { c = "1"; }
                if (c == "false") {c = "0";}

                if (d == "true") {d = "1";}
                if (d == "false") {d = "0";}

                if(c == "L") { c = L1; }
                if(c=="M") {c = M1;}
                if(c == "H") { c = H1; }
                if(c=="E") {c = E1;}
                if(d=="L") {d = L2;}
                if(d=="M") {d = M2;}
                if(d=="H") {d = H2;}
                if(d=="E") {d = E2;}
                    
                if (c == "Infinity" || d == "Infinity")
                {
                    code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("category", "basic"), new XAttribute("domainId", FunctionName), "Infinity"));
                }
                else if (Function.Contains("/") && (d == "0" || d == "0.0"))
                {
                    code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("category", "basic"), new XAttribute("domainId", FunctionName), "Infinity"));
                }
                else if (c!=null && d!=null)
                {
                    double res = Evaluate(c + Function + d);
                    code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("category", "basic"), new XAttribute("domainId", FunctionName), res.ToString()));
                }
            return;
            }

            else
            {
                IEnumerable<XElement> elementsNode = code.Elements("node");
                foreach (XElement el in elementsNode)
                {
                    if ((String)el.Attribute("switchRole") == "yes")
                    {
                        IEnumerable<XElement> elements = code.Elements("parameter");
                        string c = null;
                        string d = null;
                        foreach (XElement par in elements)
                        {
                            if ((string)par.Attribute("category") == "basic" && (string)par.Attribute("domainId") == FirstParam)
                            {
                                c = par.Value;
                            }
                            else if ((string)par.Attribute("category") == "basic" && (string)par.Attribute("domainId") == SecondParam)
                            {
                                d = par.Value;
                            }
                        }

                        if (c == "true") { c = "1"; }
                        if (c == "false") { c = "0"; }

                        if (d == "true") { d = "1"; }
                        if (d == "false") { d = "0"; }

                        if (c == "L") { c = L1; }
                        if (c == "M") { c = M1; }
                        if (c == "H") { c = H1; }
                        if (c == "E") { c = E1; }
                        if (d == "L") { d = L2; }
                        if (d == "M") { d = M2; }
                        if (d == "H") { d = H2; }
                        if (d == "E") { d = E2; }

                        if (c == "Infinity" || d == "Infinity")
                        {
                            code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("category", "basic"), new XAttribute("domainId", FunctionName), "Infinity"));
                        }
                        else if (Function.Contains("/") && (d == "0" || d == "0.0"))
                        {
                            code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("category", "basic"), new XAttribute("domainId", FunctionName), "Infinity"));
                        }
                        else if (c != null && d != null)
                        {
                            double res = Evaluate(c + Function + d);
                            code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("category", "basic"), new XAttribute("domainId", FunctionName), res.ToString()));
                        }
                        searchAndChange(el, 1);
                        return;
                    }
                    else
                    {
                        searchAndChange(el);
                    }
                }
            }
        }

        public override XMLFile createResultingFile(XMLFile file) { return file; }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;

namespace Glasir
{
    public class Filter : Module
    {
        public Filter()
        {
        }
        
        public int FirstParam
        {
            get;
            private set;
        }

        public int SecondParam
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

        public Filter(XMLFile f, String name, String fn, int firstP, int secondP, String l1, String m1, String h1, String e1, String l2, String m2, String h2, String e2)
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
            FirstParam = firstP;
            SecondParam = secondP;
        }

        /*
        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public XMLFile createResultingFile()
        {

            XElement fn = (XElement) File.XmlCode.FirstNode;
            IEnumerable<XElement> domains = fn.Elements("domain");
            String domainClass = domains.ElementAt(FirstParam).Element("class").Value;
            fn.Add(new XElement("domain", 
                new XAttribute("id", FunctionName), 
                new XElement("class", domainClass), 
                new XElement("tool", "ADTool")));
            XElement elemRoot = fn.Element("node");
            Console.WriteLine("\n");
            searchAndChange(elemRoot);
            XMLFile resultFile = this.File.createResultFile();
            return resultFile;
        }

        public static double Evaluate(string expression)
        {
            var loDataTable = new DataTable();
            var loDataColumn = new DataColumn("Eval", typeof(double), expression);
            loDataTable.Columns.Add(loDataColumn);
            loDataTable.Rows.Add(0);
            return (double)(loDataTable.Rows[0]["Eval"]);
        }
        */

        /*
         * Change les valuations de la feuille XML qui lui ai passé en param, suivant les conditions définie par la fonction 
         * Si le noeud en en param n'est pas une feuille mais un noeud de l'arbre, procède à un appel récursif sur ses fils et ses noeuds frères
         */

        /*
        public void searchAndChange(XElement code)
        {
            Console.WriteLine(code);
            Console.WriteLine("\n");

            if (code.Element("node") == null || (String) code.Element("node").Attribute("switchRole") == "yes")
            {
                IEnumerable<XElement> elements = code.Elements("parameter");
       
                    string c = elements.ElementAt(FirstParam).Value;
                    string d = elements.ElementAt(SecondParam).Value;

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
                        code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("domainId", FunctionName), "Infinity"));
                    }
                    else if (Function.Contains("/") && (d == "0" || d == "0.0"))
                    {
                        code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("domainId", FunctionName), "Infinity"));
                    }
                    else
                    {
                        double res = Evaluate(c + Function + d);
                        code.Element("label").AddAfterSelf(new XElement("parameter", new XAttribute("domainId", FunctionName), res.ToString()));
                    }
                    
                
                Console.WriteLine("a");
                Console.WriteLine(code);
                return;
            }
            else
            {
                IEnumerable<XElement> elementsNode = code.Elements("node");
                foreach (XElement el in elementsNode)
                {
                    if ((String) el.Attribute("switchRole")!="yes")
                    {
                        Console.WriteLine("b");
                        Console.WriteLine(el);
                        searchAndChange(el);
                    }
                }
            }
        }

        public override XMLFile createResultingFile(XMLFile file) { return file; }
    }
          */

        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public override XMLFile createResultingFile(XMLFile fileName)
        {
            throw new NotImplementedException();
        }
    }
}

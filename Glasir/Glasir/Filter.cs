using System;
using System.Collections.Generic;
using System.Data;
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
        
        public int DomainParam
        {
            get;
            private set;
        }

        public XMLFile File
        {
            get;
            private set;
        }

        public double Max
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

        public Filter(XMLFile f, int domain, String max, String l1, String m1, String h1, String e1, String l2, String m2, String h2, String e2)
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
            DomainParam = domain;
            Max= Convert.ToDouble(max);
        }


        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public XMLFile createResultingFile()
        {

            XElement fn = (XElement) File.XmlCode.FirstNode;
            IEnumerable<XElement> domains = fn.Elements("domain");
            XElement elemRoot = fn.Element("node");
            Console.WriteLine("\n");
            searchAndChange(elemRoot, Max);
            XMLFile resultFile = this.File.createResultFile(2);
            return resultFile;
        }

        /*
         * Change les valuations de la feuille XML qui lui ai passé en param, suivant les conditions définie par la fonction 
         * Si le noeud en en param n'est pas une feuille mais un noeud de l'arbre, procède à un appel récursif sur ses fils et ses noeuds frères
         */

        public void searchAndChange(XElement code, double m)
        {
            Console.WriteLine(code);
            Console.WriteLine("\n");

            if (code.Element("node") != null && (String)code.Element("node").Attribute("switchRole") == "yes")
            {
                IEnumerable<XElement> subnodes = code.Elements("node");
                foreach (XElement elem in subnodes)
                {
                    searchAndChange(elem, m);
                }
            }

            else
            {
                IEnumerable<XElement> elements = code.Elements("parameter");
       
                    string value = elements.ElementAt(DomainParam).Value;

                    if (value == "true") { value = "1"; }
                    if (value == "false") {value = "0";}

                    if(value == "L") { value = L1; }
                    if(value=="M") {value = M1;}
                    if(value == "H") { value = H1; }
                    if(value=="E") {value = E1;}
                    
                    if (value == "Infinity")
                    {
                        code.Remove();
                    }
                    else
                    {
                        if (Evaluate(value+"-"+m) > 0)
                        {
                                code.Remove();
                        }
                        else if (code.Element("node") != null) {
                            IEnumerable<XElement> subnodes = code.Elements("node");
                            foreach(XElement elem in subnodes) {
                                searchAndChange(elem, m); 
                            }
                        }
                        else if ((String)code.Attribute("refinement") == "conjunctive")
                        {

                            IEnumerable<XElement> elements2 = code.Elements("parameter");
                            string value2 = elements2.ElementAt(DomainParam).Value;


                            IEnumerable<XElement> elementsNode = code.Elements("node");
                            foreach (XElement el in elementsNode)
                            {
                                if ((String)el.Attribute("switchRole") != "yes")
                                {
                                    IEnumerable<XElement> elem = el.Elements("parameter");
                                    string val = elem.ElementAt(DomainParam).Value;
                                    double maxtemp = Evaluate(val + " - " + value2);
                                    maxtemp = m + maxtemp;
                                    Console.WriteLine("b");
                                    Console.WriteLine(el);
                                    searchAndChange(el, maxtemp);
                                }
                            }
                        }

                        else
                        {

                            IEnumerable<XElement> elementsNode = code.Elements("node");
                            foreach (XElement el in elementsNode)
                            {
                                if ((String)el.Attribute("switchRole") != "yes")
                                {
                                    Console.WriteLine("b");
                                    Console.WriteLine(el);
                                    searchAndChange(el, m);
                                }
                            }
                        }
                    }
                    
                
                Console.WriteLine("a");
                Console.WriteLine(code);
                return;
            }

            
        }

        public static double Evaluate(string expression)
        {
            var loDataTable = new DataTable();
            var loDataColumn = new DataColumn("Eval", typeof(double), expression);
            loDataTable.Columns.Add(loDataColumn);
            loDataTable.Rows.Add(0);
            return (double)(loDataTable.Rows[0]["Eval"]);
        }

        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public override XMLFile createResultingFile(XMLFile file) { return file; }
    
    }
}

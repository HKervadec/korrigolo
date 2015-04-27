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

        public List<XElement> listDelete
        {
            get;
            private set;
        }

        public string DomainParam
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


        public Filter(XMLFile f, string domain, String max)
        {
            File = f;
            DomainParam = domain;
            max = max.Replace('.',',');
            if (max == "L") { max = "1"; }
            else if (max == "M") { max = "2"; }
            else if (max == "H") { max = "3"; }
            else if (max == "E") { max = "4"; }
            Max= Convert.ToDouble(max);
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
            XElement elemRoot = fn.Element("node");
            Console.WriteLine("\n");
            listDelete = new List<XElement>();
            searchAndChange(elemRoot, Max);
            foreach(XElement el in listDelete) {
                el.Remove();
            }
            XMLFile resultFile = codeXML.createResultFile(2);
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

            IEnumerable<XElement> parameters = code.Elements("parameter");
            string value=null;
            foreach (XElement par in parameters)
            {
                if ((string)par.Attribute("category") == "derived" && (string)par.Attribute("domainId") == DomainParam)
                {
                    value = par.Value;
                }
            }
            if (value == null)
            {
                foreach (XElement par in parameters)
                {
                    if ((string)par.Attribute("category") == "basic" && (string)par.Attribute("domainId") == DomainParam)
                    {
                        value = par.Value;
                    }
                }
            }

            if (value == "true") {value = "1";}
            if (value == "false") {value = "0";}

            if(value == "L") {value = "1";}
            if(value == "M") {value = "2";}
            if(value == "H") {value = "3";}
            if(value == "E") {value = "4";}

            if (value == "Infinity")
            {
                listDelete.Add(code);
                return;
            }

            if (code.Element("node") == null)
            {
                if (Evaluate(m + "-" + value) < 0.0) { listDelete.Add(code); } 
                Console.WriteLine("a");
                Console.WriteLine(code);
                return;
            }

            else {

                if (Evaluate(m + "-" + value) < 0.0)
                {
                    listDelete.Add(code);
                    //code.Remove();
                    Console.WriteLine("a");
                    Console.WriteLine(code);
                    return;
                }
                else {
                    if ((String)code.Attribute("refinement") == "conjunctive")
                    {
                        IEnumerable<XElement> subNodes = code.Elements("node");
                        foreach (XElement el in subNodes)
                        {
                            IEnumerable<XElement> subParams = el.Elements("parameter");
                            string subVal = null;
                            foreach (XElement par in subParams)
                            {
                                if ((string)par.Attribute("category") == "derived" && (string)par.Attribute("domainId") == DomainParam)
                                {
                                    subVal = par.Value;
                                }
                            }
                            if (subVal == null)
                            {
                                foreach (XElement par in subParams)
                                {
                                    if ((string)par.Attribute("category") == "basic" && (string)par.Attribute("domainId") == DomainParam)
                                    {
                                        subVal = par.Value;
                                    }
                                }
                            }
                            if (subVal == "true") { subVal = "1"; }
                            if (subVal == "false") { subVal = "0"; }

                            if (subVal == "L") { subVal = "1"; }
                            if (subVal == "M") { subVal = "2"; }
                            if (subVal == "H") { subVal = "3"; }
                            if (subVal == "E") { subVal = "4"; }

                            if (subVal == "Infinity")
                            {
                                listDelete.Add(el);
                                return;
                            }
                            else
                            {
                                double maxtemp = Evaluate(subVal + " - " + value);
                                maxtemp = m + maxtemp;
                                Console.WriteLine("b");
                                Console.WriteLine(el);
                                searchAndChange(el, maxtemp);
                            }
                        }
                    }
                    else
                    {
                        IEnumerable<XElement> subNodes = code.Elements("node");
                        foreach (XElement el in subNodes)
                        {
                            Console.WriteLine("b");
                            Console.WriteLine(el);
                            searchAndChange(el, m);
                        }
                    }
                }
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

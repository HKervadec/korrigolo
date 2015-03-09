using System;
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

        public FunctionEditor()
        {
        }

        public FunctionEditor(XMLFile f, String name, String fn, int firstP, int secondP)
        {
            File = f;
            FunctionName = name;
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
            XMLFile resultFile = File.createResultFile();
            return resultFile;
        }

        static double Evaluate(string expression)
        {
            var loDataTable = new DataTable();
            var loDataColumn = new DataColumn("Eval", typeof(double), expression);
            loDataTable.Columns.Add(loDataColumn);
            loDataTable.Rows.Add(0);
            return (double)(loDataTable.Rows[0]["Eval"]);
        }

        public void searchAndChange(XElement code)
        {
            Console.WriteLine(code);
            Console.WriteLine("\n");

            if (code.Element("node") == null)
            {
                IEnumerable<XElement> elements = code.Elements("parameter");
                try
                {
                    string c = elements.ElementAt(FirstParam).Value;
                    string d = elements.ElementAt(SecondParam).Value;
                    double res = Evaluate(c+Function+d);
                    code.Add(new XElement("parameter", new XAttribute("domainId", "minCostTime"), res.ToString()));
                }
                catch (FormatException)
                {
                    Console.WriteLine("Unable to convert to a Double.");
                }
                catch (OverflowException)
                {
                    Console.WriteLine("is outside the range of a Double.");
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
                    Console.WriteLine("b");
                    Console.WriteLine(el);
                    searchAndChange(el);
                }
            }
        }

        public override XMLFile createResultingFile(XMLFile file) { return file; }
    }
}

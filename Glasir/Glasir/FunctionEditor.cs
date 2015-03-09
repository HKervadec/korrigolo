using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
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

        public FunctionEditor()
        {
        }

        public FunctionEditor(String name)
        {
            FunctionName = name;
        }

        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public override XMLFile createResultingFile(XMLFile file)
        {

            XElement fn = (XElement) file.XmlCode.FirstNode;
            fn.Add(new XElement("domain", 
                new XAttribute("id", FunctionName), 
                new XElement("class", "lu.uni.adtool.domains.predefined.MinCost"), 
                new XElement("tool", "ADTool")));
            XElement elemRoot = fn.Element("node");
            Console.WriteLine("\n");
            //Console.WriteLine(elemRoot);
            searchAndChange(elemRoot);


            // tests //
            /*
            Console.WriteLine(file.XmlCode);
            XElement rm = (XElement) file.XmlCode.FirstNode;
            XElement rd = (XElement) rm.FirstNode;
            Console.WriteLine("\n");
            Console.WriteLine(rm);
            Console.WriteLine("\n");
            Console.WriteLine(rd);
            XElement rm2 = (XElement) rm.Element("node");
            XElement rm3 = (XElement) rm2.Element("node");
            Console.WriteLine("\n");
            Console.WriteLine(rm3);
            rm3.Remove();
            Console.WriteLine("\n");
            Console.WriteLine(file.XmlCode);
            XElement elem = (XElement)elemRoot.Element("parameter");
            Console.WriteLine(elem);*/
            // fin tests //

            XMLFile resultFile = file.createResultFile();
            return resultFile;
        }

        public void searchAndChange(XElement code)
        {
            Console.WriteLine(code);
            Console.WriteLine("\n");

            if (code.Element("node") == null)
            {
                IEnumerable<XElement> elements = code.Elements("parameter");
                String a = elements.First().Value;
                String b = elements.ElementAt(1).Value;
                code.Add(new XElement("parameter", new XAttribute("domainId", "minCostTime"), (a + b).ToString()));
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
            /*
            Console.WriteLine(code);
            Console.WriteLine("\n");
            if (code.Element("node") == null)
            {
                IEnumerable<XElement> elements = code.Elements("parameter");
                String a = elements.First().Value;
                String b = elements.ElementAt(1).Value;
                code.Add(new XElement("parameter", new XAttribute("domainId", "minCostTime"), (a + b).ToString()));
                Console.WriteLine("a");
                Console.WriteLine(code);
                return;
            }

            while (code.NextNode != null)
            {
                if (code.Element("node") != null)
                {
                    searchAndChange(code.Element("node"));
                    code = (XElement)code.NextNode;
                }
                Console.WriteLine("b");
                Console.WriteLine(code);
            }*/
        }
    }
}

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Linq;

namespace Glasir
{
    public class XMLFile
    {
        

        public string FileName
        {
            get;
            private set;
        }

        public XDocument XmlCode
        {
            get;
            private set;
        }

        public XMLFile(string path)
        {
            FileName = path;
            Console.WriteLine("XML.load " + path);
            XmlCode = XDocument.Load(path);
        }

        public XMLFile(string xmlfile, XDocument code)
        {
            FileName = xmlfile;
            XmlCode = code;
        }

        public XMLFile()
        {
            throw new NotImplementedException();
        }

        public string getXMLCodeFromFile()
        {
            return XmlCode.ToString(); 
        }

        public XMLFile createResultFile()
        {
            string newName = Path.GetFileNameWithoutExtension(this.FileName) + ".1.xml";
            XMLFile resultingFile = new XMLFile(newName, this.XmlCode);
            resultingFile.XmlCode.Save(newName);
            return resultingFile;
        }

        public List<String> getDomains()
        {
            XElement fn = (XElement) this.XmlCode.FirstNode;
            IEnumerable<XElement> domains = fn.Elements("domain");
            int i = domains.Count();
            List<String> res = new List<String>();
            for (int j = 0; j < i; j++)
            {
                res.Add(domains.ElementAt(j).Attribute("id").Value);
            }
            return res;
        }
        
    }
}

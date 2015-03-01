using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Linq;

namespace Glasir
{
    public class XMLFile
    {
        

        public string fileName
        {
            get;
            private set;
        }

        public XDocument xmlcode
        {
            get;
            private set;
        }

        public void buildFile(string xmlfile)
        {
            fileName = xmlfile;
            xmlcode = XDocument.Load(xmlfile);
        }

        public XMLFile(string xmlfile)
        {
            buildFile(xmlfile);
        }

        public string getXMLCodeFromFile()
        {
            return xmlcode.ToString();
        }

        
    }
}

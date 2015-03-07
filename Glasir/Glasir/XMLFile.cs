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

        public XMLFile(string xmlfile)
        {
            FileName = xmlfile;
            string path = "../../Trees/" + FileName +".xml";
            XmlCode = XDocument.Load(path);
        }

        public XMLFile()
        {
            // TODO: Complete member initialization
        }

        public string getXMLCodeFromFile()
        {
            return XmlCode.ToString();
        }

        
    }
}

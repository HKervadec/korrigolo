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

        /// <summary>
        /// Create a new XMLFile with the given parameter
        /// </summary>
        /// <param name="path"></param>
        public XMLFile(string path)
        {
            FileName = path;
            Console.WriteLine("XML.load " + path);
            try
            {
                XmlCode = XDocument.Load(path);
            }
            catch (System.IO.FileNotFoundException)
            {
                MainWindow.messageBox("Unable to open " + path+".\nCheck if the file exists and if you have the rights to access it.\n");
            }
            
        }

        /// <summary>
        /// Create a new XMLFile with the given parameter
        /// </summary>
        /// <param name="file"></param>
        public XMLFile(XMLFile file)
        {
            FileName = file.FileName;
            XmlCode = new XDocument(file.XmlCode);
        }

        /// <summary>
        /// Create a new XMLFile object with the given parameters
        /// </summary>
        /// <param name="xmlfile"></param>
        /// <param name="code"></param>
        public XMLFile(string xmlfile, XDocument code)
        {
            FileName = xmlfile;
            XmlCode = code;
        }

        /// <summary>
        /// Tell if the XMLTree is empty or not
        /// </summary>
        /// <returns></returns>
        public Boolean isEmpty()
        {
            XElement fn = (XElement) this.XmlCode.FirstNode;
            XElement elemRoot = fn.Element("node");
            if(elemRoot == null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public XMLFile()
        {
            throw new NotImplementedException();
        }


        /// <summary>
        /// Return the XMLCode of the file
        /// </summary>
        /// <returns></returns>
        public string getXMLCodeFromFile()
        {
            return XmlCode.ToString(); 
        }

        /// <summary>
        /// Create the resulting file of a module
        /// </summary>
        /// <param name="a"></param>
        /// <returns></returns>
        public XMLFile createResultFile(int a)
        {
            string newName;
            if (a == 1)
            {
                newName = Path.GetFileNameWithoutExtension(this.FileName) + ".functEdit.xml";
            }
            else if (a == 2)
            {
                newName = Path.GetFileNameWithoutExtension(this.FileName) + ".Filter.xml";
            }
            else
            {
                newName = Path.GetFileNameWithoutExtension(this.FileName) + ".Optimizer.xml";
            }
            XMLFile resultingFile = new XMLFile(newName, this.XmlCode);
            resultingFile.XmlCode.Save(newName);
            return resultingFile;
        }

        /// <summary>
        /// get the list of the domains used in the XMLFile
        /// </summary>
        /// <returns></returns>
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

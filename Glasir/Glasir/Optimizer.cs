using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;

namespace Glasir
{
    public class Optimizer : Module
    {
        public Optimizer()
        {
            
        }

        public XMLFile File
        {
            get;
            private set;
        }

        public String domain
        {
            get;
            private set;
        }

        public String max
        {
            get;
            private set;
        }

        /// <summary>
        /// Create a new Optimizer object with the given properties
        /// </summary>
        /// <param name="f"></param>
        /// <param name="item"></param>
        public Optimizer(XMLFile f, String item)
        {
            File = f;
            domain = item;
        }

        /// <summary>
        /// Optimize the given tree by the given parameter 
        /// </summary>
        /// <returns></returns>
        public XMLFile createResultingFile()
        {
            XMLFile codeXML = new XMLFile(File);
            XElement fn = (XElement)codeXML.XmlCode.FirstNode;
            XElement elemRoot = fn.Element("node");
            IEnumerable<XElement> listParams = elemRoot.Elements("parameter");
            foreach (XElement dom in listParams)
            {
                if ((String)dom.Attribute("domainId") == domain)
                {
                    max = dom.Value;
                }
            }
            Filter filt = new Filter(File, domain, max);
            XMLFile newFile = filt.createResultingFile();
            XMLFile resultFile = newFile.createResultFile(3);
            return resultFile;
        }


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

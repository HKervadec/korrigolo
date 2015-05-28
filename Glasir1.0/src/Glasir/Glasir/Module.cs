using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Glasir
{
    public abstract class Module
    {
        
        /// <summary>
        /// return xml code of file f
        /// </summary>
        /// <param name="f"></param>
        /// <returns></returns>
        public string openFile(XMLFile file)
        {
            return file.getXMLCodeFromFile();
        }

        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="file"></param>
        /// <param name="xmlCode"></param>
        public abstract XMLFile createResultingFile(XMLFile file);

    }
}

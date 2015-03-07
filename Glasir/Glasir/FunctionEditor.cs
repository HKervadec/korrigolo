using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Glasir
{
    public class FunctionEditor : Module
    {
        public FunctionEditor()
        {
            
        }

        /// <summary>
        /// create the resulting tree of the function
        /// </summary>
        /// <param name="fileName"></param>
        /// <param name="xmlCode"></param>
        public override XMLFile createResultingFile(XMLFile file)
        {







            string newName = file.FileName+"1";
            XMLFile resultingFile = new XMLFile(newName);
            resultingFile.XmlCode.Save("../../Trees/" + newName + ".xml");
            return resultingFile;
        }
    }
}

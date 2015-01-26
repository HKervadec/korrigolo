using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Glasir
{
    public class XMLFile
    {
        public string code
        {
            get;
            private set;
        }

        public void buildFile()
        {
            throw new System.NotImplementedException();
        }

        public XMLFile(string xmlCode)
        {
            this.code = xmlCode;
        }

        
    }
}

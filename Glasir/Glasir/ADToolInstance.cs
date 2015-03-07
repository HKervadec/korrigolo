using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;

namespace Glasir
{
    public class ADToolInstance
    {
        public static ADToolInstance foregroundInstance
        {
            get;
            set;
        }

        public Process process
        {
            get;
            private set;
        }

        public XMLFile file
        {
            get;
            private set;
        }

        /// <summary>
        /// ADToolInstance constructor
        /// </summary>
        /// <param name="fileName"></param>
        public ADToolInstance(string fileName)
        {
            Process proc = new Process();
            proc.StartInfo.FileName = "ADTool-1.4-jar-with-dependencies.jar";
            proc.StartInfo.WorkingDirectory = Directory.GetCurrentDirectory();
            proc.StartInfo.Arguments = fileName;
            proc.Start();
            this.process = proc;
            
        }

        public ADToolInstance()
        {
            Process proc = new Process();
            proc.StartInfo.FileName = "ADTool-1.4-jar-with-dependencies.jar";
            proc.StartInfo.WorkingDirectory = Directory.GetCurrentDirectory();
            proc.Start();
            this.process = proc;
            this.file = new XMLFile();
        }

    }
}

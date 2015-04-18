﻿using System;
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
            proc.StartInfo.Arguments = "--viewmode "+fileName;
            proc.Start();
            proc.StartInfo.Arguments = fileName;
            Console.WriteLine("Started "+proc.StartInfo.Arguments);
            this.process = proc;
            file = new XMLFile(fileName);
            foregroundInstance = this;
        }
        

        internal void close()
        {
            try
            {
                this.process.Kill();
                this.process = null;
                this.file = null;
            }
            catch (InvalidOperationException)
            {

            }
            
        }
    }
}

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;

namespace Glasir
{
    public class BigGlasir
    {

        public Filter Filter
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public Optimizer Optimizer
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }


        public FunctionEditor FunctionEditor
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public List<Process> ADToolInstances
        {
            get;
            set;
        }


        /// <summary>
        /// launch an instance of ADTool containing an ADTree
        /// </summary>
        /// <returns></returns>
        public void launchADToolInstance()
        {
            // add ADTool and adtree3.adt to bin/Debug if this don't work mdfk
            Process process = new Process();
            process.StartInfo.FileName = "ADTool-1.4-jar-with-dependencies.jar";
            process.StartInfo.WorkingDirectory = Directory.GetCurrentDirectory();
            process.StartInfo.Arguments = "adtree3.adt";
            process.Start();
            
            this.ADToolInstances.Add(process);
        }
    }
}

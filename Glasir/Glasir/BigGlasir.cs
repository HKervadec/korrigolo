using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.InteropServices;
using System.Runtime.Serialization.Formatters.Binary;

namespace Glasir
{
    
    public class BigGlasir
    {

        public BigGlasir()
        {
            this.ADToolInstances = new List<ADToolInstance>();
            this.FunctionEditor = new FunctionEditor();
            this.Optimizer = new Optimizer();
            this.Filter = new Filter();
            this.projectName = "";
        }

        public string projectName
        {
            get;
            set;
        }

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

        public System.Collections.Generic.List<ADToolInstance> ADToolInstances
        {
            get;
            set;
        }

        public TemplateLibrary templateLibrary
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        


        
        /// <summary>
        /// launch an instance of ADTool containing an ADTree
        /// </summary>
        /// <returns></returns>
        public void launchADToolInstance(string fileName)
        {
            this.ADToolInstances.Add(new ADToolInstance(fileName));
        }

        /// <summary>
        /// load a template
        /// </summary>
        public void loadTemplate()
        {
            throw new System.NotImplementedException();
        }

        
       
        internal void openProject(string filename)
        {
            Stream fileStream = File.OpenRead(filename);
            BinaryFormatter deserializer = new BinaryFormatter();
            List<string> resultOfDeserial = (List<string>) deserializer.Deserialize(fileStream);
            fileStream.Close();
            this.projectName = filename;
            foreach(string adtfile in resultOfDeserial)
            {
                this.launchADToolInstance(adtfile);
            }
        }


        /// <summary>
        /// save a project
        /// </summary>
        public void saveProject()
        {
            Stream fileStream = File.Create(this.projectName);
            BinaryFormatter serializer = new BinaryFormatter();
            List<string> adtFiles = new List<string>();
            foreach (ADToolInstance adt in this.ADToolInstances)
            {
                adtFiles.Add(adt.process.StartInfo.Arguments);
            }
            serializer.Serialize(fileStream, adtFiles);
            
            fileStream.Close();
        }

        /// <summary>
        /// close adtool instances
        /// </summary>
        internal void closeProject()
        {
            foreach (ADToolInstance adt in ADToolInstances)
            {
                adt.close();
            }
            this.ADToolInstances = new List<ADToolInstance>();
        }

        /// <summary>
        /// set the i-th adtool instance as the foreground instance
        /// </summary>
        /// <param name="i"></param>
        internal void setForegroundInstance(int i)
        {
            ADToolInstance.foregroundInstance = this.ADToolInstances[i];
        }

    }
}

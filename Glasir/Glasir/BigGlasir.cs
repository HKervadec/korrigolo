using System;
using System.Collections.Generic;
using System.IO;
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
            if (File.Exists(filename))
            {
                Stream fileStream = File.OpenRead(filename);
                BinaryFormatter deserializer = new BinaryFormatter();
                string resultOfDeserial = (string) deserializer.Deserialize(fileStream);
                fileStream.Close();
                Console.WriteLine(resultOfDeserial);
            }
            else
            {
                Console.WriteLine("Raté.");
            }
        }


        /// <summary>
        /// save a project
        /// </summary>
        public void saveProject()
        {
            Stream fileStream = File.Create(this.projectName);
            BinaryFormatter serializer = new BinaryFormatter();
            foreach (ADToolInstance adt in this.ADToolInstances)
            {
                Console.WriteLine(adt.process.StartInfo.Arguments);
                //serializer.Serialize(fileStream, adt.process.StartInfo.Arguments);
            }
            
            fileStream.Close();
        }

        /// <summary>
        /// close adtool instances
        /// </summary>
        internal void closeInstances()
        {
            foreach (ADToolInstance adt in ADToolInstances)
            {
                adt.close();
            }
            this.ADToolInstances = null;
        }
    }
}

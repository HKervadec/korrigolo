using System;
using System.Collections.Generic;


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

        public List<ADToolInstance> ADToolInstances
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
        public void launchADToolInstance()
        {
            // add ADTool and adtree3.adt to bin/Debug if this don't work mdfk
            

            ADToolInstance newADToolInstance = new ADToolInstance("adtree3.adt");
            this.ADToolInstances.Add(newADToolInstance);
        }

        public void loadTemplate()
        {
            throw new System.NotImplementedException();
        }
    }
}

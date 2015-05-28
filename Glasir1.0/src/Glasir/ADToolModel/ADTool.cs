using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    public class ADTool
    {

        public List<ADTree> adtrees
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public HistoryManager historyManager
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public ADTreeTransfertHandler transfertHandler
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public void addChild(ADTree adt)
        {
            historyManager.storeAndExecute(new AddChildEdit(adt));
        }

        public void changeLabel(ADTree adt)
        {
            historyManager.storeAndExecute(new ChangeLabelEdit(adt));
        }

        public void addDomain(ADTree adt)
        {
            historyManager.storeAndExecute(new AddDomainEdit(adt));
        }
    }
}

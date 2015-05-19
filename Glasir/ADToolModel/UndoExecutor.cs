using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    public class UndoExecutor
    {
        public IEnumerable<ADTreeNode> queue
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public int memoryLimit
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public void undo()
        {
            throw new System.NotImplementedException();
        }

        public void saveTreeState()
        {
            throw new System.NotImplementedException();
        }
    }
}

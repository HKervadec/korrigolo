using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    public abstract class AbstractCanvasHandler : CanvasHandler
    {
        public ADTreeCanvas canvas
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public ADTreeTransferHandler transferHandler
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public UndoExecutor undoExecutor
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }
    }
}

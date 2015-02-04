using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    public class UndoManager
    {
        public List<UndoableEdit> history
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        public void addEdit(UndoableEdit edit)
        {
            throw new System.NotImplementedException();
        }

        public void canRedo()
        {
            throw new System.NotImplementedException();
        }

        public void canUndo()
        {
            throw new System.NotImplementedException();
        }

        public void undo()
        {
            throw new System.NotImplementedException();
        }

        public void redo()
        {
            throw new System.NotImplementedException();
        }
    }
}

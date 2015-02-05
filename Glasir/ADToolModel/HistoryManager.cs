using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    public class HistoryManager
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

        /// <summary>
        /// already defined in Java
        /// </summary>
        /// <param name="edit"></param>
        private void addEdit(UndoableEdit edit)
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

        /// <summary>
        /// implementation for this edit
        /// </summary>
        /// <param name="addChildEdit"></param>
        internal void storeAndExecute(AddChildEdit addChildEdit)
        {
            this.history.Add(addChildEdit);
            addChildEdit.execute();
        }

        /// <summary>
        /// idem
        /// </summary>
        /// <param name="changeLabelEdit"></param>
        internal void storeAndExecute(ChangeLabelEdit changeLabelEdit)
        {
            this.history.Add(changeLabelEdit);
            changeLabelEdit.execute();
        }

        /// <summary>
        /// idem
        /// </summary>
        /// <param name="addDomainEdit"></param>
        internal void storeAndExecute(AddDomainEdit addDomainEdit)
        {
            this.history.Add(addDomainEdit);
            addDomainEdit.execute();
        }
    }
}

﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    public class AddDomainEdit : UndoableEdit
    {
        private ADTree adt;
    
        public void execute()
        {
            throw new NotImplementedException();
        }

        public void undo()
        {
            throw new NotImplementedException();
        }


        public void redo()
        {
            throw new NotImplementedException();
        }

        public void canUndo()
        {
            throw new NotImplementedException();
        }

        public void canRedo()
        {
            throw new NotImplementedException();
        }

        public AddDomainEdit(ADTree adt)
        {
            this.adt = adt;
        }
    }
}

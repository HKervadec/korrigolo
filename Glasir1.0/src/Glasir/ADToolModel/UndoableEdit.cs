using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    // http://docs.oracle.com/javase/7/docs/api/javax/swing/undo/UndoableEdit.html
    public interface UndoableEdit
    {
        void undo();

        void redo();

        void canUndo();

        void canRedo();

        void execute();
    }
}

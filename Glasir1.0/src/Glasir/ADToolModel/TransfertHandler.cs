using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ADToolModel
{
    //http://www.coderanch.com/t/339663/GUI/java/JTrees-Drag-Drop
    public interface TransfertHandler
    {
        void getCopyAction();

        void getCutAction();

        void getPasteAction();
    }
}

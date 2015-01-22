using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Interop;

namespace Glasir
{
    public class ADToolHost : HwndHost
    {
        IntPtr hwndControl;
        IntPtr hwndHost;
        int hostHeight, hostWidth;

        internal const int
        WS_CHILD = 0x40000000,
        WS_VISIBLE = 0x10000000,
        LBS_NOTIFY = 0x00000001,
        HOST_ID = 0x00000002,
        LISTBOX_ID = 0x00000001,
        WS_VSCROLL = 0x00200000,
        WS_BORDER = 0x00800000;

        /// <summary>
        /// constructor
        /// </summary>
        /// <param name="height"></param>
        /// <param name="width"></param>
        public ADToolHost(double height, double width)
        {
            hostHeight = (int)height;
            hostWidth = (int)width;
        }
    }
}

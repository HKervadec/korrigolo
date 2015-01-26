using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Glasir
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        public BigGlasir Glasir
        {
            get;
            set;
        }

        private void launchADTool(object sender, RoutedEventArgs e)
        {
            Glasir.launchADToolInstance();
        }

        


        /*
        [DllImport("user32.dll", SetLastError = true)]
        static extern IntPtr SetParent(IntPtr hWndChild, IntPtr hWndNewParent);

        private void niktamer(object sender, RoutedEventArgs e)
        {
            Process process = new Process();
            process.StartInfo.FileName = "C:\\Program Files (x86)\\Notepad++\\notepad++.exe";
            
            process.StartInfo.WorkingDirectory = Directory.GetCurrentDirectory() ;
            
            
            process.Start();

            // Wait for process to be created and enter idle condition
            process.WaitForInputIdle();

            // Get the main handle
            IntPtr _appWin = process.MainWindowHandle;
                
            // Put it into this form
            var helper = new WindowInteropHelper(Window.GetWindow(ADToolZone));
            SetParent(_appWin, helper.Handle);

               
        }
        */

        

    }
}

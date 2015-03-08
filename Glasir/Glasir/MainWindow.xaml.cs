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
using System.Xml.Linq;

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

        /// <summary>
        /// constructor
        /// </summary>
        public MainWindow()
        {   
            InitializeComponent();
            this.Glasir = new BigGlasir();
        }


        private void functionEdition(object sender, RoutedEventArgs e)
        {
            XMLFile file = new XMLFile("test3");
            FunctionEditor functEdit = new FunctionEditor("minCostTime");
            functEdit.createResultingFile(file);
        }

        private void openFile(object sender, RoutedEventArgs e)
        {
            // Create OpenFileDialog 
            Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();

            // Set filter for file extension and default file extension
            dlg.InitialDirectory = System.IO.Directory.GetCurrentDirectory();
            dlg.DefaultExt = ".adt";
            dlg.Filter = "ADT Files|*.adt;*.xml|All Files|*.*";

            // Display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();

            // Get the selected file name and display in a TextBox 
            if (result == true)
            {
                // Open document 
                string filename = dlg.FileName;
                Glasir.launchADToolInstance(filename);
            }
        }

        

    }
}

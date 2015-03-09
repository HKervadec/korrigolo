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

        


        private void newProject(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("hueeeheee ça marche pô");
        }

        private void openProject(object sender, RoutedEventArgs e)
        {
            // Create OpenFileDialog 
            Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();

            // Set filter for file extension and default file extension
            dlg.InitialDirectory = System.IO.Directory.GetCurrentDirectory();
            dlg.DefaultExt = ".glpf";
            dlg.Filter = "Glasir Project Files|*.glpf;|All Files|*.*";

            // Display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();

            if (result == true)
            {
                // Open project
                Glasir.openProject(dlg.FileName);
            }
        }

        
        private void saveProject(object sender, RoutedEventArgs e)
        {
            if (this.Glasir.projectName == "")
            {
                this.saveProjectAs(sender, e);
            }
            else
            {
                this.Glasir.saveProject();
            }
        }

        private void saveProjectAs(object sender, RoutedEventArgs e)
        {
            // Create OpenFileDialog 
            Microsoft.Win32.SaveFileDialog dlg = new Microsoft.Win32.SaveFileDialog();

            // Set filter for file extension and default file extension
            dlg.InitialDirectory = System.IO.Directory.GetCurrentDirectory();
            dlg.AddExtension = true;
            dlg.DefaultExt = ".glpf";
            dlg.Filter = "Glasir Project Files|*.glpf";
            
            
            // Display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();

            if (result == true)
            {
                this.Glasir.projectName = dlg.FileName;
                this.Title = dlg.SafeFileName + " - Glasir";
                this.Glasir.saveProject();
            }
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

            if (result == true)
            {
                // Open document 
                string filename = dlg.FileName;
                Glasir.launchADToolInstance(filename);
            }
        }

        private void exit(object sender, RoutedEventArgs e)
        {
            this.Glasir.closeInstances();
        }

        private void newFile(object sender, RoutedEventArgs e)
        {
            this.Glasir.ADToolInstances.Add(new ADToolInstance());
        }

    }
}

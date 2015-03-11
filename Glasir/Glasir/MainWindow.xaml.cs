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
            FunctionEditor functEdit = new FunctionEditor(file, "minCostTime", "+2*", 0, 1);
            functEdit.createResultingFile();
        }

        

        /// <summary>
        /// create a new project
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void newProject(object sender, RoutedEventArgs e)
        {
            this.closeProject(sender, e);
            this.Glasir.projectName = "";
            this.Title = "Untitled project - Glasir";
        }

        /// <summary>
        /// open a specified project
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void openProject(object sender, RoutedEventArgs e)
        {
            // close current project
            closeProject(sender, e);

            // create OpenFileDialog 
            Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();

            // set filter for file extension and default file extension
            dlg.InitialDirectory = System.IO.Directory.GetCurrentDirectory();
            dlg.DefaultExt = ".glpf";
            dlg.Filter = "Glasir Project Files|*.glpf;|All Files|*.*";

            // display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();

            if (result == true)
            {
                if (dlg.FileName.EndsWith(".glpf"))
                {
                    // Open project
                    Glasir.openProject(dlg.FileName);
                    this.Title = dlg.SafeFileName + " - Glasir";
                }
                else
                {
                    MessageBox.Show("Only Glasir Project File (.glpf) can be opened.");
                }
            }
        }

        /// <summary>
        /// save the current project
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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

        /// <summary>
        /// save the project under a specified name
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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

        /// <summary>
        /// create a new ADT file
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void newFile(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.SaveFileDialog dlg = new Microsoft.Win32.SaveFileDialog();
            dlg.InitialDirectory = System.IO.Directory.GetCurrentDirectory();
            dlg.AddExtension = true;
            dlg.DefaultExt = ".adt";
            dlg.Filter = "ADT Files|*.adt;*.xml|All Files|*.*";
            

            // Display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();

            if (result == true)
            {
                if (dlg.FileName.EndsWith(".adt") || dlg.FileName.EndsWith(".xml"))
                {
                    // Open the new file 
                    Glasir.launchADToolInstance(dlg.FileName);
                }
            }
        }

        /// <summary>
        /// open an ADTfile
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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
                // Open ADT file 
                string filename = dlg.FileName;
                Glasir.launchADToolInstance(filename);
            }
        }

        /// <summary>
        /// close all opened instances of ADTool
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void closeProject(object sender, RoutedEventArgs e)
        {
            this.Glasir.closeProject();
            this.Title = "Glasir";
        }

        private void exit(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("t-t-t y a une croix rouge en haut à droite pour ça.");
        }

        

    }
}

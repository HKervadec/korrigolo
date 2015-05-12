using System;
using System.Collections.Generic;
using System.ComponentModel;
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

        private List<String> domains;
        
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
            checkIfADToolIsInstalled();
        }

        private void checkIfADToolIsInstalled()
        {
            string path = Directory.GetCurrentDirectory() + "\\" + BigGlasir.AdtoolVersion;
            if (!System.IO.File.Exists(path))
            {
                MessageBox.Show(path + "   was not found.\nPlease follow the steps described in the ReadMe file provided with Glasir.");
                this.Close();
            }
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            // LA donnée source "domains" doit être collection

            //Binding par XAML
            this.Param2Editor.DataContext = this.domains;
            this.Param1Editor.DataContext = this.domains;
            this.FilterComboBox.DataContext = this.domains;

            ////Binding par code
            //Binding bind = new Binding();
            //bind.Source = domains;
            //bind.Mode = BindingMode.OneWay;

            //Définition de ma property à afficher dans la combo
            Param1Editor.DisplayMemberPath = "Chars";
            Param2Editor.DisplayMemberPath = "Chars";
            FilterComboBox.DisplayMemberPath = "Chars";
        }


        public void testDomain1(object sender, SelectionChangedEventArgs args)
        {
            try
            {
                String item = (String)Param1Editor.SelectedItem;
                if (item.StartsWith("DiffLMHE", StringComparison.CurrentCultureIgnoreCase))
                {
                    MessageBoxResult result = MessageBox.Show("Please select a continuous domain for the result domain"); 
                }
                else if (item.StartsWith("DiffLMH", StringComparison.CurrentCultureIgnoreCase))
                {
                    MessageBoxResult result = MessageBox.Show("Please select a continuous domain for the result domain"); 
                }
                else
                {
                    L1.Visibility = Visibility.Hidden;
                    M1.Visibility = Visibility.Hidden;
                    H1.Visibility = Visibility.Hidden;
                    E1.Visibility = Visibility.Hidden;
                }
            }
            catch { }
        }

        public void testDomain2(object sender, SelectionChangedEventArgs args)
        {
            try
            {
                String item = (String)Param2Editor.SelectedItem;
                if (item.StartsWith("DiffLMHE", StringComparison.CurrentCultureIgnoreCase))
                {
                    L2.Visibility = Visibility.Visible;
                    M2.Visibility = Visibility.Visible;
                    H2.Visibility = Visibility.Visible;
                    E2.Visibility = Visibility.Visible;
                }
                else if (item.StartsWith("DiffLMH", StringComparison.CurrentCultureIgnoreCase))
                {
                    L2.Visibility = Visibility.Visible;
                    M2.Visibility = Visibility.Visible;
                    H2.Visibility = Visibility.Visible;
                    E2.Visibility = Visibility.Hidden;
                }
                else
                {
                    L2.Visibility = Visibility.Hidden;
                    M2.Visibility = Visibility.Hidden;
                    H2.Visibility = Visibility.Hidden;
                    E2.Visibility = Visibility.Hidden;
                }
            }
            catch { }
        }

        public void testDomainFiltering(object sender, SelectionChangedEventArgs args)
        {
            try
            {
                String item = (String)FilterComboBox.SelectedItem;
                if (item.StartsWith("DiffLMHE", StringComparison.CurrentCultureIgnoreCase))
                {
                    L3.Visibility = Visibility.Visible;
                    M3.Visibility = Visibility.Visible;
                    H3.Visibility = Visibility.Visible;
                    E3.Visibility = Visibility.Visible;
                }
                else if (item.StartsWith("DiffLMH", StringComparison.CurrentCultureIgnoreCase))
                {
                    L3.Visibility = Visibility.Visible;
                    M3.Visibility = Visibility.Visible;
                    H3.Visibility = Visibility.Visible;
                    E3.Visibility = Visibility.Hidden;
                }
                else
                {
                    L3.Visibility = Visibility.Hidden;
                    M3.Visibility = Visibility.Hidden;
                    H3.Visibility = Visibility.Hidden;
                    E3.Visibility = Visibility.Hidden;
                }
            }
            catch { }
        }

        /// <summary>
        /// create the new parameter for the foreground tree
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void functionEdition(object sender, RoutedEventArgs e)
        {
            try
            {

                if (domains.Contains(functionName.Text))
                {
                    MessageBox.Show("Invalid Name : The function name is already used.");
                    return;
                }

                String item = (String)Param1Editor.SelectedItem;
                if (item.StartsWith("DiffLMH", StringComparison.CurrentCultureIgnoreCase))
                {
                    MessageBoxResult result = MessageBox.Show("Please select a continuous domain for the result domain");
                }
                else
                {

                    XMLFile file = ADToolInstance.foregroundInstance.file;
                    FunctionEditor functEdit = new FunctionEditor(file, functionName.Text, FunctionFormula.Text, (string)Param1Editor.SelectedItem, (string)Param2Editor.SelectedItem, L1.Text, M1.Text, H1.Text, E1.Text, L2.Text, M2.Text, H2.Text, E2.Text);
                    double test = FunctionEditor.Evaluate("2" + FunctionFormula.Text + "1");
                    XMLFile newfile = functEdit.createResultingFile();
                    Glasir.launchADToolInstance(newfile.FileName);
                    domains = ADToolInstance.foregroundInstance.file.getDomains();
                    Window_Loaded(sender, e);
                    this.updateTreeView();
                }
            }
            catch
            {
                MessageBox.Show("Invalid function.");
            }
        }


        /// <summary>
        /// filter the foregroung tree
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void filtering(object sender, RoutedEventArgs e)
        {
            try
            {

                String item = (String)FilterComboBox.SelectedItem;
                /*if (item.StartsWith("DiffLMH", StringComparison.CurrentCultureIgnoreCase))
                {
                    MessageBoxResult result = MessageBox.Show("Please select a continuous domain for the filter");
                }
                else
                {*/

                    XMLFile file = ADToolInstance.foregroundInstance.file;
                    Filter filter = new Filter(file, (String)FilterComboBox.SelectedItem, maxFilter.Text);
                    XMLFile newfile = filter.createResultingFile();
                    Glasir.launchADToolInstance(newfile.FileName);
                    domains = ADToolInstance.foregroundInstance.file.getDomains();
                    Window_Loaded(sender, e);
                    this.updateTreeView();
                //}
            }
            catch
            {
                MessageBox.Show("Invalid filter.");
            }
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
            this.updateTreeView();
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
            this.updateTreeView();
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
                domains = ADToolInstance.foregroundInstance.file.getDomains();
                Window_Loaded(sender, e);
            }
            this.updateTreeView();
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
            this.updateTreeView();
        }

        /// <summary>
        /// exit Glasir
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void exit(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        /// <summary>
        /// close project when exitting
        /// </summary>
        /// <param name="e"></param>
        protected override void OnClosing(CancelEventArgs e)
        {
            this.Glasir.closeProject();
        }

        /// <summary>
        /// about us
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void aboutUsClick(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Software developed during the 4th year of engineering studies at INSA Rennes.\n\nAuthors : Pierre-Marie Airiau, Valentin Esmieu and Maud Leray.\nDate : 2014 - 2015.\n\nNo guarantee is provided.\nUse at your own risk.");
        }


        private void updateTreeView()
        {
            treeView.Items.Clear();
            foreach (ADToolInstance adti in Glasir.ADToolInstances)
            {   
                if((bool) this.showFilename.IsChecked)
                {
                    treeView.Items.Add(new TreeViewItem() { Header = adti.process.StartInfo.Arguments});
                }
                else
                {
                    treeView.Items.Add(new TreeViewItem() { Header = System.IO.Path.GetFileName(adti.process.StartInfo.Arguments) });
                }
                
            }
        }

        public static void messageBox(string message)
        {
            MessageBox.Show(message);
        }

        // ugly
        private void updateTreeView(object sender, RoutedEventArgs e)
        {
            this.updateTreeView();
        }


        /// <summary>
        /// update the foreground instance when clicking on a treeview item
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void updateForegroundInstance(object sender, RoutedPropertyChangedEventArgs<object> e)
        {
            for (int i = 0; i< this.treeView.Items.Count; i++)
            {
                if ( ((TreeViewItem) this.treeView.Items[i]).IsSelected)
                {
                    this.Glasir.setForegroundInstance(i);
                    domains = ADToolInstance.foregroundInstance.file.getDomains();
                    Window_Loaded(sender, e);
                    return;
                }
            }
        }

        private void helpADTool(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Files with .jar extention might be associate with something else than Java.\nPlease open the ReadMe file and follow the steps to change .jar file association.");
        }

        
        
    }
}

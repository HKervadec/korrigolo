using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
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
        public MainWindow()
        {
            InitializeComponent();
        }

        /// <summary>
        /// launch ADTool
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void launchADTool(object sender, RoutedEventArgs e)
        {
            Process process = new Process();
            process.StartInfo.FileName = "ADTool-1.4-jar-with-dependencies.jar";
            process.StartInfo.WorkingDirectory = Directory.GetCurrentDirectory() ;
            process.StartInfo.Arguments = "adtree3.adt";
            
            process.Start();
            //int code = clientProcess.ExitCode != 0)
        }


        /// <summary>
        /// when you drop (drag'n'drop)
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void onDrop(object sender, DragEventArgs e)
        {

        }

        private void test(object sender, RoutedEventArgs e)
        {
            
        }

        /*
         *  Pour héberger le contrôle dans la page, vous créez tout d'abord une nouvelle instance de la classe ControlHost. Passez la hauteur et la largeur de l'élément de bordure qui contient le contrôle (ControlHostElement) au constructeur ControlHost. Cela garantit que la Zone de liste est dimensionnée correctement. Vous hébergez ensuite le contrôle dans la page en assignant l'objet ControlHost à la propriété Child de l'hôte Border. 
         *  */




        internal static IntPtr getADTool()
        {
            Process process = new Process();
            process.StartInfo.FileName = "ADTool-1.4-jar-with-dependencies.jar";
            process.StartInfo.WorkingDirectory = "C:\\Users\\Valentin\\korrigolo\\Glasir\\Glasir\\bin\\Debug";
            process.StartInfo.Arguments = "adtree3.adt";
            process.Start();
            return process.MainWindowHandle;
            
            //int code = clientProcess.ExitCode != 0)
        }

        internal static void launchADToolMDFCKERS(Process process)
        {
            process.Start();
        }
    }
}

using System;
using System.Collections.Generic;
using System.Diagnostics;
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

        private void launchADTool(object sender, RoutedEventArgs e)
        {
            Process process = new Process();
            process.StartInfo.FileName = "ADTool-1.4-jar-with-dependencies.jar";
            process.StartInfo.WorkingDirectory = "..\\bin\\Debug";
            process.StartInfo.Arguments = "COUCOU.adt";
            process.Start();
            //int code = clientProcess.ExitCode != 0)
        }
    }
}

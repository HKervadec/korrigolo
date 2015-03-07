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
        

        /// <summary>
        /// new project
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void launchADTool(object sender, RoutedEventArgs e)
        {
            this.Glasir.launchADToolInstance();
        }

        private void xmltest(object sender, RoutedEventArgs e)
        {
            XMLFile file = new XMLFile("test3");
            FunctionEditor functEdit = new FunctionEditor();
            Console.WriteLine(file.XmlCode);
            //functEdit.createResultingFile(file);
            /*
            XDocument doc = XDocument.Load("../../testXML/test3.xml");
            //Console.WriteLine(doc);
            XElement rm = (XElement) doc.FirstNode;
            XElement rd = (XElement) rm.FirstNode;
            //Console.WriteLine(rm);
            //Console.WriteLine(rd);
            XElement rm2 = (XElement)rm.Element("node");
            XElement rm3 = (XElement)rm2.Element("node");
            Console.WriteLine(rm3);
            rm3.Remove();
            Console.WriteLine(doc);*/
        }

        

    }
}

/**
 * Author: Piotr Kordy (piotr.kordy@uni.lu <mailto:piotr.kordy@uni.lu>)
 * Date:   06/06/2013
 * Copyright (c) 2013,2012 University of Luxembourg -- Faculty of Science,
 *     Technology and Communication FSTC
 * All rights reserved.
 * Licensed under GNU Affero General Public License 3.0;
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as
 *    published by the Free Software Foundation, either version 3 of the
 *    License, or (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lu.uni.adtool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;
// import java.awt.AlphaComposite;

public class StatusLine extends JLabel implements ActionListener
{
/**
   *
   */
  private static final long serialVersionUID = 4285019474424274296L;
  private LogView log;
  //   private static final Font FONT = new Font("Serif", Font.PLAIN, 12);
//   private static String STRING = "Ready...";
//   private static final float DELTA = 0.05f;
  private static final Timer timer = new Timer(100, null);
//   private float alpha = 1f;

  public StatusLine(){
    super("Ready");
    log=null;
//     this.setPreferredSize(new Dimension(26, 26));
// //     this.setOpaque(true);
//     this.setBackground(Color.white);
    timer.setInitialDelay(10000);
//     timer.setDelay(10);
    timer.addActionListener(this);
    timer.start();
  }

  public void report(final String s)
  {
    setText(s);
    if(log!=null){
      log.addMessage(s);
    }
    timer.restart();
  }
  public void reportError(String message){
    report("<html><font color='red'> Error: </font>"+message+"<html>");
  }
  public void reportWarning(String message){
    report("<html><font color='orange'> Warning: </font>"+message+"<html>");
  }
  public void setLog(LogView newLog){
    this.log=newLog;
  }

//   @Override
//   protected void paintComponent(Graphics g) {
//     super.paintComponent(g);
//     Graphics2D g2d = (Graphics2D) g;
//     g2d.setFont(FONT);
//     int xx = this.getWidth();
//     int yy = this.getHeight();
//     int w2 = g.getFontMetrics().stringWidth(STRING) / 2;
//     int h2 = g.getFontMetrics().getDescent();
//     g2d.fillRect(0, 0, xx, yy);
// //     g2d.setComposite(AlphaComposite.getInstance(
// //         AlphaComposite.SRC_IN, alpha));
//     g2d.setPaint(new Color(1-alpha,1-alpha,1-alpha));
//     g2d.drawString(STRING, xx / 2 - w2, yy / 2 + h2);
//   }

  public void actionPerformed(ActionEvent e) {
    setText("");
//   alpha -= DELTA;
//     if (alpha < 0) {
//       STRING="";
//       alpha = 1;
//     }
//     else{
//       timer.restart();
//     }
//     repaint();
//   }
  }
}

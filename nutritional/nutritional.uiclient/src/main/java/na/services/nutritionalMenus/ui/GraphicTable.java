package na.services.nutritionalMenus.ui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/*
 * Draws a table as big as the JLayeredPanel size.
 */
 
@SuppressWarnings("serial")
public class GraphicTable extends JLayeredPane  // <<<---- not JPanel 
{
    String[] msgs;
    final int
        ROWS =  8,
        COLS =  4,
        PAD  = 10;
 
    
 
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        Insets insets = getInsets();    // border info
        double xInc = (w - insets.left - insets.right - 2*PAD)/COLS;
        double yInc = (h - insets.top - insets.bottom - 2*PAD)/ROWS;
        
        
        double x1 = insets.left + PAD, y1 = insets.top + PAD,
               y2 = h - insets.bottom - PAD, x2;
        
        // background first
        x1 = insets.left + PAD; x2 = w - insets.right - PAD;
        g2.setColor(new Color(210, 235, 242));
//        g2.setColor(Color.GRAY);
        for(int j = 0; j <= ROWS; j++) {
        	if (j%2!=0) {
	        	g2.fillRect(10, ((int) y1), 840, 50);
        	}
        	y1 += yInc;
        }
        y1 = insets.top + PAD;
        
     // vertical lines
        int extra = 50;
        g2.setPaint(new Color(74, 172, 197)); //light blue
        for(int j = 0; j <= COLS; j++) {
        	if (j==1) {
        		g2.draw(new Line2D.Double(x1-extra, y1, x1-extra, y2));
        	} else if ((j!=2) && (j!=3)) {
        		g2.draw(new Line2D.Double(x1, y1, x1, y2));
        	} 
        	x1 += xInc;
        }
        g2.draw(new Line2D.Double(380, y1, 380, y2));
        g2.draw(new Line2D.Double(597, y1, 597, y2));
        // horizontal lines
        x1 = insets.left + PAD; x2 = w - insets.right - PAD;
        for(int j = 0; j <= ROWS; j++)
        {
        	g2.setPaint(new Color(74, 172, 197)); //light blue
            g2.draw(new Line2D.Double(x1, y1, x2, y1));
            if (j==1) {
            	g2.draw(new Line2D.Double(x1, y1+1, x2, y1+1));
            	g2.draw(new Line2D.Double(x1, y1+2, x2, y1+2));
        	}
            y1 += yInc;
        }
    }
 
  
}

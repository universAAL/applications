package org.universAAL.agenda.gui;

/*
 * Programming graphical user interfaces
 * Example: AnalogClock.java
 * Jarkko Leponiemi 2003
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Class for creating analog clock with test main method.
 * @see <a href="http://www.uta.fi/~jl/pguibook/examples/AnalogClock_Java/AnalogClock.java">online link</a>
 * 
 */
public class AnalogClock extends JComponent implements Runnable {

    private static Stroke SEC_STROKE = new BasicStroke();
    private static Stroke MIN_STROKE = new BasicStroke(4F,
	    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static Stroke HOUR_STROKE = MIN_STROKE;

    private Dimension size = null;
    private Insets insets = new Insets(0, 0, 0, 0);
    private static final long serialVersionUID = 1L;

    public AnalogClock() {
	(new Thread(this)).start();
    }

    public void run() {
	try {
	    for (;;) {
		Thread.sleep(500);
		repaint();
	    }
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}
    }

    public void paint(Graphics graphics) {
	super.paint(graphics);

	// calculate the time values
	double sec = ((double) Calendar.getInstance().get(Calendar.SECOND)) / 60L;
	double min = ((double) Calendar.getInstance().get(Calendar.MINUTE)) / 60L;
	double hour = ((double) Calendar.getInstance().get(Calendar.HOUR)) / 12L;

	// calculate the clock hand angles
	double sec_angle = -Math.PI / 2 + 2 * Math.PI * sec;
	double min_angle = -Math.PI / 2 + 2 * Math.PI * min;
	double hour_angle = -Math.PI / 2 + 2 * Math.PI * hour;

	// Draw the hands
	Graphics2D g = (Graphics2D) graphics;
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	size = new Dimension(200, 200);
	insets = new Insets(50, 50, 50, 50);
	int radius = Math.min(size.width - insets.left - insets.top,
		size.height - insets.top - insets.bottom) / 2;
	g.translate((double) size.width / 2D, (double) size.height / 2D);

	// draw the seconds
	g.setColor(Color.red);
	g.setStroke(SEC_STROKE);
	g.rotate(sec_angle);
	g.drawLine(0, 0, radius - 6, 0);
	g.rotate(-sec_angle);

	// draw the minutes
	g.setColor(Color.black);
	g.setStroke(MIN_STROKE);
	g.rotate(min_angle);
	g.drawLine(0, 0, radius - 10, 0);
	g.rotate(-min_angle);

	// draw the hours
	g.setColor(Color.black);
	g.setStroke(HOUR_STROKE);
	g.rotate(hour_angle);
	g.drawLine(0, 0, radius / 2, 0);
	g.rotate(-hour_angle);

	// draw the perimeter
	g.setColor(Color.darkGray);
	g.drawOval(-radius + 2, -radius + 2, 2 * radius - 4, 2 * radius - 4);
	g.drawString("12", -3, -radius - 3);
	g.drawString("3", radius + 3, +3);
	g.drawString("6", -3, radius + 12);
	g.drawString("9", -radius - 8, 6);

	g.drawString("9", -radius - 8, 6);
    }

    /**
     * Test method for displaying clock.
     * 
     * @param args
     */
    public static void main(String[] args) {
	JFrame f = new JFrame("Clock");
	AnalogClock clock = new AnalogClock();
	clock.setPreferredSize(new Dimension(150, 150));
	clock.setSize(150, 150);
	f.getContentPane().add(clock);
	f.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	f.setBounds(50, 50, 200, 200);
	f.setVisible(true);
    }

}
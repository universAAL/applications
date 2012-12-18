package org.universAAL.agenda.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class DigitalClock extends JLabel {
    private static final long serialVersionUID = -7006877674857478502L;
    private String clockPattern;
    private Timer timer;

    

    /**
     * Constructs a Digital Clock using the given pattern and delay.
     * 
     * @param delay
     *            - the number of milliseconds between action events
     * @param pattern
     *            - the pattern describing the date and time format
     */

    protected DigitalClock(String pattern, int delay) {
	clockPattern = pattern;
	timer = new Timer(delay, new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		setText(new SimpleDateFormat(clockPattern).format(new Date()));
	    }
	});
	timer.start();
    }

    protected DigitalClock() {
	this("HH:mm:ss", 1000);
	//this("hh:mm:ss a", 1000); //for e.g. 03:04:34 PM 
    }
    
//    private static DigitalClock instance=null;
//    
//    public static DigitalClock getInstance(){
//	
//	if (instance==null){
//	    instance= new DigitalClock();
//	}
//	return instance;
//	
//    }
    
    public static void main(String[] args){
	  JFrame frame = new JFrame();
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setBounds(100, 100, 100, 100);
	  JPanel contentPane = new JPanel();
	  contentPane.setLayout(new BorderLayout());
	  frame.setContentPane(contentPane);
	  contentPane.add(new DigitalClock());
	  frame.setVisible(true);
	 }
}
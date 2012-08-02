/**
 * 
 */
package org.universAAL.ltba.publisher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mllorente
 * 
 */
public class PseudoClock {

	private static PseudoClock INSTANCE;
	private Calendar clock;

	private PseudoClock() {
		super();
		start();
		INSTANCE = this;
	}

	public static PseudoClock getInstance() {
		if (INSTANCE == null) {
			return new PseudoClock();
		} else {
			return INSTANCE;
		}
	}

	private void start() {
		clock = Calendar.getInstance();
		javax.swing.Timer clockTimer = new javax.swing.Timer(1,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						clock.setTimeInMillis(clock.getTimeInMillis() + 1);
					}
				});
		clockTimer.start();
	}

	public long getTimeInMillis() {
		return clock.getTimeInMillis();
	}

	public Date getTime() {
		return clock.getTime();
	}

	public void setTimeInMillis(long time) {
		clock.setTimeInMillis(time);
	}

	public void setTime(Date date) {
		clock.setTime(date);
	}

}

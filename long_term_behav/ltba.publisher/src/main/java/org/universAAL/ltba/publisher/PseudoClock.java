/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.ltba.publisher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Pseudo clock used for show time references.
 * 
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

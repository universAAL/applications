package org.universAAL.FitbitPublisher.FitbitAPI;

/* 
 * Author: Carsten Ringe
 * Project repository: https://github.com/MoriTanosuke/fitbitclient 
*/

import java.io.IOException;

public class FitbitException extends Exception {
	private static final long serialVersionUID = 1L;

	public FitbitException(IOException e) {
		super(e);
	}

	public FitbitException(String msg) {
		super(msg);
	}

}

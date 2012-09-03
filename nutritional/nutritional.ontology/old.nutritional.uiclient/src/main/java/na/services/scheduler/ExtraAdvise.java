package na.services.scheduler;

import java.util.Calendar;

import na.miniDao.Advise;


public class ExtraAdvise {
	public Advise advise = new Advise();
	private boolean sentToPublicist;
	private Calendar lastTimeShown;
	
	public boolean isSentToPublicist() {
		return sentToPublicist;
	}
	public void setSentToPublicist(boolean sentToPublicist) {
		this.sentToPublicist = sentToPublicist;
	}
	public void setLastTimeShown(Calendar lastTimeShown) {
		this.lastTimeShown = lastTimeShown;
	}
	public Calendar getLastTimeShown() {
		return lastTimeShown;
	}
	
	
}

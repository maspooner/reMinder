package org.spooner.java.reMinder;

public class Alarm extends TimedEvent{
	
	//members
	private long timeEnd;
	private boolean isRepeated;
	private String popupMessage;
	private boolean shouldShowPopup;
	
	//constuctors
	public Alarm(String name, String desc, long timeEnd) {
		super(name, desc);
		this.timeEnd=timeEnd;
		isRepeated=false;
		popupMessage="Alarm: "+getName()+" has finished.";
		shouldShowPopup=true;
	}
	public Alarm(String name, String desc, long timeEnd, boolean isRepeated) {
		this(name, desc, timeEnd);
		this.isRepeated=isRepeated;
	}
	public Alarm(String name, String desc, long timeEnd, boolean isRepeated, String message) {
		this(name, desc, timeEnd, isRepeated);
		popupMessage=message;
	}
	
	//methods
	public final long getTimeEnd(){return timeEnd;}
	public final void setTimeEnd(long millis){timeEnd=millis;}
	public final boolean isRepeated(){return isRepeated;}
	public final void setIsRepeated(boolean isRepeated){this.isRepeated=isRepeated;}
	public final String getMessage(){return popupMessage;}
	public final void setMessage(String message){popupMessage=message;}
	public final boolean getShouldShowPopup(){return shouldShowPopup;}
	public final void setShouldShowPopup(boolean shouldShowPopup){this.shouldShowPopup=shouldShowPopup;}
	@Override
	public void print() {
		super.print();
		System.out.println("Time End:\t"+timeEnd);
		System.out.println("Repeated?:\t"+isRepeated);
	}
	protected void recalculate(){
		//adds a day while timeEnd is less than now
		while(timeEnd<System.currentTimeMillis()){
			timeEnd+=86400000;
		}
		setIsEnded(false);
	}
	@Override
	protected void terminateEvent() {
		if(isRepeated && isEnded())
			recalculate();
		else
			setIsEnded(true);
	}
	public long getTimeRemaining() {
		long remain=timeEnd-System.currentTimeMillis();
		return remain<0 ? 0 : remain;
	}
	@Override
	protected void checkEnded() {
		if(isEnded() && !isRepeated)
			return;
		if(timeEnd<=System.currentTimeMillis())
			terminateEvent();
	}

	public final String getTimeRemainingString(){
		return MinderTime.toTime(getTimeRemaining());
	}
}

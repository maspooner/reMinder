package org.spooner.java.reMinder;

public class Reminder extends Alarm{
	//members
	private long interval;
	
	//constructors
	public Reminder(String name, String desc, long interInMillis){
		super(name, desc, System.currentTimeMillis()+interInMillis);
		interval=interInMillis;
		setMessage("Reminder: "+getName()+" has finished.");
	}
	public Reminder(String name, String desc, long interInMillis, long timeEnd, boolean isRepeated, String message){
		this(name, desc, interInMillis);
		setIsRepeated(isRepeated);
		setTimeEnd(timeEnd);
		setMessage(message);
	}
	
	//methods
	public final long getInterval(){return interval;}
	@Override
	public void print() {
		super.print();
		System.out.println("Interval:\t"+interval);
	}
	@Override
	protected void recalculate(){
		//TODO test
		setTimeEnd(System.currentTimeMillis()+interval+MinderTime.getDSTAllignment());
		setIsEnded(false);
	}
	
	public final String getStringInterval(){
		return MinderTime.getSplitTimeString(interval);
	}
}

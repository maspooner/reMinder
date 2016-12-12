package org.spooner.java.reMinder;

public abstract class TimedEvent {
	
	//members
	private String eventName, eventDesc;
	private boolean isEnded;
	
	//constructors
	public TimedEvent(String name, String desc){
		eventName=name;
		eventDesc=desc;
	}
	
	//methods
	public final String getName(){return eventName;}
	public final String getDesc(){return eventDesc;}
	public final boolean isEnded(){return isEnded;}
	public final void setIsEnded(boolean isEnded){this.isEnded=isEnded;}
	
	public void print(){
		System.out.println("Event:\t\t"+eventName);
		System.out.println("Description:\t"+eventDesc);
	}
	protected abstract void checkEnded();
	protected abstract void terminateEvent();
}

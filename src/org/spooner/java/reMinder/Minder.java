package org.spooner.java.reMinder;

import java.util.ArrayList;

/*
 * reMinder
 * Made by Matt Spooner
 * 
 * Last Modified:Oct 17, 2013
 * Date Started:Sep 19, 2013
 * 
 * Programming Concepts Applied For the First Time:
 *   inheritance
 *   working with milliseconds
 *   multi-threading
 *   
 * Error codes:
 * 2 - create file error
 * 3 - write to file error
 * 5 - parse time error
 * 6 - argument error
 * 7 - read from file error
 * 11 - build events from read data error
 * 
 * TODO:
 * remove .* imports
 * fix those warnings
 * ability to adjust beep interval/number
 * packages?
 * new data type: DueDate: set with a specific day/month/year
 * optimize updating (don't need to update some components unless edited)
 * get rid of shouldUpdate
 * sort events!
 */
public class Minder {
	//static members
	private static ArrayList<TimedEvent> events=new ArrayList<TimedEvent>();
	private static MinderIO io;
	protected static volatile boolean shouldUpdate=false;
	
	private static Thread checkTime=new Thread(new Runnable(){
		@Override
		public void run() {
			while(true){
				try{
					Thread.sleep(MinderConstants.EVENT_CHECK_INERVAL);
					for (TimedEvent te : events){
						te.checkEnded();
					}
				}catch(Exception e){
					e.printStackTrace();
					return;
				}
			}
		}
	});
	
	//static methods
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String runType=args[0];
//		try{
//			//TODO
//			if(isGUIEnabled(runType)){
//				System.out.println("GUI enabled");
//			}
//		}
//		catch(Exception e){
//			System.err.println("Invalid command line arguments!");
//			System.exit(6);
//		}
		checkTime.start();
		new MinderFrame();
		io=new MinderIO();
		TimedEvent[] readEvents=io.read();
		initEvents(readEvents);
	}
	public static final int getEventSize(){return events.size();}
	public static final ArrayList<TimedEvent> getEvents(){return events;}
	public static final void setEvent(TimedEvent index, TimedEvent newValue){
		events.set(events.indexOf(index), newValue);
	}
	public static final void addEvent(TimedEvent te){
		events.add(te);
	}
	public static final void removeEvent(TimedEvent te){
		events.remove(te);
	}
	
	private static final boolean isGUIEnabled(String type) throws Exception{return Integer.parseInt(type)==0;}
	private static final void initEvents(TimedEvent[] readEvents){
		for(TimedEvent te : readEvents){
			events.add(te);
		}
	}
	public static final void manualSave(){
		TimedEvent[] tes=new TimedEvent[events.size()];
		events.toArray(tes);
		io.write(tes);
	}
}

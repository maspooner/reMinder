package org.spooner.java.reMinder;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;

/*
 * reMinder
 * Made by Matt Spooner
 * 
 * Last Modified:Nov 24, 2013
 * Date Started:Sep 19, 2013
 * 
 * Programming Concepts Applied For the First Time:
 *   inheritance
 *   working with milliseconds
 *   multi-threading
 *   system tray
 *   
 * Error codes:
 * 2 - create file error
 * 3 - write to file error
 * 4 - read image error
 * 5 - parse time error
 * 6 - argument error
 * 7 - read from file error
 * 8 - add to tray error
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
public abstract class Minder {
	//static members
	private static ArrayList<TimedEvent> events=new ArrayList<TimedEvent>();
	private static MinderFrame frame;
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
		frame=new MinderFrame();
		io=new MinderIO();
		TimedEvent[] readEvents=io.read();
		initEvents(readEvents);
		new MinderTray();
		checkTime.start();
	}
	public static final int getEventSize(){return events.size();}
	public static final ArrayList<TimedEvent> getEvents(){return events;}
	public static final TimedEvent getEvent(int i){return events.get(i);}
	public static final void setEvent(TimedEvent index, TimedEvent newValue){
		events.set(events.indexOf(index), newValue);
	}
	public static final void addEvent(TimedEvent te){
		events.add(te);
	}
	public static final void addEvent(int i, TimedEvent te){
		events.add(i, te);
	}
	public static final void removeEvent(TimedEvent te){
		events.remove(te);
	}
	public static final TimedEvent removeEvent(int i){
		return events.remove(i);
	}
	public static final void swapEvents(int i, int j){
		Collections.swap(events, i, j);
	}
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
	public static final Image getImage(String fileName){
		return io.readImage(fileName);
	}
	public static void toggleGUI() {
		boolean enabled=true;
		if(frame.isVisible()) enabled=false;
		frame.setVisible(enabled);
	}
}

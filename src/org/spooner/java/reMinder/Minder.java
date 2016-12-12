package org.spooner.java.reMinder;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.Clip;

/*
 * reMinder
 * Made by Matt Spooner
 * 
 * Last Modified:Dec 14, 2013
 * Date Started:Sep 19, 2013
 * 
 * Programming Concepts Applied For the First Time:
 *   inheritance
 *   working with milliseconds
 *   multi-threading
 *   system tray
 *   
 * Error codes:
 * 1 - clip error
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
 * ability to adjust beep sound
 * new data type: DueDate: set with a specific day/month/year
 * optimize updating (don't need to update some components unless edited)
 * get rid of shouldUpdate
 */
public abstract class Minder {
	//static members
	private static ArrayList<TimedEvent> events=new ArrayList<TimedEvent>();
	private static MinderFrame frame;
	private static MinderIO io;
	private static Clip bing;
	protected static volatile boolean shouldUpdate=false;
	
	private static Thread checkTime=new Thread(new Runnable(){
		@Override
		public void run() {
			while(true){
				try{
					//delay added so that message has time to appear before event ended is switched to false
					Thread.sleep(MinderOptions.updateInterval+200);
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
		frame=new MinderFrame();
		io=new MinderIO();
		bing=io.getClip("bing.wav");
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
	public static final void beep(){
		bing.setFramePosition(0);
		try {
			//give time for frame reset
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bing.start();
	}
}

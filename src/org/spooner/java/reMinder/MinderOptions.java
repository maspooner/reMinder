package org.spooner.java.reMinder;

public class MinderOptions {
	//holds all options for the app
	//members
	protected static int updateInterval=1000;
	protected static int saveInterval=300000;
	protected static boolean doAutosave=true;
	protected static boolean doBeep=true;
	protected static boolean wasDST=false;
	
	//methods
	protected static void print() {
		System.out.println(updateInterval);
		System.out.println(saveInterval);
		System.out.println(doAutosave);
		System.out.println(doBeep);
	}
	
	protected static String[] toArray(){
		return new String[]{Integer.toString(updateInterval), Integer.toString(saveInterval),
				Boolean.toString(doAutosave), Boolean.toString(doBeep), Boolean.toString(wasDST)};
	}
	
	protected static void parseOptions(String[] data){
		updateInterval=Integer.parseInt(data[0]);
		saveInterval=Integer.parseInt(data[1]);
		doAutosave=Boolean.parseBoolean(data[2]);
		doBeep=Boolean.parseBoolean(data[3]);
		wasDST=Boolean.parseBoolean(data[4]);
	}
}

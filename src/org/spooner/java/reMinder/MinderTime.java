package org.spooner.java.reMinder;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class MinderTime {
	//this class converts between time
	
	//static members
	//contains week...second in millis
	private static final int[] timesInMillis=new int[]{604800000,86400000,3600000,60000,1000};
	private static final String[] timeStrings=new String[]{"weeks","days","hours","minutes","seconds"};
	
	//static methods
	public static final String getDateString(long millis){return new Date(millis).toString();}
	public static final String getTimeStringFromNumber(int number){
		switch(number){
		case 604800000: return "weeks";
		case 86400000: return "days";
		case 3600000: return "hours";
		case 60000: return "minutes";
		case 1000: return "seconds";
		default: return "err";
		}
	}
	
	public static final int getTimeFromString(String time){
		if(time.equals("weeks")) return 604800000;
		else if(time.equals("days")) return 86400000;
		else if(time.equals("hours")) return 3600000;
		else if(time.equals("minutes")) return 60000;
		else return 1000;
	}
	
	public static final String toTime(long time){
		//finds two highest units to display
		if(time<=1000) return "DONE";
		String timeString="";
		int i=0;
		for(int milli : timesInMillis){
			if(i==2) break;
			//if it subtracted time
			int timePiece=subtractTime(time, milli);
			if(timePiece!=0){
				timeString+=timePiece+" "+getTimeStringFromNumber(milli)+" ";
				i++;
			}
			//subtract from time
			for(int j=0;j<timePiece;j++)
				time-=milli;
		}
		return timeString;
	}
	
	private static final int subtractTime(Long left, int subtraction){
		if(left>=subtraction){
			int numOfTime=0;
			while(left>=subtraction){
				left-=subtraction;
				numOfTime++;
			}
			return numOfTime;
		}
		return 0;
	}
	
	public static final long timeToMillis(String time){
		try {
			Calendar hourMinute=Calendar.getInstance();
			Calendar correct=Calendar.getInstance();
			//set the right hour and minute
			hourMinute.setTime(DateFormat.getTimeInstance(DateFormat.SHORT).parse(time));
			//apply hourMinute relative to real time
			correct.set(Calendar.HOUR, hourMinute.get(Calendar.HOUR));
			correct.set(Calendar.MINUTE, hourMinute.get(Calendar.MINUTE));
			correct.set(Calendar.AM_PM, hourMinute.get(Calendar.AM_PM));
			//increase the day by one if the time is before now
			if(correct.getTime().before(new Date()))
				correct.roll(Calendar.DATE, true);
			return correct.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(5);
		}
		return 0L;
	}
	
	public static final String millisToTime(long millis){
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(millis));
	}
	
	public static final int[] divideIntoParts(long millis){
		for(int milli : timesInMillis){
			if(isDivisibleBy(millis, milli)){
				return new int[]{(int) (millis/milli), milli};
			}
		}
		return null;
	}
	
	public static final String getSplitTimeString(long millis){
		int[] parts=divideIntoParts(millis);
		return parts[0]+" "+getTimeStringFromNumber(parts[1]);
	}
	
	private static final boolean isDivisibleBy(long dividend, int divisor){return dividend%divisor==0;}
	public static synchronized final String[] getTimeStrings(){return timeStrings;}
	public static final String getSecondString(int millis){
		float secs=((float)millis)/1000f;
		return secs+" seconds";
	}
}

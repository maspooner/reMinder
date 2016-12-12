package org.spooner.java.reMinder;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MinderIO implements Runnable{
	//static members
	private static final String PATH="reMinderData.txt";
	private static final String EVENT_SEPARATOR="^&^";
	private static final String MEMBER_SEPARATOR=":%:";
	private static final String EVENT_REGEX="\\^&\\^+";
	private static final String MEMBER_REGEX=":%:+";
	
	//members
	private File dataFile;
	private Thread saveThread;
	
	//constuctors
	public MinderIO(){
		dataFile=new File(PATH);
		saveThread=new Thread(this);
		checkForFile();
		saveThread.start();
	}
	
	//methods
	private final void checkForFile(){
		if(dataFile.exists())
			return;
		try{
			dataFile.createNewFile();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public final TimedEvent[] read(){
		try{
			TimedEvent[] events;
			String data=readFile();
			//split by event separator
			String[] eventPieces=data.split(EVENT_REGEX);
			events=new TimedEvent[eventPieces.length-1];//-1 to not include options
			for(int i=0;i<eventPieces.length-1;i++){//same reason
				String piece=eventPieces[i];
				String[] eventMembers=piece.split(MEMBER_REGEX);
				
				String name=eventMembers[1];
				String desc=eventMembers[2];
				if(eventMembers[0].equals("T")){
					//Todo build
					events[i]=new Todo(name, desc, 
							Todo.stringToColor(eventMembers[3]), 
							Todo.stringToPriority(eventMembers[4]));
				}
				else{
					//Alarm/Reminder build
					long end=Long.parseLong(eventMembers[3]);
					boolean rep=Boolean.parseBoolean(eventMembers[4]);
					String mess=eventMembers[5];
					
					if(eventMembers[0].equals("R")){
						events[i]=new Reminder(name, desc, 
								Long.parseLong(eventMembers[6]), end, rep, mess);
					}
					else
						events[i]=new Alarm(name, desc, end, rep, mess);
				}
			}
			//init options
			if(!data.isEmpty())//make sure it's not empty
				MinderOptions.parseOptions(eventPieces[eventPieces.length-1].split(MEMBER_REGEX));
			return events;
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(11);
		}
		return null;
	}
	
	public final void write(TimedEvent[] events){
		StringBuilder sb=new StringBuilder();
		//events
		for(TimedEvent te : events){
			//appends first letter of the class name ex: 'T' for Todo
			sb.append(te.getClass().getSimpleName().charAt(0));
			sb.append(MEMBER_SEPARATOR);
			sb.append(te.getName());
			sb.append(MEMBER_SEPARATOR);
			sb.append(te.getDesc());
			sb.append(MEMBER_SEPARATOR);
			//class specifics
			if(te instanceof Todo){
				Todo t= (Todo) te;
				sb.append(t.getColor());
				sb.append(MEMBER_SEPARATOR);
				sb.append(t.getPriority());
			}
			else if(te instanceof Alarm){
				Alarm a= (Alarm) te;
				sb.append(a.getTimeEnd());
				sb.append(MEMBER_SEPARATOR);
				sb.append(a.isRepeated());
				sb.append(MEMBER_SEPARATOR);
				sb.append(a.getMessage());
				if(a instanceof Reminder){
					Reminder r=(Reminder) a;
					sb.append(MEMBER_SEPARATOR);
					sb.append(r.getInterval());
				}
			}
			sb.append(EVENT_SEPARATOR);
		}
		//options
		sb.append(getOptionsData());
		
		writeFile(sb.toString());
	}
	
	private final String getOptionsData(){
		String[] options=MinderOptions.toArray();
		StringBuilder sb=new StringBuilder();
		for(String s : options){
			sb.append(s);
			sb.append(MEMBER_SEPARATOR);
		}
		//remove last member separator
		sb.setLength(sb.length()-EVENT_SEPARATOR.length());
		return sb.toString();
	}
	
	private final void writeFile(String data){
		try{
			FileOutputStream fos=new FileOutputStream(dataFile);
			fos.write(data.getBytes());
			fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(3);
		}
	}
	
	private final String readFile(){
		byte[] data=new byte[0];
		try{
			FileInputStream fis=new FileInputStream(dataFile);
			data=new byte[fis.available()];
			fis.read(data);
			fis.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(7);
		}
		return new String(data);
	}

	public final Image readImage(String fileName){
		Image i=null;
		try{
			//FIXME change  when editing
//			i=ImageIO.read(new File(fileName));
			i=ImageIO.read(ClassLoader.class.getResourceAsStream("/"+fileName));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(4);
		}
		return i;
	}
	
	public final Clip getClip(String fileName){
		Clip c=null;
		try{
			//FIXME editing
			AudioInputStream ais=AudioSystem.getAudioInputStream
				(new BufferedInputStream(ClassLoader.class.getResourceAsStream("/"+fileName)));
//			AudioInputStream ais=AudioSystem.getAudioInputStream(new File(fileName));
			c=AudioSystem.getClip();
			c.open(ais);
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return c;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				System.out.println("Waiting...");
				Thread.sleep(MinderOptions.saveInterval);
				if(MinderOptions.doAutosave){
					TimedEvent[] eventArray=new TimedEvent[Minder.getEventSize()];
					Minder.getEvents().toArray(eventArray);
					System.out.println("SAVED!");
					write(eventArray);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

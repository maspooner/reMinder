package org.spooner.java.reMinder;

public class Todo extends TimedEvent{

	//static members
	public static enum evColor{NONE,YELLOW, BLUE, RED, CYAN, GREEN, PURPLE};
	public static enum evPriority{NONE,LOW, MED, HIGH};
	
	//members
	private evColor eventColor;
	private evPriority eventPriority;
	
	//constructors
	public Todo(String name, String desc) {
		super(name, desc);
		eventColor=evColor.NONE;
		eventPriority=evPriority.NONE;
	}
	public Todo(String name, String desc, evColor color){
		this(name, desc);
		eventColor=color;
	}
	public Todo(String name, String desc, evPriority prior){
		this(name, desc);
		eventPriority=prior;
	}
	public Todo(String name, String desc, evColor color, evPriority prior){
		this(name, desc);
		eventColor=color;
		eventPriority=prior;
	}

	//methods
	public final String getColor(){return eventColor.toString();}
	public final String getPriority(){return eventPriority.toString();}
	@Override
	public void print() {
		super.print();
		System.out.println("Color:\t\t"+eventColor);
		System.out.println("Priority:\t"+eventPriority);
	}
	@Override
	protected void terminateEvent(){}
	@Override
	protected void checkEnded(){}
	
	//static methods
	public static evColor stringToColor(String s){return evColor.valueOf(s);}
	public static evPriority stringToPriority(String s){return evPriority.valueOf(s);}
	public static String[] getColorsAsStrings(){
		String[] colorStrings=new String[evColor.values().length];
		int i=0;
		for(evColor ec : evColor.values()){
			colorStrings[i]=ec.toString();
			i++;
		}
		return colorStrings;
	}
	public static String[] getPrioritiesAsStrings(){
		String[] priorityStrings=new String[evPriority.values().length];
		int i=0;
		for(evPriority ep : evPriority.values()){
			priorityStrings[i]=ep.toString();
			i++;
		}
		return priorityStrings;
	}
}

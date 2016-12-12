package org.spooner.java.reMinder;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AlarmBox extends EventBox {
	
	//members
	private Alarm alarm;
	private JLabel timeEndLabel;
	private JLabel timeRemainingLabel;
	
	//constuctors
	public AlarmBox(Alarm a, boolean isGray) {
		super(a, isGray);
		alarm=a;
		timeEndLabel=new JLabel("Ends: "+MinderTime.getDateString(a.getTimeEnd()));
		timeRemainingLabel=new JLabel("Remaining: "+a.getTimeRemainingString());
		timeEndLabel.setFont(MinderConstants.MISC_FONT);
		timeRemainingLabel.setFont(MinderConstants.MISC_FONT);
		if(this.getClass()== AlarmBox.class)
			arrangeComponents();
	}

	//methods
	public final String getMessage(){return alarm.getMessage();}
	public final String getEventName(){return alarm.getName();}
	public final boolean isMessageToDisplay(){return alarm.isEnded() && alarm.getShouldShowPopup();}
	
	@Override
	public void update() {
		timeRemainingLabel.setText("Remaining: "+alarm.getTimeRemainingString());
	}

	@Override
	public void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		c.anchor=GridBagConstraints.WEST;
		c.weighty=0.01;
		c.weightx=0.01;
		c.gridy=0;
		add(getNameLabel(), c);
		c.gridy=1;
		add(getDescLabel(), c);
		c.gridy=2;
		add(timeEndLabel, c);
		c.gridx=1;
		c.anchor=GridBagConstraints.EAST;
		add(timeRemainingLabel, c);
	}

	@Override
	protected TimedEvent getEvent() {return alarm;}
}

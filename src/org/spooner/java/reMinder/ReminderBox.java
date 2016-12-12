package org.spooner.java.reMinder;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ReminderBox extends AlarmBox{

	//members
	private JLabel intervalLabel;
	private Reminder reminder;
	
	//constructors
	public ReminderBox(Reminder r) {
		super(r);
		reminder=r;
		intervalLabel=new JLabel("Reminds every "+r.getStringInterval());
		intervalLabel.setFont(MinderConstants.MISC_FONT);
		arrangeComponents();
	}
	
	//methods
	@Override
	public void arrangeComponents() {
		super.arrangeComponents();
		GridBagConstraints c=new GridBagConstraints();
		c.anchor=GridBagConstraints.WEST;
		c.weightx=0.01;
		c.gridx=0;
		c.gridy=4;
		add(intervalLabel, c);
	}
	@Override
	protected TimedEvent getEvent() {return reminder;}
}

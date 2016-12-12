package org.spooner.java.reMinder;

import java.awt.GridBagConstraints;
import java.text.DateFormat;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AlarmDialog extends EventDialog{
	//members
	private JFormattedTextField endTimeBox;
	private JTextField messageBox;
	private JCheckBox isRepeatedBox;
	//constructors
	public AlarmDialog(Alarm a) {
		super(a);
		getTitleLabel().setText("Create a new Alarm");
		endTimeBox=new JFormattedTextField(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US));
		messageBox=new JTextField();
		isRepeatedBox=new JCheckBox("Repeated?");
		endTimeBox.setFont(MinderConstants.NAME_FONT);
		endTimeBox.setBorder(getTitledBorder("Time End in format:              h:mm [AM/PM]"));
		messageBox.setFont(MinderConstants.NAME_FONT);
		messageBox.setBorder(getTitledBorder("Message:"));
		isRepeatedBox.setFont(MinderConstants.NAME_FONT);
		if(a!=null)
			handleFields(a);
		arrangeComponents();
		setVisible(true);
	}

	//methods
	@Override
	protected void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.01;
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;
		add(getTitleLabel(), c);
		c.gridy=1;
		add(getNameField(), c);
		c.gridy=2;
		add(getDescField(), c);
		c.gridy=3;
		add(endTimeBox, c);
		c.gridy=4;
		add(messageBox, c);
		c.gridy=5;
		add(isRepeatedBox, c);
		c.gridy=6;
		c.fill=GridBagConstraints.NONE;
		add(getDoneButton(), c);
	}
	@Override
	protected TimedEvent generateEvent() {
		String name, desc, message;
		name=getNameField().getText();
		desc=getDescField().getText();
		if(desc.isEmpty()) desc="No desc.";
		message=messageBox.getText();
		if(message.isEmpty()) message="Event ended";
		return new Alarm(name, desc, MinderTime.timeToMillis(endTimeBox.getText()),
				isRepeatedBox.isSelected(), message);
	}
	
	@Override
	protected void handleFields(TimedEvent te) {
		super.handleFields(te);
		Alarm a=(Alarm) te;
		
		endTimeBox.setText(MinderTime.millisToTime(a.getTimeEnd()));
		messageBox.setText(a.getMessage());
		isRepeatedBox.setSelected(a.isRepeated());
	}

	@Override
	protected boolean isEmptyFields() {
		return super.isEmptyFields() || endTimeBox.getText().isEmpty(); //message is optional
	}
}

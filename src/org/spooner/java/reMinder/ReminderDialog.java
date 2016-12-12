package org.spooner.java.reMinder;

import java.awt.GridBagConstraints;
import java.text.NumberFormat;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ReminderDialog extends EventDialog{
	//members
	private JFormattedTextField intervalField;
	private JComboBox<String> unitOfTimeBox;
	private JTextField messageBox;
	private JCheckBox isRepeatedBox;
	
	//constructors
	public ReminderDialog(Reminder r) {
		super(r);
		getTitleLabel().setText("Create a new Reminder");
		NumberFormat nf=NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(3);
		intervalField=new JFormattedTextField(nf);
		unitOfTimeBox=new JComboBox<String>(MinderTime.getTimeStrings());
		messageBox=new JTextField();
		isRepeatedBox=new JCheckBox("Repeated?");
		intervalField.setFont(MinderConstants.NAME_FONT);
		intervalField.setBorder(getTitledBorder("Interval:"));
		unitOfTimeBox.setFont(MinderConstants.NAME_FONT);
		unitOfTimeBox.setBorder(getTitledBorder("Unit:"));
		messageBox.setFont(MinderConstants.NAME_FONT);
		messageBox.setBorder(getTitledBorder("Message:"));
		isRepeatedBox.setFont(MinderConstants.NAME_FONT);
		if(r!=null)
			handleFields(r);
		arrangeComponents();
		setVisible(true);
	}

	//methods
	private long getInterval(){
		return Long.parseLong(intervalField.getText()) 
				* MinderTime.getTimeFromString(unitOfTimeBox.getSelectedItem().toString());
	}
	@Override
	protected void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.01;
		c.weighty=0.5;
		c.gridwidth=2;
		c.fill=GridBagConstraints.BOTH;
		add(getTitleLabel(), c);
		c.gridy=1;
		add(getNameField(), c);
		c.gridy=2;
		add(getDescField(), c);
		c.gridy=3;
		c.gridwidth=1;
		add(intervalField, c);
		c.gridx=1;
		add(unitOfTimeBox, c);
		c.gridx=0;
		c.gridy=4;
		c.gridwidth=2;
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
		long interval=getInterval();
		return new Reminder(name, desc, interval, System.currentTimeMillis()+interval, isRepeatedBox.isSelected(), message);
	}
	@Override
	protected boolean isEmptyFields() {
		return super.isEmptyFields() || intervalField.getText().isEmpty(); //message is optional
	}
	@Override
	protected void handleFields(TimedEvent te) {
		super.handleFields(te);
		Reminder r=(Reminder) te;
		int[] intervalParts=MinderTime.divideIntoParts(r.getInterval());
		intervalField.setText(Integer.toString(intervalParts[0]));
		unitOfTimeBox.setSelectedItem(MinderTime.getTimeStringFromNumber(intervalParts[1]));
		isRepeatedBox.setSelected(r.isRepeated());
		messageBox.setText(r.getMessage());
	}
}

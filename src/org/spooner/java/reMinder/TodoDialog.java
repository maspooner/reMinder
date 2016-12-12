package org.spooner.java.reMinder;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class TodoDialog extends EventDialog{
	//members
	private JComboBox<String> colorCombo;
	private JComboBox<String> priorityCombo;
	
	//constructors
	public TodoDialog(Todo t){
		super(t);
		getTitleLabel().setText("Create a new Todo");
		colorCombo=new JComboBox<String>(Todo.getColorsAsStrings());
		priorityCombo=new JComboBox<String>(Todo.getPrioritiesAsStrings());
		colorCombo.setBorder(getTitledBorder("Color:"));
		priorityCombo.setBorder(getTitledBorder("Priority:"));
		if(t!=null)
			handleFields(t);
		
		arrangeComponents();
		setVisible(true);
	}
	
	//methods
	@Override
	public void arrangeComponents() {
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
		add(colorCombo, c);
		c.gridy=4;
		add(priorityCombo, c);
		c.gridy=5;
		c.fill=GridBagConstraints.NONE;
		add(getDoneButton(), c);
	}

	@Override
	protected TimedEvent generateEvent() {
		String name, desc, color, priority;
		name=getNameField().getText();
		desc=getDescField().getText();
		if(desc.isEmpty()) desc="No desc.";
		color=colorCombo.getSelectedItem().toString();
		priority=priorityCombo.getSelectedItem().toString();
		return new Todo(name, desc, Todo.stringToColor(color), 
				Todo.stringToPriority(priority));
	}
	
	@Override
	protected void handleFields(TimedEvent te) {
		super.handleFields(te);
		Todo t=(Todo) te;
		colorCombo.setSelectedItem(t.getColor());
		priorityCombo.setSelectedItem(t.getPriority());
	}

}

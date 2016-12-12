package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public abstract class EventDialog extends JDialog implements ActionListener{
	//TODO adjust fonts as nessessary (ie create new, switch to others etc)
	//members
	private JLabel titleLabel;
	private JTextField nameField;
	private JTextField descField;
	private JButton doneButton;
	private boolean isDonePressed;
	
	//constructors
	public EventDialog(TimedEvent te){
		titleLabel=new JLabel();
		nameField=new JTextField();
		descField=new JTextField();
		doneButton=new JButton("Done!");
		isDonePressed=false;
		titleLabel.setFont(MinderConstants.NAME_FONT);
		nameField.setFont(MinderConstants.NAME_FONT);
		nameField.setBorder(getTitledBorder("Name:"));
		descField.setFont(MinderConstants.NAME_FONT);
		descField.setBorder(getTitledBorder("Description:"));
		doneButton.setFont(MinderConstants.NAME_FONT);
		doneButton.addActionListener(this);
		
		setTitle("New Event");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(MinderConstants.MIN_DIALOG_SIZE);
		setPreferredSize(MinderConstants.PREFERED_DIALOG_SIZE);
		setLayout(new GridBagLayout());
		pack();
	}
	//methods
	public final JLabel getTitleLabel(){return titleLabel;}
	public final JTextField getNameField(){return nameField;}
	public final JTextField getDescField(){return descField;}
	public final JButton getDoneButton(){return doneButton;}
	public final boolean isDonePressed(){return isDonePressed;}
	protected abstract void arrangeComponents();
	protected abstract TimedEvent generateEvent();
	protected void handleFields(TimedEvent te){
		nameField.setText(te.getName());
		descField.setText(te.getDesc());
	}
	protected boolean isEmptyFields(){
		return nameField.getText().isEmpty(); //desc field is optional
	}
	protected final TitledBorder getTitledBorder(String title){
		return BorderFactory.createTitledBorder(MinderConstants.LINE_BORDER, 
				title, TitledBorder.LEFT, TitledBorder.CENTER,
				MinderConstants.MISC_FONT, Color.BLACK);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(!isEmptyFields()){
			isDonePressed=true;
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else{
			JOptionPane.showMessageDialog(this, "Fill all the fields!", "Empty Fields", JOptionPane.ERROR_MESSAGE);
		}
	}
}

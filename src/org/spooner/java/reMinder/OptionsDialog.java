package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class OptionsDialog extends JDialog implements ActionListener{
	//members
	private JCheckBox beepBox;
	private JCheckBox autosaveBox;
	private JSlider saveIntervalSlider;
	private JSlider updateUISlider;
	private JButton doneButton;
	//constructors
	public OptionsDialog(){
		setTitle("reMinder Options");
		setMinimumSize(MinderConstants.MIN_DIALOG_SIZE);
		setPreferredSize(MinderConstants.PREFERED_DIALOG_SIZE);
		setLayout(new GridBagLayout());
		setModal(true);
		
		addComponents();
		setVisible(true);
	}
	//methods
	private void addComponents(){
		beepBox=new JCheckBox("Beep upon event end", MinderOptions.doBeep);
		autosaveBox=new JCheckBox("Autosave?", MinderOptions.doAutosave);
		saveIntervalSlider=setupSlider(MinderConstants.MAX_SAVE_INTERVAL, MinderConstants.MIN_SAVE_INTERVAL,
				MinderConstants.MAJOR_SAVE_INTERVAL, MinderConstants.MINOR_SAVE_INTERVAL, MinderOptions.saveInterval);
		updateUISlider=setupSlider(MinderConstants.MAX_UI_INTERVAL, MinderConstants.MIN_UI_INTERVAL,
				MinderConstants.MAJOR_UI_INTERVAL, MinderConstants.MINOR_UI_INTERVAL, MinderOptions.updateInterval);
		doneButton=new JButton("Done");
		
		saveIntervalSlider.setBorder(getTitledBorder("How often to autosave:"));
		updateUISlider.setBorder(getTitledBorder("How often to update the UI:"));
		doneButton.setFont(MinderConstants.NAME_FONT);
		doneButton.addActionListener(this);
		
		arrangeComponents();
	}
	
	private void arrangeComponents(){
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.1;
		c.weighty=0.1;
		c.fill=GridBagConstraints.BOTH;
		add(beepBox, c);
		c.gridy=1;
		add(autosaveBox, c);
		c.gridy=2;
		add(saveIntervalSlider, c);
		c.gridx=1;
		add(updateUISlider, c);
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.fill=GridBagConstraints.NONE;
		add(doneButton, c);
	}
	
	private JSlider setupSlider(int max, int min, int majInter, int minInter, int start){
		JSlider slider=new JSlider(JSlider.VERTICAL, min, max, start);
		
		slider.setMajorTickSpacing(majInter);
		slider.setMinorTickSpacing(minInter);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		//set the slider's labels to the string equalivents of milliseconds
		Hashtable<Integer, JLabel> ht=new Hashtable<Integer, JLabel>();
		//add min value
		ht.put(min, new JLabel(getTimeString(min)));
		//add other values STARTING AT 0
		for(int j=0;j<=max;j+=majInter){
			ht.put(j, new JLabel(getTimeString(j)));
		}
		//add max value
		ht.put(max, new JLabel(MinderTime.toTime(max)));
		slider.setLabelTable(ht);
		return slider;
	}
	
	private TitledBorder getTitledBorder(String name){
		return BorderFactory.createTitledBorder(MinderConstants.ETCHED_BORDER, 
				name, TitledBorder.LEFT, TitledBorder.CENTER,
				MinderConstants.MISC_FONT, Color.BLACK);
	}
	
	private String getTimeString(int millis){
		//millisecond precision needed, 2nd part to not display "done" in place of 1 second
		if(millis%1000!=0 || millis==1000) return MinderTime.getSecondString(millis);
		else return MinderTime.toTime(millis);
	}
	
	private void applyToOptions(){
		MinderOptions.doBeep=beepBox.isSelected();
		MinderOptions.doAutosave=autosaveBox.isSelected();
		MinderOptions.saveInterval=saveIntervalSlider.getValue();
		MinderOptions.updateInterval=updateUISlider.getValue();
		MinderOptions.print();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		applyToOptions();
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}

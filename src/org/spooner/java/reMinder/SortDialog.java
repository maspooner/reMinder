package org.spooner.java.reMinder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;

@SuppressWarnings("serial")
public class SortDialog extends JDialog implements ActionListener{
	//members
	private JList<String> eventList;
	private DefaultListModel<String> listModel;
	private JButton toTopButton;
	private JButton toBottomButton;
	private JButton upOneButton;
	private JButton downOneButton;
	private JButton doneButton;
	//consturctors
	public SortDialog(){
		setTitle("Sort Events");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(MinderConstants.MIN_DIALOG_SIZE);
		setPreferredSize(MinderConstants.PREFERED_DIALOG_SIZE);
		setLayout(new GridBagLayout());
		pack();
		
		addComponents();
		arrangeComponents();
		setVisible(true);
	}
	//methods

	private final void addComponents() {
		setupList();
		
		toTopButton=new JButton("To Top");
		toBottomButton=new JButton("To Bottom");
		upOneButton=new JButton("Up");
		downOneButton=new JButton("Down");
		setupMoveButton(toTopButton);
		setupMoveButton(toBottomButton);
		setupMoveButton(upOneButton);
		setupMoveButton(downOneButton);
		
		doneButton=new JButton("Done");
		doneButton.setFont(MinderConstants.NAME_FONT);
		doneButton.addActionListener(this);
	}

	private final void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.1;
		c.weighty=0.2;
		c.fill=GridBagConstraints.BOTH;
		c.gridwidth=4;
		add(eventList, c);
		c.fill=GridBagConstraints.NONE;
		c.gridy=1;
		c.gridwidth=1;
		add(toTopButton, c);
		c.gridx=1;
		add(toBottomButton, c);
		c.gridx=2;
		add(upOneButton, c);
		c.gridx=3;
		add(downOneButton, c);
		c.gridwidth=4;
		c.gridx=0;
		c.gridy=2;
		add(doneButton, c);
	}
	
	private final void setupList(){
		listModel=new DefaultListModel<String>();
		eventList=new JList<String>(listModel);
		eventList.setFont(MinderConstants.DESC_FONT);
		eventList.setFixedCellHeight(40);
		
		for(TimedEvent te : Minder.getEvents())
			listModel.addElement(te.getName());
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String ac=ae.getActionCommand();
		int index=eventList.getSelectedIndex();
		if(ac.equals("Done")){
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else{
			if(eventList.getSelectedIndex()==-1){
				Toolkit.getDefaultToolkit().beep();
			}
			else{
				//changing order, must update
				Minder.shouldUpdate=true;
				
				if(ac.equals("To Top")) moveToTop(index);
				else if(ac.equals("To Bottom")) moveToBottom(index);
				else if(ac.equals("Up")) moveUpOne(index);
				else if(ac.equals("Down")) moveDownOne(index);
				
			}
		}
	}
	
	private void setupMoveButton(JButton button){
		button.setFont(MinderConstants.DESC_FONT);
		button.addActionListener(this);
	}
	
	private void moveToTop(int i){
		if(i==0){
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		TimedEvent te=Minder.removeEvent(i);
		Minder.addEvent(0, te);
		redrawEventList();
		eventList.setSelectedIndex(0);
	}
	private void moveToBottom(int i){
		if(i==Minder.getEventSize()-1){
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		TimedEvent te=Minder.removeEvent(i);
		Minder.addEvent(te);
		redrawEventList();
		eventList.setSelectedIndex(Minder.getEventSize());
	}
	private void moveUpOne(int i){
		if(i-1<0){
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		Minder.swapEvents(i, i-1);
		swapListEvents(i, i-1);
		eventList.setSelectedIndex(i-1);
	}
	private void moveDownOne(int i){
		if(i+1==Minder.getEventSize()){
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		Minder.swapEvents(i, i+1);
		swapListEvents(i, i+1);
		eventList.setSelectedIndex(i+1);
	}
	private void swapListEvents(int i, int j){
		String iString = listModel.getElementAt(i);
        String jString = listModel.getElementAt(j);
        listModel.set(i, jString);
        listModel.set(j, iString);
	}
	private void redrawEventList(){
		listModel.removeAllElements();
		
		for(TimedEvent te : Minder.getEvents())
			listModel.addElement(te.getName());
	}
}

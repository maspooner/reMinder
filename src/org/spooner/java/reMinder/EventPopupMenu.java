package org.spooner.java.reMinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class EventPopupMenu extends JPopupMenu implements ActionListener{
	//members
	private JMenuItem editItem;
	private JMenuItem destroyItem;
	//constructors
	public EventPopupMenu(String name){
		setupItem(new JMenuItem("Event: "+name));
		editItem=new JMenuItem("[ Edit ]");
		destroyItem=new JMenuItem("[ Destroy ]");
		setupItem(editItem);
		setupItem(destroyItem);
	}
	//methods
	private void setupItem(JMenuItem item){
		item.setFont(MinderConstants.MISC_FONT);
		item.addActionListener(this);
		add(item);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		EventBox eb=(EventBox) getInvoker();
		if(e.getActionCommand().equals("[ Edit ]")){
			EventDialog ed;
			//open correct dialog with event info in place
			if(eb instanceof TodoBox) ed=new TodoDialog((Todo) eb.getEvent());
			else if(eb instanceof ReminderBox) ed=new ReminderDialog((Reminder) eb.getEvent());
			else ed=new AlarmDialog((Alarm) eb.getEvent());
			//tell to refresh the UI
			if(ed.isDonePressed()){
				TimedEvent alteredEvent=ed.generateEvent();
				Minder.setEvent(eb.getEvent(), alteredEvent);
				Minder.shouldUpdate=true;
			}
		}
		else if(e.getActionCommand().equals("[ Destroy ]")){
			Minder.removeEvent(eb.getEvent());
		}
	}
}

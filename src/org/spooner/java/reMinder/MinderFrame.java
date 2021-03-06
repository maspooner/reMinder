package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MinderFrame extends JFrame implements Runnable, WindowListener{
	//members
	private JScrollPane scrollPane;
	private JPanel eventPanel;
	private Thread updateUI;
	private MinderBar menuBar;
	
	//constructors
	public MinderFrame(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				init();
			}
		});
	}
	
	//methods
	private final void init(){
		scrollPane=new JScrollPane();
		eventPanel=new JPanel();
		updateUI=new Thread(this);
		//frame stuff
		setTitle(MinderConstants.NAME);
		setIconImage(Minder.getImage("icon.png"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		setPreferredSize(MinderConstants.PREFERED_FRAME_SIZE);
		setMinimumSize(MinderConstants.MIN_FRAME_SIZE);
		getContentPane().add(scrollPane);
		
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		
		scrollPane.setViewportView(eventPanel);
		scrollPane.setBackground(Color.white);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		setJMenuBar(new MinderBar());
		pack();
		setVisible(true);
		updateUI.start();
	}
	
	private final void addAllEvents(ArrayList<TimedEvent> tes){
		boolean isGray=false;
		eventPanel.removeAll();
		for(TimedEvent te : tes){
			addEvent(te, isGray);
			isGray=!isGray;
		}
	}
	
	private final void addEvent(TimedEvent te, boolean isGray){
		if(te instanceof Todo){
			eventPanel.add(new TodoBox((Todo) te, isGray));
		}
		else if(te instanceof Reminder){
			eventPanel.add(new ReminderBox((Reminder) te, isGray));
		}
		else if(te instanceof Alarm){
			eventPanel.add(new AlarmBox((Alarm) te, isGray));
		}
	}
	
	private final void showDialog(String text, String name){
		String message="Event: "+name+" has ended.          Message:   "+text;
		if(MinderOptions.doBeep)
			Minder.beep();
		MinderTray.showMessage(message);
	}
	private void setJMenuBar(MinderBar mb) {
		super.setJMenuBar(mb);
		menuBar=mb;
	}
	
	private void handleCommand(String actionCommand){
		if(actionCommand.equals("About")){
			JOptionPane.showMessageDialog(this, MinderConstants.ABOUT, "About Minder "+MinderConstants.VERSION, JOptionPane.INFORMATION_MESSAGE);
		}
		else if(actionCommand.equals("Sort")){
			new SortDialog();
		}
		else{
			//else show one of the 3 dialogs
			EventDialog ed;
			if(actionCommand.equals("Todo...")) ed=new TodoDialog(null);
			else if(actionCommand.equals("Alarm...")) ed=new AlarmDialog(null);
			else ed=new ReminderDialog(null);
			
			//add event if done button pressed
			if(ed.isDonePressed())
				Minder.addEvent(ed.generateEvent());
		}
	}
	
	@Override
	public void run() {
		while(true){
			//check for action command
			if(menuBar.getActionCommand().length()!=0){
				handleCommand(menuBar.getActionCommand());
				menuBar.deleteCommand();
			}
			//update components check
			if(eventPanel.getComponentCount() != Minder.getEventSize() || Minder.shouldUpdate){
				Minder.shouldUpdate=false;
				addAllEvents(Minder.getEvents());
			}
			else{
				for(Component c : eventPanel.getComponents()){
					EventBox box=(EventBox) c;
					box.update();
					if(box instanceof AlarmBox){
						AlarmBox aBox=(AlarmBox) box;
						if (aBox.isMessageToDisplay()){
							Alarm a=(Alarm) aBox.getEvent();
							//show popup again if repeated
							if(!a.isRepeated()) a.setShouldShowPopup(false);
							showDialog(aBox.getMessage(), aBox.getEventName());
						}
					}
				}
			}
			revalidate();
			repaint();
			try {
				Thread.sleep(MinderOptions.updateInterval);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void close(){
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		Minder.manualSave();
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
}

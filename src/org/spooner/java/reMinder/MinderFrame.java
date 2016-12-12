package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class MinderFrame extends JFrame implements Runnable, WindowListener{
	//members
	private JScrollPane scrollPane;
	private JPanel eventPanel;
	private Thread updateUI;
	private MinderBar menuBar;
	private Image icon;
	private TrayIcon trayIcon;
	
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
		icon=Minder.getImage("icon.png");
		initTray();
		//frame stuff
		setTitle(MinderConstants.NAME);
		setIconImage(icon);
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
		eventPanel.removeAll();
		for(TimedEvent te : tes)
			addEvent(te);
	}
	
	private final void initTray(){
		trayIcon=new TrayIcon(icon, MinderConstants.NAME);
		trayIcon.setImageAutoSize(true);
		try{
			SystemTray.getSystemTray().add(trayIcon);
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(8);
		}
	}
	
	private final void addEvent(TimedEvent te){
		if(te instanceof Todo){
			eventPanel.add(new TodoBox((Todo) te));
		}
		else if(te instanceof Reminder){
			eventPanel.add(new ReminderBox((Reminder) te));
		}
		else if(te instanceof Alarm){
			eventPanel.add(new AlarmBox((Alarm) te));
		}
	}
	
	private final void showDialog(String text, String name){
		String message="Event: "+name+" has ended.          Message:   "+text;
		if(MinderOptions.doBeep)
			Toolkit.getDefaultToolkit().beep();
//		JOptionPane.showMessageDialog(this, message, "Event Done", JOptionPane.WARNING_MESSAGE, null);
		trayIcon.displayMessage("Event Done", message, TrayIcon.MessageType.WARNING);
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
		// TODO
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
		System.exit(0);//FIXME get rid of when implementing non-GUI mode
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
}

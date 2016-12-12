package org.spooner.java.reMinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MinderBar extends JMenuBar implements ActionListener{
	//members
	private String actionCommand;
	//constructors
	public MinderBar(){
		actionCommand="";
		initMenus();
	}
	
	//methods
	public final String getActionCommand() {return this.actionCommand;}
	public final void deleteCommand(){this.actionCommand="";}
	private final void initMenus(){
		JMenu menu=new JMenu("File");
		//file menu
		menu.add(getNewMenuItem("Save"));
		menu.add(getNewMenuItem("Options"));
		menu.add(getNewMenuItem("About"));
		menu.add(getNewMenuItem("Exit"));
		add(menu);
		//add menu
		menu=new JMenu("Add");
		menu.add(getNewMenuItem("Todo..."));
		menu.add(getNewMenuItem("Alarm..."));
		menu.add(getNewMenuItem("Reminder..."));
		add(menu);
		//sort menu
		menu=new JMenu("Sorting");
		menu.add(getNewMenuItem("Sort"));
		add(menu);
	}
	
	private JMenuItem getNewMenuItem(String name){
		JMenuItem item=new JMenuItem(name);
		item.addActionListener(this);
		return item;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String ac=ae.getActionCommand();
		if(ac.equals("Exit")){
			System.exit(0);
		}
		else if(ac.equals("Save")){
			Minder.manualSave();
		}
		else if(ac.equals("Options")){
			new OptionsDialog();
		}
		else{
			//it's one of the add or sort commands
			this.actionCommand=ac;
		}
	}
}

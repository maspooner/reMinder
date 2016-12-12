package org.spooner.java.reMinder;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinderTray implements ActionListener{
	//members
	private static TrayIcon trayIcon;
	private static PopupMenu popup;
	//constructors
	public MinderTray(){
		popup=new PopupMenu();
		MenuItem mi=new MenuItem("Toggle GUI");
		mi.addActionListener(this);
		popup.add(mi);
		initTray();
	}
	//methods
	private final void initTray(){
		trayIcon=new TrayIcon(Minder.getImage("icon.png"), MinderConstants.NAME, popup);
		trayIcon.setImageAutoSize(true);
		try{
			SystemTray.getSystemTray().add(trayIcon);
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(8);
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Minder.toggleGUI();
	}
	public static void showMessage(String message){
		trayIcon.displayMessage("Event Done", message, TrayIcon.MessageType.WARNING);
	}
}

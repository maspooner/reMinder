package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public abstract class EventBox extends JPanel implements MouseListener{
	private static int num=0;//TODO bug later?
	
	//members
	private JLabel nameLabel;
	private JLabel descLabel;
	private EventPopupMenu popup;
	
	//constructors
	public EventBox(TimedEvent te){
		nameLabel=new JLabel(getShortenedName(te.getName()));
		descLabel=new JLabel(getShortenedDesc(te.getDesc()));
		popup=new EventPopupMenu(getShortenedName(te.getName()));
		nameLabel.setFont(MinderConstants.NAME_FONT);
		descLabel.setFont(MinderConstants.DESC_FONT);
		addMouseListener(this);
		setBackground(num%2==0 ? Color.LIGHT_GRAY : Color.WHITE);//TODO change?
		
		String typeString=this.getClass().getSimpleName().replaceFirst("Box", "")+" Event";
		setBorder(BorderFactory.createTitledBorder(MinderConstants.LINE_BORDER, 
				typeString, TitledBorder.LEFT, TitledBorder.CENTER, 
				MinderConstants.DESC_FONT, Color.MAGENTA));
		setLayout(new GridBagLayout());
		
		num++;
	}
	
	//methods
	private String getShortenedName(String name){return name.length()<=50 ? name :name.substring(0, 50)+"...";}//TODO adjust? this and bellow
	private String getShortenedDesc(String desc){return desc.length()<=50 ? desc :desc.substring(0, 50)+"...";}
	public final JLabel getNameLabel(){return nameLabel;}
	public final JLabel getDescLabel(){return descLabel;}
	protected abstract void update();
	protected abstract void arrangeComponents();
	protected abstract TimedEvent getEvent();
	@Override
	public void mouseReleased(MouseEvent e) {
		popup.show(e.getComponent(), e.getX(), e.getY());
	};
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
}

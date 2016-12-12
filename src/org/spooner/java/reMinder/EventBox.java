package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public abstract class EventBox extends JPanel implements MouseListener{
	//members
	private JLabel nameLabel;
	private JLabel descLabel;
	private EventPopupMenu popup;
	
	//constructors
	public EventBox(TimedEvent te, boolean isGray){
		nameLabel=new JLabel(getShortenedName(te.getName()));
		descLabel=new JLabel(getShortenedDesc(te.getDesc()));
		popup=new EventPopupMenu(getShortenedName(te.getName()));
		nameLabel.setFont(MinderConstants.NAME_FONT);
		descLabel.setFont(MinderConstants.DESC_FONT);
		addMouseListener(this);
		setBackground(isGray ? Color.LIGHT_GRAY : Color.WHITE);
		
		String typeString=this.getClass().getSimpleName().replaceFirst("Box", "")+" Event";
		setBorder(BorderFactory.createTitledBorder(MinderConstants.LINE_BORDER, 
				typeString, TitledBorder.LEFT, TitledBorder.CENTER, 
				MinderConstants.DESC_FONT, Color.MAGENTA));
		setLayout(new GridBagLayout());
	}
	
	//methods
	private String getShortenedName(String name){return name.length()<=50 ? name :name.substring(0, 50)+"...";}
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

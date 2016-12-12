package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;

import javax.swing.*;


@SuppressWarnings("serial")
public class TodoBox extends EventBox{
	//static members
	private static final Font priorFont=new Font(Font.SANS_SERIF, Font.BOLD, 15);
	
	//members
	private ColoredBox coloredBox;
	private JLabel priorityLabel;
	private Todo todo;
	
	//constructors
	public TodoBox(Todo t) {
		super(t);
		todo=t;
		coloredBox=new ColoredBox(t.getColor());
		priorityLabel=new JLabel("Priority: "+t.getPriority());
		priorityLabel.setForeground(getPriorityColor(t.getPriority()));
		priorityLabel.setFont(priorFont);
		
		arrangeComponents();
	}

	//methods
	@Override
	public void update() {}
	@Override
	public void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		
		c.weighty=0.1;
		c.gridheight=3;
		c.anchor=GridBagConstraints.WEST;
		c.gridx=0;
		c.gridy=0;
		add(coloredBox, c);
		c.weightx=0.2;
		c.gridheight=1;
		c.gridx=1;
		c.gridy=0;
		add(priorityLabel, c);
		c.gridy=1;
		add(getNameLabel(), c);
		c.gridy=2;
		add(getDescLabel(), c);
	}
	
	private Color getPriorityColor(String priority){
		if(priority.equals("HIGH")) return Color.RED;
		else if(priority.equals("MED")) return Color.ORANGE;
		else if(priority.equals("LOW")) return Color.GREEN;
		else return Color.BLUE;
	}
	@Override
	protected TimedEvent getEvent() {return todo;}
	
	//classes
	private static class ColoredBox extends JComponent{
		//static members
		private static final Dimension PREFERED_SIZE=new Dimension(105,105);
		
		//members
		private Color boxColor;
		
		//constructors
		public ColoredBox(String color){
			boxColor=getColor(color);
		}
		
		//methods
		private Color getColor(String color){
			if(color.equals("BLUE")) return Color.BLUE;
			else if(color.equals("RED")) return Color.RED;
			else if(color.equals("YELLOW")) return Color.ORANGE;
			else if(color.equals("CYAN")) return Color.CYAN;
			else if(color.equals("GREEN")) return Color.GREEN;
			else if(color.equals("PURPLE")) return Color.MAGENTA;
			else return Color.BLACK;
		}
		@Override
		public Dimension getPreferredSize() {return PREFERED_SIZE;}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(boxColor);
			g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		}
	}
}

package org.spooner.java.reMinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public abstract class MinderConstants {
	//contains all constants
	//static members
	protected static final String VERSION="v1.2.2";
	protected static final String NAME="reMinder "+VERSION;
	protected static final String ABOUT=NAME+"\nMade by Matt Spooner" +
			"\nLast Modified:Mar 21, 2014\nDate Started:Sep 19, 2013" +
			"\nAbout this project:\nSince the majority of programs I made up until this point were games," +
			"\nI decided I wanted to create a utility. I decided to make an app that kept track of what I" +
			"\nneeded to do and remind me about it. I had downloaded a todo app before, but it was too complex" +
			"\nfor what I wanted it to be. So this project was one that I was motivated for, as it would be" +
			"\nsomething I would use regularly. I am very happy with how it turned out and I use it to this day.";
	//GUI
	protected static final Font NAME_FONT=new Font(Font.SANS_SERIF, Font.BOLD, 25);
	protected static final Font DESC_FONT=new Font(Font.SANS_SERIF, Font.ITALIC, 20);
	protected static final Font MISC_FONT=new Font(Font.SANS_SERIF, Font.PLAIN, 15);
	protected static final Border LINE_BORDER=BorderFactory.createLineBorder(Color.BLACK, 2, true);
	protected static final Border ETCHED_BORDER=BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY);
	//sizes
	protected static final Dimension MIN_FRAME_SIZE=new Dimension(600,400);
	protected static final Dimension PREFERED_FRAME_SIZE=new Dimension(800,600);
	protected static final Dimension MIN_DIALOG_SIZE=new Dimension(500,400);
	protected static final Dimension PREFERED_DIALOG_SIZE=new Dimension(600,500);
	//options
	protected static final int MAX_SAVE_INTERVAL=3600000;//1 hour
	protected static final int MIN_SAVE_INTERVAL=5000;//5 seconds
	protected static final int MAJOR_SAVE_INTERVAL=300000; //5 minutes
	protected static final int MINOR_SAVE_INTERVAL=150000; //2.5 minutes
	protected static final int MAX_UI_INTERVAL=3000;//3 seconds
	protected static final int MIN_UI_INTERVAL=100;//0.1 second
	protected static final int MAJOR_UI_INTERVAL=500;//0.5 second
	protected static final int MINOR_UI_INTERVAL=250;//0.25 second
}

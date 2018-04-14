package edu.cmu.cs.cs214.hw4.gui;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Works as the action listener for the special tiles button, the board buttons, and the letter tile buttons.
 * 
 * @author Akhil Prakash
 *
 * @param <A> arbitrary type
 */
public class ActionListenerWrapper<A> implements ActionListener
{

	private List<A> toBePlayed;
	private A toAdd;
	private JButton button;
	
	public ActionListenerWrapper(List<A> toBePlayed, A toAdd, JButton button)
	{
		this.toBePlayed = toBePlayed;
		this.toAdd = toAdd;
		this.button = button;
	}

	@Override
	/**
	 * Adds whatever you clicked on to the list.
	 * Then disables the button that was just clicked on.
	 */
	public void actionPerformed(ActionEvent e)
	{
		toBePlayed.add(toAdd);
		button.setEnabled(false);
	}
}

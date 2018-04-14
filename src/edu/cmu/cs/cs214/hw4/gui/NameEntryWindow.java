package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Makes the first window that pops up. This window is used for adding players to the game.
 * 
 * @author Akhil Prakash
 */
public class NameEntryWindow extends JPanel 
{
	private static final long serialVersionUID = -7258071600712637251L;
	private static final int NUM_COLUMNS = 15;
	private final List<String> playersNames;
	
	public NameEntryWindow() 
	{
		playersNames = new ArrayList<String> ();
	        JLabel nameLabel = new JLabel("Name: ", SwingConstants.LEFT);
	        add(nameLabel);
	        
	        final JTextField textbox = new JTextField(NUM_COLUMNS);
	        add(textbox);
	        
	        JButton addPlayerButton = new JButton("Add Player");
	        add(addPlayerButton);
	        
	        addPlayerButton.addActionListener(new ActionListener() 
	        {
	                @Override
	                public void actionPerformed(ActionEvent e) 
	                {
	                	String name = textbox.getText();
	                	/*
	                	 * Makes sure names are unique and non empty
	                	 */
				if (!name.isEmpty() && !playersNames.contains(name)) 
				{
					playersNames.add(name);
				}
				textbox.setText("");
				textbox.requestFocus();
	                }
	        });

	}

	protected List<String> getPlayers()
	{
		return playersNames;
	}
}
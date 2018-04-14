package edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles;

import java.awt.Color;

/**
 * Pulls out common code for all scrabble defined tiles
 * 
 * @author Akhil Prakash
 */
public abstract class ScrabbleDefinedTile
{

	/**
	 * true  if we have to used the multiplier yet
	 * false if we have already used the multiplier
	 */
	private boolean isActive;
	
	public ScrabbleDefinedTile()
	{
		isActive = true;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public void deactivate()
	{
		isActive = false;
	}
	
	public abstract int wordMultiplier();
	
	public abstract int letterMultiplier();
	
	public abstract Color getBackgroundColor();
}

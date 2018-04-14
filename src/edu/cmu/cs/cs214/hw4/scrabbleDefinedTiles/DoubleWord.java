package edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles;

import java.awt.Color;

/**
 * Represents a double word tile in scrabble
 * 
 * @author Akhil Prakash
 */
public class DoubleWord extends ScrabbleDefinedTile
{

	@Override
	/** @{inheritDoc} */
	public int wordMultiplier()
	{
		return 2;
	}

	@Override
	/** @{inheritDoc} */
	public int letterMultiplier()
	{
		return 1;
	}

	@Override
	public String toString()
	{
		return "Double Word";
	}

	@Override
	public Color getBackgroundColor()
	{
		return Color.GREEN;
	}
}

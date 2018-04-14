package edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles;

import java.awt.Color;

/**
 * Represents a double letter tile in scrabble
 * 
 * @author Akhil Prakash
 */
public class DoubleLetter extends ScrabbleDefinedTile
{

	@Override
	/** @{inheritDoc} */
	public int wordMultiplier()
	{
		return 1;
	}

	@Override
	/** @{inheritDoc} */
	public int letterMultiplier()
	{
		return 2;
	}

	@Override
	public String toString()
	{
		return "Double Letter";
	}

	@Override
	public Color getBackgroundColor()
	{
		return Color.BLUE;
	}
}

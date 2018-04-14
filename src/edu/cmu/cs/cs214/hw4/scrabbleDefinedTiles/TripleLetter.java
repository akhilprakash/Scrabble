package edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles;

import java.awt.Color;

/**
 * Represents a triple letter scrabble defined tile
 * 
 * @author Akhil Prakash
 */
public class TripleLetter extends ScrabbleDefinedTile
{

	@Override
	public int wordMultiplier()
	{
		return 1;
	}

	@Override
	public int letterMultiplier()
	{
		return 3;
	}

	@Override
	public String toString()
	{
		return "Triple Letter";
	}

	@Override
	public Color getBackgroundColor()
	{
		return Color.ORANGE;
	}
}

package edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles;

import java.awt.Color;

/**
 * Represents a triple word scrabble defined tile
 * 
 * @author Akhil Prakash
 */
public class TripleWord extends ScrabbleDefinedTile
{

	@Override
	public int wordMultiplier()
	{
		return 3;
	}

	@Override
	public int letterMultiplier()
	{
		return 1;
	}

	@Override
	public String toString()
	{
		return "Triple Word";
	}

	@Override
	public Color getBackgroundColor()
	{
		return Color.RED;
	}
}

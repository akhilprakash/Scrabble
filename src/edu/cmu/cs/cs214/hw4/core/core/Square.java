package edu.cmu.cs.cs214.hw4.core;

import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.ScrabbleDefinedTile;
import edu.cmu.cs.cs214.hw4.specialTiles.SpecialTile;

/**
 * Represents a single square on a board. I am only letting there be one special tile per square.
 * Basic class which just contains getters and setters to get which ever type of tile we want.
 * 
 * @author Akhil Prakash
 */
public class Square
{

	private final Location LOCATION;
	private LetterTile tile;
	private SpecialTile specialTile;
	private ScrabbleDefinedTile defaultTile;
	
	public Square(Location location, LetterTile tile, SpecialTile specialTile, ScrabbleDefinedTile defaultTile)
	{
		LOCATION = location;
		this.tile = tile;
		this.specialTile = specialTile;
		this.defaultTile = defaultTile;
	}
	
	public LetterTile getTile()
	{
		return tile;
	}
	
	public void setTile(LetterTile tile)
	{
		this.tile = tile;
	}
	
	public SpecialTile getSpecialTile()
	{
		return specialTile;
	}
	
	public void setSpecialTile(SpecialTile specialTile)
	{
		this.specialTile = specialTile;
	}
	
	public ScrabbleDefinedTile getDefaultTile()
	{
		return defaultTile;
	}
	
	public void setDefaultTile(ScrabbleDefinedTile defaultTile)
	{
		this.defaultTile = defaultTile;
	}
	
	/**
	 * Used for debugging
	 */
	public String toString(Player currentPlayer)
	{
		String result = LOCATION.toString() + " ";
		if (tile != null)
		{
			result += tile.toString();
			
		}
		else
		{
			result += "Letter tile: null";
		}
		result += " Scrabble default tile: ";
		if (defaultTile != null)
		{
			result += defaultTile.toString();
		}
		else
		{
			result += "null";
		}
		result += " Special Tile : ";
		if (specialTile != null && specialTile.isVisible(currentPlayer))
		{
			result +=  specialTile.toString();
		}
		else 
		{
			result += "null";
		}
		return result;
	}
}

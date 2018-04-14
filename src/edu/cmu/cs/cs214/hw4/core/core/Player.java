package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;
import edu.cmu.cs.cs214.hw4.specialTiles.SpecialTile;

/**
 * Represents a single person playing the scrabble game
 * 
 * @author Akhil Prakash
 */
public class Player
{

	private final String name;	
	private int score;
	private final List<LetterTile> tiles;
	private final List<SpecialTile> specialTiles;
	protected static final int HAND_SIZE = 7;
	
	public Player(String name)
	{
		score = 0;
		tiles = new ArrayList<LetterTile> (HAND_SIZE);
		specialTiles = new ArrayList<SpecialTile> ();
		this.name = name;
	}
	
	/**
	 * Adds a tile to the player's hand
	 * 
	 * @param tile the tile to be added
	 */
	public void addTileToHand(LetterTile tile)
	{
		tiles.add(tile);
	}
	
	/**
	 * Adds a special tile to the player's hand
	 * 
	 * @param tile the tile to be added
	 */
	public void addSpecailTileToHand(SpecialTile tile)
	{
		specialTiles.add(tile);
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void addToScore(int addition)
	{
		score += addition;
	}
	
	/**
	 * Removes a single instance of the tile from the player's hand
	 * 
	 * @param tile the tile to be removed
	 * 
	 * Precondition: tile should be in tiles. In other words, tiles.contains(tile) should return true
	 */
	public void removeTileFromHand(LetterTile tile)
	{
		tiles.remove(tile);
	}
	
	/**
	 * Removes a single instance of the special tile form the player's hand
	 * 
	 * @param tile the special tile to be removed
	 * 
	 * Precondition: tile should be in specialTiles. In other words, specialTiles.contains(tile) should return true
	 */
	public void removeSpecialTileFromHand(SpecialTile tile)
	{
		specialTiles.remove(tile);
	}

	/*
	 * only used for testing
	 * 
	 * NOTE: this returns a deep copy of the list because people should only be able to interact with
	 * tiles list through the removeTileFromHand and addTileToHand 
	 */
	public List<LetterTile> getTiles()
	{
		List<LetterTile> deepCopy = new ArrayList<LetterTile> ();
		deepCopy.addAll(tiles);
		return deepCopy;
	}

	/*
	 * only used for testing
	 * 
	 * NOTE: this returns a deep copy of the list because people should only be able to interact with
	 * specialTiles list through the removeSpecailTileFRomHand and addSpecialTileToHand 
	 */
	public List<SpecialTile> getSpecialTiles()
	{
		List<SpecialTile> deepCopy = new ArrayList<SpecialTile> ();
		deepCopy.addAll(specialTiles);
		return deepCopy;
	}

	public String getName()
	{
		return name;
	}
	
	public int getSizeOfTileHand()
	{
		return tiles.size();
	}
	
	public int getSizeOfSpecialTileHand()
	{
		return specialTiles.size();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (! (obj instanceof Player))
		{
			return false;
		}
		Player player = (Player) obj;
		return player.getName().equals(name);
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}

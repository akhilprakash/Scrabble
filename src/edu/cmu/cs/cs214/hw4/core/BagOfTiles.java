package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * Stores the bag that contains all the tiles.
 * We can interact with the bag by asking it if it is empty, taking a tile out of the bag, and putting a tile
 * in the bag.
 * 
 * @author Akhil Prakash
 */
public class BagOfTiles
{

	/**
	 * Invariant: bag.size() <= MAX_SIZE
	 */
	private final List<LetterTile> bag;
	private static final int MAX_SIZE = 98;
	
	public BagOfTiles()
	{
		/*
		 * I have to have this line outside of createBat otherwise the compiler
		 * thinks that createBag will be called multiple times for the same instance of BagOfTiles
		 * and thus the final part of the bag will be incorrect
		 */
		bag = new ArrayList<LetterTile> (MAX_SIZE);
		createBag();
	}
	
	/**
	 * @return true  if there are no tiles in the bag
	 *         false otherwise
	 */
	public boolean isEmpty()
	{
		return bag.size() == 0;
	}
	
	/**
	 * @return a tile from the bag. that tile is no longer in the bag
	 */
	public LetterTile drawTile()
	{
		if (isEmpty())
		{
			throw new IllegalStateException("Tried to draw a tile but there were no tiles");
		}
		return bag.remove((int) (Math.random() * bag.size()));
	}
	
	/**
	 * Add a tile to the bag
	 * 
	 * @param tile the tile to be added
	 */
	public void putTileInBag(LetterTile tile)
	{
		bag.add(tile);
	}
	
	/**
	 * Initially, add all the tiles to the bag
	 * 
	 * I copied the distribution of letters, the quantity of letters, and the score
	 * for each letter from the wikipedia article
	 */
	private void createBag()
	{
		Triple[] letterScoreQuantity = new Triple[] {
			new Triple('e', 1, 12), new Triple('a', 1, 9), new Triple('i', 1, 9), new Triple('o', 1, 8),
			new Triple('n', 1, 6), new Triple('r', 1, 6),
			new Triple('t', 1, 6), new Triple('l', 1, 4), new Triple('s', 1, 4), new Triple('u', 1, 4), 
			new Triple('d', 2, 4), new Triple('g', 2, 3),
			new Triple('b', 3, 2), new Triple('c', 3, 2), new Triple('m', 3, 2), new Triple('p', 3, 2),
			new Triple('f', 4, 2), new Triple('h', 4, 2), new Triple('v', 4, 2), new Triple('w', 4, 2),
			new Triple('y', 4, 2),
			new Triple('k', 5, 1),
			new Triple('j', 8, 1), new Triple('x', 8, 1),
			new Triple('q', 10, 1), new Triple('z', 10, 1)
		};
		
		for (Triple t : letterScoreQuantity)
		{
			
			Character letter = t.getFirst();
			int score = t.getSecond();
			int quantity = t.getThird();
			for (int i = 0; i < quantity; i ++)
			{
				bag.add(new LetterTile(letter, score));
			}
		}
		
	}
	
}

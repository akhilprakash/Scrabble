package edu.cmu.cs.cs214.hw4.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * I pulled out the common algorithms that do not depend on the game system.
 * I am doing this to make the game system have less stuff in it
 * 
 * @author Akhil Prakash
 */
public abstract class PlayWordMoveAlgorithmns
{
	/*
	 * Invariant: tileAndLocations != null 
	 */
	protected final List<Pair<LetterTile, Location>> tileAndLocation;
	
	public PlayWordMoveAlgorithmns(List<Pair<LetterTile, Location>> tileAndLocation)
	{
		this.tileAndLocation = Collections.unmodifiableList(tileAndLocation);
	}

	/**
	 * Determines if a move is horizontal
	 * A move is horizontal if all the locations of the tiles have the same y coordinate.
	 * 
	 * @return true  if the move is horizontal
	 *         false otherwise
	 */
	protected boolean isMoveHorizontal()
	{
		if (tileAndLocation.isEmpty())
		{
			return false;
		}
		Location loc = tileAndLocation.get(0).getSecond();
		int y = loc.getY();
		for (Pair<LetterTile, Location> tileAndLoc : tileAndLocation)
		{
			if (y != tileAndLoc.getSecond().getY())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return true  if the move either has all the same x coordinates or all the same y coordinates
	 *         false otherwise
	 *         
	 * NOTE: if the list of tile and locations is empty, then we return false           
	 */
	protected boolean moveEitherHorizontalOrVertical()
	{
		if (isMoveHorizontal())
		{
			return true;
		}
		if (tileAndLocation.isEmpty())
		{
			return false;
		}
		int x = tileAndLocation.get(0).getSecond().getX();
		for (Pair<LetterTile, Location> tileAndLoc : tileAndLocation)
		{
			if (x != tileAndLoc.getSecond().getX())
			{
				return false;
			}
		}
		return true;
	}
	
	protected abstract String getWordHorizontally(Location loc);
	
	protected abstract String getWordVertically(Location loc);
	
	/**
	 * Get all the new words created by the move 
	 *
	 * @return all the new words that were created by the move
	 */
	protected Set<String> getNewWords()
	{
		/*
		 * we use a set here. Without loss of generality, assume the move is horizontal.
		 * Then, when we gall getWordHorizontally() we will end up getting the same word and we don't need
		 * to be checking if the same word is in the dictionary multiple times.
		 */
		Set<String> newWords = new HashSet<String> ();
		for (Pair<LetterTile, Location> tileLoc: tileAndLocation)
		{
			String word = getWordHorizontally(tileLoc.getSecond());
			/*
			 * We check for the word length being greater than 1. We do this because if we play ATE as the
			 * first move of the game then if we did not do this check we would have
			 * newWords = { ATE, A, T, E} which is a problem since T and E are not words
			 * but playing ATE was a perfectly valid move 
			 */
			if (word.length() > 1)
			{
				newWords.add(word);
			}
			word = getWordVertically(tileLoc.getSecond());
			if (word.length() > 1)
			{
				newWords.add(word);
			}
		}
		return newWords;
	}
	
	/**
	 * @param newWords the words that were created by the current move
	 * 
	 * @return true  if the move intersects with at least one letter tile that is on the board before
	 *               the current move
         *         false otherwise
         *         
         * NOTE: this will return false on the first move of the game
	 */
	protected boolean doesMoveIntersectWithAlreadyOnBoard(Set<String> newWords)
	{
		int sum = 0;
		for (String word : newWords)
		{
			sum += word.length();
		}
		sum -= tileAndLocation.size();
		return sum > 0;
	}
	
	/**
	 * @return true  if all the locations where the player wants to place the tiles are valid
	 *         false otherwise
	 */
	protected boolean areLocationsValid()
	{
		for (Pair<LetterTile, Location> tileLoc: tileAndLocation)
		{
			if (! Board.isValidLocation(tileLoc.getSecond()))
			{
				return false;
			}
		}
		return true;
	}
}

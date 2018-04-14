package edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles;

/**
 * Represents a letter tile. The letter tile has two pieces of information: its score and the letter.
 * Simple basic class which just has getters.
 * 
 * @author Akhil Prakash
 */
public class LetterTile
{

	private final char LETTER;
	private final int SCORE;
	
	public LetterTile(char letter, int score)
	{
		LETTER = letter;
		SCORE = score;
	}
	
	public char getLetter()
	{
		return LETTER;
	}
	
	public int getScore()
	{
		return SCORE;
	}
	
	@Override
	/**
	 * Used for debugging
	 */
	public String toString()
	{
		return "Letter Tile: (" + LETTER + "," + SCORE + ")"; 
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof LetterTile))
		{
			return false;
		}
		LetterTile tile = (LetterTile) obj;
		return LETTER == tile.LETTER && SCORE == tile.SCORE;
	}
	
	@Override
	public int hashCode()
	{
		return Character.valueOf(LETTER).hashCode() + Integer.valueOf(SCORE).hashCode();
	}
}

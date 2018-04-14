package edu.cmu.cs.cs214.hw4.specialTiles;

import java.util.List;

import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * Represents a special tile which does not allow a vowel to be played on it.
 * 
 * @author Akhil Prakash
 */
public class NoVowel extends SpecialTile
{

	public NoVowel(Player player)
	{
		super(player);
	}
	
	@Override
	/** @{inheritDoc} */
	public int getCost()
	{
		return 10;
	}
	
	@Override
	/** @{inheritDoc} */
	public boolean affectsMoveValidation()
	{
		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof NoVowel;
	}
	
	@Override
	public int hashCode()
	{
		return 2;
	}

	@Override
	public boolean execute(GameSystem gameSystem, Location specialTileLocation,
		List<Pair<LetterTile, Location>> move)
	{
		return false;
	}

	@Override
	public boolean executeMoveValidation(GameSystem gameSystem, Location specialTileLocation)
	{
		char letter = gameSystem.getBoard().getSquare(specialTileLocation).getTile().getLetter();
		boolean isVowel = letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u';
		return !isVowel;
	}
	
	@Override
	public String toString()
	{
		return "No Vowel";
	}
}

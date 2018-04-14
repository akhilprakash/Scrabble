package edu.cmu.cs.cs214.hw4.specialTiles;

import java.util.List;

import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * Represents a negative points special tile
 * 
 * @author Akhil Prakash
 */
public class NegativePoints extends SpecialTile
{

	public NegativePoints(Player player)
	{
		super(player);
	}

	@Override
	public int getCost()
	{
		return 10;
	}

	@Override
	public boolean affectsMoveValidation()
	{
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof NegativePoints;
	}
	
	@Override
	public int hashCode()
	{
		return 1;
	}

	@Override
	public boolean execute(GameSystem gameSystem, Location specialTileLocation,
		List<Pair<LetterTile, Location>> move)
	{
		return true;
	}

	@Override
	public boolean executeMoveValidation(GameSystem gameSystem, Location specialTileLocation)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString()
	{
		return "Negative Points";
	}
}

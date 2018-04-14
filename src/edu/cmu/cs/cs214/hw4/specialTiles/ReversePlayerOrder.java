package edu.cmu.cs.cs214.hw4.specialTiles;

import java.util.List;

import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * Represents the reverses the player order tile
 * 
 * @author Akhil Prakash
 */
public class ReversePlayerOrder extends SpecialTile
{

	public ReversePlayerOrder(Player player)
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
	public boolean execute(GameSystem gameSystem, Location specialTileLocation,
		List<Pair<LetterTile, Location>> move)
	{
		//when we reverse the player order we want the current player to still be the current player
		//so that when updateScore we update the score of the right person
		
		gameSystem.changeIncrement();
		return false;
	}

	@Override
	/** @{inheritDoc} */
	public boolean affectsMoveValidation()
	{
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof ReversePlayerOrder;
	}
	
	@Override
	/**
	 * I have this so findBugs wont complain.
	 */
	public int hashCode()
	{
		return 3;
	}

	@Override
	public boolean executeMoveValidation(GameSystem gameSystem, Location specialTileLocation)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString()
	{
		return "Reverse Player Order";
	}
}

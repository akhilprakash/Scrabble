package edu.cmu.cs.cs214.hw4.specialTiles;

import java.util.List;

import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

public class StealMove extends SpecialTile
{

	public StealMove(Player player)
	{
		super(player);
	}

	@Override
	public int getCost()
	{
		return 10;
	}

	public boolean execute(GameSystem gameSystem, Location specialTileLocation,
		List<Pair<LetterTile, Location>> move)
	{
		for (Pair<LetterTile, Location> tileLoc : move)
		{
			gameSystem.getBoard().getSquare(tileLoc.getSecond()).setTile(null);
			WHO_PLAYED.addTileToHand(tileLoc.getFirst());
		}
		return false;
	}

	@Override
	public boolean affectsMoveValidation()
	{
		return true;
	}

	@Override
	public boolean executeMoveValidation(GameSystem gameSystem,
			Location specialTileLocation)
	{
		return ! (WHO_PLAYED.equals(gameSystem.getCurrentPlayer()));
	}

	@Override
	public String toString()
	{
		return "Steal Move"; 
	}
}

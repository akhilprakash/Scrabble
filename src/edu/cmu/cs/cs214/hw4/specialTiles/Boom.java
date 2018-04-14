package edu.cmu.cs.cs214.hw4.specialTiles;

import java.util.List;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * Represents the special boom tile
 * 
 * @author Akhil Prakash
 */
public class Boom extends SpecialTile
{

	private static final int RADIUS = 3;
	
	public Boom(Player player)
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
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Boom;
	}
	
	@Override
	public int hashCode()
	{
		return Integer.valueOf(RADIUS).hashCode();
	}

	@Override
	public boolean execute(GameSystem gameSystem, Location specialTileLocation,
		List<Pair<LetterTile, Location>> move)
	{
		int startX = Math.max(0, specialTileLocation.getX() - RADIUS);
		int startY = Math.max(0, specialTileLocation.getY() - RADIUS);
		int endX = Math.min(Board.BOARD_SIZE, specialTileLocation.getX() + RADIUS);
		int endY = Math.min(Board.BOARD_SIZE, specialTileLocation.getY() + RADIUS);
		//get 3 square radius and set all the letter tile's to null
		for (int x = startX; x < endX; x++)
		{
			for (int y = startY; y < endY; y++)
			{
				gameSystem.getBoard().getSquare(new Location(x,y)).setTile(null);
			}
		}	
		return false;
	}

	@Override
	public boolean executeMoveValidation(GameSystem gameSystem, Location specialTileLocation)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString()
	{
		return "Boom";
	}
}

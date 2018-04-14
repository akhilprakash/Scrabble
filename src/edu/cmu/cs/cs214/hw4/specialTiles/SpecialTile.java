package edu.cmu.cs.cs214.hw4.specialTiles;

import java.util.List;

import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;

/**
 * Pulls out common features of all special tiles
 * 
 * @author Akhil Prakash
 */
public abstract class SpecialTile
{

	/**
	 * Invariant: WHO_PLAYED != null. WHO_PLAYED should be one of the players in the 
	 * array of players which is stored in the game system
	 */
	protected final Player WHO_PLAYED;
	
	public SpecialTile(Player player)
	{
		WHO_PLAYED = player;
	}

	/**
	 * The special tiles is only visible to the player who played it
	 * 
	 * @param player the current player
	 * 
	 * @return true  if the player played the special tile and thus can see it
	 *         false otherwise
	 */
	public boolean isVisible(Player player)
	{
		return player.equals(WHO_PLAYED);
	}
	
	/**
	 * @return the cost of buying the special tile
	 */
	public abstract int getCost();
	
	/**
	 * Applies the special tile to the current situation. Basically this is where the special tile
	 * does whatever it is suppose to do
	 * 
	 * @param gameSystem          the current game state
	 * @param specialTileLocation the location of the special tile
	 * @param move                list of tiles and where they should be placed
	 * 
	 * @return true  if the tile is a negative points tile
	 *         false otherwise
	 */
	public abstract boolean execute(GameSystem gameSystem, Location specialTileLocation,
		List<Pair<LetterTile, Location>> move);
	
	/**
	 * @return true  if the special tile affects move validation
	 *         false otherwise
	 */
	public abstract boolean affectsMoveValidation();

	/**
	 * Checks if the move is valid
	 * 
	 * @param gameSystem          the current state of the game
	 * @param specialTileLocation the location of the special tile
	 * 
	 * @return true  if the move is valid
	 *         false otherwise
	 */
	public abstract boolean executeMoveValidation(GameSystem gameSystem, Location specialTileLocation);

}

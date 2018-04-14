package edu.cmu.cs.cs214.hw4.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.ScrabbleDefinedTile;
import edu.cmu.cs.cs214.hw4.specialTiles.SpecialTile;

/**
 * Represents the GameSytem for scrabble. This class is in charge of knowing about the board and the players
 * and making moves.
 * 
 * @author Akhil Prakash
 */
public class GameSystem
{

	private final BagOfTiles bag;
	private final Board board;
	private final Player[] players;
	private int index; //where in the array of players we are
	/**
	 * Invariant: increment is either 1 or -1
	 */
	private int increment;
	
	public GameSystem(List<String> playerNames)
	{
		index = 0;
		increment = 1; //Going forward
		bag = new BagOfTiles();
		board = new Board();
		players = new Player[playerNames.size()];
		
		for (int i = 0; i < playerNames.size(); i++)
		{
			players[i] = new Player(playerNames.get(i));
		}
	
		for (int i = 0; i < Player.HAND_SIZE; i++)
		{
			for (Player player : players)
			{
				player.addTileToHand(bag.drawTile());
			}
		}
	}
	
	/**
	 * Initialize the game
	 */
	public GameSystem()
	{
		this(Arrays.asList(new String[] {"Joe", "Bob", "Mary", "Sally"}));
	}
	
	/**
	 * @return true  if the game is over
	 *         false otherwise
	 */
	public boolean isGameOver()
	{
		return bag.isEmpty();
	}
	
	/**
	 * Implements passing on your turn
	 */
	public void makePassMove()
	{
		//does nothing
	}

	/**
	 * Plays a word on the board if it is a valid move and then updates the score.
	 * If the move is not valid then the state of the game should not change 
	 * 
	 * The player draws how many tiles he played so that he always has the same number of tiles in his hand
	 * 
	 * @param tileLocs the letter tiles and where the player wants them placed
	 */
	public boolean makePlayWordMove(List<Pair<LetterTile, Location>> tileLocs)
	{
		Player currentPlayer = getCurrentPlayer();
		PlayWordMove move = new PlayWordMove(tileLocs);
		if (move.execute(this))
		{
			move.updateScore(this);
			//need to give tiles to the player
			
			while(currentPlayer.getSizeOfTileHand() < Player.HAND_SIZE)
			{
				if (! bag.isEmpty())
				{
					currentPlayer.addTileToHand(bag.drawTile());
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Plays a special tile on a certain location
	 * The move is invalid if there is already a special tile at that location or if the location
	 *  is invalid
	 * 
	 * @param specialTile the special tile to be placed
	 * @param loc         the location to place the special tile
	 * 
	 * NOTE: the special tile should be in the current player's hand. In this method we remove 
	 * the special tile from the player hand.
	 */
	public boolean makeSpecialTileMove(List<Pair<SpecialTile, Location>> specialTileLocs)
	{
		//Not allowed to place a special tile on top of another special tile
		Player currentPlayer = getCurrentPlayer();
		for (Pair<SpecialTile, Location> specialTilesLoc : specialTileLocs)
		{
			SpecialTile specialTile = specialTilesLoc.getFirst();
			Location loc = specialTilesLoc.getSecond();
			if (Board.isValidLocation(loc) && currentPlayer.getSpecialTiles().contains(specialTile))
			{
				board.getSquare(loc).setSpecialTile(specialTile);
			}
			else
			{
				return false;
			}
		}
		for (Pair<SpecialTile, Location> specialTileLoc: specialTileLocs)
		{
			currentPlayer.removeSpecialTileFromHand(specialTileLoc.getFirst());
		}
		return true;
		//Don't update score
		
	}
	
	/**
	 * Buys a special tile
	 * The player looses points for buying the special tile
	 * 
	 * Precondition: The player must have a large enough score to be the special tile
	 * 
	 * @param toBeBought the special tile the player wants to buy
	 */
	public boolean makeBuySpecialTileMove(SpecialTile toBeBought)
	{
		//make the move if possible
		Player currentPlayer = getCurrentPlayer();
		if (currentPlayer.getScore() < toBeBought.getCost())
		{
			return false;
		}
		currentPlayer.addSpecailTileToHand(toBeBought);
		
		//update score
		currentPlayer.addToScore(- toBeBought.getCost());
		//negative since loose points for buying special tile.
		return true;
	}
	
	/**
	 * Exchanges tiles
	 * 
	 * Precondition: the tiles the player wants to exchange must be in the player's hand
	 * 
	 * @param toBeExchanged the tiles the player wants to exchange
	 */
	public void makeExchangeTileMove(List<LetterTile> toBeExchanged)
	{
		Player currentPlayer = getCurrentPlayer();
		//check that the tiles to be exchanged are actually in the current players hand
		if (! currentPlayer.getTiles().containsAll(toBeExchanged))
		{
			System.err.println("Illegal Move");
		}
		for (int i = 0; i < toBeExchanged.size(); i++)
		{
			if (! bag.isEmpty())
			{
				currentPlayer.addTileToHand(bag.drawTile());
			}
		}
		//have to do the removing from the bag and adding to the bag in 
		//Separate loops or will get concurrent modification error
		for (LetterTile tile : toBeExchanged)
		{
			currentPlayer.removeTileFromHand(tile);
			bag.putTileInBag(tile);
		}
		//Don't update score
	}
	
	/**
	 * @return the next player
	 * 
	 * NOTE: this is not an idempotent function
	 */
	public Player getNextPlayer()
	{
		index = (index + increment)  % players.length;
		while (index < 0)
		{
			index += players.length;
		}
		return players[index];
	}
	
	/**
	 * Flip the order of the players
	 * Used only in the reverse order special tile
	 */
	public void changeIncrement()
	{
		increment = increment * -1;
	}
	
	public String getScoreBoard()
	{
		int counter = 0;
		StringBuilder result = new StringBuilder();
		while (counter < players.length)
		{
			result.append(getCurrentPlayer().getName()).append("'s Score : ")
				.append(getCurrentPlayer().getScore()).append("\n");
			getNextPlayer();
			counter++;
		}
		return result.toString();
	}
	
	/**
	 * Precondition: isGameOver() returns true
	 * 
	 * @return the winner of the game if the game is over
	 *         null if the game is not over
	 */
	public Player findWinner()
	{
		Player winner = players[0];
		for (int i = 1; i < players.length; i++)
		{
			if (winner.getScore() < players[i].getScore())
			{
				winner = players[i];
			}
		}
		return winner;
	}
	
	public Player getCurrentPlayer()
	{
		return players[index];
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	private class PlayWordMove extends PlayWordMoveAlgorithmns
	{
		
		public PlayWordMove(List<Pair<LetterTile, Location>> tileAndLocation)
		{
			super(tileAndLocation);
		}

		/**
		 * adds the tiles from the move to the board
		 * 
		 * @return false if we placed two tiles on top of each other
		 *         true  otherwise
		 */
		private boolean addTileToBoard()
		{
			for (Pair<LetterTile, Location> tileAndLoc : tileAndLocation)
			{
				LetterTile tile = tileAndLoc.getFirst();
				Location loc = tileAndLoc.getSecond();
				Square square = board.getSquare(loc);
				if (square.getTile() != null)
				{
					return false;
				}
				square.setTile(tile);
			}
			return true;
		}
		
		/**
		 * @return true  if there are no spaces in the word
		 *         false otherwise
		 *         
		 * NOTE: I space is an null letter tile
		 * 
		 * It is illegal to play a move like AT (Space) E
		 */
		private boolean checkNoSpacesInWord()
		{
			if (isMoveHorizontal())
			{
				// find the minimum y coordinate
				Comparator<Pair<LetterTile, Location>> compareY = 
					new Comparator<Pair<LetterTile, Location>>()
				{

					@Override
					public int compare(Pair<LetterTile, Location> o1,
						Pair<LetterTile, Location> o2)
					{
						return o1.getSecond().getY() - o2.getSecond().getY();
					}
				};
				Location minY = Collections.min(tileAndLocation, compareY).getSecond();

				// find the max y coordinate
				Location maxY = Collections.max(tileAndLocation, compareY).getSecond();

				for (Location loc = minY; loc.equals(maxY); 
					loc = new Location(loc.getX(), loc.getY() + 1))
				{
					if (board.getSquare(loc).getTile() == null)
					{
						return false;
					}
				}
				return true;
			}

			Comparator<Pair<LetterTile, Location>> compareX = new Comparator<Pair<LetterTile, Location>>()
			{

				@Override
				public int compare(Pair<LetterTile, Location> o1, Pair<LetterTile, Location> o2)
				{
					return o1.getSecond().getX() - o2.getSecond().getX();
				}
			};

			Location minX = Collections.min(tileAndLocation, compareX).getSecond();

			// find the max y coordinate
			Location maxX = Collections.max(tileAndLocation, compareX).getSecond();

			for (Location loc = minX; loc.equals(maxX); 
				loc = new Location(loc.getX() + 1, loc.getY()))
			{
				if (board.getSquare(loc).getTile() == null)
				{
					return false;
				}
			}
			return true;
		}
		
		/**
		 * Go through all the special tiles that could have been activated by the move
		 * if the special tile affects move validation then see if the move is valid
		 * 
		 * @param gameSystem the current state of the game
		 * 
		 * @return true  if pass all the special tiles (that were activated on this move)
		 *               move validation checks
		 *         false otherwise
		 */
		private boolean checkSpecialTiles(GameSystem gameSystem)
		{
			boolean moveValid = true;
			for (Pair<LetterTile, Location> tileLoc : tileAndLocation)
			{
				Square square = board.getSquare(tileLoc.getSecond());
				SpecialTile specialTile = square.getSpecialTile();
				if (specialTile != null && specialTile.affectsMoveValidation())
				{
					moveValid = specialTile.executeMoveValidation(gameSystem,
						tileLoc.getSecond()) && moveValid;
				}
			}
			return moveValid;
		}
		
		/**
		 * Given a location find the start of the word using that the word is horizontal
		 * 
		 * @param loc the location which we are using to find the start of the word
		 * 
		 * @return the location of where the word starts
		 * 
		 * Postcondition: returns a valid location
		 */
		private Location findMinX(Location loc)
		{
			while (loc.getX() >= 0 && board.getSquare(loc).getTile() != null)
			{
				loc = new Location(loc.getX() - 1, loc.getY());
			}
			if (loc.getX() != 0)
			{
				loc = new Location(loc.getX() + 1, loc.getY());
			}
			return loc;
		} 
		
		/**
		 * Given a location get the word that is at that location horizontally
		 * 
		 * @param loc location to start looking at
		 * 
		 * @return a horizontal word on the board which goes through location
		 */
		public String getWordHorizontally(Location loc)
		{	
			loc = findMinX(loc);
			//gets us to the beginning of the word
			StringBuilder word = new StringBuilder();
			while (loc.getX() <= Board.BOARD_SIZE && board.getSquare(loc).getTile() != null)
			{
				word.append(board.getSquare(loc).getTile().getLetter());
				loc = new Location(loc.getX() + 1, loc.getY());
			}
			return word.toString();
		}
		
		/**
		 * Given a location find the start of the word using that the word is vertical
		 * 
		 * @param loc the location which we are using to find the start of the word
		 * 
		 * @return the location of where the word starts
		 * 
		 * Postcondition: returns a valid location
		 */
		private Location findMinY(Location loc)
		{
			while (loc.getY() >= 0 && board.getSquare(loc).getTile() != null)
			{
				loc = new Location(loc.getX(), loc.getY() - 1);
			}
			if (loc.getY() != 0)
			{
				loc = new Location(loc.getX(), loc.getY() + 1);
			}
			return loc;
		}
		
		/**
		 * Given a location get the word that is at that location vertically
		 * 
		 * @param loc location to start looking at
		 * 
		 * @return a vertical word on the board which goes through location
		 */
		public String getWordVertically(Location loc)
		{
			loc = findMinY(loc);
			//gets us to the beginning of the word
			StringBuilder word = new StringBuilder();
			while (loc.getY() <= Board.BOARD_SIZE && board.getSquare(loc).getTile() != null)
			{
				word.append(board.getSquare(loc).getTile().getLetter());
				loc = new Location(loc.getX(), loc.getY() + 1);
			}
			return word.toString();
		}
		
		/**
		 * @return true  if the current move placed a tile on the star
		 *         false otherwise
		 *         
		 * NOTE: should return true only on the first move of the game         
		 */
		private boolean checkHasLetterOnStar()
		{
			for (Pair<LetterTile, Location> tileLocs : tileAndLocation)
			{
				if (tileLocs.getSecond().equals(Board.STAR_LOCATION))
				{
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Undoes the current move because the current move was invalid.
		 */
		private void undoMove()
		{
			for (Pair<LetterTile, Location> tileLoc : tileAndLocation)
			{
				board.getSquare(tileLoc.getSecond()).setTile(null);
			}
		}
		
		/**
		 * If the move is valid, we play the move and get back the number of tiles that we played
		 * If the move is invalid, the game state should not change.
		 * 
		 * @param gameSystem the current state of the game
		 * 
		 * @return true  if the move is valid
		 *         false otherwise
		 */
		public boolean execute(GameSystem gameSystem)
		{
			if (areLocationsValid() && moveEitherHorizontalOrVertical() && addTileToBoard()
				&& checkSpecialTiles(gameSystem) && checkNoSpacesInWord()
			   )
			{
				Set<String> newWords = getNewWords();
				boolean anotherCheck = Dictionary.areWords(newWords)
					&& (checkHasLetterOnStar() || doesMoveIntersectWithAlreadyOnBoard(newWords)); 
				if (! anotherCheck)
				{
					undoMove();
					return false;
				}
				for (Pair<LetterTile, Location> tileLoc : tileAndLocation)
				{
					gameSystem.getCurrentPlayer().removeTileFromHand(tileLoc.getFirst());
				}
				return true;
			}
			undoMove();
			return false;
		}

		/**
		 * Calculates the score for the main word created from the move
		 * 
		 * Precondition: increment should either be new Location(0,1) or new Location(1,0)
		 * 
		 * @param start     the beginning of the main word 
		 * @param increment how much to increment the location by
		 * 
		 * @return score for the main word
		 */
		private int calculateScoreMainWord(Location start, Location increment)
		{
			int wordMultiplier = 1;
			int score = 0;
			int incrementX = increment.getX();
			int incrementY = increment.getY();
			for (Location loc = start; board.getSquare(loc).getTile() != null;
				loc = new Location(loc.getX() + incrementX, loc.getY() + incrementY))
			{
				int letterScore = board.getSquare(loc).getTile().getScore();
				ScrabbleDefinedTile sdTile = board.getSquare(loc).getDefaultTile();
				if (sdTile != null && sdTile.isActive())
				{
					score += letterScore * sdTile.letterMultiplier();
					wordMultiplier *= sdTile.wordMultiplier();
					sdTile.deactivate();
				}
				else
				{
					score += letterScore;
				}
			}
			return score * wordMultiplier;
		}
		
		/**
		 * we cannot put together the calculating perpendicular score and the main score together because
		 * we have different ways of computing the staring location and we need the number if iterations
		 * in one case
		 * 
		 * @param increment either Location(1,0) or Location(0,1)
		 * 
		 * @return score form all the perpendicular words created
		 */
		private int calculateScoreAllPerpendicularWord(Location increment)
		{
			int score = 0; //overall score from all the perpendicular words
			int incrementX = increment.getX();
			int incrementY = increment.getY();
			for (Pair<LetterTile, Location> tileLoc : tileAndLocation)
			{
				Location min = (incrementY == 0) ? findMinX(tileLoc.getSecond())
					: findMinY(tileLoc.getSecond());
				//gets us to the beginning of the word
				int wordMultiplier = 1;
				int wordScore = 0; //score for a single perpendicular word
				/*
				 * We have this counter for the number of iterations through the loop since if we
				 * only go through the loop once then that means if we did not have this and ATE
				 * was played we would get 3 points for all the perpendicular words since we will
				 * think that A, T, E are all words
				 */
				int numIterations = 0;
				for (Location loc = min; Board.isValidLocation(loc) && 
					board.getSquare(loc).getTile() != null;
					loc = new Location(loc.getX() + incrementX, loc.getY() + incrementY))
				{
					int letterScore = board.getSquare(loc).getTile().getScore();
					ScrabbleDefinedTile sdTile = board.getSquare(loc).getDefaultTile();
					numIterations++;
					if (sdTile != null && sdTile.isActive())
					{
						wordScore += letterScore * sdTile.letterMultiplier();
						wordMultiplier *= sdTile.wordMultiplier();
						sdTile.deactivate();
					}
					else
					{
						wordScore += letterScore;
					}
				}
				if (numIterations > 1)
				{
					score = wordScore * wordMultiplier + score;
				}
			}
			return score;
		}
		
		
		/**
		 * Updates the score after playing a move on the board
		 * 
		 * @param gameSystem the current game system
		 */
		public void updateScore(GameSystem gameSystem)
		{
			//First do all the special tile stuff
			boolean isNegative = false;
			for (Pair<LetterTile, Location> tileLoc : tileAndLocation)
			{
				SpecialTile specialTile = board.getSquare(tileLoc.getSecond()).getSpecialTile();
				if (specialTile != null)
				{
					isNegative = specialTile.execute(gameSystem, tileLoc.getSecond(),
						tileAndLocation) || isNegative;
				}
			}
			
			int score = 0;
			if (isMoveHorizontal())
			{
				Location minX = findMinX(tileAndLocation.get(0).getSecond());
				score += calculateScoreMainWord(minX, new Location(1, 0));
				score += calculateScoreAllPerpendicularWord(new Location(0, 1));
			}
			else
			{
				Location minY = findMinY(tileAndLocation.get(0).getSecond());
				score += calculateScoreMainWord(minY, new Location(0, 1));
				score += calculateScoreAllPerpendicularWord(new Location(1, 0));
			}
			int newScore = isNegative ? -score : score; 
			getCurrentPlayer().addToScore(newScore);
		}

	}
}

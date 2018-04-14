package edu.cmu.cs.cs214.hw4.core;

import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.DoubleLetter;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.DoubleWord;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.TripleLetter;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.TripleWord;

/**
 * Stores the board which contains which tiles are on which squares
 * 
 * @author Akhil Prakash
 */
public class Board
{

	/**
	 * Invariant: Once a scrabble default tile is placed it cannot be removed. Also we only place scrabble default 
	 * tiles on the board in intilaizeBoard
	 */
	private final Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
	
	public final static int BOARD_SIZE = 15;
	
	public final static Location STAR_LOCATION = new Location(BOARD_SIZE / 2, BOARD_SIZE / 2);
	
	//coordinates are (y,x)
	
	public Board()
	{
		initializeBoard();
	}
	
	/**
	 * @param loc the location to be tested
	 * 
	 * @return true  if the location is on the board
	 *         false otherwise
	 */
	public static boolean isValidLocation(Location loc)
	{
		int x = loc.getX();
		int y = loc.getY();
		return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
	}
	
	/**
	 * Creates all the squares and sets all the default scrabble defined tiles.
	 */
	private void initializeBoard()
	{
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				board[i][j] = new Square(new Location(j, i), null, null, null);
			}
		}
		addAllTripleLetterHorizontal();
		addTripleLetterVertical(BOARD_SIZE / 2 - 2);
		addTripleLetterVertical(BOARD_SIZE / 2 + 2);
		addTripleWordAllHorizontal();
		addTripleWordAllVertical();
		addDoubleWordDiagonals();
		addDoubleLetterHorizontal();
		addDoubleLetterHorizontal(BOARD_SIZE - 1);
	}
	
	/**
	 * Adds the triple words horizontally
	 * Do three iterations through the loop since BOARD_SIZE is odd
	 * 
	 * @param j the y value to start adding the triple words
	 */
	private void addTripleWordHorizontal(int j)
	{
		for (int i = 0; i < BOARD_SIZE; i += BOARD_SIZE / 2)
		{
			board[j][i].setDefaultTile(new TripleWord());
		}
	}
	
	/**
	 * Adds triple words vertically
	 * Do three iterations through loop
	 * 
	 * @param j the x value to start adding triple words
	 */
	private void addTripleWordVertical(int j)
	{
		for (int i = 0; i < BOARD_SIZE; i += BOARD_SIZE / 2)
		{
			board[i][j].setDefaultTile(new TripleWord());
		}
	}
	
	/**
	 * Adds all the horizontal triple words
	 */
	private void addTripleWordAllHorizontal()
	{
		addTripleWordHorizontal(0);
		addTripleWordHorizontal(BOARD_SIZE - 1);
	}
	
	/**
	 * Adds all the vertical triple words
	 */
	private void addTripleWordAllVertical()
	{
		addTripleWordVertical(0);
		addTripleWordVertical(BOARD_SIZE - 1);
	}
	
	/**
	 * Adds triple letters horizontally
	 * 
	 * @param j the y value to add the triple letters
	 */
	private void addTripleLetterHorizontal(int j)
	{
		for (int i = 1; i < BOARD_SIZE; i += 4)
		{
			board[j][i].setDefaultTile(new TripleLetter());
		}
	}
	
	/**
	 * adds all the triple letter tiles vertically
	 * 
	 * @param j the given y value
	 */
	private void addTripleLetterVertical(int j)
	{
		for (int i = 1; i < BOARD_SIZE; i += 4)
		{
			board[j][i].setDefaultTile(new TripleLetter());
		}
	}
	
	/**
	 * adds all the triple letters horizontally
	 */
	private void addAllTripleLetterHorizontal()
	{
		addTripleLetterHorizontal(BOARD_SIZE / 2 - 2);
		addTripleLetterHorizontal(BOARD_SIZE / 2 + 2);
	}
	
	/**
	 * Get the square at a given location
	 * 
	 * @param loc the location which we want to square at
	 * 
	 * @return the square at the location loc
	 */
	public Square getSquare(Location loc)
	{
		if (! isValidLocation(loc))
		{
			throw new IllegalArgumentException("tried to access a square out of bounds");
		}
		return board[loc.getY()][loc.getX()];
	}
	
	/**
	 * Adds the double word tiles along the diagonals
	 */
	private void addDoubleWordDiagonals()
	{
		for (int i = 1; i < BOARD_SIZE - 1; i++)
		{
			Square square = getSquare(new Location(i, i));
			if (square.getDefaultTile() == null)
			{
				square.setDefaultTile(new DoubleWord());
			}
		}
		
		for (int i = 1; i < BOARD_SIZE - 1; i++)
		{
			Square square = getSquare(new Location(i, BOARD_SIZE - i - 1));
			if (square.getDefaultTile() == null)
			{
				square.setDefaultTile(new DoubleWord());
			}
		}
	}
	
	/**
	 * Adds the double ltters hrozontally
	 */
	private void addDoubleLetterHorizontal()
	{
		for (int j = 3; j < BOARD_SIZE; j += 8)
		{
			board[0][j].setDefaultTile(new DoubleLetter());
		}
		
		board[2][BOARD_SIZE / 2 - 1].setDefaultTile(new DoubleLetter());
		board[2][BOARD_SIZE / 2 + 1].setDefaultTile(new DoubleLetter());
		for (int i = 0; i < BOARD_SIZE; i+=BOARD_SIZE / 2)
		{
			board[3][i].setDefaultTile(new DoubleLetter());
		}
		board[BOARD_SIZE / 2 -1][2].setDefaultTile(new DoubleLetter());
		board[BOARD_SIZE / 2 -1][BOARD_SIZE - 3].setDefaultTile(new DoubleLetter());
		for (int i = 3; i < BOARD_SIZE; i+= 8)
		{
			board[BOARD_SIZE / 2][i].setDefaultTile(new DoubleLetter());
		}
	}
	
	/**
	 * add the double letters horizontally on the other half of the board. The board is symmetric across the 
	 * x axis.
	 * 
	 * @param shift does the reflection part of the symmetry
	 */
	private void addDoubleLetterHorizontal(int shift)
	{
		for (int j = 3; j < BOARD_SIZE; j += 8)
		{
			board[shift - 0][j].setDefaultTile(new DoubleLetter());
		}
		
		board[shift - 2][BOARD_SIZE / 2 - 1].setDefaultTile(new DoubleLetter());
		board[shift - 2][BOARD_SIZE / 2 + 1].setDefaultTile(new DoubleLetter());
		for (int i = 0; i < BOARD_SIZE; i+=BOARD_SIZE / 2)
		{
			board[shift - 3][i].setDefaultTile(new DoubleLetter());
		}
		board[shift - (BOARD_SIZE / 2 - 1)][2].setDefaultTile(new DoubleLetter());
		board[shift - (BOARD_SIZE / 2 - 1)][BOARD_SIZE - 3].setDefaultTile(new DoubleLetter());
		for (int i = 3; i < BOARD_SIZE; i+= 8)
		{
			board[shift - BOARD_SIZE / 2][i].setDefaultTile(new DoubleLetter());
		}
	}
	
	/**
	 * Prints the whole board. Used for debugging purposes.
	 */
	public void printBoard()
	{
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				System.out.println(board[j][i]);
			}
		}
	}
}

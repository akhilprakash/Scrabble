package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.GameSystem;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Pair;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.LetterTile;
import edu.cmu.cs.cs214.hw4.scrabbleDefinedTiles.ScrabbleDefinedTile;
import edu.cmu.cs.cs214.hw4.specialTiles.Boom;
import edu.cmu.cs.cs214.hw4.specialTiles.NegativePoints;
import edu.cmu.cs.cs214.hw4.specialTiles.NoVowel;
import edu.cmu.cs.cs214.hw4.specialTiles.ReversePlayerOrder;
import edu.cmu.cs.cs214.hw4.specialTiles.SpecialTile;
import edu.cmu.cs.cs214.hw4.specialTiles.StealMove;

/**
 * Controls the UI for the Scrabble Game
 * 
 * @author Akhil Prakash
 */
public class ScrabbleUI 
{
	//Button text
	private static final String TITLE = "Scrabble ";
	private static final String NEW_GAME = "Start New Game";
	private static final String PLAY_WORD_MOVE = "Submit Play Word Move";
	private static final String BUTTONS_SCOREBOARD = "Buttons and Scoreboard";
	private static final String TILE_EXCHANGE_MOVE = "Submit Tile Exchange Move";
	private static final String PASS_MOVE = "Submit Pass Move";
	private static final String SPECIAL_TILE_MOVE = "Submit Special Tile Move";
	private static final String NEGATIVE_POINTS = "Buy Negative Points Costs ";
	private static final String NO_VOWEL = "Buy No Vowel Costs ";
	private static final String BOOM = "Buy Boom Costs ";
	private static final String REVERSE = "Buy Reverse Costs ";
	private static final String STEAL_MOVE = "Buy Steal Move Costs ";
	
	private static final String INVALID = "Invalid Move";
	private static final String TRY_AGAIN = ". Please Try again.";
	private static final String WINNER = "Winner!";
	private static final String TURN = " Turn";
	private static final String WIN = " just won the game!";
	private static final String SCORE = "SCORE : ";
	
	private static GameSystem scrabble;

	private JFrame frame;
	private final JPanel boardPanel;

	private final NameEntryWindow namePanel;
	private final JButton newGameButton = new JButton(NEW_GAME);
	private final JTextArea scoreArea = new JTextArea();
	private JPanel letterTileArea;
	private JPanel specialTileArea;
	private JPanel allTiles;
	private static final int TILES_PER_ROW = 5;

	//stores actual data in the game
	private final List<LetterTile> tilesToBePlayed;
	private final List<Location> locations; 
	private final List<SpecialTile> specialTilesToBePlayed;
	private final JButton[][] squares;

	public ScrabbleUI() {

		namePanel = new NameEntryWindow();

		frame = new JFrame(TITLE);
		frame.setResizable(true);

		addElements(frame.getContentPane()); // Adds all of the buttons, etc.
		frame.pack();

		// When this window's "exit" button is clicked, the program ends
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		squares = new JButton[Board.BOARD_SIZE][Board.BOARD_SIZE];
		specialTilesToBePlayed = new ArrayList<SpecialTile> ();
		tilesToBePlayed = new ArrayList<LetterTile> ();
		locations = new ArrayList<Location> ();
		boardPanel = new JPanel(new GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE));
	}

	/**
	 * Adds all the buttons
	 * 
	 * @param container the thing we want to add things to
	 */
	private void addElements(Container container) {
		container.setLayout(new BorderLayout()); // set up a layout manager
		container.add(namePanel, BorderLayout.NORTH);

		//Creates a panel with two buttons on it and adds it to the top
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newGameButton);
		namePanel.add(buttonPanel);

		/*
		 * Start up the scrabble game on click
		 */
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (namePanel.getPlayers().size() <= 1)
				{
					//cant play scrabble with 0 or 1 players
					return;
				}
				scrabble = new GameSystem(namePanel.getPlayers());
				Player currentPlayer = scrabble.getCurrentPlayer();
				
				//get rid of the player name window
				frame.setVisible(false);
				frame.dispose();
				
				//create a new window
				frame = new JFrame();
				updateTitle();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				frame.setSize(new Dimension(700, 700));
				//draw letter tiles buttons and the special tile buttons
				refreshLetterTileArea();
				refreshSpecialTileArea();
				allTiles = new JPanel();
				allTiles.add(letterTileArea, BorderLayout.NORTH);
				allTiles.add(specialTileArea, BorderLayout.SOUTH);
				frame.add(allTiles, BorderLayout.SOUTH);

				//draw the board
				Board board = scrabble.getBoard();
				for (int y = 0; y < Board.BOARD_SIZE; y++) 
				{
					for (int x = 0; x < Board.BOARD_SIZE; x++) 
					{
						JButton button = new JButton();
						button.setFont(new Font(null, Font.PLAIN, 10));
						button.setSize(new Dimension(50, 50));
						Location loc = new Location(x, y);
						button.setToolTipText(board.getSquare(loc).toString(currentPlayer));
						button.addActionListener(new ActionListenerWrapper<Location>(locations,
							loc, button));
						
						boardPanel.add(button);
						squares[y][x] = button;
						ScrabbleDefinedTile defaultTile = board.getSquare(loc).getDefaultTile();
						if (defaultTile != null && defaultTile.isActive())
						{
							squares[y][x].setBackground(defaultTile.getBackgroundColor());
						}
						squares[y][x].setFont(new Font(null, Font.PLAIN, 16));
					}
				}
				updateBoard();
				frame.add(boardPanel, BorderLayout.CENTER);

				//make the buttons for submitting different types of moves
				JButton submitButton = new JButton(PLAY_WORD_MOVE);
				submitButton.setToolTipText(submitButton.getText());
				submitButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						boolean isValid = false;
						if (tilesToBePlayed.size() == locations.size())
						{
							List<Pair<LetterTile, Location>> tilesLocs =
								new ArrayList<Pair<LetterTile, Location>> ();
							for (int i = 0; i < locations.size(); i++)
							{
								tilesLocs.add(new Pair<LetterTile, Location>(
									tilesToBePlayed.get(i), locations.get(i)));
							}
							isValid = scrabble.makePlayWordMove(tilesLocs);
						}
						//end of turn
						if (isValid)
						{
							scrabble.getNextPlayer();
						}
						else
						{
							invalidMoveDialog();
						}
						refreshScreen();
						updateBoard();
						endGameIfPossible();
					}
				});
				
				JFrame buttonAndScroreboardFrame = new JFrame(BUTTONS_SCOREBOARD);
				JPanel buttonGrid = new JPanel(new GridLayout(3, 3));

				JButton submitTileExchange = new JButton(TILE_EXCHANGE_MOVE);
				submitTileExchange.setToolTipText(submitTileExchange.getText());
				submitTileExchange.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						//end of turn
						if (tilesToBePlayed.isEmpty())
						{
							invalidMoveDialog();
							return;
						}
						scrabble.makeExchangeTileMove(tilesToBePlayed);
						scrabble.getNextPlayer();
						refreshScreen();
						updateBoard();
						endGameIfPossible();
					}
				});

				JButton passMove = new JButton(PASS_MOVE);
				passMove.setToolTipText(passMove.getText());
				passMove.addActionListener(new ActionListener() 
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						//end of turn
						scrabble.makePassMove();
						scrabble.getNextPlayer();
						refreshScreen();
						updateBoard();
						endGameIfPossible();
					}
				});

				JButton playSpecialTile = new JButton(SPECIAL_TILE_MOVE);
				playSpecialTile.setToolTipText(playSpecialTile.getText());
				playSpecialTile.addActionListener(new ActionListener() 
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						boolean isValid = false;
						if (specialTilesToBePlayed.size() == locations.size()
							&& locations.size() != 0)
						{
							List<Pair<SpecialTile, Location>> tilesLocs = 
								new ArrayList<Pair<SpecialTile, Location>> ();
							for (int i = 0; i < locations.size(); i++)
							{
								tilesLocs.add(new Pair<SpecialTile, Location>(
									specialTilesToBePlayed.get(i), locations.get(i)
								));
							}
							isValid = scrabble.makeSpecialTileMove(tilesLocs);
						}
						//dont want to refresh screen since can play another move
						if (! isValid)
						{
							invalidMoveDialog();
						}
						refreshScreen();
						updateBoard();
					}
				});

				buttonGrid.add(submitButton);
				buttonGrid.add(playSpecialTile);
				buttonGrid.add(submitTileExchange);
				buttonGrid.add(passMove);
				addBuySpecialTileButtons(buttonGrid);
				
				buttonAndScroreboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				buttonAndScroreboardFrame.setSize(new Dimension(500, 500));
				buttonAndScroreboardFrame.setResizable(true);
				buttonAndScroreboardFrame.add(buttonGrid, BorderLayout.NORTH);
				buttonAndScroreboardFrame.add(scoreArea, BorderLayout.SOUTH);
				buttonAndScroreboardFrame.setVisible(true);
				
				scoreArea.setFont(new Font(null, Font.PLAIN, 20));
				updateScore();
				
				frame.pack();
			}
		});

	}

	public void show() 
	{
		frame.setVisible(true);
	}

	public static void createAndShowGUI() {
		ScrabbleUI main = new ScrabbleUI();
		main.show();
	}

	/**
	 * Updates the score board
	 */
	private void updateScore()
	{
		scoreArea.setText(scrabble.getScoreBoard());
	}

	/**
	 * Updates the board. redraws which letter tiles are on the board
	 */
	private void updateBoard()
	{
		Board board = scrabble.getBoard();
		for (int y = 0; y < Board.BOARD_SIZE; y++)
		{
			for (int x = 0; x < Board.BOARD_SIZE; x++)
			{
				Location loc = new Location(x, y);
				squares[y][x].setToolTipText(board.getSquare(loc)
						.toString(scrabble.getCurrentPlayer()));
				LetterTile letter = board.getSquare(loc).getTile();
				if (letter != null)
				{
					//to upper case to make it easier to read
					squares[y][x].setText(String.valueOf(letter.getLetter()).toUpperCase());
				}
				else
				{
					if (loc.equals(Board.STAR_LOCATION))
					{
						squares[y][x].setText("*");
						squares[y][x].setFont(new Font(null, Font.PLAIN, 16));
					}
					else
					{
						/*
						 * deals with boom where there were letters but now there aren't there
						 * anymore
						 */
						squares[y][x].setText("");
						squares[y][x].setEnabled(true);
					}
				}
			}
		}
	}
	
	/**
	 * Redraw the letter tile area every thing it is a new player's turn
	 */
	private void refreshLetterTileArea()
	{
		Player currentPlayer = scrabble.getCurrentPlayer();
		int sizeOfTileHand = currentPlayer.getSizeOfTileHand();
		letterTileArea = new JPanel(new GridLayout(sizeOfTileHand / TILES_PER_ROW + 1, 0));
		JButton[] tiles = new JButton[sizeOfTileHand];

		for (int i = 0; i < sizeOfTileHand; i++)
		{
			LetterTile letter = currentPlayer.getTiles().get(i);
			tiles[i] = new JButton(letter.toString());
			tiles[i].addActionListener(new ActionListenerWrapper<LetterTile>(tilesToBePlayed,
				letter, tiles[i]));
			letterTileArea.add(tiles[i]);
		}
	}
	
	/**
	 * Redraw the special tile area every time the player changes
	 */
	private void refreshSpecialTileArea()
	{
		Player currentPlayer = scrabble.getCurrentPlayer();
		int sizeOfSpecialTileHand = currentPlayer.getSizeOfSpecialTileHand();
		specialTileArea = new JPanel(new GridLayout(sizeOfSpecialTileHand / TILES_PER_ROW + 1, 0));
		JButton[] specialTiles = new JButton[sizeOfSpecialTileHand];
		for (int i = 0; i < sizeOfSpecialTileHand; i++)
		{
			SpecialTile specialTile = currentPlayer.getSpecialTiles().get(i);
			specialTiles[i] = new JButton(specialTile.toString());
			specialTiles[i].addActionListener(new ActionListenerWrapper<SpecialTile> (
				specialTilesToBePlayed, specialTile, specialTiles[i]));
			specialTileArea.add(specialTiles[i]);
		}
	}
	
	/**
	 * Adds all the buy special tile buttons to the button grid
	 * 
	 * @param buttonGrid the grid that contains the layout of all the buttons
	 */
	private void addBuySpecialTileButtons(JPanel buttonGrid)
	{
		Player currentPlayer = scrabble.getCurrentPlayer();
		//HACKY way to get the cost since getCost cannot be static since it is abstract
		JButton buyBoom = new JButton(BOOM + new Boom(currentPlayer).getCost());
		buyBoom.setToolTipText(buyBoom.getText());
		buyBoom.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//not end of turn
				//have to do scrabble.getCUrrentPlayer9) here
				//because the current player is changing all the time
				boolean isValid = scrabble.makeBuySpecialTileMove(
					new Boom(scrabble.getCurrentPlayer()));
				
				if (! isValid)
				{
					invalidMoveDialog();
				}
				else
				{
					refreshScreen();
				}
			}
		});

		JButton buyNegativePoints = new JButton(NEGATIVE_POINTS + 
			new NegativePoints(currentPlayer).getCost());
		
		buyNegativePoints.setToolTipText(buyNegativePoints.getText());
		buyNegativePoints.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//not end of turn
				boolean isValid = scrabble.makeBuySpecialTileMove(
					new NegativePoints(scrabble.getCurrentPlayer()));
				if (! isValid)
				{
					invalidMoveDialog();
				}
				else
				{
					refreshScreen();
				}
			}
		});

		JButton noVowel = new JButton(NO_VOWEL + 
			new NoVowel(currentPlayer).getCost());
		
		noVowel.setToolTipText(noVowel.getText());
		noVowel.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//not end of turn
				boolean isValid = scrabble.makeBuySpecialTileMove(
					new NoVowel(scrabble.getCurrentPlayer()));
				
				if (! isValid)
				{
					invalidMoveDialog();
				}
				else
				{
					refreshScreen();
				}
			}
		});

		JButton reverse = new JButton(REVERSE + 
			new ReversePlayerOrder(currentPlayer).getCost());
		
		reverse.setToolTipText(reverse.getText());
		reverse.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//not end of turn
				boolean isValid = scrabble.makeBuySpecialTileMove(
					new ReversePlayerOrder(scrabble.getCurrentPlayer()));
				
				if (! isValid)
				{
					invalidMoveDialog();
				}
				else
				{
					refreshScreen();
				}
			}
		});

		JButton stealMove = new JButton(STEAL_MOVE +
			new StealMove(currentPlayer).getCost());
		
		stealMove.setToolTipText(stealMove.getText());
		stealMove.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//not end of turn
				boolean isValid = scrabble.makeBuySpecialTileMove(
					new StealMove(scrabble.getCurrentPlayer()));
				
				if (! isValid)
				{
					invalidMoveDialog();
				}
				else
				{
					refreshScreen();
				}
			}
		});
		
		buttonGrid.add(buyNegativePoints);
		buttonGrid.add(buyBoom);
		buttonGrid.add(noVowel);
		buttonGrid.add(reverse);
		buttonGrid.add(stealMove);

	}

	/**
	 * Refreshes the screen which entails redrawing the letter tiles, the special tiles, the title bar,
	 * the scoreboard.
	 */
	private void refreshScreen()
	{
		tilesToBePlayed.clear();
		locations.clear();
		specialTilesToBePlayed.clear();
		updateScore();
		updateTitle();
		letterTileArea.removeAll();
		frame.remove(letterTileArea);
		specialTileArea.removeAll();
		frame.remove(specialTileArea);
		allTiles.removeAll();
		frame.remove(allTiles);

		refreshLetterTileArea();
		refreshSpecialTileArea();
		
		allTiles = new JPanel();
		allTiles.add(letterTileArea, BorderLayout.NORTH);
		allTiles.add(specialTileArea, BorderLayout.SOUTH);
		frame.add(allTiles, BorderLayout.SOUTH);
//		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Updates the title of the main window to show who is playing
	 */
	private void updateTitle()
	{
		frame.setTitle(TITLE + scrabble.getCurrentPlayer().getName() + TURN);
	}
	
	/**
	 * Makes a pop up window which shows who won the game and their score
	 * 
	 * @param winner the winner
	 */
	private void gameEnded(Player winner) 
	{
		StringBuilder output = new StringBuilder();
		output.append(winner.getName()).append(WIN).append(SCORE).append(winner.getScore());
		JOptionPane.showMessageDialog(frame, WINNER, output.toString(), JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Makes a pop up window which says that the move made was illegal
	 */
	private void invalidMoveDialog()
	{
		JOptionPane.showMessageDialog(frame, INVALID + TRY_AGAIN, INVALID, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * If the game is over print out who is the winner. 
	 */
	private void endGameIfPossible()
	{
		if (scrabble.isGameOver())
		{
			Player winner = scrabble.findWinner();
			gameEnded(winner);
		}
	}

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
}
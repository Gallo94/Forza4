package unicam.cs.forza4;

import java.io.Serializable;
import java.util.ArrayList;

/** Grid represent the game's grid */
public class Grid implements Serializable
{
	/** ID Serializable */
	private static final long serialVersionUID = -6825761503971516066L;
	
	/** Maximum number of discs */
	public static final int MAX_DISCS = 42;
	/** Number di rows in grid */
	public static final int ROWS = 6;
	/** Number di columns in grid */
	public static final int COLUMNS = 7;
	
	/** Grid's cells */
	private char[][] cells;
	/** O's discs for player0 */
	public static final char CIRCLE = 'O';
	/** X's discs for player1 */
	public static final char CROSS = 'X';
	/** Empty grid cell */
	public static final char EMPTY = ' ';
	
	/** Number of the discs */
	private int numDisc;
	/** Victory */
	public boolean won;
	
	
	/** Constructor */
	public Grid()
	{	
		this.cells = new char[ROWS][COLUMNS];
		for (int row = 0; row < ROWS; row++)
			for (int col = 0; col < COLUMNS; col++)
				this.cells[row][col] = EMPTY;
		
		this.numDisc = 0;
	}
	
	/**
	 * Get the grid's cells
	 * 
	 * @return grid's cells
	 */
	public char[][] getCells()
	{
		return this.cells;
	}
	
	/**
	 * Add player's disc in a specific column
	 * 
	 * @param player current player
	 * @param col column
	 * @return true if disc is placed, false otherwise
	 */
	public boolean addDisc(Player player, final int col)
	{
		int id = player.getId();
		for (int row = ROWS - 1; row >= 0; row--)
		{
			if (this.cells[row][col] == EMPTY && isIndexInBound(row, col))
			{
				this.cells[row][col] = (player.getId() == 0) ? CIRCLE : CROSS;
				this.numDisc++;
				
				won = checkWin(id, row, col);
				
				return true;
			}
			else
				continue;
		}
		
		return false;
	}
	
	/**
	 * Return player disc
	 * 
	 * @param player player id
	 * @return disc
	 */
	private char getPlayerDisc(final int player)
	{
		return (player == 0) ? CIRCLE : CROSS;
	}
	
	/**
	 * Check if grid is full
	 * 
	 * @return true if grid is full,
	 * 		   false otherwise
	 */
	public boolean isCompleted()
	{
		return numDisc == MAX_DISCS;
	}
	
	/**
	 * Check if position is inside grid's bounds
	 * 
	 * @param row grid's row
	 * @param col grid's column
	 * @return true if inside bounds, false otherwise
	 */
	public boolean isIndexInBound(final int row, final int col)
	{
		return (row >= 0 && row < ROWS) && (col >= 0 && col < COLUMNS);
	}
	
	/**
	 * Check win conditions
	 * 
	 * @param player player id
	 * @param row grid's row
	 * @param col grid's column
	 * @return type of win vertical or horizontal or diagonal
	 */
	public boolean checkWin(final int player, final int row, final int col)
	{	
		return checkVerticalWin(player, row, col) || checkHorizontalWin(player, row, col) || checkDiagonals(player, row, col);
	}
	
	/** See {@link #checkWin(int, int, int)} */
	private boolean checkVerticalWin(final int player, final int row, final int col)
	{
		// Vertical check
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col))
				break;
			
			if (getPlayerDisc(player) != this.cells[row + i][col])
				break;

			numDisc++;
			if (numDisc >= 4)
				return true;
		}
		
		return false;
	}
	
	/** See {@link #checkWin(int, int, int)} */
	private boolean checkHorizontalWin(final int player, final int row, final int col)
	{
		// Check last position to left
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row, col - i))
				break;
			
			if (getPlayerDisc(player) != this.cells[row][col - i])
				break;

			numDisc++;
			if (numDisc >= 4)
				return true;
		}
		
		// Check last position to right
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row, col + i))
				break;
			
			if (getPlayerDisc(player) != this.cells[row][col + i])
				break;
			
			numDisc++;
			if (numDisc >= 4)
				return true;
			
		}
		
		return false;
	}
	
	/** See {@link #checkWin(int, int, int)} */
	private boolean checkDiagonals(final int player, final int row, final int col)
	{	
		// from top-right to bottom-left
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row - i, col + i))
				break;
			
			if (getPlayerDisc(player) != this.cells[row - i][col + i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col - i))
				break;
			
			if (getPlayerDisc(player) != this.cells[row + i][col - i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		// from top-left to bottom-right
		numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col + i))
				break;
			
			if (getPlayerDisc(player) != this.cells[row + i][col + i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row - i, col - i))
				break;
			
			if (getPlayerDisc(player) != this.cells[row - i][col - i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		return false;
	}
	
	/**
	 * Get available columns. A column is available when is not filled
	 * 
	 * @return available columns
	 */
	public ArrayList<Integer> getAvailableColumns()
	{
		ArrayList<Integer> availableColumns = new ArrayList<>();
		
		for (int col = 0; col < COLUMNS; col++)
		{
			int value = getAvailColumnPositions(col);
			if (value > 0)
			{
				availableColumns.add(col);
			}
		}
		
		return availableColumns;
	}
	
	/**
	 * Get number of available positions of a column
	 * 
	 * @param col grid's column
	 * @return number of positions
	 */
	public int getAvailColumnPositions(final int col)
	{
		int positionsAvail = 0;
		for (int row = 0; row < ROWS; row++)
		{
			if (cells[row][col] == EMPTY)
			{
				positionsAvail++;
			}
		}
		
		return positionsAvail;
	}
	
	/**
	 * Get next available row
	 * 
	 * @param col grid's column
	 * @return first available index
	 */
	public int getAvailRowIndex(final int col)
	{
		return getAvailColumnPositions(col) - 1;
	}
	
}
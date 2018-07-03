package unicam.cs.forza4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rapresent the game's grid
 * 
 * @author gall9
 */
public class Grid implements Serializable
{
	private static final long serialVersionUID = -6825761503971516066L;
	
	public static final int MAX_DISCS = 42;
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	
	private char[][] cells;
	/** O's discs for player0 */
	public static final char CIRCLE = 'O';
	/** X's discs for player1 */
	public static final char CROSS = 'X';
	public static final char EMPTY = ' ';
	
	private int numDisc;
	public boolean won;
	
	
	/** Create the grid of rows and columns */
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
	 * Add disc of the player in column
	 * 
	 * @param player current player
	 * @param col column
	 * @return true if addDisc is done,
	 * 		   false otherwise
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
	
	private char getPlayerDisc(final int player)
	{
		return (player == 0) ? CIRCLE : CROSS;
	}
	
	/**
	 * Check grid if is full
	 * 
	 * @return true if grid is full,
	 * 		   false otherwise
	 */
	public boolean isCompleted()
	{
		return numDisc == MAX_DISCS;
	}
	
	/**
	 * The disc is in bounds of the grid
	 * 
	 * @param row
	 * @param col
	 * @return true if is in bounds,
	 * 		   false otherwise
	 */
	public boolean isIndexInBound(final int row, final int col)
	{
		return (row >= 0 && row < ROWS) && (col >= 0 && col < COLUMNS);
	}
	
	/**
	 * Check if win in vertical, horizontal and diagonal 
	 * 
	 * @param row grid's rows
	 * @param col grid's columns
	 * @return type of win vertical or horizonatal or diagonal
	 */
	public boolean checkWin(final int player, final int row, final int col)
	{	
		return checkVerticalWin(player, row, col) || checkHorizontalWin(player, row, col) || checkDiagonals(player, row, col);
	}
	
	/**
	 * Check if win in vertical
	 * 
	 * @param row
	 * @param col
	 * @return true if win in vertical,
	 * 		   false otherwise
	 */
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
	
	/**
	 * Check if win in horizontal
	 * 
	 * @param row
	 * @param col
	 * @return true if win in horizontal,
	 * 		   false otherwise
	 */
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
			
			if (getPlayerDisc(player) != this.cells[row][col + i]) // FIXME
				break;
			
			numDisc++;
			if (numDisc == 4)
				return true;
			
		}
		
		return false;
	}
	
	/**
	 * Check if win in diagonal (top left - bottom right) or
	 * (top right - bottom left)
	 * 
	 * @param row
	 * @param col
	 * @return true win in diagonal (TL-BR o TR-BL),
	 * 		   false otherwise
	 */
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
	 * Get available columns in grid. Create a ArrayList to save the
	 * available moves
	 * 
	 * @return ArrayList available moves
	 */
	public ArrayList<Integer> getAvailableColumns()
	{
		ArrayList<Integer> availableMoves = new ArrayList<>();
		
		for (int col = 0; col < COLUMNS; col++)
		{
			int value = getAvailColumnPositions(col);
			if (value > 0)
			{
				availableMoves.add(col);
			}
		}
		
		return availableMoves;
	}
	
	/**
	 * Get available columns position in grid
	 * 
	 * @param col grid's columns
	 * @return position available
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
	 * Get available rows in grid
	 * 
	 * @param col grid's columns
	 * @return 
	 */
	public int getAvailRowIndex(final int col)
	{
		return getAvailColumnPositions(col) - 1;
	}
	
}
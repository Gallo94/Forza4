package unicam.cs.forza4;

import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable
{
	private static final long serialVersionUID = -6825761503971516066L;
	
	public static final int MAX_DISCS = 42;
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	
	private char[][] cells;
	
	private int numDisc;
	public boolean won;
	
	public Grid()
	{	
		this.cells = new char[ROWS][COLUMNS];
		for (int row = 0; row < ROWS; row++)
			for (int col = 0; col < COLUMNS; col++)
				this.cells[row][col] = Player.EMPTY;
		
		this.numDisc = 0;
	}
	
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
			if (this.cells[row][col] == Player.EMPTY && isIndexInBound(row, col))
			{
				this.cells[row][col] = Player.getDisc(id);
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
	 * Check grid is full
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
	 * Check win in vertical, horizontal and diagonal 
	 * 
	 * @param row
	 * @param col
	 * @return type of win
	 */
	public boolean checkWin(final int player, final int row, final int col)
	{	
		return checkVerticalWin(player, row, col) || checkHorizontalWin(player, row, col) || checkDiagonals(player, row, col);
	}
	
	/**
	 * Check win in vertical
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
			
			if (Player.getDisc(player) != this.cells[row + i][col])
				break;

			numDisc++;
			if (numDisc >= 4)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Check win in horizontal
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
			
			if (Player.getDisc(player) != this.cells[row][col - i])
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
			
			if (Player.getDisc(player) != this.cells[row][col + i]) // FIXME
				break;
			
			numDisc++;
			if (numDisc == 4)
				return true;
			
		}
		
		return false;
	}
	
	/**
	 * Check diagonal win (top left - bottom right) or
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
			
			if (Player.getDisc(player) != this.cells[row - i][col + i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col - i))
				break;
			
			if (Player.getDisc(player) != this.cells[row + i][col - i])
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
			
			if (Player.getDisc(player) != this.cells[row + i][col + i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row - i, col - i))
				break;
			
			if (Player.getDisc(player) != this.cells[row - i][col - i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * 
	 * @return
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
	 * 
	 * 
	 * @param col
	 * @return
	 */
	public int getAvailColumnPositions(final int col)
	{
		int positionsAvail = 0;
		for (int row = 0; row < ROWS; row++)
		{
			if (cells[row][col] == Player.EMPTY)
			{
				positionsAvail++;
			}
		}
		
		return positionsAvail;
	}
	
	/**
	 * 
	 * 
	 * @param col
	 * @return
	 */
	public int getAvailRowIndex(final int col)
	{
		return getAvailColumnPositions(col) - 1;
	}
	
}
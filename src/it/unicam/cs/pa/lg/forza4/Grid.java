package it.unicam.cs.pa.lg.forza4;

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
	 * Aggiunge il disco del relativo player nella colonna
	 * 
	 * @param player giocatore corrente
	 * @param col colonna della griglia
	 * @return l'aggiunta di un disco
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
	 * Controlla che la griglia sia completa
	 * 
	 * @return numero massimo di dischi inseriti, griglia completa
	 */
	public boolean isCompleted()
	{
		return numDisc == MAX_DISCS;
	}
	
	/**
	 * Verifica che il disco venga inserito all'interno dei margini
	 * della griglia.
	 * 
	 * @param row riga della griglia
	 * @param col colonna della griglia
	 * @return i limiti della griglia 
	 */
	public boolean isIndexInBound(final int row, final int col)
	{
		return (row >= 0 && row < ROWS) && (col >= 0 && col < COLUMNS);
	}
	
	/**
	 * Verifica la vittoria in verticale, orizzontale e diagonale.
	 * 
	 * @param row riga della griglia
	 * @param col colonna della griglia
	 * @return il tipo di vittoria
	 */
	public boolean checkWin(final int player, final int row, final int col)
	{	
		return checkVerticalWin(player, row, col) || checkHorizontalWin(player, row, col) || checkDiagonals(player, row, col);
	}
	
	/**
	 * Verifica la vittoria in verticale.
	 * 
	 * @param row  riga della griglia
	 * @param col  colonna della griglia
	 * @return la vittoria in verticale
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
	 * Verifica la vittoria in orizzontale.
	 * 
	 * @param row riga della griglia
	 * @param col colonna della griglia
	 * @return la vittoria in orizzontale
	 */
	private boolean checkHorizontalWin(final int player, final int row, final int col)
	{
		// Controllo a sinistra dell'ultima posizione usata
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
		
		// Controllo a destra dell'ultima posizione usata
		numDisc = 1;
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
	 * Verifica la vittoria in diagonale (top left - bottom right) e
	 * (top right - bottom left)
	 * 
	 * @param row riga della griglia
	 * @param col colonna della griglia
	 * @return vittoria in diagonale(TL-BR o TR-BL) 
	 */
	private boolean checkDiagonals(final int player, final int row, final int col)
	{	
		// da top-right a bottom-left
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
		
		// da top-left a bottom-right
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
	
	public int getAvailRowIndex(final int col)
	{
		return getAvailColumnPositions(col) - 1;
	}
}
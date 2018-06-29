package it.unicam.cs.pa.lg.forza4;

import java.io.Serializable;

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
	public boolean addDisc(Player player, final byte col)
	{
		for (int i = ROWS - 1; i >= 0; i--)
		{
			if (this.cells[i][col] == Player.EMPTY && isIndexInBound(i, col))
			{
				this.cells[i][col] = player.getDisc();
				this.numDisc++;
				
				won = checkWin(player, i, col);
				
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
	public boolean checkWin(Player player, final int row, final int col)
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
	private boolean checkVerticalWin(Player player, final int row, final int col)
	{
		// Vertical check
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col))
				break;
			
			if (player.getDisc() != this.cells[row + i][col])
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
	private boolean checkHorizontalWin(Player player, final int row, final int col)
	{
		// Controllo a sinistra dell'ultima posizione usata
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row, col - i))
				break;
			
			if (player.getDisc() != this.cells[row][col - i])
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
			
			if (player.getDisc() != this.cells[row][col + i]) // FIXME
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
	private boolean checkDiagonals(Player player, final int row, final int col)
	{	
		// da top-right a bottom-left
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row - i, col + i))
				break;
			
			if (player.getDisc() != this.cells[row - i][col + i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col - i))
				break;
			
			if (player.getDisc() != this.cells[row + i][col - i])
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
			
			if (player.getDisc() != this.cells[row + i][col + i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row - i, col - i))
				break;
			
			if (player.getDisc() != this.cells[row - i][col - i])
				break;
			
			numDisc++;
		}
		
		if (numDisc >= 4)
			return true;
		
		return false;
	}
}
package it.unicam.cs.pa.lg.forza4;

import java.io.PrintStream;

public class Grid {
	
	public static final int MAX_DISCS = 42;
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	private Cell[][] cells;
	private int disc_counter;
	public boolean won;
	
	public Grid()
	{
		this.cells = new Cell[ROWS][COLUMNS];
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				this.cells[i][j] = new Cell();
		
		this.disc_counter = 0;
	}
	
	public boolean addDisc(Player player, final byte col)
	{
		for (int i = ROWS - 1; i >= 0; i--)
		{
			Cell cell = this.cells[i][col];
			if (cell.isEmpty())
			{
				cell.setPlayer(player);
				cell.setRow((byte)i);
				cell.setCol(col);
				won = checkWin((byte)i, col);
				
				this.disc_counter++;
				
				return true;
			}
			else continue;
		}
		
		return false;
	}
	
	public boolean isCompleted()
	{
		return disc_counter == MAX_DISCS;
	}
	
	public boolean isIndexInBound(final int row, final int col)
	{
		return (row >= 0 && row < ROWS) && (col >= 0 && col < COLUMNS);
	}
	
	public boolean checkWin(final int row, final int col)
	{	
		return checkVerticalWin(row, col) || checkHorizontalWin(row, col) || checkDiagonals(row, col);
	}
	
	private boolean checkVerticalWin(final int row, final int col)
	{
		Cell lastCell = this.cells[row][col];

		// Vertical check
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col))
				break;
			
			Cell cell = this.cells[row + i][col];
			if (lastCell.getPlayer() == cell.getPlayer())
			{
				numDisc++;
				if (numDisc == 4)
					return true;
			}
		}
		
		return false;
	}
	
	private boolean checkHorizontalWin(final int row, final int col)
	{
		Cell lastCell = this.cells[row][col];

		// Controllo a sinistra dell'ultima posizione usata
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row, col - i))
				break;
			
			Cell cell = this.cells[row][col - i];
			if (lastCell.getPlayer() == cell.getPlayer())
			{
				numDisc++;
				if (numDisc == 4)
					return true;
			}
		}
		
		// Controllo a destra dell'ultima posizione usata
		numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row, col + i))
				break;
			
			Cell cell = this.cells[row][col + i];
			if (lastCell.getPlayer() == cell.getPlayer())
			{
				numDisc++;
				if (numDisc == 4)
					return true;
			}
		}
		
		return false;
	}
	
	private boolean checkDiagonals(final int row, final int col)
	{
		Cell lastCell = this.cells[row][col];
		
		int numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col + i))
				break;
			
			Cell cell = this.cells[row + i][col + i];
			if (lastCell.getPlayer() == cell.getPlayer())
			{
				numDisc++;
				if (numDisc == 4)
					return true;
			}
		}
		
		numDisc = 1;
		for (int i = 1; i <= 3; i++)
		{
			if (!isIndexInBound(row + i, col - i))
				break;
			
			Cell cell = this.cells[row + i][col - i];
			if (lastCell.getPlayer() == cell.getPlayer())
			{
				numDisc++;
				if (numDisc == 4)
					return true;
			}
		}
		
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////
	
	public static void printField(PrintStream writer, final Grid grid)
	{
		int width = Grid.COLUMNS;
		int height = Grid.ROWS;
		
		printColumnsHeader(writer, width);
		for( int i=0; i<height ; i++ )
		{
			printRowDelimiter(writer, width);
			printRow(writer, i, grid);
		}
		
		printRowDelimiter(writer,width);
	}
	
	private static void printRowDelimiter(PrintStream writer , int size)
	{
		writer.print("    ");
		for( int i=0 ; i<size ; i++ ) {
			writer.print("+---");
		}
		writer.println("+");
	}
	
	private static void printRow(PrintStream writer, int row, final Grid grid)
	{
		writer.print(String.format("%4d", row ));

		int width = Grid.COLUMNS;
		for (int i = 0; i < width; i++)
		{
			writer.print("|");
			writer.print(" " + (!grid.cells[row][i].isEmpty() ? grid.cells[row][i].getPlayer().getId() : " ") + " ");
		}
		writer.println("|");
	}
	
	private static void printColumnsHeader(PrintStream writer, int width) {
		writer.print("   ");
		for( int i=0 ; i<width ; i++ ) {
			writer.print(String.format("%4d", i));
		}
		writer.println();
	}

}
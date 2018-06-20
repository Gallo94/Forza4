package it.unicam.cs.pa.lg.forza4;

import java.io.PrintStream;

public class Grid {
	
	public static final int MAX_DISCS = 42;
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	private Cell[][] cells;
	private int disc_counter;
	
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
				cell.setDisc(player, (byte)i, col);
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
			writer.print(" " + (!grid.cells[row][i].isEmpty() ? "R" : " ") + " ");
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
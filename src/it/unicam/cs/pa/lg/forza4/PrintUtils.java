package it.unicam.cs.pa.lg.forza4;

import java.io.PrintStream;

/**
 * Print match's grid
 * 
 * @author Luca
 */
public class PrintUtils
{
	/** Reset escape character */
	public static final String ANSI_RESET = "\u001B[0m";
	/** Bright red escape character */
	public static final String ANSI_RED = "\u001b[41;1m";
	/** Bright green escape character */
	public static final String ANSI_GREEN = "\u001b[42;1m";
	
	/**
	 * Print grid
	 * 
	 * @param writer
	 * @param grid match's grid
	 */
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

	/**
	 * Print border rows and upper/lower edges +---+ 
	 * 
	 * @param writer
	 * @param size
	 */
	private static void printRowDelimiter(PrintStream writer, int size)
	{
		writer.print("     ");
		for( int i=0 ; i<size ; i++ ) {
			writer.print("+---");
		}
		writer.println("+");
	}
	
	/**
	 * Print rows and side edges |  |
	 * 
	 * @param writer
	 * @param row grid's rows
	 * @param grid match's grid
	 */
	private static void printRow(PrintStream writer, int row, final Grid grid)
	{
		/** Print header for rows */
		writer.print(String.format("%4d ", row));
		
		int width = Grid.COLUMNS;
		String disc = " ";
		for (int i = 0; i < width; i++)
		{
			if (grid.getCells()[row][i] == Player.CIRCLE)
				disc = ANSI_GREEN + Player.CIRCLE + ANSI_RESET;
			else if (grid.getCells()[row][i] == Player.CROSS) 
				disc = ANSI_RED + Player.CROSS + ANSI_RESET;
			else
				disc = Player.EMPTY + "";
				
			writer.print("|");
			writer.print(" " + disc + " ");
		}
		writer.println("|");
	}
	
	/**
	 * Print header for columns
	 * 
	 * @param writer
	 * @param width grid's width
	 */
	private static void printColumnsHeader(PrintStream writer, int width) {
		writer.print("    ");
		for( int i=0 ; i<width ; i++ ) {
			writer.print(String.format("%4d", i));
		}
		writer.println();
	}
}

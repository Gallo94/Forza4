package it.unicam.cs.pa.lg.forza4;

import java.io.PrintStream;

public class PrintUtils
{
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
	
	private static void printRowDelimiter(PrintStream writer, int size)
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
			writer.print(" " + (!grid.getCells()[row][i].isEmpty() ? grid.getCells()[row][i].getPlayer().getId() : " ") + " ");
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

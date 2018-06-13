package it.unicam.cs.pa.lg.forza4;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Battlefield {
	
	public static final int MAX_DISCS = 42;
	public static final int ROWS = 7;
	public static final int COLUMNS = 6;
	private Cell[][] cells;
	private int disc_counter;
	
	public Battlefield()
	{
		this.cells = new Cell[ROWS][COLUMNS];
		this.disc_counter = 0;
		
		printField(new PrintStream(new FileOutputStream(FileDescriptor.out)), ROWS, COLUMNS);
	}
	
	public boolean addDisc(Disc disc, final int column)
	{
		for (int i = ROWS - 1; i >= 0; i--)
		{
			Cell cell = this.cells[column][i];
			if (cell.isEmpty())
			{
				cell.setDisc(disc);
				this.disc_counter++;
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
	public static void printField(PrintStream writer, int width, int height) {
		printColumnsHeader(writer, width);
		for( int i=0; i<height ; i++ ) {
			printRowDelimiter(writer,width);
			printRow(writer, i, width);
		}
		printRowDelimiter(writer,width);
	}
	
	private static void printRowDelimiter(PrintStream writer , int size ) {
		writer.print("    ");
		for( int i=0 ; i<size ; i++ ) {
			writer.print("+---");
		}
		writer.println("+");
	}
	
	private static void printRow(PrintStream writer , int row , int width) {
		writer.print(String.format("%4d", row ));
		for( int i=0 ; i<width ; i++ ) {
			writer.print("|   ");
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
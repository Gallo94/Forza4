package it.unicam.cs.pa.lg.forza4;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Battlefield {
	
	public static final int DEFAULT_WIDTH = 7;
	public static final int DEFAULT_HEIGHT = 6;
	private Cell[][] cells;
	private int disc;
	
	public Battlefield()
	{
		this.cells = new Cell[DEFAULT_WIDTH][DEFAULT_HEIGHT];
		printField(new PrintStream(new FileOutputStream(FileDescriptor.out)), DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
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
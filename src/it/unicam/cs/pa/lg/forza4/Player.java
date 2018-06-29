package it.unicam.cs.pa.lg.forza4;

import java.io.Serializable;
/**
 * Vengono dichiarati il name, l'ID del Player
 * 
 * @author Luca
 */
public abstract class Player implements Serializable
{

	private static final long serialVersionUID = 3324651541378196476L;
	public static final char CIRCLE = 'O';
	public static final char CROSS = 'X';
	public static final char EMPTY = ' ';
	
	protected String name;
	protected int id;
	
	public Player(final int id)
	{
		this.name = String.valueOf(id);
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public static char getDisc(int id)
	{
		return id == 0 ? CIRCLE : CROSS;
	}
	
	public boolean placeDisc(Grid grid, final int col)
	{
		return grid.addDisc(this, col);
	}
	
	public abstract int input(final Grid grid);
}

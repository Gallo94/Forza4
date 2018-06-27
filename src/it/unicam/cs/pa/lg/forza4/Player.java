package it.unicam.cs.pa.lg.forza4;

import java.io.Serializable;
/**
 * Vengono dichiarati il name, l'ID del Player
 * 
 * @author Luca
 */
public abstract class Player implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324651541378196476L;
	
	protected String name;
	protected byte id;
	
	public Player(final byte id)
	{
		this.name = String.valueOf(id);
		this.id = id;
	}
	
	public byte getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean placeDisc(Grid grid, final byte col)
	{
		return grid.addDisc(this, col);
	}
	
	public abstract byte input();
}

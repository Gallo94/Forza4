package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;

public abstract class Player
{
	protected String name;
	protected byte id;
	
	public Player(InetAddress address, byte id)
	{
		this.name = address.getHostAddress().toString();
		this.id = id;
	}
	
	public byte getId()
	{
		return this.id;
	}
	
	public boolean placeDisc(Grid grid, final byte col)
	{
		return grid.addDisc(this, col);
	}
	
	public abstract byte input();
}

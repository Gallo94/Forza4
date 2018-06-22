package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;

public class Player
{
	private String name;
	private byte id;
	
	public Player(InetAddress address, byte id)
	{
		this.name = address.getHostAddress().toString();
		this.id = id;
	}
	
	public byte getId()
	{
		return this.id;
	}
	
	public void placeDisc(Grid grid, final byte col)
	{
		grid.addDisc(this, col);
	}
	
}

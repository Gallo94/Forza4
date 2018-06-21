package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;

public class Player
{
	private Grid grid;
	private String name;
	private byte id;
	
	public Player(Grid grid, InetAddress address, byte id)
	{
		this.grid = grid;
		this.name = address.getHostAddress().toString();
		this.id = id;
	}
	
	public void placeDisc(byte col)
	{
		grid.addDisc(this, col);
	}
	
	public byte getId()
	{
		return this.id;
	}
}

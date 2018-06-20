package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;

public class Player
{
	private Grid grid;
	private String name;
	
	public Player(Grid grid, InetAddress address)
	{
		this.grid = grid;
		this.name = address.getHostAddress().toString();
		
		System.out.println("Player: " + this.name);
	}
	
	public void placeDisc(byte col)
	{
		grid.addDisc(this, col);
	}
}

package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;
import java.util.Scanner;

public class HumanPlayer
{
	private String name;
	private byte id;
	
	public HumanPlayer(InetAddress address, byte id)
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
	
	public byte input()
	{
		System.out.println("Your turn!");
		System.out.println("Insert column between 0-6: ");
		Scanner scanner = new Scanner(System.in);
		while (!scanner.hasNextByte())
		{
			scanner.next();
			System.out.println("Enter the correct number in range");
		}
		
		return scanner.nextByte();
	}
}

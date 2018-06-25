package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;
import java.util.Scanner;

public class PlayerHuman extends Player
{	
	public PlayerHuman(byte id)
	{
		super(id);
	}
	
	@Override
	public byte input()
	{
		System.out.println("Your turn!");
		System.out.println("Insert column between 0-6: ");
		Scanner scanner = new Scanner(System.in);
		byte value;
		do
		{
			while (!scanner.hasNextByte())
			{
				scanner.next();
				System.out.println("Usage: numbers [0-6]");
			}
		
			value = scanner.nextByte();
			if (value < 0 || value > 6)
				System.out.println("Enter the correct number in range");
		}
		while (value < 0 || value > 6);
		
		// FIXME: close scanner
//		scanner.close();
		
		return value;
	}
}

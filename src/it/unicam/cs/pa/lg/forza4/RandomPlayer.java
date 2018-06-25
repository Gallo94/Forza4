package it.unicam.cs.pa.lg.forza4;

import java.net.InetAddress;
import java.util.Random;

public class RandomPlayer extends Player
{
	public RandomPlayer(InetAddress address,  final byte id)
	{
		super(address, id);
	}
	
	@Override
	public byte input()
	{
		Random rng = new Random(System.currentTimeMillis());
		return (byte) rng.nextInt(6);
	}

}

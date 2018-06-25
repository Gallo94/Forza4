package it.unicam.cs.pa.lg.forza4;

import java.util.Random;

public class RandomPlayer extends Player
{
	public RandomPlayer(final byte id)
	{
		super(id);
	}
	
	@Override
	public byte input()
	{
		Random rng = new Random(System.currentTimeMillis());
		return (byte) rng.nextInt(6);
	}

}

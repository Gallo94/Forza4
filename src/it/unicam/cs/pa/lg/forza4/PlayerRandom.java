package it.unicam.cs.pa.lg.forza4;

import java.util.Random;

public class PlayerRandom extends Player
{
	private static final long serialVersionUID = -1123456076446536258L;

	public PlayerRandom(final int id)
	{
		super(id);
	}
	
	@Override
	public int input()
	{
		Random rng = new Random(System.currentTimeMillis());
		return rng.nextInt(6);
	}
	
	
}
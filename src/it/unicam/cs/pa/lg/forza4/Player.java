package it.unicam.cs.pa.lg.forza4;

public abstract class Player
{
	private static final int MAX_DISCS_PER_PLAYER = 21;
	private Disc[] discs;
	private Battlefield bf;
	
	public Player(Battlefield bf)
	{
		this.bf = bf;
		this.discs = new Disc[MAX_DISCS_PER_PLAYER];
		for (int i = 0; i < MAX_DISCS_PER_PLAYER; i++)
			this.discs[i].setPlayer(this);
	}

	public boolean placeDisc(int column)
	{
		
		return false;
	}}

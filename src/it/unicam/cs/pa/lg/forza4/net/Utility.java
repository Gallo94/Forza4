package it.unicam.cs.pa.lg.forza4.net;

import java.util.Random;

public class Utility
{
	private final int NO_TURN	  = 0;
	private final int PLAYER_TURN = 1;
	private final int PC_TURN	  = -1;
	
	private final char NO_MARK	   = ' ';
	private final char PLAYER_MARK = 'X';
	private final char PC_MARK	   = 'O';
	
	private final int GAME_LEVEL = 3;
	
	/* Descrive di chi è il turno */
	private int whoTurn = NO_TURN;
	
	/* Descrive chi inizierà a giocare */
	private int firstTurn = NO_TURN;
	
	public void chooseFirstPlayer()
	{
		Random generator = new Random();
		if(generator.nextInt(2) == 0) { setFirstTurn(PLAYER_TURN); } else { setFirstTurn(PC_TURN); }
	}
	
	public void setFirstTurn(int firstTurn)
	{
		this.firstTurn = firstTurn;
	}
	
	public int getPLAYER_TURN()
	{
		return PLAYER_TURN;
	}
	
	public int getPC_TURN()
	{
		return PC_TURN;
	}
}

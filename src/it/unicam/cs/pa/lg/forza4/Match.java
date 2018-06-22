package it.unicam.cs.pa.lg.forza4;

import java.sql.Time;
import java.util.Random;

// Manager della partita
public class Match
{
	enum MatchStatus
	{
		IDLE,
		P0_TURN,
		P1_TURN,
		P0_WON,
		P1_WON,
		DRAW
	}

	private int curPlayer;
	private Grid grid;
	
	MatchStatus status = MatchStatus.IDLE; 
	
	
	public Match()
	{
		grid = new Grid();
		
		// First to play
		Random rng = new Random(System.currentTimeMillis());
		curPlayer = rng.nextInt(1);
		status = curPlayer == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN; 
	}
	
	public void switchTurn()
	{		
		curPlayer = (curPlayer == 0) ? 1 : 0;
		status = curPlayer == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN;  
	}
	
	public void checkVictory()
	{		
		status = (status == MatchStatus.P0_TURN) ? MatchStatus.P0_WON : MatchStatus.P1_WON;
	}
	
	public void checkDraw()
	{
		// FIXME
		if (!grid.isCompleted())
			return;
		
		status = MatchStatus.DRAW;
	}
	
	public MatchStatus getStatus()	
	{
		return status;
	}
	
	public int getCurrentPlayer()
	{
		return curPlayer;
	}
	
	public Grid getGrid()
	{
		return this.grid;
	}
}

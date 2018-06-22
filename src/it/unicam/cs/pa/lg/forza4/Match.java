package it.unicam.cs.pa.lg.forza4;

import java.sql.Time;
import java.util.Random;

// Manager della partita
public class Match
{
	enum MatchStatus
	{
		NONE,
		P0_TURN,
		P1_TURN,
		P0_WON,
		P1_WON,
		DRAW
	}

	private Player player0;
	private Player player1;
	private Player curPlayer;
	private Grid grid;
	
	
	MatchStatus status = MatchStatus.NONE; 
	
	
	public Match(final Player p0, final Player p1)
	{
		this.player0 = p0;
		this.player1 = p1;
		this.grid = new Grid();
		
		// First to play
		Random rng = new Random(System.currentTimeMillis());
		int randNumber = rng.nextInt(1);
		this.curPlayer = randNumber == 0 ? player0 : player1;
		this.status = randNumber == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN; 
	}
	
	public void switchTurn()
	{
		curPlayer = (curPlayer == player0) ? player1 : player0;
		status = (status == MatchStatus.P0_TURN) ? MatchStatus.P1_TURN : MatchStatus.P0_TURN;  
	}
	
	public void checkVictory()
	{
		if (!grid.won)
			return;
		
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
	
	public Grid getGrid()
	{
		return this.grid;
	}
}

package it.unicam.cs.pa.lg.forza4;

import java.util.Random;

/**
 * Rapresent a game's manager
 * 
 * @author Luca
 */
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
	
	private int winPlayer;
	
	/** Create a match */
	public Match()
	{
		grid = new Grid();
		
		// Who is first player?
		Random rng = new Random(System.currentTimeMillis());
		curPlayer = rng.nextInt(1);
		status = curPlayer == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN;
		
		winPlayer = -1;
	}
	
	/** Switch turn to player1 or player0 */
	public void switchTurn()
	{
		if (!this.grid.won)
		{
			curPlayer = (curPlayer == 0) ? 1 : 0;
			status = curPlayer == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN;
		}
	}
	
	/** Check win player1 or player0 */
	public void checkVictory()
	{	
		if (this.grid.won)
		{
			status = (status == MatchStatus.P0_TURN) ? MatchStatus.P0_WON : MatchStatus.P1_WON;
			winPlayer = (status == MatchStatus.P0_WON) ? 0 : 1;
		}
	}
	
	/** Check draw */
	public void checkDraw()
	{
		// FIXME
		if (!grid.isCompleted())
			return;
		
		status = MatchStatus.DRAW;
	}
	
	/**
	 *  Get the match's status
	 * 
	 * @return status
	 */
	public MatchStatus getStatus()	
	{
		return status;
	}
	
	/**
	 * Get the match's current player
	 * 
	 * @return current player
	 */
	public int getCurrentPlayer()
	{
		return curPlayer;
	}
	
	/**
	 * Get the match's win player
	 * 
	 * @return win player
	 */
	public int getWinPlayer()
	{
		return winPlayer;
	}
	
	/**
	 * Get the match's grid
	 * 
	 * @return grid
	 */
	public Grid getGrid()
	{
		return this.grid;
	}
}

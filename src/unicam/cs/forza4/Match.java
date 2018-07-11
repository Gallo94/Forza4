package unicam.cs.forza4;

import java.util.Random;

/** Match manages game states */
public class Match
{
	/** MatchStatus represent each possible game state */
	enum MatchStatus
	{
		/** Idle status */
		IDLE,
		/** Turn of the player0 */
		P0_TURN,
		/** Turn of the player1 */
		P1_TURN,
		/** Player0 won */
		P0_WON,
		/** Player1 won */
		P1_WON,
		/** Draw */
		DRAW
	}

	/** Game's grid */
	private Grid grid;
	/** Initial state of the match */
	MatchStatus status = MatchStatus.IDLE;
	/** Current player */
	private int curPlayer;
	/** Winning player */
	private int winPlayer;
	
	/** Constructor */
	public Match()
	{
		grid = new Grid();
		
		// Who is first player?
		Random rng = new Random(System.currentTimeMillis());
		curPlayer = rng.nextInt(1);
		status = curPlayer == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN;
		
		winPlayer = -1;
	}
	
	/** Switch players turn */
	public void switchTurn()
	{
		if (!this.grid.won && !grid.isCompleted())
		{
			curPlayer = (curPlayer == 0) ? 1 : 0;
			status = curPlayer == 0 ? MatchStatus.P0_TURN : MatchStatus.P1_TURN;
		}
	}
	
	/** Check if current player has won */
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
		if (!grid.isCompleted())
			return;
		
		status = MatchStatus.DRAW;
	}
	
	/**
	 * Get the match's status
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

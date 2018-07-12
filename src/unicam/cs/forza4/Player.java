package unicam.cs.forza4;

/** Player */
public abstract class Player 
{
	/** Player's id */
	protected int id;

	/**
	 * Constructor
	 * 
	 * @param id player's id
	 */
	public Player(final int id)
	{
		this.id = id;
	}

	/**
	 * Get player's id
	 * 
	 * @return player's id
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Place the disc in the grid's column
	 * 
	 * @param grid game's grid
	 * @param col grid's column
	 * @return true if place disc is done, false otherwise
	 */
	public boolean placeDisc(Grid grid, final int col)
	{
		return grid.addDisc(this, col);
	}

	/**
	 * Returns a input from player.
	 * Must be overridden by inherited class
	 * 
	 * @param grid game's grid
	 * @return player's input
	 */
	public abstract int input(final Grid grid);
}

package unicam.cs.forza4;

/**
 * Player
 * 
 * @author Luca
 */
public abstract class Player 
{
	protected String name;		// FIXME
	protected int id;

	/**
	 * Constructor
	 * 
	 * @param id player's id
	 */
	public Player(final int id)
	{
		this.name = String.valueOf(id);
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
	 * Get player's name
	 * 
	 * @return player's name
	 */
	public String getName()
	{
		return this.name;
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
	 * @param grid
	 * @return player's input
	 */
	public abstract int input(final Grid grid);
}

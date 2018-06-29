package unicam.cs.forza4;

import java.io.Serializable;

/**
 * Rappresent a Player
 * 
 * @author Luca
 */
public abstract class Player implements Serializable
{

	private static final long serialVersionUID = 3324651541378196476L;
	public static final char CIRCLE = 'O';
	public static final char CROSS = 'X';
	public static final char EMPTY = ' ';
	
	/** Name and ID of the player */
	protected String name;
	protected int id;
	
	/**
	 *Create a player
	 * 
	 * @param id ID's player
	 */
	public Player(final int id)
	{
		this.name = String.valueOf(id);
		this.id = id;
	}
	
	/**
	 * Get ID's player
	 * 
	 * @return id ID's player
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Get name's player
	 * 
	 * @return name name's player
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Get ID's disc
	 * 
	 * @param id ID's player
	 * @return CIRCLE if player0
	 * 		   CROSS otherwise
	 */
	public static char getDisc(int id)
	{
		return id == 0 ? CIRCLE : CROSS;
	}
	
	/**
	 * Place the disc in the grid's column
	 * 
	 * @param grid
	 * @param col
	 * @return true if place disc is done
	 * 		   false otherwise
	 */
	public boolean placeDisc(Grid grid, final int col)
	{
		return grid.addDisc(this, col);
	}
	
	public abstract int input(final Grid grid);
}

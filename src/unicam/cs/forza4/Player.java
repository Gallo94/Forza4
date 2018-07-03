package unicam.cs.forza4;

import java.io.Serializable;

/**
 * Rapresent the Player in game
 * 
 * @author Luca
 */
public abstract class Player implements Serializable {

	private static final long serialVersionUID = 3324651541378196476L;

	protected String name;
	protected int id;

	/**
	 * Create the player's ID
	 * 
	 * @param id
	 *            ID's player
	 */
	public Player(final int id) {
		this.name = String.valueOf(id);
		this.id = id;
	}

	/**
	 * Get ID's player
	 * 
	 * @return id ID's player
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get name's player
	 * 
	 * @return name name's player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Place the disc in the grid's column
	 * 
	 * @param grid
	 *            game's grid
	 * @param col
	 *            grid's columns
	 * @return true if place disc is done false otherwise
	 */
	public boolean placeDisc(Grid grid, final int col) {
		return grid.addDisc(this, col);
	}

	public abstract int input(final Grid grid);
}

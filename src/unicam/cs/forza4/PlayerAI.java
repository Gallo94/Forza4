package unicam.cs.forza4;

import java.util.ArrayList;

/**
 * Rapresent the AI Player (PC)
 * 
 * @author gall9
 */
public class PlayerAI extends Player
{
	/** If there are 3 discs in row of opponent */
	public static final int MUST_WIN = 3;
	/** If there are 2 discs in row of opponent */
	public static final int MUST_BLOCK = 2;
	/** If there is 1 disc of opponent */
	public static final int NORMAL = 1;
	
	/**
	 * Create the AI player
	 * 
	 * @param id ID player
	 */
	public PlayerAI(int id)
	{
		super(id);
		
	}
	
	/** Handle input for the AI player */
	@Override
	public int input(final Grid grid)
	{
		// Get available positions
		ArrayList<Integer> availableColumns = grid.getAvailableColumns();
		
		// Evaluate weights
		int opponentId = (this.id % 2 == 0) ? 1 : 0;
		int[] columnValues = new int[availableColumns.size()];
		for (int i = 0; i < columnValues.length; i++)
		{
			int col = availableColumns.get(i);
			boolean mustWin = grid.checkWin(this.id, grid.getAvailRowIndex(col), col);
			boolean mustBlock = grid.checkWin(opponentId, grid.getAvailRowIndex(col), col);
			columnValues[i] = mustWin   ? MUST_WIN : 
							  mustBlock ? MUST_BLOCK : NORMAL; 
		}
		
		ArrayList<Integer> bestColumns = new ArrayList<>();

		for (int i = 0; i < columnValues.length; i++)
		{
			if (columnValues[i] == MUST_WIN)
			{
				bestColumns.add(availableColumns.get(i));
			}
		}

		if (bestColumns.size() == 0)
		{
			for (int i = 0; i < columnValues.length; i++)
			{
				if (columnValues[i] == MUST_BLOCK)
				{
					bestColumns.add(availableColumns.get(i));
				}
			}
		}
		
		if (bestColumns.size() == 0)
		{
			for (int i = 0; i < columnValues.length; i++)
			{
				if (columnValues[i] == NORMAL)
				{
					bestColumns.add(availableColumns.get(i));
				}
			}			
		}
		
		int index = (int) (Math.random() * bestColumns.size());					
		return bestColumns.get(index);
	}
}

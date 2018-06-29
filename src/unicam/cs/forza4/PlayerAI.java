package unicam.cs.forza4;

import java.util.ArrayList;

public class PlayerAI extends Player
{
	private static final long serialVersionUID = -8413214234228884030L;
			
	public static final int MUST_WIN = 3; 
	public static final int MUST_BLOCK = 2;
	public static final int NORMAL = 1; 
	
	public PlayerAI(int id)
	{
		super(id);
		
	}

	@Override
	public int input(final Grid grid)
	{
		// Get available positions
		ArrayList<Integer> availableColumns = grid.getAvailableColumns();
		
		// Evaluate weights
		int opponentId = 0;
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

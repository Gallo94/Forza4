package it.unicam.cs.pa.lg.forza4;

import java.util.ArrayList;
import java.util.Random;

public class PlayerAI extends Player
{
	private static final long serialVersionUID = -8413214234228884030L;
		
	private int[] valueColumn = {3, 4, 2, 5, 1, 6, 0};
	
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

		// Debug
		System.out.println("Available columns");
		for (int i = 0; i < availableColumns.size(); i++)
		{
			System.out.println(availableColumns.get(i) + " : " + grid.getAvailColumnPositions(availableColumns.get(i)));
		}
		
		System.out.println("bestColumns[" + index + "] = " + bestColumns.get(index));
					
		return bestColumns.get(index);
	}
}

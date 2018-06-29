package it.unicam.cs.pa.lg.forza4;

import java.util.ArrayList;
import java.util.Random;

public class PlayerAI extends Player
{
	private static final long serialVersionUID = -8413214234228884030L;
		
	private int[] valueColumn = {3, 4, 2, 5, 1, 6, 0};
	
	public PlayerAI(int id)
	{
		super(id);
		
	}

	@Override
	public int input(final Grid grid)
	{
		// Get available positions
		ArrayList<Integer> availableColumns = grid.getAvailableColumns();
		for (int i = 0; i < availableColumns.size(); i++)
		{
			System.out.println(availableColumns.get(i) + " : " + grid.getAvailColumnPositions(availableColumns.get(i)));
		}
		
		// Evaluate weights
		int opponentId = 0;
		int[] columnValues = new int[availableColumns.size()];
		for (int i = 0; i < columnValues.length; i++)
		{
			int col = availableColumns.get(i); 
			boolean mustBlock = grid.checkWin(opponentId, grid.getAvailRowIndex(col), col);
			columnValues[i] = mustBlock ? 2 : 1;
		}
		
		ArrayList<Integer> bestColumns = new ArrayList<>();
		for (int i = 0; i < columnValues.length; i++)
		{
			if (columnValues[i] == 2)
			{
				bestColumns.add(availableColumns.get(i));
			}
		}
		
		if (bestColumns.size() == 0)
		{
			for (int i = 0; i < columnValues.length; i++)
			{
				if (columnValues[i] == 1)
				{
					bestColumns.add(availableColumns.get(i));
				}
			}			
		}
			
		int index = (int) (Math.random() * bestColumns.size());
		return bestColumns.get(index);
	}
}

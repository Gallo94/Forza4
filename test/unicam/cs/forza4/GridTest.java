package unicam.cs.forza4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Tests Draw and Win's types */
public class GridTest
{

	public GridTest()
	{
	}
	
	/** Test Draw */
	@Test
	public void checkDrawTest()
	{
		final Player player = new PlayerHuman(0);
		final Player opponent = new PlayerHuman(1);
		
		Grid grid = new Grid();
		
		// First row
		grid.addDisc(player, 0);
		grid.addDisc(opponent, 1);
		grid.addDisc(player, 2);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 5);
		grid.addDisc(player, 6);
		
		// Second row
		grid.addDisc(opponent, 1);
		grid.addDisc(player, 0);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 2);
		grid.addDisc(opponent, 5);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 6);
		
		// Third row
		grid.addDisc(opponent, 0);
		grid.addDisc(player, 1);
		grid.addDisc(opponent, 2);
		grid.addDisc(player, 3);
		grid.addDisc(opponent, 4);
		grid.addDisc(player, 5);
		grid.addDisc(opponent, 6);
		
		// Fourth row
		grid.addDisc(player, 1);
		grid.addDisc(opponent, 0);
		grid.addDisc(player, 3);
		grid.addDisc(opponent, 2);
		grid.addDisc(player, 5);
		grid.addDisc(opponent, 4);
		grid.addDisc(player, 6);
		
		// Fifth row
		grid.addDisc(opponent, 1);
		grid.addDisc(player, 0);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 2);
		grid.addDisc(opponent, 5);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 6);
		
		// Last row
		grid.addDisc(opponent, 0);
		grid.addDisc(player, 1);
		grid.addDisc(opponent, 2);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 5);
		grid.addDisc(opponent, 6);
		
		if (grid.isCompleted() && !grid.won)
		{
			return;
		}

		fail("checkDrawTest failed");
	}
	/** Test VerticalWin */
	@Test
	public void checkVerticalWinTest()
	{
		final Player player = new PlayerHuman(0);
		final Player opponent = new PlayerHuman(1);
		
		Grid grid = new Grid();
		
		grid.addDisc(player, 0);
		grid.addDisc(player, 0);
		grid.addDisc(player, 0);
		grid.addDisc(player, 0);
		
		if (!grid.checkWin(player.getId(), 2, 0))
		{
			fail("checkVerticalWinTest failed");
		}
		
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 3);
		grid.addDisc(player, 3);
		grid.addDisc(player, 3);
		grid.addDisc(player, 3);
		
		if (!grid.checkWin(player.getId(), 1, 3))
		{
			fail("checkVerticalWinTest failed");
		}

		grid.addDisc(opponent, 6);
		grid.addDisc(opponent, 6);
		grid.addDisc(player, 6);
		grid.addDisc(player, 6);
		grid.addDisc(player, 6);
		grid.addDisc(player, 6);
		
		if (!grid.checkWin(player.getId(), 0, 6))
		{
			fail("checkVerticalWinTest failed");
		}
	}
	
	/** Test HorizontalWin */
	@Test
	public void checkHorizontalWinTest()
	{
		final Player player = new PlayerHuman(0);
		
		Grid grid = new Grid();
		
		grid.addDisc(player, 0);
		grid.addDisc(player, 1);
		grid.addDisc(player, 2);
		grid.addDisc(player, 3);
		
		if (!grid.checkWin(player.getId(), 5, 0))
		{
			fail("checkHorizontalWinTest failed");
		}
		
		if (!grid.checkWin(player.getId(), 5, 1))
		{
			fail("checkHorizontalWinTest failed");
		}
		
		if (!grid.checkWin(player.getId(), 5, 2))
		{
			fail("checkHorizontalWinTest failed");
		}
		
		if (!grid.checkWin(player.getId(), 5, 3))
		{
			fail("checkHorizontalWinTest failed");
		}
	}
	
	/** Test DiagonalWin */
	@Test
	public void checkDiagonalWinTest()
	{
		final Player player = new PlayerHuman(0);
		final Player opponent = new PlayerHuman(1);
		
		Grid grid = new Grid();
		
		grid.addDisc(player, 0);
		grid.addDisc(opponent, 1);
		grid.addDisc(player, 2);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 5);
		grid.addDisc(player, 6);
		
		grid.addDisc(opponent, 0);
		grid.addDisc(player, 1);
		grid.addDisc(opponent, 2);
		grid.addDisc(player, 3);
		grid.addDisc(opponent, 4);
		grid.addDisc(player, 5);
		grid.addDisc(opponent, 6);
		
		grid.addDisc(player, 0);
		grid.addDisc(opponent, 1);
		grid.addDisc(player, 2);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 5);
		grid.addDisc(player, 6);
		
		grid.addDisc(opponent, 0);
		grid.addDisc(player, 1);
		grid.addDisc(opponent, 2);
		grid.addDisc(player, 3);
		grid.addDisc(opponent, 4);
		grid.addDisc(player, 5);
		grid.addDisc(opponent, 6);
		
		grid.addDisc(player, 0);
		grid.addDisc(opponent, 1);
		grid.addDisc(player, 2);
		grid.addDisc(opponent, 3);
		grid.addDisc(player, 4);
		grid.addDisc(opponent, 5);
		grid.addDisc(player, 6);
		
		grid.addDisc(opponent, 0);
		grid.addDisc(player, 1);
		grid.addDisc(opponent, 2);
		grid.addDisc(player, 3);
		grid.addDisc(opponent, 4);
		grid.addDisc(player, 5);
		grid.addDisc(opponent, 6);
		
		if (!grid.checkWin(opponent.getId(), 2, 0))
		{
			fail("checkDiagonalWinTest failed");
		}
		
		if (!grid.checkWin(opponent.getId(), 3, 1))
		{
			fail("checkDiagonalWinTest failed");
		}
		
		if (!grid.checkWin(player.getId(), 2, 3))
		{
			fail("checkDiagonalWinTest failed");
		}
	}
}

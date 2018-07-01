package unicam.cs.forza4;

import java.util.Scanner;

/**
 * Rapresent the Human player
 * 
 * @author Luca
 */
public class PlayerHuman extends Player
{	
	private static final long serialVersionUID = -1496290258391331642L;

	/**
	 * Create a human player
	 * 
	 * @param id ID player
	 */
	public PlayerHuman(int id)
	{
		super(id);
	}
	
	/** Handle input for the human player */
	@Override
	public int input(final Grid grid)
	{
		System.out.println("Your turn!");
		System.out.println("Insert column between 0-6: ");
		Scanner scanner = new Scanner(System.in);
		int value;
		do
		{
			while (!scanner.hasNextByte())
			{
				scanner.next();
				System.out.println("Usage: numbers [0-6]");
			}
		
			value = scanner.nextByte();
			if (value < 0 || value > 6)
				System.out.println("Enter the correct number in range");
		}
		while (value < 0 || value > 6);
		
//		FIXME: close scanner
//		scanner.close();
		
		return value;
	}
}

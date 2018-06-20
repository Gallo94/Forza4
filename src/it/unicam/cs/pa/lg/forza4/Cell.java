package it.unicam.cs.pa.lg.forza4;

public class Cell
{
	public static final byte INVALID_COORD = -1;
	Player player;
	byte x, y;
		
	public Cell()
	{
		this.player = null;
		this.x = INVALID_COORD;
		this.y = INVALID_COORD;
		
		System.out.println("x: " + this.x + ", y: " + this.y);
	}
	
	public void setDisc(Player player, byte x, byte y)
	{ 
		this.player = player;
	}
				
	public boolean isEmpty()
	{
		return this.player == null;
	}
}

package it.unicam.cs.pa.lg.forza4;

public class Cell
{
	public static final byte INVALID_COORD = -1;
	private HumanPlayer player;
	private byte col, row;
		
	public Cell()
	{
		this.player = null;
		this.col = INVALID_COORD;
		this.row = INVALID_COORD;
	}
	
	public void setPlayer(final HumanPlayer player)
	{ 
		this.player = player;
	}
	
	public HumanPlayer getPlayer()
	{
		return this.player;
	}
	
	public void setCol(final byte col)
	{
		this.col = col;
	}
	
	public byte getCol()
	{
		return this.col;
	}
	
	public void setRow(final byte row)
	{
		this.row = row;		
	}
	
	public byte getRow()
	{
		return this.row;
	}
				
	public boolean isEmpty()
	{
		return this.player == null;
	}
}

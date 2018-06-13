package it.unicam.cs.pa.lg.forza4;

public class Cell {
	
	private Disc disc;
	private int x;
	private int y;
	
	public Cell() { this.disc = null; }
	
	public void setDisc(Disc disc)
	{ 
		this.disc = disc;
	}
	
	public Disc getDisc()
	{
		return this.disc;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int  getX()
	{
		return this.x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getY()
	{
		return this.y;
	}
		
	public boolean isEmpty()
	{
		return this.disc == null;
	}
}

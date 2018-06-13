package it.unicam.cs.pa.lg.forza4;

public class Cell {
	
	private Disc disc;
	
	public Cell() { this.disc = null; }
	
	public void setDisc(Disc disc)
	{ 
		this.disc = disc;
	}
	
	public Disc getDisc()
	{
		return this.disc;
	}
			
	public boolean isEmpty()
	{
		return this.disc == null;
	}
}

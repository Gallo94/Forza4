package it.unicam.cs.pa.lg.forza4;

import java.io.Serializable;

/**
 * La classe Message istanzia un messaggio di 2byte
 * che viene scambiato tra il client/server.
 * Il tipo rappresentato dal primo ed i dati dal secondo.
 * 
 * @author Luca
 */
public class Message implements Serializable
{
	private static final long serialVersionUID = 7097116029864916962L;
	
	private byte type;
	private byte data;
	
	public Message(byte type, byte data)
	{
		this.type = type;
		this.data = data;
	}
	
	public byte getType()
	{
		return this.type;
	}
	
	public byte getData()
	{
		return this.data;
	}
}

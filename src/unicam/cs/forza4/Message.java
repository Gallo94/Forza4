package unicam.cs.forza4;

import java.io.Serializable;

/** Message is the basic unit for communicating between client and server */
public class Message implements Serializable
{
	/** ID Serializable */
	private static final long serialVersionUID = 7097116029864916962L;
	/** Message's type */
	private int type;
	/** Message's data */
	private int data;
	
	/**
	 * Constructor
	 * 
	 * @param type 
	 * @param data
	 */
	public Message(int type, int data)
	{
		this.type = type;
		this.data = data;
	}
	
	/**
	 * Get the message's type
	 * 
	 * @return message's type
	 */
	public int getType()
	{
		return this.type;
	}
	
	/**
	 * Get the message's data
	 * 
	 * @return message's data
	 */
	public int getData()
	{
		return this.data;
	}
}

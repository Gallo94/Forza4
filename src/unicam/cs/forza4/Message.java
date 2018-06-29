package unicam.cs.forza4;

import java.io.Serializable;

/**
 * Rapresent a message between client and server
 * 
 * @author Luca
 */
public class Message implements Serializable
{
	private static final long serialVersionUID = 7097116029864916962L;
	
	private int type;
	private int data;
	
	/**
	 * Create a message
	 * 
	 * @param type first paramter of message
	 * @param data second paramter of message
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

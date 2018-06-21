package it.unicam.cs.pa.lg.forza4;

public class MessageType
{
	// Message: server to client
	public static final byte PLAYER_ID = 0;	
	public static final byte PLAYER_TURN = 1;
	public static final byte GAME_OVER = 2;

	// Message: client to server
	public static final byte PLAYER_MOVE = 0;
}

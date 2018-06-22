package it.unicam.cs.pa.lg.forza4;

public class MessageType
{
	// Message: server to client
	public static final byte PLAYER_ID = 0;	
	public static final byte PLAYER_TURN = 1;	// data: id_player
	public static final byte GAME_OVER = 2;		// 0: player0 won; 1: player1 won, 2: draw
	public static final byte VALID_PLAY = 3;
	public static final byte BAD_PLAY = 4;

	// Message: client to server
	public static final byte PLAYER_MOVE = 0;
	public static final byte PLAYER_WAIT = 1;
}

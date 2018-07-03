package unicam.cs.forza4;

/**
 * Represents the messages from server to client and the other way
 * 
 * @author Luca
 */
public class MessageType
{
	// Message: server to client
	public static final byte PLAYER_ID = 0;	
	public static final byte PLAYER_TURN = 1;	// type: player_turn; data: id_player
	public static final byte GAME_OVER = 2;		// 0: player0 won; 1: player1 won, 2: draw
	public static final byte VALID_PLAY = 3;

	// Message: client to server
	public static final byte PLAYER_MOVE = 0;
	public static final byte PLAYER_WAIT = 1;
}

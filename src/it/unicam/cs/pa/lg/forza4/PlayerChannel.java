package it.unicam.cs.pa.lg.forza4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.Match.MatchStatus;

/**
 * A communication's channel between server and client 
 * 
 * @author Luca
 */
public class PlayerChannel
{	
	public final static int MAX_MESSAGE_LEN = 2; // byte
	private Socket socket;
	private Player player;
	private Match match;
	
	/**
	 * Create a player's channel
	 * 
	 * @param socket server's socket
	 * @param player match's player
	 * @param match game's manager
	 */
	public PlayerChannel(Socket socket, Player player, Match match)
	{
		this.socket = socket;
		this.player = player;
		this.match = match;
	}
	
	public void start()
	{
		try
		{
			writePlayerId();

			while (true)
			{
				writeResponse();
								
				if (match.getGrid().won)
				{
					if (player.id == this.match.getWinPlayer())
						System.out.println("Player" + player.id + " won!");
					else 
						System.out.println("Player" + player.id + " lose!");
					
					break;
				}
				
				processPlayerInput();
			}

//			this.socket.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Client reads message, send it from server 
	 * 
	 * @return message 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Message readMessage() throws IOException, ClassNotFoundException
	{
		Message message = null;

		try
		{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			message = (Message) in.readObject();
		}
		catch (IOException e)
		{
			System.out.println("PlayerChannel: Wrong read message");
			throw e;
		}

		return message;
	}
	
	/**
	 * Write message to client
	 * 
	 * @param type id player
	 * @param data game's status
	 * @throws IOException
	 */
	private void writeMessage(final int type, final int data) throws IOException
	{
		Message message = new Message(type, data);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(message);
	}
	
	/**
	 * Send grid from server to client 
	 * 
	 * @throws IOException
	 */
	private void writeGrid() throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(this.match.getGrid());
	}
	
	/**
	 * Processing commands on server
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void processPlayerInput() throws IOException, ClassNotFoundException
	{
		Message message = readMessage();
		
		switch (message.getType())
		{
		case MessageType.PLAYER_MOVE:
		{	
			boolean success = false;
			
			int col = message.getData();
			success = makeMove(col);		
			writeMessage(MessageType.VALID_PLAY, (success ? 1 : 0));
			writeGrid();
			
			match.checkVictory();
			match.checkDraw();
			
			if (success)
				match.switchTurn();
		
			break;
		}
		case MessageType.PLAYER_WAIT:
			// do nothing

			break;
		}
	}
	
	/**
	 * After processing, server send it to client
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void writeResponse() throws IOException, ClassNotFoundException
	{
		MatchStatus status = match.getStatus();
		
		switch (status)
		{
		case P0_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, 0);
				writeGrid();
				break;
			}
		case P1_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, 1);
				writeGrid();
				break;
			}
		case P0_WON:
			{
				writeMessage(MessageType.GAME_OVER, 0);
				writeGrid();
				break;
			}
		case P1_WON:
			{
				writeMessage(MessageType.GAME_OVER, 1);
				writeGrid();
				break;
			}
		case DRAW:
			{
				writeMessage(MessageType.GAME_OVER, 2);
				writeGrid();
				break;
			}
		case IDLE:
			// do nothing
			break;
		}
	}
	
	/**
	 * Server send ID Player to client  
	 * 
	 * @throws IOException
	 */
	private void writePlayerId() throws IOException
	{
		writeMessage(MessageType.PLAYER_ID, this.player.getId());
	}
	
	/**
	 * Player makes move on grid's column
	 * 
	 * @param col grid's columns
	 * @return true if make move is done
	 * 		   false otherwise
	 */
	private boolean makeMove(int col)
	{
		return player.placeDisc(this.match.getGrid(), col);
	}
	
}
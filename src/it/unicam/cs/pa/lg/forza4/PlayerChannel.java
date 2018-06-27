package it.unicam.cs.pa.lg.forza4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.Match.MatchStatus;

public class PlayerChannel
{	
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	private Socket socket;
	private Player player;
	private Match match;
	
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
					System.out.println("Player" + this.match.getWinPlayer() + " won!");
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
	
	// Lettura del messaggio dal client
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
	
	// Scrive messaggio del Server per il Client
	private void writeMessage(final byte type, final byte data) throws IOException
	{
		Message message = new Message(type, data);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(message);
	}
	
	private void writeGrid() throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(this.match.getGrid());
	}
	
	// Elaborazione comandi del Client sul Server
	private void processPlayerInput() throws IOException, ClassNotFoundException
	{
		Message message = readMessage();
		
		switch (message.getType())
		{
		case MessageType.PLAYER_MOVE:
		{	
			boolean success = false;
			
			do
			{
				byte col = message.getData();
				success = makeMove(col);
				writeMessage(MessageType.VALID_PLAY, (byte)(success ? 1 : 0));
				writeGrid();
				
			} while (!success);
			
			match.checkVictory();
			match.switchTurn();
		
			break;
		}
		case MessageType.PLAYER_WAIT:
			// do nothing

			break;
		}
	}
	
	// Invio dello stato al Client
	private void writeResponse() throws IOException, ClassNotFoundException
	{
		MatchStatus status = match.getStatus();
		
		switch (status)
		{
		case P0_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, (byte) 0);
				writeGrid();
				break;
			}
		case P1_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, (byte) 1);
				writeGrid();
				break;
			}
		case P0_WON:
			{
				writeMessage(MessageType.GAME_OVER, (byte) 0);
				writeGrid();
				break;
			}
		case P1_WON:
			{
				writeMessage(MessageType.GAME_OVER, (byte) 1);
				writeGrid();
				break;
			}
		case DRAW:
			{
				writeMessage(MessageType.GAME_OVER, (byte) 2);
				writeGrid();
				break;
			}
		case IDLE:
			// do nothing
			break;
		}
	}
	
	private void writePlayerId() throws IOException
	{
		writeMessage(MessageType.PLAYER_ID, this.player.getId());
	}
	
	private boolean makeMove(byte col)
	{
		return player.placeDisc(this.match.getGrid(), col);
	}
}
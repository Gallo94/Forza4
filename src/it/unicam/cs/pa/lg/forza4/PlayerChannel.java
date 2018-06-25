package it.unicam.cs.pa.lg.forza4;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.Match.MatchStatus;

public class PlayerChannel
{	
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	private Socket socket;
	private Player player;
	private volatile Grid grid;
	private Match match;
	
	public PlayerChannel(Socket socket, Player player, Grid grid, Match match)
	{
		this.socket = socket;
		this.player = player;
		this.grid = grid;
		this.match = match;
	}
	
	public void start()
	{
		try
		{
			sendPlayerId();

			while (true)
			{
				respondToPlayer();
				
				if (this.grid.won)
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
	
	// Elaborazione comandi del Client sul Server
	private void processPlayerInput() throws IOException, ClassNotFoundException
	{
		Message message = readMessage();
		
		switch (message.getType())
		{
		case MessageType.PLAYER_MOVE:
		{			
			byte col = message.getData();
			
			boolean success = makeMove(col);
			writeMessage(MessageType.VALID_PLAY, (byte)(success ? 1 : 0));
			
			if (success)
			{
				if (grid.won)
					match.checkVictory();
				else
					match.switchTurn();
			}
		
			Grid.printField(new PrintStream(new FileOutputStream(FileDescriptor.out)), grid);		

			break;
		}
		case MessageType.PLAYER_WAIT:
			// do nothing
			break;
		}
	}
	
	// Invio dello stato al Client
	private void respondToPlayer() throws IOException, ClassNotFoundException
	{
		MatchStatus status = match.getStatus();
		
		switch (status)
		{
		case P0_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, (byte) 0);
				break;
			}
		case P1_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, (byte) 1);
				break;
			}
		case P0_WON:
			{
				writeMessage(MessageType.GAME_OVER, (byte) 0);
				break;
			}
		case P1_WON:
			{
				writeMessage(MessageType.GAME_OVER, (byte) 1);
				break;
			}
		case DRAW:
			{
				writeMessage(MessageType.GAME_OVER, (byte) 2);
				break;
			}
		case IDLE:
			// do nothing
			break;
		}
	}
	
	private void sendPlayerId() throws IOException
	{
		writeMessage(MessageType.PLAYER_ID, this.player.getId());
	}
	
	private boolean makeMove(byte col)
	{
		return player.placeDisc(this.grid, col);
	}
}
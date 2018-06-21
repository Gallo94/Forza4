package it.unicam.cs.pa.lg.forza4;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Message;
public class PlayerChannel
{	
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	private Socket socket;
	private Grid grid;
	private Player player;
	
	private volatile static byte numPlayer = 0;

	public PlayerChannel(Socket socket, Grid grid) throws IOException
	{
		this.socket = socket;
		this.grid = grid;
		
		numPlayer++;
		this.player = new Player(grid, socket.getInetAddress(), numPlayer);
		sendPlayerId();	
			
		try
		{
			while (true)
			{
				processPlayerInput();
				Grid.printField(new PrintStream(new FileOutputStream(FileDescriptor.out)), grid);
				respondToPlayer();
				
				if (grid.won)
				{
					System.out.println("WON");
					break;
				}
			}

			this.socket.close();
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
			System.out.println("Player Move");
			makeMove(message.getData());
			
			break;
		}
		default: break;	
		}
	}
	
	// Invio dello stato al Client
	private void respondToPlayer() throws IOException
	{
		if (grid.won)
		{
			writeMessage(MessageType.GAME_OVER, (byte) 1);
			return;
		}
		
		writeMessage(MessageType.PLAYER_TURN, (byte) 0);
	}
	
	private void sendPlayerId() throws IOException
	{
		writeMessage(MessageType.PLAYER_ID, numPlayer);
	}
	
	private void makeMove(byte col)
	{
		player.placeDisc(col);
	}
}
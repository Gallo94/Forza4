package it.unicam.cs.pa.lg.forza4;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class PlayerChannel
{	
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	private Socket socket;
	private Grid grid;
	private Player player;
	

	public PlayerChannel(Socket socket, Grid grid)
	{
		this.socket = socket;
		this.grid = grid;
		
//		Grid.printField(new PrintStream(new FileOutputStream(FileDescriptor.out)), grid);

		this.player = new Player(grid, socket.getInetAddress());
			
		try
		{
			processPlayerInput();
			
			Grid.printField(new PrintStream(new FileOutputStream(FileDescriptor.out)), grid);

			this.socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	// Lettura del messaggio dal client
	private byte[] readMessage() throws IOException
	{
		InputStream in = socket.getInputStream();
		byte[] buf = new byte[MAX_MESSAGE_LEN];
		int bytes_read = in.read(buf);
		if (bytes_read != MAX_MESSAGE_LEN)
		{
			System.out.println("PlayerChannel: Wrong read message");
		}

		return buf;
	}
	// Scrive messaggio del Server per il Client
	private void writeMessage(final byte type, final byte data) throws IOException
	{
		OutputStream out = socket.getOutputStream();
		byte[] buf = new byte[] { type, data };
		out.write(buf);
	}
	// Elaborazione comandi del Client sul Server
	private void processPlayerInput() throws IOException
	{
		byte[] message = readMessage();
		
		switch (message[0])
		{
		case MessageType.PLAYER_MOVE:
		{
			System.out.println("Player Move");
			makeMove(message[1]);
			
			break;
		}
		default: break;	
		}
	}
	// Invio dello stato al Client
	private void respondToPlayer()
	{
		
	}
	
	private void makeMove(byte col)
	{
		player.placeDisc(col);
	}
}
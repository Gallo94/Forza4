package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.MessageType;

public class Connect4Client
{
	public static final int PORT = 9001;
	private Socket socket = null;
	private byte playerId;
	private boolean isActive = false;

	// Message attributes
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	
	public Connect4Client(final String server, final int port)
	{
		try {
			connectToServer(server, port);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		boolean gameOver = false;

		while (!gameOver)
		{
			// Ricezione dal server
			Message imsg = null;
			ObjectInputStream in;
			
			try
			{
				in = new ObjectInputStream(socket.getInputStream());
				imsg = (Message) in.readObject();
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
			byte id = imsg.getData();
			switch (imsg.getType())
			{
			case MessageType.PLAYER_TURN:
				{	
					if (id == playerId)
					{
						System.out.println("Your turn!");
						System.out.println("Insert column:");
						Scanner scanner = new Scanner(System.in);
						byte input = scanner.nextByte();
//						scanner.close();
						
						try
						{
							ObjectOutputStream out;
							out = new ObjectOutputStream(socket.getOutputStream());
							Message omsg = new Message(MessageType.PLAYER_MOVE, input);
							out.writeObject(omsg);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
//						System.out.println("Wait your turn!");
						try
						{
							ObjectOutputStream out;
							out = new ObjectOutputStream(socket.getOutputStream());
							Message omsg = new Message(MessageType.PLAYER_WAIT, (byte)0); // garbage data
							out.writeObject(omsg);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					
					break;
				}
			case MessageType.GAME_OVER:
				{				
					if (id == playerId)
						System.out.println("You Won!");
					else if (id == 2)
						System.out.println("Draw!");
					else
						System.out.println("You Lose!");
					
					gameOver = true;
					
					break;
				}
			}
		}
	}
	
	public void connectToServer(final String server, final int PORT) throws UnknownHostException, IOException, ClassNotFoundException
	{
		socket = new Socket(server, PORT);
		
		Message imsg = null;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		imsg = (Message) in.readObject();
		assert (imsg.getType() == MessageType.PLAYER_ID);
		
		this.playerId = imsg.getData();
		System.out.println("Player ID: " + this.playerId);
	}
}
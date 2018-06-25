package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.MessageType;
import it.unicam.cs.pa.lg.forza4.Player;
import it.unicam.cs.pa.lg.forza4.RandomPlayer;
import it.unicam.cs.pa.lg.forza4.HumanPlayer;

public class Connect4Client
{
	public static final int PORT = 9001;
	private Socket socket = null;
	private boolean isActive = false;
	private Player player;

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
	
	public void start() throws IOException
	{
		boolean gameOver = false;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		while (!gameOver)
		{
			// Ricezione dal server
			Message imsg = null;
			byte id = 0;
			try
			{
				in = new ObjectInputStream(socket.getInputStream());
				imsg = (Message) in.readObject();
				id = imsg.getData();
				switch (imsg.getType())
				{
				case MessageType.PLAYER_TURN:
					{	
						if (id == player.getId())
						{	
							try
							{
								boolean isValid = false;
								do
								{
									byte input = player.input();
									out = new ObjectOutputStream(socket.getOutputStream());
									Message omsg = new Message(MessageType.PLAYER_MOVE, input);
									out.writeObject(omsg);
									
									in = new ObjectInputStream(socket.getInputStream());
									Message returnMessage = (Message) in.readObject();
									if (returnMessage.getType() == MessageType.VALID_PLAY)
									{
										isValid = returnMessage.getData() == 1 ? true : false;
										if (!isValid)
											System.out.println("Bad play");
									}
								}
								while (!isValid);
								
								System.out.println("Wait your turn!");
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							try
							{
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
						if (id == 2)
							System.out.println("Draw!");
						else if (id == player.getId())
							System.out.println("You Won!");
						else
							System.out.println("You Lose!");
						
						gameOver = true;
						
						break;
					}
				}
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		in.close();
		out.close();
		socket.close();
	}
	
	public void connectToServer(final String server, final int PORT) throws UnknownHostException, IOException, ClassNotFoundException
	{
		socket = new Socket(server, PORT);
		
		Message imsg = null;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		imsg = (Message) in.readObject();
		assert (imsg.getType() == MessageType.PLAYER_ID);
		
		byte playerId = imsg.getData();
		this.player = new RandomPlayer(socket.getInetAddress(), playerId);
//		this.player = new HumanPlayer(socket.getInetAddress(), playerId);
		System.out.println("Player ID: " + this.player.getId());
	}
}
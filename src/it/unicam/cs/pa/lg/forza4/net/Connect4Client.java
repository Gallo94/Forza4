package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import it.unicam.cs.pa.lg.forza4.PlayerHuman;
import it.unicam.cs.pa.lg.forza4.Grid;
import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.MessageType;
import it.unicam.cs.pa.lg.forza4.Player;
import it.unicam.cs.pa.lg.forza4.PlayerRandom;
import it.unicam.cs.pa.lg.forza4.PrintUtils;

public class Connect4Client
{
	public static final int PORT = 9001;
	private Socket socket = null;
	private Player player;
	private Grid grid;

	// Message attributes
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	
	public Connect4Client(final String server, final int port)
	{
		try
		{
			connectToServer(server, port);
		}
		catch (ClassNotFoundException | IOException e)
		{
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
										else
										{
											readGrid();
											PrintUtils.printField(System.out, grid);
										}
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
		this.player = new PlayerRandom(playerId);
//		this.player = new HumanPlayer(playerId);
		System.out.println("Player ID: " + this.player.getId());
	}
	
	public void readGrid() throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		this.grid = (Grid) in.readObject();
	}
}
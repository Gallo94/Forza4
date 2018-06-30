package unicam.cs.forza4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import unicam.cs.forza4.Forza4ClientMain.ClientMode;

public class Forza4Client
{
	public static final int PORT = 9001;
	private Socket socket = null;
	private Player player;
	private Grid grid;
	private ClientMode mode;

	// Message attributes
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	
	public Forza4Client(final String server, final int port, final ClientMode mode)
	{
		this.mode = mode;
		this.grid = new Grid(); 
		
		try
		{
			connectToServer(server, port);
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void start() throws IOException, ClassNotFoundException
	{
		boolean gameOver = false;

		while (!gameOver)
		{
			// Ricezione dal server
			Message imsg = null;
			int id = 0;
						
			try
			{			
				imsg = readResponse();
				switch (imsg.getType())
				{
				case MessageType.PLAYER_TURN:
					{	
						this.grid = readGrid();
						id = imsg.getData();
						if (id == player.getId())
						{		
							try
							{
								boolean isValid = false;
								PrintUtils.printField(System.out, grid);
								int input = player.input(this.grid);
								
								writeMessage(MessageType.PLAYER_MOVE, input);								
								Message returnMessage = readMessage();
								
								this.grid = readGrid();
								PrintUtils.printField(System.out, grid);

								if (returnMessage.getType() == MessageType.VALID_PLAY)
								{
									isValid = returnMessage.getData() == 1 ? true : false;
									if (!isValid)
										System.out.println("Bad play");
								}
								
								System.out.println("Wait your turn!");
							}
							catch (IOException e)
							{
								e.printStackTrace();
								System.exit(-1);
							}
						}
						else
						{
							try
							{
								writeMessage(MessageType.PLAYER_WAIT, 0);
							}
							catch (IOException e)
							{
								e.printStackTrace();
								System.exit(-1);
							}
						}
						
						break;
					}
				case MessageType.GAME_OVER:
					{		
						id = imsg.getData();

						if (id == 2)
							System.out.println("Draw!");
						else if (id == player.getId())
							System.out.println("You Won!");
						else
							System.out.println("You Lose!");
						
						this.grid = readGrid();
						PrintUtils.printField(System.out, grid);

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
		
		socket.close();
	}
	
	public void connectToServer(final String server, final int PORT) throws UnknownHostException, IOException, ClassNotFoundException
	{
		socket = new Socket(server, PORT);
		
		int playerId = readPlayerId();
		this.player = (this.mode == ClientMode.HUMAN) ? new PlayerHuman(playerId) : new PlayerAI(playerId);
		System.out.println("Player ID: " + this.player.getId());
	}
		
	public int readPlayerId() throws ClassNotFoundException, IOException
	{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Message message = (Message) in.readObject();
		assert (message.getType() == MessageType.PLAYER_ID);
		return message.getData();
	}
	
	public Grid readGrid() throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		return (Grid) in.readObject();
	}
	
	public Message readResponse() throws IOException, ClassNotFoundException
	{
		ObjectInputStream in;
		Message message = null;
		try
		{
			in = new ObjectInputStream(socket.getInputStream());
			message = (Message) in.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		return message;
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
	private void writeMessage(final int type, final int data) throws IOException
	{
		Message message = new Message(type, data);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(message);
	}
	
}
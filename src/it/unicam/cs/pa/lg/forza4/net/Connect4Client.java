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

	// Message attributes
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	
	public Connect4Client(final String server, final int port) throws IOException, ClassNotFoundException
	{
		boolean gameOver = false;
		connectToServer(server, port);
		
		while (!gameOver)
		{
			// Inserimento dati
			System.out.println("Insert column:");
			Scanner scanner = new Scanner(System.in);
			byte input = scanner.nextByte();
			
			// Invio comando a server
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			Message omsg = new Message(MessageType.PLAYER_MOVE, input);
			out.writeObject(omsg);
//			out.close();
			
			// Ricezione risposta dal server
			Message imsg = null;
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			imsg = (Message) in.readObject();
//			in.close();
			
			switch (imsg.getType())
			{
			case MessageType.PLAYER_TURN:
			{
//					imsg.getData();
				System.out.println("Your turn!");
				break;
			}
			case MessageType.GAME_OVER:
			{
				System.out.println("Game Won!");
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
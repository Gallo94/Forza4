package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import it.unicam.cs.pa.lg.forza4.MessageType;

public class Connect4Client
{
	private static int PORT = 9001;
	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null;
	// Message attributes
	public final static int MAX_MESSAGE_LEN = 2; // Byte
	
	public Connect4Client(final String server, final int PORT) throws IOException
	{
		socket = new Socket(server, PORT);		
		out = socket.getOutputStream();
		in = socket.getInputStream();
		Scanner scanner = new Scanner(System.in);

		boolean gameOver = false;
		
		while (!gameOver)
		{
			// Inserimento dati
			System.out.println("Insert column:");
			byte input = scanner.nextByte();
			
			// Invio comando a server
			byte[] obuf = new byte[] {MessageType.PLAYER_MOVE, input};
			out.write(obuf);
			
			// Ricezione risposta dal server
			byte[] ibuf = new byte[2];
			int bytes_read = 0;
			while (bytes_read < MAX_MESSAGE_LEN)
				bytes_read = in.read(ibuf);
			
			switch (ibuf[0])
			{
				case MessageType.GAME_OVER:
				{
					System.out.println("Game Won!");
					gameOver = true;
					break;
				}
				case MessageType.PLAYER_ONE_TURN:
				{
					System.out.println("Your turn!");
					break;
				}
			}		
		}
	}
}
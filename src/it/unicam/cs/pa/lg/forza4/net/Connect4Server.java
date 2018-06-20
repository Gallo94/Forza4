package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Grid;
import it.unicam.cs.pa.lg.forza4.PlayerChannel;

public class Connect4Server
{
	public final static int PORT = 9001;
	public final static int MAX_NUM_PLAYER = 2;
	private int playerCounter = 0;
	private Grid grid;
	
	public void start()
	{
		this.grid = new Grid();
		
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Accept connections...");
			
			while (playerCounter != MAX_NUM_PLAYER)
			{
				Socket client = server.accept();
				playerCounter++;
				System.out.println("Player " + playerCounter + " connected");
				
				new Thread(new SessionThread(client, grid)).start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class SessionThread implements Runnable
	{
		private Socket client = null;
		private Grid grid;

		public SessionThread(Socket client, Grid grid)
		{
			this.client = client;
			this.grid = grid;
		}

		public void run()
		{
			System.out.println("Connected with " + client.getInetAddress().toString());
			new PlayerChannel(this.client, this.grid);
		}
	}
}
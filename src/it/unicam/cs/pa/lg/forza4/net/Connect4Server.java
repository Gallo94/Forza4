package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unicam.cs.pa.lg.forza4.PlayerChannel;

public class Connect4Server
{
	public final static int PORT = 9001;
	public final static int MAX_NUM_PLAYER = 2;
	private int playerCounter = 0;
	
	public void start()
	{
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Accept connections...");
			
			while (playerCounter != MAX_NUM_PLAYER)
			{
				Socket client = server.accept();
				playerCounter++;
				System.out.println("Player " + playerCounter + " connected");
				
				new Thread(new SessionThread(client)).start();
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

		public SessionThread(Socket client)
		{
			this.client = client;
		}

		public void run()
		{
			System.out.println("Connected with " + client.getInetAddress().toString());
			new PlayerChannel(this.client);
		}
	}
}
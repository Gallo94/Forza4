package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Match;
import it.unicam.cs.pa.lg.forza4.Player;
import it.unicam.cs.pa.lg.forza4.PlayerHuman;
import it.unicam.cs.pa.lg.forza4.PlayerChannel;

public class Connect4Server
{
	public final static int PORT = 9001;
	public final static int MAX_NUM_PLAYER = 2;
	
	private Socket[] clients;
	private Player[] players;
	private int numPlayers = 0;
	private Match match;
	
	public Connect4Server()
	{
		players = new PlayerHuman[MAX_NUM_PLAYER];
		clients = new Socket[MAX_NUM_PLAYER];
	}
	
	public void start()
	{
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			
			System.out.println("Accept connections...");

			while (numPlayers != MAX_NUM_PLAYER)
			{
				Socket client = server.accept();
				clients[numPlayers] = client;
				
				Player player = new PlayerHuman((byte) numPlayers);
				players[numPlayers] = player;
				
				numPlayers++;
			}
			
			match = new Match();
			
			for (int i = 0; i < MAX_NUM_PLAYER; i++)
			{
				new Thread(
					new SessionThread(
						clients[i],
						players[i],
						match
					)
				).start();
			}
			
			server.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class SessionThread implements Runnable
	{
		private Socket client;
		private Player player;
		private Match match;

		public SessionThread(Socket client, Player player, Match match)
		{
			this.client = client;
			this.player = player;
			this.match = match;
		}

		public void run()
		{
			System.out.println("Connected with " +  this.player.getName());
			
			new PlayerChannel(this.client, this.player, this.match).start();
		}
	}
}
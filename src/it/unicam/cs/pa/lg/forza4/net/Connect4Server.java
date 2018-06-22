package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Grid;
import it.unicam.cs.pa.lg.forza4.Match;
import it.unicam.cs.pa.lg.forza4.Player;
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
		players = new Player[MAX_NUM_PLAYER];
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
				
				Player player = new Player(client.getInetAddress(), (byte)numPlayers);
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
						match.getGrid(),
						match
					)
				).start();
			}
			
//			server.close();
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
		private Grid grid;
		private Match match;

		public SessionThread(Socket client, Player player, Grid grid, Match match)
		{
			this.client = client;
			this.player = player;
			this.grid = grid;
			this.match = match;
		}

		public void run()
		{
			System.out.println("Connected with Player" + this.player.getId());
			
			new PlayerChannel(this.client, this.player, this.grid, this.match).start();
		}
	}
}
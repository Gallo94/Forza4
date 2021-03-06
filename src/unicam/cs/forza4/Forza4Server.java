package unicam.cs.forza4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Forza4Server represent the game server. It accepts connection from clients,
 * communication mechanics and states synchronization.
 */
public class Forza4Server
{
	/** Door's number of server socket */
	public final static int PORT = 9001;
	/** Maximum number of players */
	public final static int MAX_NUM_PLAYER = 2;
	/** Client's Socket */
	private Socket[] clients;
	/** Players of the game */
	private Player[] players;
	/** Initial number of players */
	private int numPlayers = 0;
	/** Match */
	private Match match;
	
	/** Constructor */
	public Forza4Server()
	{
		players = new PlayerHuman[MAX_NUM_PLAYER];
		clients = new Socket[MAX_NUM_PLAYER];
	}
	
	/** Start the server and wait 2 player for running the match */
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
				
				Player player = new PlayerHuman(numPlayers);
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
			//e.printStackTrace();
		}
	}
	
	/** Represent the thread game's session for a single player */
	private class SessionThread implements Runnable
	{
		private Socket client;
		private Player player;
		private Match match;
		
		/**
		 * Constructor
		 * 
		 * @param client client
		 * @param player player0, player1
		 * @param match Forza4's match
		 */
		public SessionThread(Socket client, Player player, Match match)
		{
			this.client = client;
			this.player = player;
			this.match = match;
		}
		
		@Override
		public void run()
		{
			System.out.println("Connected with Player " + this.player.getId());
			
			new PlayerChannel(this.client, this.player, this.match).start();
		}
	}
}
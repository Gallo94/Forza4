package unicam.cs.forza4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Rapresent a Forza4Server
 * 
 * @author gall9
 */
public class Forza4Server
{
	public final static int PORT = 9001;
	public final static int MAX_NUM_PLAYER = 2;
	
	private Socket[] clients;
	private Player[] players;
	private int numPlayers = 0;
	private Match match;
	
	/** Accept 2 Players and 2 Player's sockets */
	public Forza4Server()
	{
		players = new PlayerHuman[MAX_NUM_PLAYER];
		clients = new Socket[MAX_NUM_PLAYER];
	}
	
	/** Start the server and wait 2 player to run the match */
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
			e.printStackTrace();
		}
	}
	
	/** Rapresent the thread game's session for players  */
	private class SessionThread implements Runnable
	{
		private Socket client;
		private Player player;
		private Match match;
		
		/**
		 * Create a thread per players in a match
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
		
		/** After 2 players have connected, run the match */
		public void run()
		{
			System.out.println("Connected with " +  this.player.getName());
			
			new PlayerChannel(this.client, this.player, this.match).start();
		}
	}
}
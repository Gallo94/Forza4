package unicam.cs.forza4;

/**
 * Declare the Forza4Server
 * 
 * @author gall9
 */
public class Forza4ServerMain
{
	/**
	 * Main method to start server
	 * 
	 * @param argc
	 */
	public static void main(String[] argc)
	{
		Forza4Server server = new Forza4Server();
		server.start();
	}
}

package unicam.cs.forza4;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

/**
 * Client application bootstrap
 * 
 * @author gall9
 */
public class Forza4ClientMain 
{
	private static InetAddress server;
	public enum ClientMode { HUMAN,	AI }
	private static ClientMode clientMode;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		server = InetAddress.getByName("localhost");
		clientMode = ClientMode.HUMAN;
		
		parseCommandLine(args);
		
		Forza4Client client = new Forza4Client(server.getHostAddress(), 9001, clientMode);
		client.start();
	}
	
	/**
	 * Handle the parameters on command line such as start local or networked match, or play as AI.
	 * 
	 * @param args command line
	 */
	public static void parseCommandLine(String[] args)
	{
        Options options = new Options();

        Option mode = new Option("mode", true, "human or ai");
        mode.setRequired(false);
        options.addOption(mode);
        
        Option address = new Option("server", true, "server address");
        address.setRequired(false);
        options.addOption(address);
        
        Option help = new Option("help", "help");
        help.setRequired(false);
        options.addOption(help);
        
        CommandLineParser parser = new DefaultParser();
        try
        {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.getOptions().length == 0)
				return;

			if (cmd.hasOption("help"))
			{
				System.out.println("Usage: Connect4Client [-server <server_ip>][-mode <mode>][-help]");
				System.out.println();
				System.out.println("Default server_ip is localhost (127.0.0.1)");
				System.out.println();
				System.out.println("Available modes:");
				System.out.println("- human");
				System.out.println("- ai");
				System.out.println();
				System.out.println("Connect4Client -help show this");
				System.exit(0);
			}
			
			if(cmd.hasOption("server"))
				server = InetAddress.getByName(cmd.getOptionValue("server"));
			
			if (cmd.hasOption("mode"))
			{
				String value = cmd.getOptionValue("mode");
				if (value.equals("human"))
					clientMode = ClientMode.HUMAN;
				else if (value.equals("ai"))
					clientMode = ClientMode.AI;
				else
				{
					System.err.println("Usage: Connect4Client [-server <server_ip>][-mode <mode>][-help]");
					System.exit(-1);
				}
			}
			else
			{
				clientMode = ClientMode.HUMAN;		
			}
		}
        catch (ParseException e)
        {
			System.err.println("Wrong parameter");
			System.err.println("Usage: Connect4Client [-server <server_ip>][-mode <mode>][-help]");
			System.exit(-1);
		}
        catch (UnknownHostException e)
        {
			System.err.println("Unknown host");
			System.exit(-1);
        }
	}
}

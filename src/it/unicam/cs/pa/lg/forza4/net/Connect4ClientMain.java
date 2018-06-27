package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class Connect4ClientMain 
{
	static InetAddress server;
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		server = InetAddress.getByName("localhost");
		
		parseCommandLine(args);
		
		Connect4Client client = new Connect4Client(server.getHostAddress(), 9001);
		client.start();
	}
	
	public static void parseCommandLine(String[] args)
	{
        Options options = new Options();

        Option address = new Option("server", true, "server address");
        address.setRequired(false);
        options.addOption(address);
        
        CommandLineParser parser = new DefaultParser();
        try
        {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.getOptions().length == 0)
				return;

			if(cmd.hasOption("server"))
				server = InetAddress.getByName(cmd.getOptionValue("server"));
				
		}
        catch (ParseException e)
        {
			System.err.println("Wrong parameter");
			System.err.println("Usage: Connect4Client [-server server_ip]");
			System.exit(-1);
		}
        catch (UnknownHostException e)
        {
			System.err.println("Unknown host");
			System.exit(-1);
        }
	}
}

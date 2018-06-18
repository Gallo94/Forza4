package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect4Server
{
	public final static int PORT = 9001;
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	public void start()
	{
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Accept connections...");
			server.accept();
			System.out.println("OK!");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
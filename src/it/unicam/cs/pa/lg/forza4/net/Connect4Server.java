package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect4Server
{
	public static final int PORT = 9000;
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	public void start()
	{
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Listening on port number " + server.getLocalPort());
			System.out.println("Accept connections...");
			Socket client = server.accept(); // Blocking
			System.out.println("OK!");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

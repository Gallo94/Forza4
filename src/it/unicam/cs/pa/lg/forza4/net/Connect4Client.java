package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Connect4Client
{
	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null;
	
	public Connect4Client(final String server)
	{
		SocketAddress addr = new InetSocketAddress(server, 9000);
		socket = new Socket();
		try
		{
			socket.connect(addr);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

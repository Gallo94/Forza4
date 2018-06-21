package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import it.unicam.cs.pa.lg.forza4.MessageType;

public class Connect4Client
{
	private static int PORT = 9001;
	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null;
	
	public Connect4Client(final String server, final int PORT) throws IOException
	{
		socket = new Socket(server, PORT);		
		out = socket.getOutputStream();


		for (int i = 3; i < 7; i++)
		{
			byte[] buf = new byte[] {MessageType.PLAYER_MOVE, (byte)i};
			out.write(buf);
		}
	}
}
package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *  Crea una connessione con il server. Riceve e invia messaggi
 * al server. Le eccezioni non vengono gestite a questo livello
 * 
 */

public class Connect4Client
{
	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null;
	private static final int NUM_BYTES = 2;
	
	/**
	 * Costruttore per Connect4Client.
	 * Crea una nuova socket e si collega al server
	 * 
	 * @throws UnknownHostException
	 * 			se il server non è stato trovate
	 * @throws IOException
	 * 			se 
	 */
	public Connect4Client(final String server) throws UnknownHostException,
			IOException {
		SocketAddress addr = new InetSocketAddress(server, 9000);
		socket = new Socket();
		
		socket.setSoTimeout(10000);
		socket.connect(addr);
		in = socket.getInputStream();
		out = socket.getOutputStream();
		
	}
	
	/**
	 * Invia un messaggio al server
	 * 
	 * @param msg
	 * @throws IOException
	 * 
	 */
	public void sendPacket(byte[] msg) throws IOException
	{
		out.write(msg);
	}
	
	/**
	 * Riceve un messaggio dal server
	 * @return
	 * @throws IOException
	 */
	public byte[] receivePacket() throws IOException {
		byte[] receiveMsg = null;
		receiveMsg = new byte[NUM_BYTES];
		int receivedBytes = 0;
		try
		{
			while (receivedBytes < NUM_BYTES)
				receivedBytes = in.read(receiveMsg);
		} catch (SocketTimeoutException ste)
			{
			if (socket != null)
				socket.close();
			throw ste;
		}
		return receiveMsg;
	}
	
	/**
	 * Chiude il socket
	 * 
	 * @throws IOException
	 */
	public void closeConnection() throws IOException
	{
		socket.close();
	}
}
package it.unicam.cs.pa.lg.forza4.net;

import java.io.IOException;
import java.net.UnknownHostException;

public class Connect4ClientMain 
{
	public static void main(String[] argc) throws UnknownHostException, IOException, ClassNotFoundException
	{
		Connect4Client client = new Connect4Client("127.0.0.1", 9001);
		client.start();
	}
}

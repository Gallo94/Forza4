package it.unicam.cs.pa.lg.forza4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.unicam.cs.pa.lg.forza4.Message;
import it.unicam.cs.pa.lg.forza4.Match.MatchStatus;

/**
 * Instanziamo un canale di comunicazione tra
 * client e server. Ogni client avra' il suo PlayerChannel
 * cioe' un proprio thread.
 * 
 * @author Luca
 */

public class PlayerChannel
{	
	public final static int MAX_MESSAGE_LEN = 2; // byte
	private Socket socket;
	private Player player;
	private Match match;
	
	public PlayerChannel(Socket socket, Player player, Match match)
	{
		this.socket = socket;
		this.player = player;
		this.match = match;
	}
	
	public void start()
	{
		try
		{
			writePlayerId();

			while (true)
			{
				writeResponse();
								
				if (match.getGrid().won)
				{
					if (player.id == this.match.getWinPlayer())
						System.out.println("Player" + player.id + " won!");
					else
						System.out.println("Player" + player.id + " lose!");						
					break;
				}
				
				processPlayerInput();
			}

//			this.socket.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Il Client legge il messaggio inviato dal Server
	 * 
	 * @return message
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Message readMessage() throws IOException, ClassNotFoundException
	{
		Message message = null;

		try
		{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			message = (Message) in.readObject();
		}
		catch (IOException e)
		{
			System.out.println("PlayerChannel: Wrong read message");
			throw e;
		}

		return message;
	}
	
	/**
	 * Il messaggio per il Client viene serializzato
	 * 
	 * @param type tipo ID del Player
	 * @param data dato Turni, Mosse, Game Over
	 * @throws IOException
	 */
	private void writeMessage(final int type, final int data) throws IOException
	{
		Message message = new Message(type, data);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(message);
	}
	
	/**
	 * Viene inviata la griglia dal Server al Client 
	 * 
	 * @throws IOException
	 */
	private void writeGrid() throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(this.match.getGrid());
	}
	
	/**
	 * Vengono elaborati i comandi inviati dal Client sul Server
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void processPlayerInput() throws IOException, ClassNotFoundException
	{
		Message message = readMessage();
		
		switch (message.getType())
		{
		case MessageType.PLAYER_MOVE:
		{	
			boolean success = false;
			
			int col = message.getData();
			success = makeMove(col);		
			writeMessage(MessageType.VALID_PLAY, (success ? 1 : 0));
			writeGrid();
			
			match.checkVictory();
			
			if (success)
				match.switchTurn();
		
			break;
		}
		case MessageType.PLAYER_WAIT:
			// do nothing

			break;
		}
	}
	
	/**
	 * Dopo l'elaborazione del comando, il Server
	 * invia lo stato e la griglia ai Client
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void writeResponse() throws IOException, ClassNotFoundException
	{
		MatchStatus status = match.getStatus();
		
		switch (status)
		{
		case P0_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, 0);
				writeGrid();
				break;
			}
		case P1_TURN:
			{
				writeMessage(MessageType.PLAYER_TURN, 1);
				writeGrid();
				break;
			}
		case P0_WON:
			{
				writeMessage(MessageType.GAME_OVER, 0);
				writeGrid();
				break;
			}
		case P1_WON:
			{
				writeMessage(MessageType.GAME_OVER, 1);
				writeGrid();
				break;
			}
		case DRAW:
			{
				writeMessage(MessageType.GAME_OVER, 2);
				writeGrid();
				break;
			}
		case IDLE:
			// do nothing
			break;
		}
	}
	
	/**
	 * Viene restituito ID del Player che verra' 
	 * 
	 * @throws IOException
	 */
	private void writePlayerId() throws IOException
	{
		writeMessage(MessageType.PLAYER_ID, this.player.getId());
	}
	
	/**
	 * Il Player compie una mossa indicando la colonna
	 * 
	 * @param col colonna della griglia
	 * @return
	 */
	private boolean makeMove(int col)
	{
		return player.placeDisc(this.match.getGrid(), col);
	}
}
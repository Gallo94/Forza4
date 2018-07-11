package unicam.cs.forza4;

import unicam.cs.forza4.Forza4ClientMain.ClientMode;

/** IPlayerFactory defines the rules to create the players
 * 
 * 	[e.g.]
 * 	IPlayerFactory factory = (mode, id) -> (mode == ClientMode.AI ? new PlayerAI(id) : new PlayerHuman(id));
 * 	Lambda Expression, in this case, provides the instructions(gametype, id) to create a player.  
 * */
@FunctionalInterface
public interface IPlayerFactory 
{
	public Player createPlayer(ClientMode type, int id);
}

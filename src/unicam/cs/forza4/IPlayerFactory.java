package unicam.cs.forza4;

import unicam.cs.forza4.Forza4ClientMain.ClientMode;

@FunctionalInterface
public interface IPlayerFactory 
{
	public Player getPlayer(ClientMode type, int id);
}

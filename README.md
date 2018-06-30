# Forza4

## Classi

* Forza4Server: Inizializza la partita e un thread di sessione per giocatore
* Forza4Client: Ha i riferimenti a Player, Grid ed alla socket del Server. Legge/scrive i messaggi dal/per il server
* Match: Game Manager
* Message: Definisce i messaggi tra client e server
* PlayerChannel: Crea canali di comunicazione tra client e server
* Player: Crea i giocatori per il gioco, con una ID ed il riferimento al disco
	- PlayerHuman: Rappresenta un giocatore locale
	- PlayerAI: Rappresenta un giocatore AI (PC)
* Grid: Array 2D di righe e colonne
* PrintUtils: Stampa la griglia del gioco

## MANUALE D'USO:

Per eseguire il progetto in Eclipse dovrete installare [ANSI Escape in Console] (https://marketplace.eclipse.org/content/ansi-escape-console).
Invece da terminale dovrete assicurarvi che i caratteri escape ANSI siano attivi.
# Forza4

## Classi

* **Forza4Server**: Inizializza la partita ed un thread di sessione per giocatore
* **Forza4Client**: Ha i riferimenti a Player, Grid ed alla socket del Server. Legge/scrive i messaggi da/per il server
* **Match**: Game Manager
* **Message**: Definisce i messaggi tra client e server
* **PlayerChannel**: Crea canali di comunicazione tra client e server
* **Player**: Definisce i giocatori per il gioco con una ID ed il riferimento al disco
	- **PlayerHuman**: Rappresenta un giocatore locale
	- **PlayerAI**: Rappresenta un giocatore AI (PC)
* **Grid**: Array 2D di righe e colonne
* **PrintUtils**: Stampa la griglia del gioco

## Manuale d'uso:

Per eseguire il progetto in Eclipse dovrete installare un plugin [ANSI Escape in Console](https://marketplace.eclipse.org/content/ansi-escape-console).
Oppure da terminale, dovrete assicurarvi che i caratteri escape ANSI siano abilitati per una corretta visualizzazione dei dischetti colorati.
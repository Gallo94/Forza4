# Forza4

## Classi

* **Forza4Server**: Inizializza la partita ed un thread di sessione per giocatore
* **Forza4Client**: Inizializza il client e la connessione alla porta del socket del server. Legge/scrive i messaggi da/per il server
* **Match**: Game Manager
* **Message**: Definisce i messaggi tra client e server
* **PlayerChannel**: Crea canali di comunicazione tra client e server
* **IPlayerFactory**: Definisce i ruoli per la creazione dei giocatori
* **Player**: Definisce i giocatori per il gioco con una ID ed il riferimento al disco
	- **PlayerHuman**: Rappresenta un giocatore locale
	- **PlayerAI**: Rappresenta un giocatore AI (PC)
* **Grid**: Array 2D di righe e colonne
* **PrintUtils**: Stampa la griglia del gioco

## Manuale d'uso:

Per eseguire il progetto in Eclipse dovrete installare un plugin [ANSI Escape in Console](https://marketplace.eclipse.org/content/ansi-escape-console).
Oppure da terminale, assicuratevi che i caratteri escape ANSI siano abilitati per una corretta visualizzazione dei dischetti colorati.
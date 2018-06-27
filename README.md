# Forza4

## Classi

* Match: main loop
* Grid: array 2D di Cell, game manager
* Cell: unit minima della grid, x, y, CellStatus { EMPTY, FULL }, controlla stati
* Disk: ha riferemento al player
* Player: Input

MANUALE D'USO:
È consigliato eseguire il progetto da terminale poiché è stata implementata una libreria di terze parti
che si occupa di gestire i colori dei dischi a video. Altrimenti su Eclipse è richiesto installare un plug-in
"ANSI Escape in console"  per la corretta visualizzazione della griglia.
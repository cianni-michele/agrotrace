## Nome

Caricare Prodotto

---

## ID

UC‑02

---

## Descrizione

Il caso d’uso descrive il processo con cui un Produttore inserisce una nuova scheda prodotto nella piattaforma AgroTrace,  
includendo dati di tracciabilità, prezzo, quantità, immagini e certificazioni.  
Alla fine dell’operazione la scheda viene salvata con stato **«In attesa di approvazione»** e il Curatore viene notificato per la revisione.

---

## Attori primari

- **Produttore** – inserisce i dati del prodotto e avvia il caricamento.

---

## Attori secondari

- **Curatore** – valida e approva la scheda prima della pubblicazione.
- **Gestore della Piattaforma** – monitora errori e autorizzazioni.
- **Sistema Storage Immagini** – salva i file multimediali.
- **Database Piattaforma** – persiste i metadati del prodotto.

---

## Pre‑condizioni

1. Il Produttore è autenticato ed ha ruolo attivo.
2. Il Produttore ha permessi per aggiungere prodotti.
3. La connessione alla piattaforma e ai servizi di storage è operativa.

---

## Flusso principale (Scenario di successo)

1. **Avvio.** Il Produttore seleziona *«Aggiungi nuovo prodotto»* dalla dashboard.
2. Il Sistema visualizza il form di inserimento prodotto.
3. Il Produttore compila i campi obbligatori:
    1. Nome commerciale.
    2. Categoria merceologica.
    3. Descrizione dettagliata e metodi di coltivazione.
    4. Certificazioni (selezione o upload documento).
    5. Quantità disponibile e unità di misura.
    6. Prezzo unitario.
    7. Coordinate geografiche del luogo di produzione (autocomplete OSM facoltativo).
4. Il Produttore allega una o più immagini del prodotto (drag & drop o selezione file).
5. Il Produttore conferma l’inserimento.
6. Il Sistema valida i dati:
    1. Presenza campi obbligatori.
    2. Formato numerico corretto per quantità e prezzo.
    3. Formato e dimensione corretta delle immagini.
7. Il Sistema salva le immagini nello storage e registra gli URI.
8. Il Sistema assegna un identificativo univoco al prodotto e crea la scheda con stato **«In attesa di approvazione»**.
9. Il Sistema registra un log di auditing.
10. Il Sistema notifica il Curatore della nuova scheda e mostra conferma al Produttore.

---

## Post‑condizioni

- La scheda prodotto esiste nel database con uno stato coerente (*In attesa di approvazione* o *Approvato* se auto‑approvato).
- Le immagini sono archiviate e collegate alla scheda.
- Il Curatore può procedere allo step successivo di pubblicazione.

---

## Flussi alternativi / Estensioni

- **1a – Annullamento da parte del Produttore**
    1. Il Produttore seleziona *«Annulla»*.
    2. Il Sistema rimuove i dati temporanei e ritorna alla dashboard.
       → Il caso d’uso termina.
- **3a – Dati obbligatori mancanti o non validi**
    1. Il Sistema evidenzia i campi errati/mancanti e visualizza un messaggio d’errore.
    2. Il Produttore corregge le informazioni e riprende dal passo 3.
- **4a – Upload immagine fallito**
    1. Il Sistema segnala il problema (timeout, formato non supportato).
    2. Il Produttore può ripetere il caricamento o procedere senza immagini.
- **6a – Prodotto duplicato rilevato**
    1. Il Sistema rileva che il Produttore ha già un prodotto con lo stesso nome.
    2. Il Sistema chiede conferma per procedere; in assenza di conferma il caso d’uso termina.
- **8a – Errore di persistenza database**
    1. Il Sistema mostra un messaggio di errore generico.
    2. Il Sistema registra l’evento e avvisa il Gestore della Piattaforma.
       → Il caso d’uso termina con fallimento.
- **8b – Storage immagini non disponibile**
    1. Il Sistema salva la scheda senza immagini e marca *«Immagini mancanti»*.
    2. Il Produttore viene invitato a riprovare il caricamento successivamente.

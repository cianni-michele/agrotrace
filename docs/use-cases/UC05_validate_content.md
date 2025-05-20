### Nome

Validare Contenuto

---

### ID

UC-05

---

### Descrizione

Il Curatore esamina e verifica i contenuti caricati dagli attori commerciali (schede prodotto, processi di trasformazione, pacchetti tipicità),  
approvandoli o respingendoli in base alle policy di qualità della piattaforma prima che siano pubblicati.

---

### Attori primari

- Curatore

---

### Attori secondari

- Produttore (autore del contenuto)
- Trasformatore (autore del contenuto)
- Distributore di Tipicità (autore del contenuto)

---

### Pre‑condizioni

- Il Curatore è autenticato con ruolo “Curatore”.
- Esiste almeno un contenuto in stato **In revisione**.

---

### Flusso principale (Scenario di successo)

1. Il Curatore apre il pannello **Contenuti da approvare**.
2. Il Sistema elenca i contenuti pendenti con metadati (tipo, autore, data caricamento).
3. Il Curatore seleziona un contenuto da esaminare.
4. Il Sistema visualizza i dettagli del contenuto, inclusi allegati e link alle certificazioni dichiarate.
5. Il Curatore valuta accuratezza, completezza e coerenza con le policy.
6. Il Curatore decide di **approvare** il contenuto.
7. Il Sistema:  
   7.1 Imposta lo stato del contenuto a **Pubblicato**;  
   7.2 Notifica l’autore del contenuto dell’avvenuta pubblicazione;  
   7.3 Rende immediatamente visibile il contenuto agli utenti finali.  
8. Il Curatore ripete i passi 3–7 finché non vi sono più contenuti da validare.

---

### Post‑condizioni

- Il contenuto validato è nello stato **Pubblicato** ed accessibile secondo le regole di visibilità.
- Gli autori hanno ricevuto notifica dell’esito.
- Il conteggio complessivo dei contenuti pendenti è aggiornato.

---

### Flussi alternativi (Eccezioni)

**3a. Contenuto già validato**

1. Il Sistema rileva che un altro Curatore ha già modificato lo stato del contenuto selezionato.
2. Il Sistema mostra lo stato aggiornato e invita l’utente a scegliere un altro elemento.
3. Il flusso riprende dal passo 2.

**5a. Richiesta di revisione minore**

1. Il Curatore rileva errori/omissioni correggibili.
2. Seleziona **Richiedi correzioni** e inserisce commenti.
3. Il Sistema imposta lo stato a **Da correggere**, logga l’esito e notifica l’autore.
4. Il flusso termina; il contenuto resterà invisibile finché non verrà ricaricato.

**5b. Rifiuto contenuto**

1. Il Curatore riscontra violazioni gravi delle policy.
2. Seleziona **Rifiuta** e specifica motivazioni.
3. Il Sistema imposta lo stato a **Rifiutato**, logga l’esito e notifica l’autore.
4. Il flusso termina.

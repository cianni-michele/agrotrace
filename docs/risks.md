- **Modello dati di tracciabilità complessa**  
  Definire un ER in grado di rappresentare l’intera catena  
  “produttore → trasformatore → distributore → evento/marketplace” è intrinsecamente complesso.  
  Errori di modellazione o relazioni improprie possono bloccare la successiva generazione delle entità JPA e i primi prototipi.

---

- **Gestione ruoli e autorizzazioni granulari**  
  Otto ruoli primari più sistemi esterni implicano una matrice di permessi articolata.  
  L’errata definizione iniziale di RBAC o dei claim OAuth2 può richiedere refactoring profondo in Spring Security.

---

- **Integrazione OSM e geodati**  
  Collegare dinamicamente coordinate, livelli mappa e marker interattivi richiede librerie geospaziali,  
  trasformazioni SRID in PostgreSQL e controlli di performance.  
  Una scelta tecnologica inadeguata può rallentare la prototipazione.

---

- **API Social in continuo cambiamento**  
  Le API dei social target variano in versioni e policy.  
  Progettare moduli di pubblicazione senza astrazione adeguata rischia di rompere la build già nei primi sprint di sviluppo.

---

- **Scalabilità dati e performance PostgreSQL**  
  Caricamento di contenuti (foto, certificazioni, log transazionali) \+ query geospaziali può generare tabelle voluminose.  
  Senza early-benchmark su indici, partizionamento o caching, il DB può diventare il collo di bottiglia.

---

- **Gestione media e storage esterno**  
  Foto prodotto, documenti di certificazione e contenuti evento vanno archiviati in modo efficiente.  
  Scegliere tardi tra filesystem, object storage o BLOB DB può rallentare l’avvio degli sprint di sviluppo backend.

---

- **Dipendenze open-source e licenze**  
  Librerie OSM, client social, e plugin Spring possono avere licenze diverse.  
  Valutazioni superficiali possono obbligare a sostituzioni di libreria in corso d’opera.
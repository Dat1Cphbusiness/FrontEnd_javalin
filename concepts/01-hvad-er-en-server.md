# 01 · Hvad er en server?

## Frontend vs. backend

Du kender allerede **frontend**: HTML, CSS og JavaScript der kører i browserens vindue – på brugerens computer.

En **server** er et program der kører et andet sted – typisk på en anden maskine – og venter på at browseren spørger om noget. Serveren svarer, og så er dens arbejde gjort.

```
[ Browser / frontend ]  ──────────────────────────────►  [ Server / backend ]
       Din computer             HTTP-request                  En anden maskine
                                 "Giv mig rides"

[ Browser / frontend ]  ◄──────────────────────────────  [ Server / backend ]
                                 HTTP-response
                                 [ {...}, {...} ]
```

Det kaldes **request/response-modellen**: browseren spørger altid først, serveren svarer aldrig af sig selv.

---

## Port og URL

En server lytter på en bestemt **port** – tænk på det som en dør ind til programmet.

```
http://localhost:7070/rides
         │        │    │
         │        │    └── sti (hvilken ressource?)
         │        └──────── port (hvilken dør?)
         └───────────────── adresse (hvilken maskine?)
```

`localhost` betyder *din egen maskine* – brugt mens du udvikler.

---

## HTTP-metoder

Ikke alle requests er ens. De vigtigste to:

| Metode | Bruges til          | Eksempel                    |
|--------|---------------------|-----------------------------|
| GET    | Hente data          | Vis alle rides              |
| POST   | Sende ny data       | Tilmeld en ny ride          |

Browseren bruger GET når du skriver en URL. JavaScript bruger `fetch()` til begge.

---

## Hvorfor ikke bare bruge filer?

Du *kunne* gemme data i en lokal fil og åbne den i browseren – men så kan kun du se den. En server gør data tilgængeligt for **alle** der kan nå adressen, og den kan gemme og opdatere data permanent.

```
❌  Browser A          ❌  Browser B
    læser fil              ser ikke As data

✅  Browser A  ──►  Server  ◄──  ✅  Browser B
                      │
                   gemmer data
                   for begge
```

---

> **Javalin** er et Java-bibliotek der gør det nemt at starte en server og definere hvad den skal svare på forskellige requests.

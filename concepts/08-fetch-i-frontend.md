# 08 · Fetch i frontend

## Hvad er fetch?

`fetch()` er en JavaScript-funktion der lader en HTML-side kontakte en server uden at navigere væk fra siden. Den bruges til både at hente og sende data.

---

## GET – hent data fra serveren

```javascript
fetch("http://localhost:7070/rides")
    .then(res => res.json())
    .then(rides => {
        console.log(rides); // et array af ride-objekter
    });
```

`.then()` bruges fordi fetch er **asynkron** – svaret kommer ikke med det samme, så koden venter uden at fryse siden.

---

## Byg DOM-elementer fra JSON

Når vi har arrayet kan vi loope over det og bygge HTML dynamisk:

```html
<!-- I din HTML -->
<ul id="ridelist"></ul>
```

```javascript
const ridelist = document.querySelector("#ridelist");

fetch("http://localhost:7070/rides")
    .then(res => res.json())
    .then(rides => {
        rides.forEach(ride => {
            ridelist.innerHTML += `<li>${ride.date}: ${ride.from} → ${ride.to}</li>`;
        });
    });
```

---

## POST – send data til serveren

```javascript
fetch("http://localhost:7070/rides", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ date: "2025-03-15", role: "driver", from: "ronne", to: "nexo" })
}).then(() => {
    console.log("Ride gemt!");
});
```

---

## POST før GET

Hvis du poster og derefter henter listen på samme side, skal GET ligge inde i POST's `.then()` – ellers henter du listen inden POST er færdig:

```javascript
fetch("http://localhost:7070/rides", { method: "POST", ... })
    .then(() => {
        // Nu er POST færdig – hent den opdaterede liste
        fetch("http://localhost:7070/rides")
            .then(res => res.json())
            .then(rides => { /* byg DOM */ });
    });
```

---

## Test at det virker

1. Åbn din HTML-side via **højreklik → Open In → Browser** i IntelliJ
2. Åbn DevTools → **Console**
3. Tjek at `console.log(rides)` viser et array
4. Tjek at listen vises på siden
5. Under **Network**-fanen kan du se dine GET og POST requests med statuskoder

> Siden skal åbnes via højreklik → Open In → Browser – ikke via genvejsikonerne i filen – ellers blokerer Chrome alle fetch-kald.

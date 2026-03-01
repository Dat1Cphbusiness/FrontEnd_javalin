# 07 · CORS

## Problemet

Når din HTML-side på port `63342` forsøger at kontakte Javalin på port `7070`, blokerer browseren requestet:

```
❌ Access to fetch at 'http://localhost:7070/rides'
   from origin 'http://localhost:63342' has been blocked
   by CORS policy
```

Dette er en sikkerhedsregel i alle browsere kaldet **Same-Origin Policy** – en side må som udgangspunkt kun kontakte den samme adresse og port den selv kommer fra.

```
HTML på port 63342  →  fetch til port 7070  →  ❌ BLOKERET
```

---

## Løsningen

Serveren kan give browseren tilladelse ved at sende en CORS-header tilbage. I Javalin gøres det med to linjer:

```java
Javalin app = Javalin.create(config -> {
    config.bundledPlugins.enableCors(cors ->
        cors.addRule(it -> it.anyHost())
    );
}).start(7070);
```

`anyHost()` betyder at alle origins får lov – passende til udvikling.

```
HTML på port 63342  →  fetch til port 7070  →  ✅ TILLADT
```

---

## Test at det virker

Uden CORS ser du denne fejl i browserens console når du sender en fetch:

```
TypeError: Failed to fetch
```

Med CORS aktiveret forsvinder fejlen og requestet går igennem.

1. Tilføj CORS-konfigurationen til din `Javalin.create()`
2. Genstart serveren
3. Åbn din HTML-side via **højreklik → Open In → Browser** i IntelliJ
4. Send en fetch-request fra siden
5. Tjek at der ikke er CORS-fejl i console

> **Vigtigt:** Åbn altid din HTML via højreklik → Open In → Browser – ikke via genvejsikonerne direkte i filen. Genvejsikonerne åbner filen som `file://` i stedet for `http://localhost`, og så blokerer Chrome alle fetch-kald uanset CORS-indstillinger.

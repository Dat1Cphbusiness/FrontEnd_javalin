# 03 · Routes og HTTP-metoder

## Hvad er en route?

En route forbinder en URL-sti med noget kode der skal køre. Når en browser sender et request til `/rides`, ved serveren præcis hvad den skal gøre – fordi du har defineret en route for det.

```
Browser sender:  GET /rides
                      │
                      ▼
Javalin finder:  app.get("/rides", ...)  ← denne route matches
                      │
                      ▼
Serveren svarer: [...data...]
```

---

## GET og POST

Routes defineres med den HTTP-metode de lytter på:

```java
// Svarer på GET /rides
app.get("/rides", ctx -> ctx.result("Her er dine rides"));

// Svarer på POST /rides
app.post("/rides", ctx -> ctx.result("Ride modtaget!"));
```

| Metode | Bruges til | Hvem kalder den? |
|--------|------------|-----------------|
| GET | Hente data | Browser, fetch |
| POST | Sende ny data | fetch med body |

---

## ctx – context

`ctx` er objektet der repræsenterer både request og response. Du bruger det til at læse hvad browseren sendte, og til at sende noget tilbage.

```java
ctx.result("tekst");   // send en tekststreng tilbage
ctx.json(objekt);      // send et objekt som JSON
ctx.status(201);       // sæt HTTP statuskode
```

---

## Test at det virker

1. Tilføj en GET-route til din `main`-metode:
```java
app.get("/rides", ctx -> ctx.result("Hej fra serveren!"));
```
2. Kør `Main.java` med den grønne play-knap
3. Åbn browseren og gå til:
```
http://localhost:7070/rides
```
4. Du skulle se teksten `Hej fra serveren!` i browseren

> En GET-route kan testes direkte i browseren. En POST-route kræver Postman eller fetch – browseren kan ikke sende POST via adresselinjen.

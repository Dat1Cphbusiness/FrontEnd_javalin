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

---

## Postman – et værktøj til at teste routes

Browseren kan kun sende GET-requests via adresselinjen. **Postman** er et gratis program der lader dig sende alle typer requests – GET, POST og mere – uden at skrive en eneste linje HTML.

**Installation:**
1. Gå til [postman.com/downloads](https://www.postman.com/downloads/)
2. Download og installer – du behøver ikke oprette en konto

**Send et GET-request i Postman:**
1. Åbn Postman
2. Vælg `GET` i dropdown-menuen til venstre
3. Skriv `http://localhost:7070/rides` i URL-feltet
4. Klik **Send**
5. Svaret vises nederst i vinduet

```
GET http://localhost:7070/rides
                    ↓
Status: 200 OK
Body:   Hej fra serveren!
```

På næste side bruger vi Postman til at sende POST-requests.
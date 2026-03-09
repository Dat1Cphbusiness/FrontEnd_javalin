# 04 · Modtage data fra en POST-request

## Hvordan sender frontend data?

Når browseren sender en POST, pakkes data ind i request **body** som JSON:

```javascript
// Frontend (JavaScript)
fetch("http://localhost:7070/rides", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ date: "2025-03-15", role: "driver" })
});
```

---

## Modtag data i Javalin

For at Javalin kan oversætte JSON til et Java-objekt, skal du først oprette en klasse der matcher JSON-strukturen:

```java
// Ride.java
public class Ride {
    public String date;
    public String role;
    public String from;
    public String to;
    public boolean smoking;
    public boolean returnTrip;
    public String message;
}
```

Feltnavnene skal matche nøglerne i den JSON frontend sender – præcist samme stavning.

Derefter kan Javalin automatisk oversætte JSON-body til et `Ride`-objekt:

```java
app.post("/rides", ctx -> {
    Ride ride = ctx.bodyAsClass(Ride.class);
    System.out.println(ride.date + " " + ride.from + " → " + ride.to);
    ctx.status(201);
});
```

---

## Test at det virker

**Med Postman:**
1. Vælg `POST` og URL `http://localhost:7070/rides`
2. Klik på **Body → raw → JSON**
3. Skriv:
```json
{
  "date": "2025-03-15",
  "role": "driver",
  "from": "ronne",
  "to": "nexo"
}
```
4. Klik **Send** – du får status `201`
5. Tjek IntelliJ's console – du skulle se `2025-03-15 ronne → nexo`

---

> `ctx.bodyAsClass()` bruger Jackson under motorhjelmen til at oversætte JSON til Java. Det er derfor `javalin-bundle` er nødvendigt – den inkluderer Jackson automatisk.


## Hvordan sender frontend data?

Når browseren sender en POST, pakkes data ind i request **body** som JSON:

```javascript
// Frontend (JavaScript)
fetch("http://localhost:7070/rides", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ date: "2025-03-15", role: "driver" })
});
```

---

## Modtag data i Javalin

For at Javalin kan oversætte JSON til et Java-objekt, skal du først oprette en klasse der matcher JSON-strukturen:

```java
// Ride.java
public class Ride {
    public String date;
    public String role;
    public String from;
    public String to;
    public boolean smoking;
    public boolean returnTrip;
    public String message;
}
```

Feltnavnene skal matche nøglerne i den JSON frontend sender – præcist samme stavning.

Derefter kan Javalin automatisk oversætte JSON-body til et `Ride`-objekt:

```java
app.post("/rides", ctx -> {
    Ride ride = ctx.bodyAsClass(Ride.class);
    System.out.println(ride.date + " " + ride.from + " → " + ride.to);
    ctx.status(201);
});
```

---

## Test at det virker

Opret en simpel `test.html` og åbn den via **højreklik → Open In → Browser**:

```html
<!DOCTYPE html>
<html>
<body>
    <button onclick="
        fetch('http://localhost:7070/rides', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ date: '2025-03-15', role: 'driver', from: 'ronne', to: 'nexo' })
        })
    ">Send test-ride</button>
</body>
</html>
```

1. Kør `Main.java`
2. Åbn `test.html` via højreklik → Open In → Browser
3. Klik knappen
4. Tjek IntelliJ's console – du skulle se `2025-03-15 ronne → nexo`
5. Åbn `http://localhost:7070/rides` i browseren – rides er i listen

---

> `ctx.bodyAsClass()` bruger Jackson under motorhjelmen til at oversætte JSON til Java. Det er derfor `javalin-bundle` er nødvendigt – den inkluderer Jackson automatisk.

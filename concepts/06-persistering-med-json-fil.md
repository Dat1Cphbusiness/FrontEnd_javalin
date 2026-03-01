# 06 · Persistering med JSON-fil

## Idéen

I stedet for kun at holde listen i RAM, gemmer vi den til en fil på disk hver gang noget tilføjes. Ved opstart læser vi filen tilbage ind i listen.

```
Server starter  →  load()  →  rides læses fra fil
POST modtages   →  rides.add(ride)
                →  save()  →  rides skrives til fil
GET modtages    →  ctx.json(rides)
```

---

## Koden

Jackson's `ObjectMapper` bruges til at læse og skrive JSON:

```java
public class Main {

    static ObjectMapper mapper = new ObjectMapper();
    static File file = new File("rides.json");
    static List<Ride> rides = load();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        app.get("/rides", ctx -> ctx.json(rides));

        app.post("/rides", ctx -> {
            Ride ride = ctx.bodyAsClass(Ride.class);
            rides.add(ride);
            save();
            ctx.status(201);
        });
    }

    static List<Ride> load() {
        try {
            if (file.exists())
                return mapper.readValue(file, new TypeReference<>() {});
        } catch (Exception e) { e.printStackTrace(); }
        return new ArrayList<>();
    }

    static void save() {
        try {
            mapper.writeValue(file, rides);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
```

---

## rides.json

Filen oprettes automatisk første gang du sender en POST. Den ser sådan ud:

```json
[
  {"date":"2025-03-15","role":"driver","from":"ronne","to":"nexo","smoking":false,"returnTrip":false,"message":""}
]
```

---

## Test at det virker

1. Kør `Main.java`
2. Send en POST med Postman eller fra din HTML-formular
3. Find `rides.json` i roden af dit IntelliJ-projekt og åbn den – din ride er der
4. **Stop og genstart** serveren
5. Åbn `http://localhost:7070/rides` i browseren – rides er stadig der ✓

> `rides.json` oprettes i projektets **working directory** – typisk samme mappe som `pom.xml`.

# 05 · In-memory data

## En liste der lever i hukommelsen

Mens serveren kører kan vi gemme data i en almindelig Java `ArrayList`. Den lever i RAM så længe programmet kører.

```java
public class Main {

    static List<Ride> rides = new ArrayList<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        app.get("/rides", ctx -> ctx.json(rides));

        app.post("/rides", ctx -> {
            Ride ride = ctx.bodyAsClass(Ride.class);
            rides.add(ride);
            ctx.status(201);
        });
    }
}
```

`static` er vigtigt – det sikrer at alle routes deler den **samme** liste.

---

## Flowet

```
POST /rides  →  rides.add(ride)  →  listen vokser
GET  /rides  →  ctx.json(rides)  →  hele listen sendes tilbage
```

---

## Problemet med in-memory

```
Server starter   →  rides = []  (tom)
POST ride 1      →  rides = [ride1]
POST ride 2      →  rides = [ride1, ride2]
Server genstarter →  rides = []  (tom igen!)
```

Data forsvinder når serveren stoppes – fordi RAM ryddes når programmet lukker.

---

## Test at det virker

1. Kør `Main.java`
2. Send en POST med Postman (se side 04)
3. Åbn `http://localhost:7070/rides` i browseren – du skulle se din ride som JSON
4. Send endnu en POST
5. Reload browseren – listen vokser
6. **Stop og genstart** serveren
7. Reload browseren – listen er tom igen ✓

> Dette er bevidst – formålet er at forstå *hvorfor* vi har brug for persistering på næste side.

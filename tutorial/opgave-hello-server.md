# Opgave · Min første server

Du skal bygge en lille webapplikation med to filer: en HTML-side og en Java-server. Brugeren skriver en besked, trykker gem, og beskeden vises på siden – selv efter serveren genstartes.

---

## Hvad du skal ende med

```
[ Et inputfelt ]  [ Gem ]

Gemte beskeder:
• Hej verden
• Dette er min første server
• Det virker!
```

---

## Trin 1 · Opret Maven-projektet

1. Åbn IntelliJ → **File → New → Project → Maven**
2. Java 21, giv projektet navnet `hello-server`
3. Åbn `pom.xml` og tilføj:

```xml
<dependencies>
    <dependency>
        <groupId>io.javalin</groupId>
        <artifactId>javalin-bundle</artifactId>
        <version>6.3.0</version>
    </dependency>
</dependencies>
```

4. IntelliJ viser et lille ikon – klik **Load Maven Changes**

**Test:** Maven downloader Javalin uden fejl i konsollen.

---

## Trin 2 · Start en server

Opret `Main.java` i `src/main/java`:

```java
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
    }
}
```

Kør `main` med den grønne play-knap.

**Test:** Åbn `http://localhost:7070` i browseren. Du får en 404-fejl – det er korrekt, serveren kører bare uden routes endnu.

> Ser du en fejl om at port 7070 er i brug? Stop serveren med den røde firkant i IntelliJ og prøv igen.

---

## Trin 3 · Tilføj en GET-route

Tilføj en route der returnerer en liste (tom for nu):

```java
import io.javalin.Javalin;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<String> messages = new ArrayList<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        app.get("/messages", ctx -> ctx.json(messages));
    }
}
```

Genstart serveren (rød firkant → grøn play).

**Test:** Åbn `http://localhost:7070/messages` i browseren. Du skulle se `[]` – en tom JSON-liste.

---

## Trin 4 · Tilføj en POST-route

Tilføj en route der modtager en besked og gemmer den i listen:

```java
app.post("/messages", ctx -> {
    String message = ctx.body();
    messages.add(message);
    ctx.status(201);
});
```

**Test med Postman:**
1. Vælg `POST` og URL `http://localhost:7070/messages`
2. **Body → raw → Text**
3. Skriv en besked, f.eks. `Hej verden`
4. Klik **Send** – du får status `201`
5. Åbn `http://localhost:7070/messages` i browseren – beskeden er i listen

> Ser du status 200 i stedet for 201? Det er fint – det betyder bare at `ctx.status(201)` mangler, men beskeden gemmes stadig.

---

## Trin 5 · Aktivér CORS

Uden CORS vil din HTML-side ikke kunne kontakte serveren. Tilføj to linjer til `Javalin.create()`:

```java
Javalin app = Javalin.create(config -> {
    config.bundledPlugins.enableCors(cors ->
        cors.addRule(it -> it.anyHost())
    );
}).start(7070);
```

Genstart serveren.

---

## Trin 6 · Lav HTML-siden

Opret en mappe `frontend` et sted på din computer (ikke inde i Java-projektet). Opret `index.html`:

```html
<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <title>Hello Server</title>
</head>
<body>

<h1>Min første server</h1>

<input type="text" id="message" placeholder="Skriv en besked">
<button id="save">Gem</button>

<h2>Gemte beskeder:</h2>
<ul id="list"></ul>

<script>
    const input = document.querySelector("#message");
    const button = document.querySelector("#save");
    const list = document.querySelector("#list");

    // Hent og vis alle beskeder
    function hentBeskeder() {
        fetch("http://localhost:7070/messages")
            .then(res => res.json())
            .then(messages => {
                list.innerHTML = "";
                messages.forEach(msg => {
                    list.innerHTML += `<li>${msg}</li>`;
                });
            });
    }

    // Gem en ny besked
    button.addEventListener("click", () => {
        fetch("http://localhost:7070/messages", {
            method: "POST",
            body: input.value
        }).then(() => {
            input.value = "";
            hentBeskeder();
        });
    });

    // Hent beskeder når siden åbner
    hentBeskeder();
</script>

</body>
</html>
```

**Test:** Højreklik på `index.html` i IntelliJ → **Open In → Browser**.

> Brug altid højreklik → Open In → Browser – ikke genvejsikonerne direkte i filen. Genvejsikonerne åbner filen som `file://` og så blokerer Chrome alle fetch-kald.

Skriv en besked og tryk Gem. Beskeden vises i listen.

> Ser du ingen besked? Åbn DevTools → Console og tjek for fejl. Ser du `Failed to fetch`? Tjek at Javalin kører på port 7070.

---

## Trin 7 · Persistering

Lige nu forsvinder alle beskeder når serveren genstarter. Tilføj load og save med Jackson:

```java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static ObjectMapper mapper = new ObjectMapper();
    static File file = new File("messages.json");
    static List<String> messages = load();

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors ->
                cors.addRule(it -> it.anyHost())
            );
        }).start(7070);

        app.get("/messages", ctx -> ctx.json(messages));

        app.post("/messages", ctx -> {
            String message = ctx.body();
            messages.add(message);
            save();
            ctx.status(201);
        });
    }

    static List<String> load() {
        try {
            if (file.exists())
                return mapper.readValue(file, new TypeReference<>() {});
        } catch (Exception e) { e.printStackTrace(); }
        return new ArrayList<>();
    }

    static void save() {
        try {
            mapper.writeValue(file, messages);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
```

**Test:**
1. Genstart serveren
2. Skriv et par beskeder via HTML-siden
3. Find `messages.json` i roden af dit IntelliJ-projekt og åbn den – beskederne er der
4. Stop serveren med den røde firkant
5. Start serveren igen
6. Reload HTML-siden – beskederne er stadig der ✓

---

## Det er det hele

Du har nu bygget en komplet serverside-applikation med:
- En server der lytter på requests
- GET og POST routes
- CORS så frontend må tale med backend
- Persistering der overlever en genstart

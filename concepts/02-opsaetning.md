# 02 · Opsætning

## Nyt Maven-projekt i IntelliJ

1. **File → New → Project**
2. Vælg **Maven** og Java 21
3. Giv projektet et navn, f.eks. `carpool-server`

---

## Tilføj Javalin til pom.xml

Maven henter automatisk Javalin fra internettet når du tilføjer denne dependency:

```xml
<dependencies>
    <dependency>
        <groupId>io.javalin</groupId>
        <artifactId>javalin-bundle</artifactId>
        <version>6.3.0</version>
    </dependency>
</dependencies>
```

`javalin-bundle` inkluderer Javalin + Jackson (til JSON) + logging. Du behøver ikke installere noget manuelt.

---

## Start en server

Opret en klasse `Main.java` med en `main`-metode:

```java
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
    }
}
```

Kør `main` med den grønne play-knap. Du skulle gerne se dette i IntelliJ's console:

```
[main] INFO io.javalin.Javalin - Listening on http://localhost:7070/
```

Serveren kører nu og venter på requests. Åbn `http://localhost:7070` i browseren – du får en 404, men det betyder serveren virker. Der er bare ingen routes endnu.

---

## Projektstruktur

```
carpool-server/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── Main.java
```

> **Stop serveren** med den røde firkant i IntelliJ før du starter den igen – ellers får du en fejl fordi port 7070 allerede er i brug.

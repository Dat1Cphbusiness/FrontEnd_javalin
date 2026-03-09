# JSON — Et format til at sende data

## Hvad er JSON?

Når en frontend (browser) og en backend (server) skal udveksle data, har de brug for et fælles sprog. Det sprog hedder **JSON** — JavaScript Object Notation.

JSON er ikke kode du kører. Det er bare et **format** — en måde at skrive data på, som både mennesker og maskiner kan læse.

---

## Sådan ser JSON ud

JSON er bygget op af **nøgle-værdi par**, ligesom en `Map` i Java:

```json
{
    "name": "Alice",
    "age": 22,
    "student": true
}
```

- Nøgler er altid en `String` med anførselstegn: `"name"`
- Nøgle og værdi adskilles med kolon: `:`
- Par adskilles med komma: `,`
- Det hele pakkes ind i krøllede parenteser: `{ }`

---

## Typer af værdier

Du kan gemme forskellige typer data i JSON:

```json
{
    "name":     "Alice",
    "age":      22,
    "gpa":      10.5,
    "active":   true,
    "nickname": null
}
```

| Type | Eksempel |
|---|---|
| Tekst (String) | `"Alice"` |
| Heltal (int) | `22` |
| Decimaltal (double) | `10.5` |
| Sand/falsk (boolean) | `true` / `false` |
| Ingen værdi | `null` |

---

## Lister i JSON

Hvis du har flere værdier af samme slags, bruger du en **array** — med firkantede parenteser:

```json
{
    "courses": ["Java", "Web", "Databaser"]
}
```

---

## Objekter inden i objekter

Du kan indlejre et JSON-objekt inde i et andet:

```json
{
    "name": "Alice",
    "address": {
        "city": "Copenhagen",
        "zip": "2200"
    }
}
```

---

## Fra Java Map til JSON

Javalin konverterer automatisk dine Java-datastrukturer til JSON, når du skriver `ctx.json()`.

**Java:**
```java
Map<String, String> reservations = new HashMap<>();
reservations.put("saturday", "No reservation");
reservations.put("sunday", "18:00");

ctx.json(reservations);
```

**JSON der sendes til browseren:**
```json
{
    "saturday": "No reservation",
    "sunday": "18:00"
}
```

Strukturen af din JSON afhænger direkte af din datastruktur i Java.

---

## Micro Assignment

### Opgave 1 — Læs JSON
Se på dette JSON-objekt og svar på spørgsmålene nedenfor:

```json
{
    "title": "The Matrix",
    "year": 1999,
    "available": true,
    "genres": ["action", "sci-fi"],
    "director": {
        "name": "The Wachowskis",
        "country": "USA"
    }
}
```

**Spørgsmål:**
1. Hvad er værdien af `"title"`? Hvilken type er det?
2. Hvad er værdien af `"year"`? Hvilken type er det?
3. Hvad gemmer `"genres"`? Hvad hedder denne type i JSON?
4. Hvad er værdien af `"country"` inde i `"director"`?
5. Hvad ville der stå, hvis filmen *ikke* var tilgængelig?

---

### Opgave 2 — Skriv JSON
En backend skal sende data om en studerende til en frontend. Skriv et JSON-objekt med følgende information:

- Navn: dit eget navn
- Alder: din alder
- Er du studerende? Ja
- Dine tre yndlingsfag (som en liste)

---

### Opgave 3 — Java til JSON
Hvad vil dette Java-objekt se ud som JSON, når Javalin sender det?

```java
Map<String, String> menu = new HashMap<>();
menu.put("monday", "Pasta");
menu.put("tuesday", "Salad");
menu.put("wednesday", "Soup");

ctx.json(menu);
```

Skriv det JSON du forventer browseren modtager.

---

### Opgave 4 — Find fejlene
Dette JSON er ikke gyldigt. Find og ret de tre fejl:

```json
{
    name: "Bob",
    "age": 25,
    "city": Copenhagen,
    "student": True
}
```

<details>
<summary>Hint</summary>
Tjek: nøgler skal have anførselstegn, string-værdier skal have anførselstegn, og booleans skrives med lille begyndelsesbogstav i JSON.
</details>

<details>
<summary>Svar</summary>

```json
{
    "name": "Bob",
    "age": 25,
    "city": "Copenhagen",
    "student": true
}
```

Fejlene var:
1. `name` manglede anførselstegn som nøgle
2. `Copenhagen` manglede anførselstegn som string-værdi
3. `True` skal være `true` med lille t

</details>
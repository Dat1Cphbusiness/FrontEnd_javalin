# JavaScript – Find og ret fejlen 🔍

10 opgaver hvor koden indeholder én fejl. Find den og ret den.

---


```js
function tjekAlder(alder) {
    if (alder >= 18) {
        let besked = "Du er myndig";
    }
    console.log(besked);
}

tjekAlder(20);
```

<details>
<summary>Hint</summary>
Hvor er `besked` erklæret, og hvor bliver den brugt?
</details>

<details>
<summary>Løsning</summary>

```js
function tjekAlder(alder) {
    let besked;
    if (alder >= 18) {
        besked = "Du er myndig";
    }
    console.log(besked);
}

tjekAlder(20);
```

`let` er **blok-scoped** — den eksisterer kun inden for de `{}` den er erklæret i. `besked` skal erklæres uden for if-blokken for at være tilgængelig i `console.log`.
</details>


---

## Opgave 2 – Grundlæggende syntax

```js
let point = 10;

if (point = 10) {
    console.log("Du har 10 point!");
}
```

<details>
<summary>Hint</summary>
Er det en sammenligning eller en tildeling?
</details>

<details>
<summary>Løsning</summary>

```js
if (point === 10) {  // = er tildeling, === er sammenligning
```

`=` tildeler en værdi. Til sammenligning skal man bruge `===`.
</details>

---

## Opgave 3 – Grundlæggende syntax

```js
const farver = ["rød", "grøn", "blå"];

for (let i = 0; i <= farver.length; i++) {
    console.log(farver[i]);
}
```

<details>
<summary>Hint</summary>
Hvad er det sidste gyldige indeks i et array med 3 elementer?
</details>

<details>
<summary>Løsning</summary>

```js
for (let i = 0; i < farver.length; i++) {  // <= → <
```

Et array med 3 elementer har indeks 0, 1, 2. `<= length` giver `undefined` på det sidste gennemløb.
</details>

---

## Opgave 4 – Event listeners

```html
<button id="knapId">Klik mig</button>

<script>
    document.getElementById("knap").addEventListener("click", function() {
        console.log("Knappen blev klikket!");
    });
</script>
```

<details>
<summary>Hint</summary>
Sammenlign id'et i HTML med det der bruges i JavaScript.
</details>

<details>
<summary>Løsning</summary>

```js
document.getElementById("knapId")  // "knap" → "knapId"
```

`getElementById` skal bruge præcis det samme id som i HTML-elementet.
</details>

---

## Opgave 5 – Event listeners

```html
<input id="navn" type="text" />

<script>
    const input = document.getElementById("navn");
    input.addEventListener("change", (e) => {
        console.log(e.target.value);
    });
</script>
```

Koden skal vise hvad brugeren skriver **løbende** – bogstav for bogstav.

<details>
<summary>Hint</summary>
Hvornår fyrer `change` kontra `input`?
</details>

<details>
<summary>Løsning</summary>

```js
input.addEventListener("input", (e) => {  // "change" → "input"
```

`change` fyrer kun når feltet mister fokus. `input` fyrer ved hvert tastetryk.
</details>

---

## Opgave 6 – Opdatering af HTML

```html
<p id="besked"></p>

<script>
    document.getElementById("besked").innerText = "<strong>Vigtigt!</strong>";
</script>
```

Teksten skal vises med fed skrift.

<details>
<summary>Hint</summary>
Hvilken egenskab fortolker HTML-tags?
</details>

<details>
<summary>Løsning</summary>

```js
document.getElementById("besked").innerHTML = "<strong>Vigtigt!</strong>";
// innerText → innerHTML
```

`innerText` viser HTML-tags som ren tekst. `innerHTML` fortolker dem som HTML.
</details>

---

## Opgave 7 – Opdatering af HTML

```html
<ul id="liste"></ul>

<script>
    const ting = ["Æble", "Banan", "Citron"];

    ting.forEach(function(element) {
        document.getElementById("liste").innerHTML += "<li>" + element + "<li>";
    });
</script>
```

<details>
<summary>Hint</summary>
Se nøje på HTML-strukturen for hvert listeelement.
</details>

<details>
<summary>Løsning</summary>

```js
"<li>" + element + "</li>"  // <li> → </li> som afsluttende tag
```

Det afsluttende tag manglede `/`. `<li>` og `<li>` er to åbnende tags.
</details>

---

## Opgave 8 – sessionStorage

```js
const bruger = { navn: "Mia", alder: 22 };

sessionStorage.setItem("bruger", bruger);

const gemt = sessionStorage.getItem("bruger");
console.log(gemt.navn);
```

<details>
<summary>Hint</summary>
sessionStorage kan kun gemme tekst.
</details>

<details>
<summary>Løsning</summary>

```js
// Gem:
sessionStorage.setItem("bruger", JSON.stringify(bruger));

// Hent:
const gemt = JSON.parse(sessionStorage.getItem("bruger"));
console.log(gemt.navn);
```

sessionStorage gemmer kun strings. Objekter skal konverteres med `JSON.stringify` og læses tilbage med `JSON.parse`.
</details>

---

## Opgave 9 – sessionStorage

```js
const side = sessionStorage.getItem("aktivSide");

if (side === null) {
    sessionStorage.setItem("aktivSide", 1);
}

sessionStorage.setItem("aktivSide", Number(side) + 1);
console.log(sessionStorage.getItem("aktivSide"));
```

Koden skal starte på side 1 og tælle op. Men den viser `null1` første gang.

<details>
<summary>Hint</summary>
Hvad sker der efter if-blokken, første gang koden kører?
</details>

<details>
<summary>Løsning</summary>

```js
const side = sessionStorage.getItem("aktivSide");

if (side === null) {
    sessionStorage.setItem("aktivSide", 1);
} else {
    sessionStorage.setItem("aktivSide", Number(side) + 1);
}
console.log(sessionStorage.getItem("aktivSide"));

```

Uden `else` kører den sidste linje altid – selv første gang, hvor `side` stadig er `null`. Tilføj `else`, og brug `Number()` så strengen ikke bare sammensættes med `+ 1`.
</details>

---

## Opgave 10 – Fetch
```html
<ul id="liste"></ul>

<script>
fetch("/api/produkter")
    .then(response => response.json)
    .then(data => {
        data.forEach(produkt => {
            document.getElementById("liste").innerHTML += "<li>" + produkt.navn + "</li>";
        });
    });
</script>
```

Siden viser ingenting, selv om serveren svarer korrekt.
<details>
<summary>Hint</summary>
`response.json` — er det en egenskab eller en metode?
</details>
<details>
<summary>Løsning</summary>

```html
<ul id="liste"></ul>
<script>
fetch("/api/produkter")
    .then(response => response.json())
    .then(data => {
        data.forEach(produkt => {
            document.getElementById("liste").innerHTML += "<li>" + produkt.navn + "</li>";
        });
    });
</script>
```
`json` er en **metode** og skal kaldes med `()`. Uden parenteser returnerer `.then` selve funktionen i stedet for at kalde den — og `data` i næste `.then` bliver aldrig et array.
</details>
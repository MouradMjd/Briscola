```mermaid
classDiagram
class IDeck {
    <<interface>>

    create() void
    shuffle() void
    popCard() Card
}

class AbstractDeck {
    <<abstract>>
    -static final int DEFAULT_DECK_SIZE

    #final List<Card> cards

    getCards() List<Card>
    getMaxSize() int
    getCardsRemaining() int
    isEmpty() boolean
}
AbstractDeck ..|> IDeck

class Deck {
    -Card trumpCard

    +create() void
    +shuffle() void
    +popCard() Card
    +selectTrumpCard() void
    +hasTrumpSeed(ICard card) boolean
}
Deck --|> AbstractDeck
```

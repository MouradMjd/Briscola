# Briscola
A Java implementation of the Italian card game Briscola written by [Mourad](https://github.com/Mourad261103) and [Starless](https://github.com/StarlessDev).

![](https://i.imgur.com/JW8uiHY.gif)
---
## How was it made
We have written a minimalistic api that enables everyone to build their own simple card game.
All the classes are located in the package [`us.teamronda.briscola.api`](src/main/java/us/teamronda/briscola/api) and are split into:
- interfaces: they hold the necessary methods and their respective javadocs
- abstract classes: they implement their respective interface and hold some code that is shared between different card games. For example the class `AbstractDeck` holds a list of cards and implements some methods to take out cards from the deck itself, which likely does not need any custom implementation from other developers.

For each object we provide the respective UML diagram (which also contains the representation of our api implementation): you can view them by clicking [here](uml/).
Now let's quickly get into the interfaces defined:

### [ICard](src/main/java/us/teamronda/briscola/api/cards/ICard.java)
This interface represents, as you might have guessed, a single playing card.
It holds two methods returning an Enum:
- ICard#getType: returns a [CardType](src/main/java/us/teamronda/briscola/api/cards/CardType.java) enum, which represents the number or figure on the card.
- ICard#getSeed: returns a (pretty self-explanatory) [Seed](src/main/java/us/teamronda/briscola/api/cards/Seed.java) enum
- ICard#getPoints: returns an integer and uses the Seed and CardType to calculate the worth of this card object

### [IDeck](src/main/java/us/teamronda/briscola/api/deck/IDeck.java)
This interface represents a Deck, aka a glorified collection of cards with all sorts of methods to interact with said collection.

Instead of directly implementing this interface, we advise to extend the abstract class [AbstractDeck.java](src/main/java/us/teamronda/briscola/api/deck/AbstractDeck.java) and to start working from there.
Want an example? Check out our [Deck.java](src/main/java/us/teamronda/briscola/objects/Deck.java) class.

### [IPlayer](src/main/java/us/teamronda/briscola/api/player/IPlayer.java)
This interface represents any Player participating in the game, being either a human or a bot. The object holds the integer representing the points of the player and a collection of cards representing the player's hand.
IPlayer also extends the [Comparable](https://docs.oracle.com/en%2Fjava%2Fjavase%2F21%2Fdocs%2Fapi%2F%2F/java.base/java/lang/Comparable.html) interface, so you need to implement the `compareTo()` to create a scoreboard at the end of the game.

Instead of directly implementing this interface, we advise to extend the abstract class [AbstractPlayer.java](src/main/java/us/teamronda/briscola/api/player/AbstractPlayer.java) and to start working from there.
Want an example? Check out our [Player.java](src/main/java/us/teamronda/briscola/objects/Player.java) class.

### [GameLoop](src/main/java/us/teamronda/briscola/api/game/GameLoop.java)
This interface defines a few methods which define some functions that need to be called in various moments of the game.

The most important method is the `tick()` method, which takes a IPlayer object and the ICard that they played and executes their turn.

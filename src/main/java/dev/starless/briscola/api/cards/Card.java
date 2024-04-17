package dev.starless.briscola.api.cards;

public record Card(CardType tipo, Seed seed) {

    public int getPunti() {
        return tipo.getPunti();
    }
}

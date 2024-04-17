package dev.starless.briscola.api;

import dev.starless.briscola.api.cards.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    private static final int STANDARD_SIZE_HAND = 3;

    private final String nome;
    private final List<Card> mano;
    private final int punti;

    public Player(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<>(STANDARD_SIZE_HAND);
        this.punti = 0;
    }

    public void addCard(Card card) {
        // TODO: IMPLEMENT
    }

    public Card pollCard(int index) {
        if (index < 0 || index >= mano.size()) {
            throw new IllegalArgumentException("Index can be only 0 to " +STANDARD_SIZE_HAND);
        }

        return mano.remove(index);
    }

    public void addPointCards(List<Card> carteVinte) {
        // TODO: IMPLEMENT
    }
}

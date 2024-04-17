package dev.starless.briscola.api.player;

import dev.starless.briscola.api.cards.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractPlayer implements IPlayer {

    private static final int DEFAULT_SIZE_HAND = 3;
    private static final int DEFAULT_STARTING_POINTS = 0;

    private final String nome;
    protected final List<Card> mano;
    protected int punti;

    public AbstractPlayer(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<>(3);
        this.punti = DEFAULT_STARTING_POINTS;
    }
}

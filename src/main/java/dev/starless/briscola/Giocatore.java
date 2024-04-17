package dev.starless.briscola;

import dev.starless.briscola.objects.Carta;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Giocatore {

    private static final int STANDARD_SIZE_HAND = 3;

    private final String nome;
    private final List<Carta> mano;
    private final int punti;

    public Giocatore(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<>(STANDARD_SIZE_HAND);
        this.punti = 0;
    }

    public void addCard(Carta carta) {
        // TODO: IMPLEMENT
    }

    public Carta pollCard(int index) {
        if (index < 0 || index >= mano.size()) {
            throw new IllegalArgumentException("Index can be only 0 to " +STANDARD_SIZE_HAND);
        }

        return mano.remove(index);
    }

    public void addPointCards(List<Carta> carteVinte) {
        // TODO: IMPLEMENT
    }
}

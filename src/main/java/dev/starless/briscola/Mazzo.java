package dev.starless.briscola;

import dev.starless.briscola.objects.Carta;
import dev.starless.briscola.objects.Seme;
import dev.starless.briscola.objects.Tipo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazzo {

    private final List<Carta> mazzo = new ArrayList<>();

    public void create() {
        mazzo.clear();

        for (Tipo tipo : Tipo.values()) {
            for (Seme seme : Seme.values()) {
                mazzo.add(new Carta(tipo, seme));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(mazzo);
    }

    public Carta popBriscola() {
        // TODO: IMPLEMENT
        // DO NOT PUT IT THE INTERFACE
        return null;
    }

    public Carta popCard() {
        //TODO: IMPLEMENT
        return null;
    }

    public int getRemainingCards() {
        return mazzo.size();
    }

    public boolean isEmpty() {
        return mazzo.isEmpty();
    }
}

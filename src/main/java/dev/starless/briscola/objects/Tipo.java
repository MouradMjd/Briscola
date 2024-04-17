package dev.starless.briscola.objects;

public enum Tipo {

    // Tipo tipo = new Tipo(11);
    ASSO(11),
    DUE,
    TRE(10),
    QUATTRO,
    CINQUE,
    SEI,
    SETTE,
    FANTE(2),
    CAVALLO(3),
    RE(4);

    // classe
    // class Tipo {
    private final int punti;

    Tipo() {
        this(0);
    }

    Tipo(int punti) {
        this.punti = punti;
    }

    public int getPunti() {
        return punti;
    }
}

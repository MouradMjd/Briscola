package dev.starless.briscola.objects;

public record Carta(Tipo tipo, Seme seme) {

    public int getPunti() {
        return tipo.getPunti();
    }
}

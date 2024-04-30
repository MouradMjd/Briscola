package us.teamronda.briscola.gui.components;

public enum AnimationType {

    UP(true),
    RIGHT(true),
    NONE(false);

    private final boolean hasAnimation;

    AnimationType(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
    }

    public boolean hasAnimation() {
        return hasAnimation;
    }
}

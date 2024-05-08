package us.teamronda.briscola.gui;

public enum AnimationType {

    UP(true),
    NONE(false);

    private final boolean hasAnimation;

    AnimationType(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
    }

    public boolean hasAnimation() {
        return hasAnimation;
    }
}

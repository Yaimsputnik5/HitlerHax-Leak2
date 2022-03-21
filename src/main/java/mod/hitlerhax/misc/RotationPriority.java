package mod.hitlerhax.misc;
public enum RotationPriority {
    Highest(200),
    High(100),
    Normal(0),
    Low(-100),
    Lowest(-200);

    final int priority;

    RotationPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
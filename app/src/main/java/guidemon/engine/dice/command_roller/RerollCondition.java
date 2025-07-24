package guidemon.engine.dice.command_roller;

//not considered a Conditional
public class RerollCondition {
    ThresholdType tt;
    int value;
    boolean once;

    RerollCondition(ThresholdType tt, int value, boolean once) {
        this.tt = tt; this.value = value; this.once = once;
    }

    boolean matches(int roll) {
        return switch(tt) {
            case EQUAL -> roll == value;
            case GE    -> roll >= value;
            case LE    -> roll <= value;
            case LT    -> roll <  value;
            case GT    -> roll >  value;
        };
    }
}
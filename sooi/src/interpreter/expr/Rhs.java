package interpreter.expr;

import interpreter.util.Instance;
import interpreter.util.Arguments;
import interpreter.value.Value;

public abstract class Rhs {

    private int line;

    protected Rhs(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public abstract Value<?> rhs(Instance self, Arguments args);

}

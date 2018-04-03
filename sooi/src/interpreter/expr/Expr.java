package interpreter.expr;

import interpreter.util.Instance;
import interpreter.util.Arguments;
import interpreter.value.Value;

public abstract class Expr extends Rhs {

    public Expr(int line) {
        super(line);
    }

    public abstract Value<?> rhs(Instance self, Arguments args);

}

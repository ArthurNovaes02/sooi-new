package interpreter.expr;

import interpreter.util.Instance;
import interpreter.util.Arguments;
import interpreter.value.Value;

public class ConstExpr extends Expr {

    private Value<?> value;

    public ConstExpr(Value<?> value, int line) {
        super(line);
        this.value = value;
    }

    public Value<?> rhs(Instance self, Arguments args) {
        return value;
    }

}

package interpreter.expr;

import interpreter.util.Instance;
import interpreter.util.Arguments;
import interpreter.util.AccessPath;
import interpreter.value.Value;

public class AccessExpr extends Expr {

    private AccessPath path;

    public AccessExpr(AccessPath path, int line) {
        super(line);
        this.path = path;
    }

    public Value<?> rhs(Instance self, Arguments args) {
        return path.getValue(self, args);
    }

}

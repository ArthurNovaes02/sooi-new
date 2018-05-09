package interpreter.expr;

import interpreter.util.Function;
import interpreter.util.Instance;
import interpreter.util.Arguments;
import interpreter.util.AccessPath;
import interpreter.util.InterpreterError;
import interpreter.util.Memory;
import interpreter.value.Value;
import interpreter.value.FunctionValue;

import java.util.List;
import java.util.ArrayList;

public class FunctionCallExpr extends Expr {

    private AccessPath path;
    private List<Rhs> params;

    public FunctionCallExpr(AccessPath path, int line) {
        super(line);

        this.path = path;
        this.params = new ArrayList<Rhs>();
    }

    public void addParam(Rhs rhs) {
        params.add(rhs);
    }

    public Value<?> rhs(Instance self, Arguments args) {
        Value<?> funct = path.getValue(self, args);
        if (!(funct instanceof FunctionValue))
            InterpreterError.abort(this.getLine());

        Memory ref = path.getReference(self, args);
        if (ref == null || !(ref instanceof Instance))
            InterpreterError.abort(this.getLine());

        Function f = ((FunctionValue) funct).value();
        Instance fSelf = (Instance) ref;
        Arguments fArgs = new Arguments();

        for (int i = 0; i < params.size(); i++) {
            Rhs rhs = params.get(i);
            Value<?> value = rhs.rhs(self, args);
            fArgs.setValue(String.format("arg%d", i+1), value);
        }

        Value<?> ret = f.call(fSelf, fArgs);
        return ret;
    }

}

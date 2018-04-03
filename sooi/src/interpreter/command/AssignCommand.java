package interpreter.command;

import interpreter.expr.Rhs;
import interpreter.util.Instance;
import interpreter.util.Arguments;
import interpreter.util.AccessPath;
import interpreter.value.Value;

public class AssignCommand extends Command {

    private AccessPath lhs;
    private Rhs rhs;

    public AssignCommand(AccessPath lhs, Rhs rhs, int line) {
        super(line);

        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void execute(Instance self, Arguments args) {
        Value<?> value = rhs.rhs(self, args);

        if (lhs != null)
            lhs.setValue(self, args, value);
    }
}

package interpreter.util;

import interpreter.value.Value;
import interpreter.value.IntegerValue;

public abstract class Function {

    protected Function() {
    }

    public abstract Value<?> call(Instance self, Arguments args);
}

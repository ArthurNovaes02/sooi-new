package interpreter.util;

import interpreter.value.Value;
import interpreter.value.IntegerValue;

import java.util.Map;
import java.util.HashMap;

public class Memory {

    private Map<String,Value<?>> memory;

    protected Memory() {
        memory = new HashMap<String,Value<?>>();
    }

    public boolean contains(String name) {
        return memory.containsKey(name);
    }

    public Value<?> getValue(String name) {
        if (!this.contains(name)) {
            IntegerValue iv = new IntegerValue(0);
            memory.put(name, iv);
        }

        return memory.get(name);
    }

    public void setValue(String name, Value<?> value) {
        memory.put(name, value);
    }

}

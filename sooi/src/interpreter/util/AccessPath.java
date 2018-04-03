package interpreter.util;

import java.util.List;
import java.util.ArrayList;

import interpreter.value.Value;
import interpreter.value.IntegerValue;
import interpreter.value.InstanceValue;

public class AccessPath {

    private int line;
    private List<String> names;

    public AccessPath(String name, int line) {
        this.line = line;

        names = new ArrayList<String>();
        names.add(name);
    }

    public void addName(String name) {
        names.add(name);
    }

    public List<String> getNames() {
        return new ArrayList<String>(names);
    }

    public Value<?> getValue(Instance self, Arguments args) {
        String name = this.getLastName();
        Memory ref = this.getReference(self, args);
        if (!ref.contains(name)) {
            ref.setValue(name, IntegerValue.Zero);
            return IntegerValue.Zero;
        } else {
            return ref.getValue(name);
        }
    }

    public void setValue(Instance self, Arguments args, Value<?> value) {
        String name = this.getLastName();
        Memory ref = this.getReference(self, args);
        ref.setValue(name, value);
    }

    public Memory getReference(Instance self, Arguments args) {
        int i;
        String name;
        Memory ref;

        name = names.get(0);
        if (name.equals("self")) {
            if (self == null)
                InterpreterError.abort(line);

            ref = self;
            i = 1;
        } else if (name.equals("args")) {
            ref = args;
            if (self == null)
                InterpreterError.abort(line);

            i = 1;
        } else {
            ref = Global.getGlobalTable();
            i = 0;
        }

        for (; i < names.size() - 1; i++) {
            name = names.get(i);

            Memory newRef;
            if (ref.contains(name) && ref.getValue(name) instanceof InstanceValue) {
                InstanceValue iv = (InstanceValue) ref.getValue(name);
                newRef = iv.value();
            } else {
                // if there are more names, than it must be an instance (object) reference.
                newRef = new Instance();
                ref.setValue(name, new InstanceValue((Instance) newRef));
            }

            ref = newRef;
        }

        return ref;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(names.get(0));
        for (int i = 1; i < names.size(); i++)
          sb.append(".").append(names.get(i));

        return sb.toString();
    }

    private String getLastName() {
        return names.get(names.size() - 1);
    }

}

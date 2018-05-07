package interpreter.util;

public class Instance extends Memory {

    public Instance dup() {
        Instance i = new Instance();
        for (String name : memory.keySet())
            i.setValue(name, this.getValue(name));

        return i;
    }

}
package interpreter.util;

import java.util.Scanner;

import interpreter.value.Value;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;

public class SpecialFunction extends Function {

    private FunctionType type;
    private Scanner in;

    public SpecialFunction(FunctionType type) {
        this.type = type;
        this.in = new Scanner(System.in);
    }

    public Value<?> call(Instance self, Arguments args) {
        Value<?> v;

        switch (type) {
            case Print:
                v = this.print(args);
                break;
            case Println:
                v = this.println(args);
                break;
            case Read:
                v = this.read(args);
                break;
            default:
                throw new RuntimeException("FIXME: implement me!");
        }

        return v;
    }

    private Value<?> print(Arguments args) {
        if (args.contains("args1")) {
            Value<?> v = args.getValue("args1");
            if (v instanceof IntegerValue) {
                IntegerValue iv = (IntegerValue) v;
                System.out.print(v.value());
            } else if (v instanceof StringValue) {
                StringValue sv = (StringValue) v;
                System.out.print(sv.value());
            } else {
                throw new RuntimeException("FIXME: Implement me!");
            }
        }

        return IntegerValue.Zero;
    }

    private Value<?> println(Arguments args) {
        Value<?> v = print(args);
        System.out.println();
        return v;
    }

    private Value<?> read(Arguments args) {
        // Print the argument.
        this.print(args);

        String str = in.nextLine();
        try {
           int n = Integer.parseInt(str);
           IntegerValue iv = new IntegerValue(n);
           return iv;
        } catch (Exception e) {
           StringValue sv = new StringValue(str);
           return sv;
        }
    }
}

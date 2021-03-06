package interpreter.util;

import java.util.Scanner;
import java.util.Random;

import interpreter.value.Value;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.FunctionValue;
import interpreter.value.InstanceValue;

public class SpecialFunction extends Function {

    private FunctionType type;
    private Scanner in;

    public SpecialFunction(FunctionType type) {
        this.type = type;
        this.in = new Scanner(System.in);
    }

    public Value<?> call(Instance self, Arguments args) {
        Value<?> v = null;

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
            case Random:
                v = this.random(args);
                break;
            case Get:
                v = this.get(args);
                break;
            case Set:
                v = set(args);
                break;
            case Abort:
                v = this.abort(args);
                break;
            case Type:
                v = this.type(args);
                break;
            case Length:
                v = this.length(args);
                break;
            case Substring:
                v = this.substring(args);
                break;
            case Clone:
                v = this.clone(args);
                break;
            default:
                v = IntegerValue.Zero;
                break;
        }

        return v;
    }

    private Value<?> print(Arguments args) {
        if (args.contains("arg1")) {
            Value<?> v = args.getValue("arg1");
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
    
    private Value<?> random (Arguments args){
        
        // so entra na condicao se tiver dois arguementos
        if (args.contains("arg1") && args.contains("arg2")){
            Random num = new Random();
            
            IntegerValue v;
            IntegerValue arg2_Go = (IntegerValue)args.getValue("arg2");
            IntegerValue arg2_Back = new IntegerValue(num.nextInt(arg2_Go.value()));
            
            IntegerValue arg1_Int = (IntegerValue)args.getValue("arg1");

            v = new IntegerValue(arg2_Back.value() + arg1_Int.value());
            
            return v;
        }
        
        return IntegerValue.Zero;
        
    }
    
    private Value<?> get(Arguments args){
        if (args.contains("arg1") && args.contains("arg2")){
            Value<?> v = args.getValue("arg1");
            StringValue name = (StringValue)args.getValue("arg2");
            
            Instance i = ((InstanceValue) v).value();
            
            return i.getValue(name.value());
        }
        return IntegerValue.Zero;
    }
    
    private Value<?> set(Arguments args){
        if (args.contains("arg1") && args.contains("arg2") && args.contains("arg3")){
            Value<?> v = args.getValue("arg1");
            StringValue name = (StringValue)args.getValue("arg2");
            Value<?> value = args.getValue("arg3");
            
            Instance i = ((InstanceValue) v).value();
            
            i.setValue(name.value(), value);
        }
        return IntegerValue.Zero;
    }
    
    private Value<?> abort(Arguments args){
        if (args.contains("arg1")) {
            Value<?> v = args.getValue("arg1");
            if (v instanceof IntegerValue) {
                IntegerValue iv = (IntegerValue) v;
                System.out.print(v.value());
            } else if (v instanceof StringValue) {
                StringValue sv = (StringValue) v;
                System.out.print(sv.value());
            }
        }
        InterpreterError.abort("exit");
        return IntegerValue.Zero;
    }
    
    private Value<?> type (Arguments args){
        if (args.contains("arg1")) {
            Value<?> x = args.getValue("arg1");
            StringValue type = null;
            
            if (x instanceof IntegerValue) 
                type = new StringValue("Integer");
            else if (x instanceof StringValue)
                type = new StringValue("String");
            else if (x instanceof FunctionValue)
                type = new StringValue("Function");
            else if (x instanceof InstanceValue)
                type = new StringValue("Instance");
                
            return type;
        }
        return IntegerValue.Zero;
    }
    
    private Value<?> length (Arguments args){
        if (args.contains("arg1")) {
            StringValue str = (StringValue) args.getValue("arg1");
            
            // armazena em um IV o tamanho da string
            IntegerValue iv = new IntegerValue(str.value().length());
            
            return iv;
        }
        return IntegerValue.Zero;
    }
    
    private Value<?> substring (Arguments args){
        if (args.contains("arg1") && args.contains("arg2") && args.contains("arg3")){
            StringValue str = (StringValue) args.getValue("arg1");
            IntegerValue i = (IntegerValue) args.getValue("arg2");
            IntegerValue f = (IntegerValue) args.getValue("arg3");
            
            // peag a substring
            String sub = str.value().substring(i.value(), i.value() + f.value());
            
            str = new StringValue(sub);
                    
            return str;
            
        }
        return IntegerValue.Zero;
    }
    
 private Value<?> clone(Arguments args) {
        if (!args.contains("arg1"))
            InterpreterError.abort("clone: primeiro argumento inexistente");

        Value<?> v = args.getValue("arg1");
        if (!(v instanceof InstanceValue))
            InterpreterError.abort("clone: primeiro argumento não é instance");

        Instance i = ((InstanceValue) v).value();
        InstanceValue iv = new InstanceValue(i.dup());
        return iv;
    }
}

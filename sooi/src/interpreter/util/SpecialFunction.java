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
                set(args);
                break;
            case Abort: // @TODO: conferir se e so isso
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
    
    private Value<?> random (Arguments args){
        
        // so entra na condicao se tiver dois arguementos
        if (args.contains("args1") && args.contains("args2")){
            Random num = new Random();
            
            IntegerValue v;
            IntegerValue arg2_Go = (IntegerValue)args.getValue("args2");
            IntegerValue arg2_Back = new IntegerValue(num.nextInt(arg2_Go.value()));
            
            IntegerValue arg1_Int = (IntegerValue)args.getValue("args1");

            v = new IntegerValue(arg2_Back.value() + arg1_Int.value());
            
            return v;
        }
        
        return IntegerValue.Zero;
        
    }
    
    private Value<?> get(Arguments args){
        if (args.contains("args1") && args.contains("args2")){
            Object obj = args.getValue("args1");
            StringValue name = (StringValue)args.getValue("args2");
            
            // @TODO:
            return name;
        }
        return IntegerValue.Zero;
    }
    
    private void set(Arguments args){
        if (args.contains("args1") && args.contains("args2") && args.contains("args3")){
            Object obj = args.getValue("args1");
            StringValue name = (StringValue)args.getValue("args2");
            Value<?> value = args.getValue("args3");
            
            // @TODO
        }
    }
    
    private Value<?> type (Arguments args){
        if (args.contains("args1")) {
            Value<?> x = args.getValue("args1");
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
        if (args.contains("args1")) {
            StringValue str = (StringValue) args.getValue("args1");
            
            // armazena em um IV o tamanho da string
            IntegerValue iv = new IntegerValue(str.value().length());
            
            return iv;
        }
        return IntegerValue.Zero;
    }
    
    private Value<?> substring (Arguments args){
        if (args.contains("args1") && args.contains("args2") && args.contains("args3")){
            StringValue str = (StringValue) args.getValue("args1");
            IntegerValue i = (IntegerValue) args.getValue("args2");
            IntegerValue f = (IntegerValue) args.getValue("args3");
            
            // peag a substring
            String sub = str.value().substring(i.value(), i.value() + f.value());
            
            str = new StringValue(sub);
                    
            return str;
            
        }
        return IntegerValue.Zero;
    }
    
    private Value<?> clone (Arguments args){
        if (args.contains("args1")){
            Object obj = args.getValue("args1");
            
            return (Value<?>) obj;
        }
        return IntegerValue.Zero;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.expr;

import interpreter.util.Arguments;
import interpreter.util.Instance;
import interpreter.value.IntegerValue;
import interpreter.value.Value;

/**
 *
 * @author arthur
 */
public class CompositeExpr extends Expr {
    Expr left;
    CompOp op;
    Expr right;
    
    public CompositeExpr(Expr left, CompOp op, Expr right, int line){
        super (line);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public Value<?> rhs(Instance self, Arguments args) {
        IntegerValue iv1, iv2, iv3;
        iv1 = (IntegerValue) left.rhs(self, args);      // conversao de tipos
        iv2 = (IntegerValue) right.rhs(self, args);     // conversao de tipos
        
        switch (op) {
            case Add:
                iv3 = new IntegerValue(iv1.value() + iv2.value());
                break;
            case Sub:
                iv3 = new IntegerValue(iv1.value() - iv2.value());
                break;
            case Div:
                iv3 = new IntegerValue(iv1.value() / iv2.value());
                break;
            case Mul:
                iv3 = new IntegerValue(iv1.value() * iv2.value());
                break;
            default:
                iv3 = new IntegerValue(0);
                break;
        }
        
        System.out.println("passou" + iv3.value());
        return iv3;
    }
}

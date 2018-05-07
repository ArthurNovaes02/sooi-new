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
    }

    public Value<?> rhs(Instance self, Arguments args) {
        IntegerValue iv1, iv2, iv3;
        iv1 = (IntegerValue) left.rhs(self, args);      // conversao de tipos
        iv2 = (IntegerValue) right.rhs(self, args);     // conversao de tipos
        
        if (op == CompOp.Add){
            iv3 = new IntegerValue(iv1.value() + iv2.value());
            return iv3;
        }
        
        else if (op == CompOp.Sub){
            iv3 = new IntegerValue(iv1.value() - iv2.value());
            return iv3;
        }
        
        else if (op == CompOp.Div){
            iv3 = new IntegerValue(iv1.value() / iv2.value());
            return iv3;
        }
        
        else if (op == CompOp.Mul){
            iv3 = new IntegerValue(iv1.value() * iv2.value());
            return iv3;
        }
        
        iv3 = new IntegerValue(0);
        return iv3;
    }
}

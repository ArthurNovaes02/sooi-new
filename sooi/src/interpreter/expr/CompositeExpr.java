/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.expr;

import interpreter.util.Arguments;
import interpreter.util.Instance;
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

    // @TODO: converter rhs para int
    public Value<?> rhs(Instance self, Arguments args) {
        if (op == CompOp.Add){
            return left.rhs(self, args) + right.rhs(self, args);
        }
        
        else if (op == CompOp.Sub){
            return left.rhs(self, args) - right.rhs(self, args);
        }
        
        else if (op == CompOp.Div){
            return left.rhs(self, args) / right.rhs(self, args);
        }
        
        else if (op == CompOp.Mul){
            return left.rhs(self, args) * right.rhs(self, args);
        }
        
        return;
    }
    
}

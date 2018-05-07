/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.expr;

import interpreter.expr.BoolExpr;
import interpreter.expr.Expr;
import interpreter.expr.RelOp;
import interpreter.util.Arguments;
import interpreter.util.Instance;
import interpreter.value.IntegerValue;

/**
 *
 * @author arthur
 */
public class SingleBoolExpr extends BoolExpr{
    private Expr left;
    private RelOp op;
    private Expr right;
    
    public SingleBoolExpr(Expr left, RelOp op, Expr right, int line){
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public boolean expr(Instance self, Arguments args){
        IntegerValue iv1, iv2;
        iv1 = (IntegerValue) left.rhs(self, args);      // conversao de tipos
        iv2 = (IntegerValue) right.rhs(self, args);     // conversao de tipos

        if(op == RelOp.Equal){
            if(iv1.value()== iv2.value())
                return true;
        }        

        else if (op == RelOp.GreaterEqual){
            if(iv1.value() >= iv2.value())
                return true;
        }
        
        else if (op == RelOp.GreaterThan){
            if(iv1.value() > iv2.value())
                return true;
        }
        
        else if (op == RelOp.LowerEqual){
            if(iv1.value() <= iv2.value())
                return true;
        }
        
        else if (op == RelOp.LowerThan){
            if(iv1.value() < iv2.value())
                return true;
        }
        
        else if (op == RelOp.NotEqual){
            if(iv1.value() != iv2.value())
                return true;
        }
        // eles
        return false;
    }
}

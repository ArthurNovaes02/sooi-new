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
        if(op == RelOp.Equal){
            if(left.rhs(self, args) == right.rhs(self, args))
                return true;
        }
        
        //@TODO: converter rhs para int
        
        else if (op == RelOp.GreaterEqual){
            if(left.rhs(self, args) >= right.rhs(self, args))
                return true;
        }
        
        else if (op == RelOp.GreaterThan){
            if(left.rhs(self, args) > right.rhs(self, args))
                return true;
        }
        
        else if (op == RelOp.LowerEqual){
            if(left.rhs(self, args) <= right.rhs(self, args))
                return true;
        }
        
        else if (op == RelOp.LowerThan){
            if(left.rhs(self, args) > right.rhs(self, args))
                return true;
        }
        
        else if (op == RelOp.NotEqual){
            if(left.rhs(self, args) != right.rhs(self, args))
                return true;
        }
        else
            return false;
    }
}

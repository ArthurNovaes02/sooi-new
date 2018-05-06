/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.expr;

import interpreter.util.Arguments;
import interpreter.util.Instance;

/**
 *
 * @author arthur
 */
public class CompositeBoolExpr extends BoolExpr{
    private BoolExpr left;
    private BoolOp op;
    private BoolExpr right;
    
    public CompositeBoolExpr(BoolExpr left, BoolOp op, BoolExpr right, int line){
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public boolean expr(Instance self, Arguments args){
        if(op == BoolOp.And){
            if(left.expr(self, args) && right.expr(self, args))
                return true;
        }
        
        else if (op == BoolOp.Or){
            if(left.expr(self, args) || right.expr(self, args))
                return true;
        }
        
        return false;
    }
    
}

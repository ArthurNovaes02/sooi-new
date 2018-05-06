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
public abstract class BoolExpr {
    
    private int line;
    
    protected BoolExpr(int line){
        this.line = line;
        
    }
    protected int getLine(){
        return line;
    }
    
    public abstract boolean expr(Instance self, Arguments args);
    
}

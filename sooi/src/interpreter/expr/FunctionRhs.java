/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.expr;

import interpreter.util.Arguments;
import interpreter.util.Instance;
import interpreter.value.FunctionValue;
import interpreter.value.Value;

/**
 *
 * @author arthur
 */
public class FunctionRhs extends Rhs{
    FunctionValue funct;
    
    public FunctionRhs(FunctionValue funct, int line){
        super(line);
        this.funct = funct;
    }

    public Value<?> rhs(Instance self, Arguments args) {
        return funct;
    }
    
    
}

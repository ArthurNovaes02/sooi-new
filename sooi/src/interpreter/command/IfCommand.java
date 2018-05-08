/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.command;

import interpreter.expr.BoolExpr;
import interpreter.util.Arguments;
import interpreter.util.Instance;

/**
 *
 * @author arthur
 */
public class IfCommand extends Command{
    private BoolExpr cond;
    private Command then;
    private Command else_;
    
    public IfCommand(BoolExpr cond, Command then, int line){
        super (line);
        this.cond = cond;
        this.then = then;
    }
    
    public IfCommand(BoolExpr cond, Command then, Command else_, int line){
        super (line);
        this.cond = cond;
        this.then = then;
        this.else_ = else_;
    }
    

    public void execute(Instance self, Arguments args) {
        
        boolean value = cond.expr(self, args);
        if(value){
            then.execute(self, args);
            if(else_ != null)
                else_.execute(self, args);
        }
    }
    
}

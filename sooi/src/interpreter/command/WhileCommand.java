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
public class WhileCommand extends Command{
    private BoolExpr expr;
    private Command cmd;
    
    public WhileCommand(BoolExpr expr, Command cmd, int line){
        super(line);
        this.expr = expr;
        this.cmd = cmd;
    }

    public void execute(Instance self, Arguments args) {
        
        while(expr.expr(self, args)){
            cmd.execute(self, args);
        }
    }
    
}

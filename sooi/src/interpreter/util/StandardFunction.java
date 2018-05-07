/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.util;

import interpreter.command.CommandsBlock;
import interpreter.expr.Rhs;
import interpreter.value.Value;

/**
 *
 * @author arthur
 */
public class StandardFunction extends Function{
    private CommandsBlock cmds;
    private Rhs ret;
    
    public StandardFunction(CommandsBlock cmds){
        this.cmds = cmds;
    }
    
    public StandardFunction(CommandsBlock cmds, Rhs ret){
        this.cmds = cmds;
        this.ret = ret;
    }

    public Value<?> call(Instance self, Arguments args) {
        Value<?> v;
        
        cmds.execute(self, args);
        v = ret.rhs(self, args);
        
        return v;
    }
}

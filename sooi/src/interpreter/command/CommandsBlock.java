package interpreter.command;

import java.util.List;
import java.util.ArrayList;

import interpreter.util.Instance;
import interpreter.util.Arguments;

public class CommandsBlock extends Command {

    private List<Command> cmds;

    public CommandsBlock() {
        super(-1);

        cmds = new ArrayList<Command>();
    }

    public void addCommand(Command c) {
        cmds.add(c);
    }

    public void execute(Instance self, Arguments args) {
        for (Command c : cmds)
            c.execute(self, args);
    }

}

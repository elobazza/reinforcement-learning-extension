package primitives.go;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import burlap.QLearningAlgorithm;

public class LearningCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
            return SyntaxJ.commandSyntax();
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        QLearningAlgorithm learning = QLearningAlgorithm.getInstance(args, context);
        learning.go(args, context);
    }
}
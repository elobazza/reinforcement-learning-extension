package primitives.setup;

import burlap.QLearningAlgorithm;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nlogo.api.AgentException;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

public class SetupCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
        return SyntaxJ.commandSyntax(new int[] {});
    }
    
    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        System.out.println("ANTES DE CRIAR O LEARNING");
        
        QLearningAlgorithm learning = QLearningAlgorithm.getInstance(args, context);
        try {       
            learning.setup();
        } catch (AgentException ex) {
            Logger.getLogger(SetupCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}

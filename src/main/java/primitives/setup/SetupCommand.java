package primitives.setup;

import burlap.ActorCriticAlgorithm;
import burlap.QLearningAlgorithm;
import burlap.SarsaAlgorithm;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AgentLearning;
import model.Session;
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
        AgentLearning agent = Session.getInstance().getAgent(context.getAgent());

        if(agent.algorithm == 1) {
            QLearningAlgorithm.setInstanceNull();
            QLearningAlgorithm learning = QLearningAlgorithm.getInstance(args, context);
            try {       
                learning.setup();
            } catch (AgentException ex) {
                Logger.getLogger(SetupCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (agent.algorithm == 2) {
            SarsaAlgorithm.setInstanceNull();
            SarsaAlgorithm learning = SarsaAlgorithm.getInstance(args, context);
            try {       
                learning.setup();
            } catch (AgentException ex) {
                Logger.getLogger(SetupCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (agent.algorithm == 3) {
            ActorCriticAlgorithm.setInstanceNull();
            ActorCriticAlgorithm learning = ActorCriticAlgorithm.getInstance(args, context);
            try {       
                learning.setup();
            } catch (AgentException ex) {
                Logger.getLogger(SetupCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            
        }
        
    }    
}

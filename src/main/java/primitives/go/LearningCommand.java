package primitives.go;

import burlap.ActorCriticAlgorithm;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import burlap.QLearningAlgorithm;
import burlap.SarsaAlgorithm;
import model.AgentLearning;
import model.Session;

public class LearningCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
            return SyntaxJ.commandSyntax();
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent = Session.getInstance().getAgent(context.getAgent());

        if(agent.algorithm.equals("qlearning")) {
            QLearningAlgorithm learning = QLearningAlgorithm.getInstance(args, context);
            learning.go(args, context);
        } else if(agent.algorithm.equals("sarsa-lambda")) {
            SarsaAlgorithm learning = SarsaAlgorithm.getInstance(args, context);
            learning.go(args, context);
        } else if(agent.algorithm.equals("actor-critic")) {
            ActorCriticAlgorithm learning = ActorCriticAlgorithm.getInstance(args, context);
            learning.go(args, context);
        }
    }
}
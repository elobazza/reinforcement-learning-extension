package primitives.setup;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;
import model.AgentLearning;
import model.Session;

public class RewardCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
        return SyntaxJ.commandSyntax(new int[] {Syntax.ReporterType()});
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent = Session.getInstance().getAgent(context.getAgent());

        if(agent == null) {
              throw new ExtensionException(
                "You should first define an state definition to the agent. Agent id: " + context.getAgent().id());
        }

        agent.rewardFunc = args[0].getReporter();
    }
}
package primitives.setup;

import model.AgentLearning;
import model.Session;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

public class IsEndEpisodeCommand implements org.nlogo.api.Command {

    public Syntax getSyntax() {
            return SyntaxJ.commandSyntax(new int[] {Syntax.ReporterType(), Syntax.CommandType()});
    }

    public void perform(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        if(agent == null) {
            throw new ExtensionException(
                      "You should first define a state definition to the agent. Agent id: " + context.getAgent().id());
        }

        agent.endEpisode = args[0].getReporter();
    }
}
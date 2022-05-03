package primitives.setup;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;

import org.nlogo.core.SyntaxJ;

import model.ActionSelection;
import model.AgentLearning;
import model.Session;

public class ActionSelectionRandomCommand implements org.nlogo.api.Command {

    public Syntax getSyntax() {
//        return SyntaxJ.commandSyntax(new int[] {});
        return SyntaxJ.commandSyntax(new int[] {Syntax.NumberType()});
    }

    public void perform(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        if(agent == null) {
            throw new ExtensionException(
                "You should first define a state definition to the agent. Agent id: " + context.getAgent().id());
        }

        ActionSelection actionSelection = agent.actionSelection;
        actionSelection.setMethod("random-normal");
        actionSelection.setRoulette(args[0].getDoubleValue());
    }
}
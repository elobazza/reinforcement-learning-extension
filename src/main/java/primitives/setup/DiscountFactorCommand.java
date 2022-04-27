package primitives.setup;

import org.nlogo.api.Argument;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import model.AgentLearning;
import model.Session;

public class DiscountFactorCommand implements org.nlogo.api.Command {
	
    public Syntax getSyntax() {
//		return SyntaxJ.commandSyntax(new int[] {Syntax.NumberType()}, Syntax.NumberType());
        return null;
    }

    @Override
    public void perform(Argument[] args, org.nlogo.api.Context context) throws ExtensionException {
        AgentLearning agent = Session.getInstance().getAgent(context.getAgent());
        if(agent == null) {
            throw new ExtensionException(
            "You should first define a state definition to this agent. Agent id: " + context.getAgent().id());
        }
        agent.setDiscountFactor(args[0].getDoubleValue());
    }
}
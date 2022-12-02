package primitives.setup;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.agent.Turtle;
import org.nlogo.agent.World;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import model.AgentLearning;
import model.Session;
import model.StateDefinition;
import org.nlogo.core.LogoList;

public class StateDefinitionCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
        return SyntaxJ.commandSyntax(new int[] {Syntax.ListType()});
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {				
        AgentLearning a = Session.getInstance().getAgent(context.getAgent());
        Turtle turtle = ((World)context.world()).getTurtle(context.getAgent().id());

        if(a == null) {
            LogoList variablesTemp = args[0].getList();
            List<String> variables = new ArrayList<String>();

            for(int i = 0; i < variablesTemp.size(); i++) {
                String variable = variablesTemp.get(i).toString().toUpperCase();
                if(turtle.ownsVariable(variable)) {
                    variables.add(variable);
                } else {
                    throw new ExtensionException("Breed " + turtle.getBreed().printName() + " doesn't own " + variable);
                }
            }

            StateDefinition stateDef = new StateDefinition();
            stateDef.setVars(variables);

            AgentLearning agent = new AgentLearning();

            agent.stateDef = stateDef;
            agent.agent = context.getAgent();

            Session.getInstance().addAgent(agent);

        } else {
            throw new ExtensionException("State definition for agent " + context.getAgent().id() + " is already defined. \n" +
            "the breed of the agent is: " + turtle.getBreed().printName());
        }
    }
}
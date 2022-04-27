package primitives.go;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import model.AgentLearning;
import model.Session;

public class DecayEpsilonCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
            return SyntaxJ.commandSyntax();
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());

        if(agent == null) {
            throw new ExtensionException("Agent " + context.getAgent().id() + " isn't a learner agent");
        } else {
            if(agent.actionSelection.getTypeOf().equals("rate")) {
                    Double roulette = agent.actionSelection.getRoulette() * agent.actionSelection.getDecreaseRateNumber();
                    agent.actionSelection.setRoulette(roulette);

            } 
            else if (agent.actionSelection.getTypeOf().equals("value")) {
                if(agent.actionSelection.getDecreaseIsNumber()) {
                  agent.actionSelection.setRoulette(agent.actionSelection.getDecreaseRateNumber());
                } 
                else {
                    Double roulette;
                    try {
                        roulette =  (Double) agent.actionSelection.getDecreaseRateReporter().report(context, args);
                    } catch (NullPointerException e){
                        throw new ExtensionException("No Epsilon Decay function for agent " + context.getAgent().id() + " was defined");
                    }
                    agent.actionSelection.setRoulette(roulette);
                }
            } else {
                Double roulette = agent.actionSelection.getRoulette() * agent.actionSelection.getDecreaseRateNumber();
                agent.actionSelection.setRoulette(roulette);
            }
        }
    }
}

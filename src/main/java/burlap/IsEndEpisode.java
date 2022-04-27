package burlap;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;
import model.AgentLearning;
import model.Session;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;

/**
 * @author Eloisa Bazzanella
 */
public class IsEndEpisode implements TerminalFunction {
    
    private Argument[] args;
    private Context context;

    public IsEndEpisode(Argument[] args, Context context) {
        this.args = args;
        this.context = context;
    }

    public IsEndEpisode(Context context) {
        this.context = context;
    }
    
    public boolean isTerminal(State s) {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        Boolean isEndEpisode = (Boolean) agent.endEpisode.report(context, args);

        return isEndEpisode;
    }
}
package burlap;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import java.util.ArrayList;
import java.util.List;
import model.AgentLearning;
import model.Session;
import org.nlogo.api.AnonymousCommand;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;

/**
 * @author Eloisa Bazzanella
 */
public class AgentStateModel implements FullStateModel {

    //TALVEZ DE PROBLEMA NISSO, NÃO SEI SE ATUALIZA
    private Argument[] args;
    private Context    context;
    
    public AgentStateModel(Argument[] args, Context context) {
        this.args = args;
        this.context = context;
    }
    
    @Override
    public List<StateTransitionProb> stateTransitions(State s, Action a) {
        return new ArrayList<StateTransitionProb>();
    }

    @Override
    public State sample(State s, Action a) {
        s = s.copy();
        AgentState state = (AgentState)s;
        
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        String actionExecute = a.actionName();
        
        for(AnonymousCommand action : agent.actions) {
            if(actionExecute.equals(action.toString())){
                action.perform(context, args);
            }
        }
        
        //O STATE É ATUALIZADO AUTOMATICAMENTE? 
        return state;
    }
    
}
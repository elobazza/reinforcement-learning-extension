package burlap;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import java.util.Arrays;
import java.util.List;
import model.AgentLearning;
import model.Session;
import org.nlogo.api.Context;

/**
 * Agent State Class
 * @author Eloisa Bazzanella
 * @since  april, 2022
 */
public class AgentState implements MutableState {
    
    private Context context;
    private final static List<Object> keys = Arrays.<Object>asList();

    public AgentState(Context context) {
        this.context = context;
    }
    
    @Override
    public MutableState set(Object variableKey, Object value) {
        AgentLearning a =  Session.getInstance().getAgent(context.getAgent());
        
        for(String state : a.stateDef.getVars()) {
            if(variableKey.equals(state)) {
               keys.add(StateUtilities.stringOrNumber(value).intValue());
            }
        }
        
        return this;
    }

    @Override
    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public Object get(Object variableKey) {
        AgentLearning a =  Session.getInstance().getAgent(context.getAgent());
        
        for(String state : a.stateDef.getVars()) {
            if(variableKey.equals(state)) {
               return state;
            }
        }
        return null;
    }

    @Override
    public State copy() {
        return new AgentState(context);
    }
    
}

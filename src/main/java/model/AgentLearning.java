package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nlogo.agent.Turtle;
import org.nlogo.agent.World;
import org.nlogo.api.AgentException;
import org.nlogo.api.AnonymousCommand;
import org.nlogo.api.AnonymousReporter;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;

/**
 * Agent Learning Class
 * @author Eloisa Bazzanella
 * @since, march, 2022
 */
public class AgentLearning {
    	
    public StateDefinition stateDef       = null;
    public List<AnonymousCommand> actions = new ArrayList();
    
    public AnonymousReporter rewardFunc    = null;
    public AnonymousReporter endEpisode    = null;
    public ActionSelection actionSelection = new ActionSelection();

    public Double learningRate   = -1.00;
    public Double discountFactor = -1.00;
    public int episode           = 0; 
    
    public org.nlogo.api.Agent agent = null;
    
    public void setDiscountFactor(Double f) throws ExtensionException {
        if(f > 1 || f < 0) {
          throw new ExtensionException("Discount factor must be a value between 0 and 1");
        }
        discountFactor = f;
    }
    
    public void setLearningRate(Double r) throws ExtensionException {
        if(r > 1 || r < 0) {
          throw new ExtensionException("Learning rate must be a value between 0 and 1");
        }
        learningRate = r;
    }
    
    public void addAction(AnonymousCommand a) {
    	actions.add(a);
    }
    
    public void setEpisode() {
    	this.episode += 1;
    }
    
    public Map<String, Double> getState(Context context) throws AgentException {
        Map<String, Double> state = new HashMap<String, Double>();

        System.out.println("OI");
        for(String v : stateDef.getVars()) {	
            System.out.println("agent world "+ agent.world());
            System.out.println("agent id "+ agent.id());
            Turtle turtle = ((World) agent.world()).getTurtle(agent.id());
            
            if(turtle.getVariable(v) != null) {
                state.put(v, (Double) turtle.getVariable(v));
            }
        }

        return state;
    }
}
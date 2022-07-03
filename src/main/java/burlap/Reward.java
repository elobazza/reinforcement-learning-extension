package burlap;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;
import model.AgentLearning;
import model.Session;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;

/**
 * Reward Class
 * @author Eloísa Bazzanella
 * @since  april, 2022
 */
public class Reward implements RewardFunction {
    
    //VER SE PODE SER DESSA FORMA
    private Argument[] args;
    private Context context;

    public Reward(Argument[] args, Context context) {
        this.args = args;
        this.context = context;
    }
    
    public double reward(State s, Action a, State sprime) {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        Double reward = (Double) agent.rewardFunc.report(context, args);
        
        System.out.println("REWARD: " + reward);
        return reward;
    }
}
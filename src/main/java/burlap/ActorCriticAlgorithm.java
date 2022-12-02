
package burlap;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.actorcritic.Actor;
import burlap.behavior.singleagent.learning.actorcritic.ActorCritic;
import burlap.behavior.singleagent.learning.actorcritic.actor.BoltzmannActor;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.statehashing.HashableState;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import model.AgentLearning;
import model.Session;
import org.nlogo.api.AgentException;
import org.nlogo.api.AnonymousCommand;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;

/**
 * ActorCritic Algorithm Class
 * @author Eloisa Bazzanella
 * @since  november, 2022
 */
public class ActorCriticAlgorithm implements DomainGenerator {
    
    private Argument[]           args;
    private Context              context;
    private ActorCritic          agentLearning;
    private CriticImplementation critic;
    private EpsilonGreedy        epsilon;
    private SimulatedEnvironment env;
    private State initialState;
    private static ActorCriticAlgorithm instance = null;

    public ActorCriticAlgorithm(Argument[] args, Context context) {
        this.args = args;
        this.context = context;
        this.epsilon = null;
    }
    
    public static void setInstanceNull() {
        instance = null;
    }
    
    public static ActorCriticAlgorithm getInstance(Argument[] args, Context context) {
        if (instance == null) {
            instance = new ActorCriticAlgorithm(args, context);
        }
        return instance;
    }
    
    public void setup() throws AgentException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        
        ActorCriticAlgorithm gen = new ActorCriticAlgorithm(args, context);
        
        SADomain domain = new SADomain();
        
        for(AnonymousCommand action : agent.actions) {
            domain.addActionType(new UniversalActionType(action.toString()));
        }
        
        AgentStateModel  stateModel   = new AgentStateModel(args, context);
        RewardFunction   reward       = new Reward(args, context);
        TerminalFunction isEndEpisode = new IsEndEpisode(args, context);

        domain.setModel(new FactoredModel(stateModel, reward, isEndEpisode));
        
        State initialState = new AgentState(context);
        
        env = new SimulatedEnvironment(domain, initialState);
       
        Actor actor = new BoltzmannActor(domain, new SimpleHashableStateFactory(), agent.learningRate);
        critic = new CriticImplementation(agent.discountFactor, new SimpleHashableStateFactory(), agent.learningRate, 0, agent.lambda);
        
        stateModel.setCritic(critic);
        
        agentLearning = new ActorCritic(actor, critic);
    }
    
    public void go(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
       
        System.out.println("EPISODIO: " + agent.episode); 
        
        Episode e = agentLearning.runLearningEpisode(env, -1);     
        agent.setEpisode();
        env.resetEnvironment(); 
        System.out.println(e.actionString());
        
        for(HashableState v : critic.vIndex.keySet()) {
            String s = "";
            for(Object a : v.s().variableKeys()) {
                s += a + ": " + v.s().get(a) + ", ";
            }
            System.out.println(s + ": " + critic.getV(v).v);
        }
        System.out.println("-------------------------------");
    }
    
    @Override
    public Domain generateDomain() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
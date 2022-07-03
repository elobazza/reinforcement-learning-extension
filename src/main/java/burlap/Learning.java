package burlap;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import model.AgentLearning;
import model.Session;
import org.nlogo.api.AgentException;
import org.nlogo.api.AnonymousCommand;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import primitives.go.DecayEpsilonCommand;

/**
 * Learning Algorithms Class
 * @author Eloisa Bazzanella
 * @since  april, 2022
 */
public class Learning implements DomainGenerator {
    
    private Argument[]           args;
    private Context              context;
    private QLearning            agentLearning;
    private EpsilonGreedy        epsilon;
    private SimulatedEnvironment env;
    private static Learning      instance = null;

    public Learning(Argument[] args, Context context) {
        this.args = args;
        this.context = context;
        this.epsilon = null;
    }
    
    public static Learning getInstance(Argument[] args, Context context) {
        if (instance == null) {
            instance = new Learning(args, context);
        }
        return instance;
    }
    
    public void setup() throws AgentException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        
        Learning gen = new Learning(args, context);
        
        SADomain domain = new SADomain();
        
        for(AnonymousCommand action : agent.actions) {
            domain.addActionType(new UniversalActionType(action.toString()));
        }
        
        AgentStateModel  stateModel   = new AgentStateModel(args, context);
        RewardFunction   reward       = new Reward(args, context);
        TerminalFunction isEndEpisode = new IsEndEpisode(context);

        
        domain.setModel(new FactoredModel(stateModel, reward, isEndEpisode));
        
        
        State initialState = new AgentState(context);
        
        env = new SimulatedEnvironment(domain, initialState);
        
        agentLearning = new QLearning(domain, agent.discountFactor, 
                new SimpleHashableStateFactory(), 0, agent.learningRate);
        
        stateModel.setQLearning(agentLearning);
        
        if(agent.actionSelection.method.equals("e-greedy")) {
            epsilon = new EpsilonGreedy(agentLearning, agent.actionSelection.roulette);
            agentLearning.setLearningPolicy(epsilon);
        }
    }
    
    public void go(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());  
        //EXECUTA UMA ÚNICA AÇÃO
        agentLearning.runLearningEpisode(env, 1);            
        
        //DECAIMENTO DO EPSILON
        
        //SE ALCANÇOU ESTADO TERMINAL, FINALIZA EPISODIO
        if(env.isInTerminalState()) {
//            agentLearning.writeQTable("qtable.txt");;
            
            if(agent.actionSelection.method.equals("e-greedy")) { 
                new DecayEpsilonCommand().perform(args, context);
                epsilon.setEpsilon(agent.actionSelection.roulette); 
                System.out.println("NEW EPSILON:" + epsilon.getEpsilon());
            }

            System.out.println("ALCANÇOU ESTADO TERMINAL");
            
            //CONTADOR DE EPISODIOS
            agent.setEpisode();
            env.resetEnvironment();
            
            
            System.out.println("-------------------------------");
            System.out.println("EPISODIO: " + agent.episode);
        }
    }
    

    @Override
    public Domain generateDomain() {
        throw new UnsupportedOperationException("Not supported yet."); }
}
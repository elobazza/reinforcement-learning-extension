/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package burlap;

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.TerminalFunction;
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
 * SARSA Algorithms Class
 * @author Eloisa Bazzanella
 * @since  september, 2022
 */
public class SarsaAlgorithm implements DomainGenerator {
    
    private Argument[]           args;
    private Context              context;
    private SarsaLam            agentLearning;
    private EpsilonGreedy        epsilon;
    private SimulatedEnvironment env;
    private State initialState;
    private static SarsaAlgorithm instance = null;

    public SarsaAlgorithm(Argument[] args, Context context) {
        this.args = args;
        this.context = context;
        this.epsilon = null;
    }
    
    public static void setInstanceNull() {
        instance = null;
    }
    
    public static SarsaAlgorithm getInstance(Argument[] args, Context context) {
        if (instance == null) {
            instance = new SarsaAlgorithm(args, context);
        }
        return instance;
    }
    
    public void setup() throws AgentException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
        
        SarsaAlgorithm gen = new SarsaAlgorithm(args, context);
        
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
       
        agentLearning = new SarsaLam(domain, agent.discountFactor, 
                new SimpleHashableStateFactory(), 0, agent.learningRate, agent.lambda);
        
        stateModel.setSarsa(agentLearning);
        //STATEMODEL
        
        if(agent.actionSelection.method.equals("e-greedy")) {
            epsilon = new EpsilonGreedy(agentLearning, agent.actionSelection.roulette);
            agentLearning.setLearningPolicy(epsilon);
        }
    }
    
    public void go(Argument[] args, Context context) throws ExtensionException {
        AgentLearning agent =  Session.getInstance().getAgent(context.getAgent());
       
        agentLearning.runLearningEpisode(env, -1);     

        if(agent.actionSelection.method.equals("e-greedy")) { 
            new DecayEpsilonCommand().perform(args, context);
            epsilon.setEpsilon(agent.actionSelection.roulette); 
            System.out.println("NEW EPSILON:" + epsilon.getEpsilon());
        }
        
        agent.setEpisode();
        env.resetEnvironment(); 
        System.out.println("-------------------------------");
        System.out.println("EPISODIO: " + agent.episode);  
        //agentLearning.writeQTable("qtable.txt");
    }
    
    @Override
    public Domain generateDomain() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}

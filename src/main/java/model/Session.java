package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eloisa Bazzanella
 */
public class Session {
    
    private static Session INSTANCE;
    private List<AgentLearning> agents = new ArrayList();

    public static Session getInstance(){ 
        if (INSTANCE == null) {
            INSTANCE = new Session();
        }
        return INSTANCE;
    }

    public void addAgent(AgentLearning agent) {
        agents.add(agent);
    }

    public AgentLearning getAgent(org.nlogo.api.Agent agent)  { 
        for(AgentLearning a : agents) {
            if(a.agent.id() == agent.id()) {
                return a;
            }
        }
        return null;
    }
}

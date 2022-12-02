package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Session Class Singleton
 * @author Eloisa Bazzanella
 * @since  march, 2022
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
        agents = new ArrayList<>();
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

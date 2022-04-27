package model;

import java.util.List;
import org.nlogo.api.AnonymousReporter;

/**
 * State Definition Class
 * @author Eloisa Bazzanella
 * @since  march, 2022
 */
public class StateDefinition {
    
    private List<String> vars = null;
    private AnonymousReporter reporterAux = null;
    
    public void setVars(List<String> vars) {
        this.vars = vars;
    }

    public void setReporterAux(AnonymousReporter reporterAux) {
        this.reporterAux = reporterAux;
    }

    public List<String> getVars() {
        return vars;
    }

    public AnonymousReporter getReporterAux() {
        return reporterAux;
    }
}

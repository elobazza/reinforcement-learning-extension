package burlap;

import burlap.mdp.core.action.UniversalActionType;
import org.nlogo.api.AnonymousCommand;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;

/**
 * @author Eloísa Bazzanella
 */
//public class ActionAdapter extends UniversalActionType {  
public class ActionAdapter {  
    
    private AnonymousCommand actionCommand;
    
    public ActionAdapter(AnonymousCommand actionCommand) {
        this.actionCommand = actionCommand;
        //super.typeName = actionCommand.toString();
    }
    
    public void execute(Argument[] args, Context context) {
        actionCommand.perform(context, args);
    }
}
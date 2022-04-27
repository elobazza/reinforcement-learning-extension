package primitives.setup;

import burlap.Learning;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

/**
 *
 * @author Eloísa Bazzanella
 */
public class SetupCommand implements org.nlogo.api.Command {

    @Override
    public Syntax getSyntax() {
        return SyntaxJ.commandSyntax(new int[] {});
    }
    
    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        Learning learning = Learning.getInstance(args, context);
        
        learning.setup();       
    }    
}

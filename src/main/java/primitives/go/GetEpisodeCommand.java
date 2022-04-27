package primitives.go;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import model.Session;

public class GetEpisodeCommand implements org.nlogo.api.Reporter {

    @Override
    public Syntax getSyntax() {
        return SyntaxJ.reporterSyntax(new int[] {}, 0);
    }

    @Override
    public Object report(Argument[] args, Context context) throws ExtensionException {
        return Session.getInstance().getAgent(context.getAgent()).episode;
    }
}

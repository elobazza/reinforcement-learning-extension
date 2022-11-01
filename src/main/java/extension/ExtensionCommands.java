package extension;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.PrimitiveManager;
import primitives.go.DecayEpsilonCommand;
import primitives.go.GetEpisodeCommand;
import primitives.go.LearningCommand;
import primitives.setup.ActionSelectionCommand;
import primitives.setup.ActionSelectionEGreedyCommand;
import primitives.setup.ActionSelectionRandomCommand;
import primitives.setup.ActionsCommand;
import primitives.setup.DiscountFactorCommand;
import primitives.setup.IsEndEpisodeCommand;
import primitives.setup.LambdaCommand;
import primitives.setup.LearningRateCommand;
import primitives.setup.RewardCommand;
import primitives.setup.DefineAlgorithmCommand;
import primitives.setup.SetupCommand;
import primitives.setup.StateDefinitionCommand;
import primitives.setup.StateDefinitionExtraCommand;

/**
 * Extension Commands Class
 * @author Eloísa Bazzanella
 * @since  march, 2022
 */
public class ExtensionCommands extends DefaultClassManager {
    public void load(PrimitiveManager primitiveManager) {
        
        //COMANDOS SETUP
        primitiveManager.addPrimitive("learning-rate", new LearningRateCommand());
        primitiveManager.addPrimitive("discount-factor", new DiscountFactorCommand());
        primitiveManager.addPrimitive("lambda", new LambdaCommand());
        primitiveManager.addPrimitive("action-selection", new ActionSelectionCommand());
        primitiveManager.addPrimitive("action-selection-egreedy", new ActionSelectionEGreedyCommand());
        primitiveManager.addPrimitive("action-selection-random", new ActionSelectionRandomCommand());
        primitiveManager.addPrimitive("reward", new RewardCommand());
        primitiveManager.addPrimitive("end-episode", new IsEndEpisodeCommand());
        primitiveManager.addPrimitive("actions", new ActionsCommand());
        primitiveManager.addPrimitive("state-def", new StateDefinitionCommand());
        primitiveManager.addPrimitive("state-def-extra", new StateDefinitionExtraCommand());
        primitiveManager.addPrimitive("define-algorithm", new DefineAlgorithmCommand());
        primitiveManager.addPrimitive("setup", new SetupCommand());
        
        //COMANDOS GO
        primitiveManager.addPrimitive("learning", new LearningCommand());
        primitiveManager.addPrimitive("decay-epsilon", new DecayEpsilonCommand());
        primitiveManager.addPrimitive("get-episode", new GetEpisodeCommand());
    }
}

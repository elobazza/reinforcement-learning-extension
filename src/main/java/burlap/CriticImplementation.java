package burlap;

import burlap.behavior.learningrate.ConstantLR;
import burlap.behavior.learningrate.LearningRate;
import burlap.behavior.singleagent.MDPSolver;
import burlap.behavior.singleagent.learning.actorcritic.Critic;
import burlap.behavior.singleagent.options.EnvironmentOptionOutcome;
import burlap.behavior.singleagent.options.Option;
import burlap.behavior.valuefunction.ConstantValueFunction;
import burlap.behavior.valuefunction.ValueFunction;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.statehashing.HashableState;
import burlap.statehashing.HashableStateFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
* @author James MacGlashan
*/
public class CriticImplementation extends MDPSolver implements Critic, ValueFunction {

    protected LearningRate							learningRate;
    protected ValueFunction							vInitFunction;
    protected double								lambda;
    public Map<HashableState, VValue>			vIndex;
    protected LinkedList<StateEligibilityTrace>		traces;
    protected int													totalNumberOfSteps = 0;

    public CriticImplementation(double gamma, HashableStateFactory hashingFactory, double learningRate, double vinit, double lambda) {

        this.solverInit(null, gamma, hashingFactory);

        this.learningRate = new ConstantLR(learningRate);
        vInitFunction = new ConstantValueFunction(vinit);
        this.lambda = lambda;


        vIndex = new HashMap<HashableState, VValue>();
    }

    public CriticImplementation(double gamma, HashableStateFactory hashingFactory, double learningRate, ValueFunction vinit, double lambda) {
        this.gamma = gamma;
        this.hashingFactory = hashingFactory;

        this.learningRate = new ConstantLR(learningRate);
        vInitFunction = vinit;
        this.lambda = lambda;


        vIndex = new HashMap<HashableState, VValue>();
    }

    @Override
    public void startEpisode(State s) {
        this.traces = new LinkedList<CriticImplementation.StateEligibilityTrace>();
    }

    @Override
    public void endEpisode() {
        this.traces.clear();
    }

    public void setLearningRate(LearningRate lr){
        this.learningRate = lr;
    }

    @Override
    public double critique(EnvironmentOutcome eo) {

        HashableState sh = hashingFactory.hashState(eo.o);
        HashableState shprime = hashingFactory.hashState(eo.op);

        double r = eo.r;
        double discount = gamma;
        if(eo.a instanceof Option){
            discount = Math.pow(gamma, ((EnvironmentOptionOutcome)eo).numSteps());
        }

        VValue vs = this.getV(sh);
        double nextV = 0.;
        if(!eo.terminated){
            nextV = this.getV(shprime).v;
        }

        double delta = r + discount*nextV - vs.v;

        //update all traces
        boolean foundTrace = false;
        for(StateEligibilityTrace t : traces){

            if(t.sh.equals(sh)){
                foundTrace = true;
                t.eligibility = 1.;
            }

            double learningRate = this.learningRate.pollLearningRate(this.totalNumberOfSteps, t.sh.s(), null);
            t.v.v = t.v.v + learningRate * delta * t.eligibility;
            t.eligibility = t.eligibility * lambda * discount;
        }

        if(!foundTrace){
            //then add it
            double learningRate = this.learningRate.pollLearningRate(this.totalNumberOfSteps, sh.s(), null);
            vs.v = vs.v + learningRate * delta;
            StateEligibilityTrace t = new StateEligibilityTrace(sh, discount*this.lambda, vs);

            traces.add(t);
        }



        this.totalNumberOfSteps++;

        return delta;
    }


    @Override
    public double value(State s) {
        return this.getV(this.hashingFactory.hashState(s)).v;
    }

    @Override
    public void resetSolver() {
        this.reset();
    }

    @Override
    public void reset(){
        this.vIndex.clear();
        this.traces.clear();
        this.learningRate.resetDecay();
    }

    protected VValue getV(HashableState sh){
        VValue v = this.vIndex.get(sh);
        if(v == null){
            v = new VValue(this.vInitFunction.value(sh.s()));
            this.vIndex.put(sh, v);
        }
        return v;
    }


    /**
     * @author James MacGlashan
     */
    class VValue{

        public double v;

        public VValue(double v){
            this.v = v;
        }

    }

    /**
     * @author James MacGlashan
     */
    public static class StateEligibilityTrace{

        public double eligibility;

        public HashableState sh;

        public VValue v;

        public StateEligibilityTrace(HashableState sh, double eligibility, VValue v){
            this.sh = sh;
            this.eligibility = eligibility;
            this.v = v;
        }
    }

}
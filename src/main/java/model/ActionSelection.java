package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;	
import java.util.Map;
import java.util.Random;

import org.nlogo.api.AnonymousReporter;
import org.nlogo.api.ExtensionException;

/**
 * @author Eloisa Bazzanella
 */
public class ActionSelection {
    
    private Random randomGen;
    private String method = "";
    private String typeOf = "";
    private Double roulette = 0.00;
    private Double decreaseRateNumber = 0.00;
    private AnonymousReporter decreaseRateReporter = null;
    private Boolean decreaseIsNumber = false;

    public ActionSelection() {}

    public ActionSelection(String m, Double e, Double d) throws ExtensionException {
            setMethod(m);
            setRoulette(e);
            setDecreaseRateNumber(d);
    }

    public void setMethod(String m) throws ExtensionException {
        if(m.equalsIgnoreCase("e-greedy") || m.equalsIgnoreCase("random-normal"))
            method = m;
        else
            throw new ExtensionException("Action selection method must be e-greedy or random-normal, " + m +
                " is not an valid action selection method");
    }

    public void setTypeOf(String t) throws ExtensionException {
        if(t.equalsIgnoreCase("rate") || t.equalsIgnoreCase("value"))
             typeOf = t;
        else
             throw new ExtensionException("Action selection type must be rate or value, " + t +
               " is not an valid action selection type");
    }

    public void setRoulette(Double r) throws ExtensionException {
        if(roulette > 1 || roulette < 0)
              throw new ExtensionException("Epsilon must be a value between 0 and 1");
            else
                roulette = r;
    }

    public void setDecreaseRateNumber(Double r) throws ExtensionException {
        if(decreaseRateNumber > 1 || decreaseRateNumber < 0)
              throw new ExtensionException("Decrease rate must be a value between 0 and 1");
        else
            decreaseRateNumber = r;
    }

    public void setDecreaseRateReporter(AnonymousReporter d) throws ExtensionException {
        if(d == null)
          throw new ExtensionException("Decrease rate must be a reporter");
        else
          decreaseRateReporter = d;
    }

    public void setDecreaseIsNumber(Boolean r){
        this.decreaseIsNumber = r;
    }

    public void setRandomGen(Random randomGen) {
        this.randomGen = randomGen;
    }

    public Double getRoulette() {
        return this.roulette;
    }

    public Double getDecreaseRateNumber() {
        return this.decreaseRateNumber;
    }

    public Random getRandomGen() {
        return this.randomGen;
    }

    public String getMethod() {
        return this.method;
    }

    public String getTypeOf() {
        return this.typeOf;
    }

    public Boolean getDecreaseIsNumber() {
        return this.decreaseIsNumber;
    }

    public AnonymousReporter getDecreaseRateReporter() {
        return this.decreaseRateReporter;
    }

    public int getAction(LinkedHashMap<String, Double> qlist) {
        Random randomGen = new Random();
        Double random = randomGen.nextDouble();

        int actionPos = 0;
        if(random <= roulette) {
          actionPos = randomGen.nextInt(qlist.size());

        } else {
            // PEGAR MAIOR VALOR DA LISTA
            Map.Entry<String, Double> maxEntry = null;
                for (Map.Entry<String, Double> entry : qlist.entrySet()) {
                    if (maxEntry == null ) {
                        maxEntry = entry;
                    } else if (entry.getValue() > maxEntry.getValue()) {
                            maxEntry = entry;
                    }
                }

            // VER QUANTOS IGUAIS TEM
            List<Integer> maxList = new ArrayList<Integer>();

            int cont = 0;
                for (Map.Entry<String, Double> entry : qlist.entrySet()) {
                    if(entry.getValue() == maxEntry.getValue()) {
                            maxEntry = entry;
                            maxList.add(cont);
                    }
                    cont++;
                }
            //SORTEAR
            actionPos = maxList.get(randomGen.nextInt(maxList.size()));	      
        }
        return actionPos;
    }
}
package glacialExpedition.models.explorers;

import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.suitcases.Carton;
import glacialExpedition.models.suitcases.Suitcase;

public abstract class BaseExplorer implements Explorer{
    private String name;
    private double energy;
    private Suitcase suitcase;

    public BaseExplorer(String name, double energy) {
        this.setName(name);
        this.setEnergy(energy);
        this.suitcase=new Carton();
    }

    public void setName(String name) {
        if(name == null || name.trim().equals("")) {
            throw new NullPointerException(ExceptionMessages.EXPLORER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergy(double energy) {
        if(energy < 0) {
            throw new IllegalArgumentException(ExceptionMessages.EXPLORER_ENERGY_LESS_THAN_ZERO);
        }
        this.energy = energy;
    }

    public void setSuitcase(Suitcase suitcase) {
        this.suitcase = suitcase;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public boolean canSearch() {
        return this.energy>0;
    }

    @Override
    public Suitcase getSuitcase() {
        return suitcase;
    }

    @Override
    public void search() {
        this.setEnergy(this.getEnergy()-10);
        if(this.energy<0)
            setEnergy(0);

        //dali???


    }
}

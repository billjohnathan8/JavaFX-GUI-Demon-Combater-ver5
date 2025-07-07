package guidemon.model.stats;

import guidemon.model.entry.Entry; 
import guidemon.model.combat.DamageType; 

//TODO: Entry and Definition Class (?)
//TODO: Not a typical entry, this is a highly technical entry that should be displayed via tooltips in StatDisplay 
public class DamageMultiplier extends Entry {
    private DamageType damageType; 
    private double multiplier; 
    private int rank; 

    public DamageMultiplier(String name, DamageType damagetype, double multiplier, int rank) {
        super(name);
        this.damageType = damageType;
        this.multiplier = multiplier;
        this.rank = rank; 
    }
    
    //getters and setters: 
    public DamageType getDamageType() {
        return this.damageType; 
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType; 
    }
    
    public double getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public int getRank() {
        return this.rank; 
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    //Other Core Methods:
    public void modifyMultiplier(double amount) {
        this.multiplier += amount; 
    }

    public void modifyRank(int amount) {
        this.rank += rank; 
    }
}

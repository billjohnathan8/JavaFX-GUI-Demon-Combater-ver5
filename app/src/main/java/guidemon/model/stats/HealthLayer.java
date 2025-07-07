package guidemon.model.stats;

import java.util.List; 
import java.util.ArrayList; 
import java.util.Map; 
import java.util.HashMap; 

import guidemon.model.entry.Entry; 
import guidemon.model.combat.DamageType; 
import guidemon.model.stats.immutable.instances.ResourceEntry; 

//TODO: Entry and Definition Class (?)
//TODO: Not a typical entry, this is a highly technical entry that should be displayed via tooltips in StatDisplay 

/**
 *  Used to store all details related to how to take incoming damage to different layers of health in the correct order of precedence
 */
public class HealthLayer extends Entry {
    private ResourceEntry healthLayerResource; 
    private boolean isIrrecoverable;                        //Will this HealthLayer be removed permanently if it is brought to <= 0 in resource value.  
    private int impedingRank;                               //TODO: Will this HealthLayer stop excess damage from flowing through? DamageAmount will determine whether the impeding rank actually stops the excess damage from flowing through. 

    private Map<DamageType, Integer> damageReduction;       //Step #0: Unaffected by ArmourPenetration, comes from effects (e.g. Innate Damage Reduction). key: DamageType, value: Amount (no concept of rank)
    private Map<DamageType, Integer> damageImmunities;      //Step #1.1: key: DamageType, value: Rank
    private Map<DamageType, Integer> damageBanes;           //Step #1.2: key: DamageType, value: Rank
    private List<DamageMultiplier> damageMultipliers;       //Step #2: contains the actual Damage Resistances or Weaknesses
    
    //TODO: For Effects: 
    private Map<String, Integer> statusEffectResistances;   //key:StatusEffectName, value: Rank
    private Map<String, Integer> statusEffectWeaknesses;    //key:StatusEffectName, value: Rank

    public HealthLayer(String name, boolean isIrrecoverable) {
        super(name);
        this.isIrrecoverable = false;                  //Default is false. I.e, the HealthLayer will persist if it is brought to 0. 
        this.damageReduction = new HashMap<>();        //Default has empty values.
        this.damageImmunities = new HashMap<>();
        this.damageBanes = new HashMap<>();
        this.damageMultipliers = new ArrayList<>(); 

    }

    public HealthLayer(String name) {
        this(name, false); 
    }

    //getters and setters:
    public ResourceEntry getResource() {
        return this.healthLayerResource; 
    }

    public void setResource(ResourceEntry healthLayerResource) {
        this.healthLayerResource = healthLayerResource; 
    }

    public boolean getIsIrrecoverable() {
        return this.isIrrecoverable; 
    }

    public void setIsIrrecoverable(boolean isIrrecoverable) {
        this.isIrrecoverable = isIrrecoverable; 
    }

    public int getImpedingRank() {
        return this.impedingRank; 
    }

    public void setImpedingRank(int impedingRank) {
        this.impedingRank = impedingRank; 
    }

    public Map<DamageType, Integer> getDamageReduction() {
        return this.damageReduction; 
    } 

    public void setDamageReduction(Map<DamageType, Integer> damageReduction) {
        this.damageReduction = damageReduction; 
    }

    public Map<DamageType, Integer> getDamageImmunities() {
        return this.damageImmunities; 
    }

    public void setDamageImmunities(Map<DamageType, Integer> damageImmunities) {
        this.damageImmunities = damageImmunities; 
    }

    public Map<DamageType, Integer> getDamageBanes() {
        return this.damageBanes; 
    }

    public void setDamageBanes(Map<DamageType, Integer> damageBanes) {
        this.damageBanes = damageBanes; 
    }    

    public List<DamageMultiplier> getDamageMultipliers() {
        return this.damageMultipliers; 
    }

    public void setDamageMultipliers(List<DamageMultiplier> damageMultipliers) {
        this.damageMultipliers = damageMultipliers; 
    }

    //Other Core Methods: 
    public void modifyImpedingRank(int amount) {
        this.impedingRank += amount; 
    }
}
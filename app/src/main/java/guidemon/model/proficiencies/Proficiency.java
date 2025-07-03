package guidemon.model.proficiencies;

import guidemon.model.entry.Entry;

//TODO: ProficiencyEntry vs ProficiencyDefinition (?)

//more general
public class Proficiency extends Entry {
    private int rank;

    public Proficiency(String name) {
        super(name);
    }
    
    //getters and setters: 
    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * used to increase or decrease rank based on certain bonuses
     */
    public void modifyRank(int amount) {
        this.rank += amount; 
    }
}

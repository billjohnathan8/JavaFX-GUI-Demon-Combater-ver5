package guidemon.model.levels;

import java.util.Map; 
import java.util.List; 

import guidemon.model.entry.Entry;
import guidemon.model.ability.Ability;

//TODO: LevelDefinition and LevelEntry 

//TODO: to be implemented
public class Level extends Entry {
    private LevelType type; 

    //Level is not a resource 
    private int levelCap;         //maxLVL
    private int currentLevel;     //curLVL

    //TODO: Levelling and Progression 
    private Map<Integer, List<Ability>> abilityProgression; 

    public Level(String name, int levelCap, int currentLevel) {
        super(name); 
        this.levelCap = levelCap; 
        this.currentLevel = currentLevel; 
    }
}
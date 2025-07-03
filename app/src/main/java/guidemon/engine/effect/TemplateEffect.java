package guidemon.engine.effect;

import java.util.List; 

import guidemon.model.entry.Entry;

//TODO: EffectEntry or EffectDefinition (?)

/*
 * All effects would have these fields at least. 
 * 
 * All effects inherit from this class 
 */
public abstract class TemplateEffect extends Entry implements Effect {
    private List<String> tags; 

    public TemplateEffect(String name, List<String> tags) {
        super(name); 
        this.tags = tags; 
    }

    /*
     * return to parse effect tags 
     */
    public List<String> getTags() {
        return this.tags; 
    }    
}
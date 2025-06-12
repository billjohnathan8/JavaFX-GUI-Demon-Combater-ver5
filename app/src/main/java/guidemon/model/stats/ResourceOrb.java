package guidemon.model.stats;

import java.util.*;

import guidemon.model.Effect; 

public class ResourceOrb extends Resource{
    private ResourceOrbState state;
    private List<Effect> effects;  

    public ResourceOrb(String name, int maximumValue) { 
        super(name, maximumValue); 

        this.state = ResourceOrbState.FREE; 
        this.effects = new ArrayList<>();
        
    }

    public ResourceOrb(String name, int currentValue, int maximumValue) { 
        super(name, currentValue, maximumValue); 

        this.state = ResourceOrbState.FREE; 
        this.effects = new ArrayList<>();
    }

    public ResourceOrbState getState() { 
        return this.state; 
    }

    public List<Effect> getEffects() { 
        return this.effects; 
    }

    public void setState(ResourceOrbState state) { 
        this.state = state; 
    }
}

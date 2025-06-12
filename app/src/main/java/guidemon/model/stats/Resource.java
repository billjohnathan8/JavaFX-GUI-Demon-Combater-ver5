package guidemon.model.stats;

public class Resource {
    private String name;
    private int maximumValue;
    private int currentValue;
    
    public Resource(String name, int maximumValue) { 
        this.name = name; 
        this.maximumValue = maximumValue;
        this.currentValue = maximumValue; 
    }

    public Resource(String name, int currentValue, int maximumValue) { 
        this.name = name; 
        this.currentValue = currentValue;
        this.maximumValue = maximumValue; 
    }    

    public String getName() { 
        return this.name; 
    }

    public int getCur(){ 
        return this.currentValue; 
    }

    public int getMax() { 
        return this.maximumValue; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public void setCur(int currentValue) { 
        this.currentValue = currentValue; 
    }

    public void setMax(int maximumValue) { 
        this.maximumValue = maximumValue; 
    }

    public void modifyCur(int modificationAmount) { 
        this.currentValue += modificationAmount;
    }

    public void modifyMax(int modificationAmount) { 
        this.maximumValue += modificationAmount;
    }
}

package guidemon.model.stats;

public class AbilityScore {
    private String name; 
    private int score; 
    private int saveScore;

    private int scoreModifier;
    private int saveModifier; 

    public AbilityScore(String name, int score) { 
        this.name = name; 
        this.score = score; 
        this.saveScore = score; 
        this.scoreModifier = calculateModifier(score); 
        this.saveModifier = calculateModifier(saveScore); 
    }

    public String getName() { 
        return this.name; 
    }

    public int getScore() { 
        return this.score; 
    }

    public int getModifier() { 
        return this.scoreModifier; 
    }

    public int getSaveScore() { 
        return this.saveScore; 
    }

    public int getSaveModifier() { 
        return this.saveModifier; 
    }

    public void setScore(int score) { 
        this.score = score; 
    }

    public void setSaveScore(int saveScore) { 
        this.saveScore = saveScore; 
    }

    public void setModifier(int scoreModifier) { 
        this.scoreModifier = scoreModifier;
    }

    public void setSaveModifier(int saveModifier) { 
        this.saveModifier = saveModifier; 
    }

    public void modifyScore(int modificationAmount) { 
        this.score += modificationAmount; 
    }

    public void modifySaveScore(int modificationAmount) { 
        this.saveScore += modificationAmount; 
    }

    public void modifyModifier(int modificationAmount) { 
        this.scoreModifier += modificationAmount; 
    }

    public void modifySaveModifier(int modificationAmount) { 
        this.saveModifier += modificationAmount; 
    }

    private int calculateModifier(int scoreValue) { 
        int result = scoreValue; 
        result -= 10;
        result /= 2; 
        result = (int) Math.floor(result); 

        return result; 
    }
}
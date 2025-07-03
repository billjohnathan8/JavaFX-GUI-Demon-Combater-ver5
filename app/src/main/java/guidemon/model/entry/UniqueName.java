package guidemon.model.entry;

public class UniqueName {
    private String baseName; 
    private Timeline timelineOfOrigin;
    private boolean isVariant;  

    public UniqueName(String baseName, Timeline timelineOfOrigin, boolean isVariant) {
        this.baseName = baseName; 
        this.timelineOfOrigin = timelineOfOrigin; 
        this.isVariant = isVariant; 
    }

    //getters: 
    public String getBaseName() {
        return baseName; 
    }

    public Timeline getTimelineOfOrigin() {
        return timelineOfOrigin; 
    }

    public boolean getIsVariant() {
        return isVariant; 
    }

    public String getUniqueName() {
        //StringBuilder used for efficiency 
        StringBuilder output = new StringBuilder(baseName);
        StringBuilder sb = new StringBuilder("");
        
        sb.append("( ");
        sb.append(timelineOfOrigin);

        if(isVariant) {
            sb.append(" , Variant");
        }

        sb.append(" )");

        output.append(sb); 

        return output.toString(); 
    }
}
package guidemon.model.casting;

public enum AreaOfEffectRangeType {
    CLAMP,         //clamp the AOE within the casting range - i.e, cut any parts outside 
    DISALLOW,      //disallow placing the AOE unless it is completely within the casting range
    EXPANSIVE;     //full AOE just has to place its center within the casting range. 
}

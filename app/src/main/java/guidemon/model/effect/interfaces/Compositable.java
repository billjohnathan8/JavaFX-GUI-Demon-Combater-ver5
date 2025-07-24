package guidemon.model.effect.interfaces;

import java.util.List; 

import guidemon.model.effect.Effect;

/**
 * Some effects can contain other effects applied at the same time as it is
 * 
 * TODO: Some occur in order. 
 */
public interface Compositable {
    List<Effect> getSubEffects();
}

//Special Override for apply() - apply all subeffects as well. 
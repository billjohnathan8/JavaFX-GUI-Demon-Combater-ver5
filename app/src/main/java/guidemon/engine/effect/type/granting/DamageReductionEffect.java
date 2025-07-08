package guidemon.engine.effect.type.granting;

import java.util.List; 

import guidemon.engine.effect.TemplateEffect; 
import guidemon.engine.scene.SceneContext; 
import guidemon.model.entry.Entry; 
import guidemon.model.combat.DamageType; 

/**
 * PassiveEffect
 * 
 * resource granting (?)
 */
public class DamageReductionEffect extends TemplateEffect {
    private DamageType damageType; 
    private int damageReductionAmount; 

    public DamageReductionEffect(String name, List<String> tags) {
        super(name, tags);
    }

    public void apply(Entry caster, List<Entry> targets, SceneContext context) {
        //No Caster - actor is null
        //Has Targets
        //Has SceneContext

        //add this effect onto the actor
    }
}
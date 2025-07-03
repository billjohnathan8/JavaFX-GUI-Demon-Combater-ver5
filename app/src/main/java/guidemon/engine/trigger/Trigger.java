package guidemon.engine.trigger;

import guidemon.engine.scene.SceneContext; 

/**
 * This is fired by the EventBus whenever something happens in a Scene or Combat Encounter. 
 */
public interface Trigger {
    /**
     * Called once at startup or bootstrap to register listeners on the EventBus
     */
    void register(SceneContext ctx);
}
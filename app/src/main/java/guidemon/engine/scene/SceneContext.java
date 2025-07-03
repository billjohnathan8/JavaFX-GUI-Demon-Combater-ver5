package guidemon.engine.scene;

import java.util.List; 
import java.util.stream.*;

import guidemon.engine.event.EventBus; 
import guidemon.model.actor.Actor; 
import guidemon.model.combat.Combatant; 
import guidemon.view.token.Token; 

/**
 * The central "bag" of runtime pieces that every system component (Effects, Triggers, UI Handlers) will pull from during a Scene, Combat Encounter etc.
 * 
 * The runtime “scene” your players inhabit—combat or not.
 * 
 * Bundles the Scene, CombatEncounter, EventBus, etc.
 * Think of it as snapshots of a scene. 
 * 
 * How It Works, Step-by-Step: 
 * 1. Bootstrap (WIP)
 * Happens in MainApp.java when Bootstrapping JavaFX
        EventBus bus        = new EventBus();
        SceneManager mgr   = new SceneManager(actors);
        AbilityProcessor ap = new AbilityProcessor(bus);
        SceneContext ctx   = new SceneContext(bus, mgr, actors, ap);
 * 
 * 2. Register Triggers
 * Each loaded Trigger calls trigger.register(ctx), which behind the scenes does:
        ctx.getEventBus()
           .register(SomeEvent.class, evt -> { 
                                                if (condition.test(evt, ctx)) 
                                                          effect.apply(...); 
                                              });
 * 
 * 3. Start Combat Loop - For Combat Encounters 
        while (!mgr.isCombatOver()) {
            Combatant turn = mgr.nextTurn();
            bus.publish(new TurnStartEvent(turn));
            // UI or AI picks an Ability + targets

            ctx.executeAbility(ability, turn, chosenTargets);
            bus.publish(new TurnEndEvent(turn));
        }
        bus.publish(new RoundEndEvent(...));
 * 
 * 4. Ability Execution - For anything else 
 * -AbilityProcessor publishes BeginCastEvent, ResourceCheckEvent, CastEvent, ApplyEffectsEvent in sequence.
 * -Triggers bound to those phases see those events and fire their effects (e.g. Reactions on HitCheckEvent).
 * 
 * 5. Effect-Driven Updates
 * Effects apply damage, buffs, statuses via methods on CombatManager or Combatant, and then often bus.publish(new DamageDealtEvent(...)) so that chained triggers can react.
 * 
 * This is not SceneManager or CombatManager:
 * Single source of truth for combat state in CombatManager.
 * 
 * It is useful for Snapshots of Combat or a Scene to Reset into for chrono or time -based abilities. 
 */
public class SceneContext {
    private final EventBus eventBus; 
    private final List<Token> tokens; //TODO: WIP move to grid manager
    private final List<Actor> actors; 
    private final SpaceManager spaceManager; 
    private final TimeManager timeManager; 

    public SceneContext(EventBus eventBus, List<Token> tokens, List<Actor> actors, SpaceManager spaceManager, TimeManager timeManager) {
        this.eventBus = eventBus;
        this.tokens = tokens; 
        this.actors = actors; 
        this.spaceManager = spaceManager;
        this.timeManager = timeManager; 
    }

    //only getters: 
    public EventBus getEventBus() {
        return this.eventBus; 
    }

    public List<Token> getTokens() {
        return this.tokens; 
    }

    public List<Actor> getActors() {
        return this.actors; 
    }

    public SpaceManager getSpaceManager() {
        return this.spaceManager; 
    }

    public TimeManager getTimeManager() {
        return this.timeManager; 
    }

    //Core Methods: 
    
    /**
     * Convenience method to grab only combatants
     * Used by CombatContext
     */
    public List<Combatant> getCombatants() {
        return actors.stream()  
                     .filter(t -> t instanceof Combatant)
                     .map(t -> (Combatant)t)
                     .collect(Collectors.toList());
    }
}

/*


 */
package guidemon.model.combat;

import java.util.Arrays;
import java.util.List;

//TODO: Deprecated into DamageTypeDefinition? 
public enum DamageType {
    SLASHING(), 
    BLUDGEONING(), 
    IMPALING(),

    SURGE(),
    PIERCING(),
    LACERATING(), //ranged slashing - special for wind spells etc. aka gashing ? 

    CUT(SLASHING, LACERATING),
    BLUNT_FORCE_TRAUMA(BLUDGEONING, SURGE),
    PUNCTURING(IMPALING, PIERCING),  
    
    STRIKING(), 
    
    MARTIAL(CUT, BLUNT_FORCE_TRAUMA, PUNCTURING, STRIKING),

    WEIGHT(),     //from falling damage 

    FIRE(),
    WATER(),
    COLD(),        //aka ice
    LIGHTNING(), 
    THUNDER(),     //aka sound 
    EARTH(),       //aka stone / rock/ dirt
    BEASTWOOD(), 
    ACID(),        //aka poison
    WIND(), 
    RADIANT(), 

    ELEMENTAL(FIRE, WATER, COLD, LIGHTNING, THUNDER, EARTH, BEASTWOOD, ACID, WIND, RADIANT), 

    NECROTIC(),       //aka rot, life energy, growth, biological etc.
    PSYCHIC(),        //aka mental
    FORCE(),          //aka pure force 
    
    ASTRAL(),         //aka void  
    BLOOD(), 
    METALLURGIC(),    //metals and their compounds or alloys
    MECHANICAL(),     //based on mechanical forces like gears etc
    UMBRAL(),         //solid darkness
    SHADOW(),         //casted shadows of darkness 
    SOLAR(RADIANT),   //sun
    LUNAR(RADIANT),   //moon
    STELLAR(SOLAR, LUNAR),
    NEOMECHANICAL(),  //redstone - not part of the principal
    BLUESTONE(), 
    CHROMECHANICAL(NEOMECHANICAL, BLUESTONE, LIGHTNING),  //chroma-mechanical (neomechanical + bluestone)

    WEAK_NUCLEAR(),
    STRONG_NUCLEAR(),
    NUCLEAR(WEAK_NUCLEAR, STRONG_NUCLEAR), 
    GRAVITATIONAL(),    
    MAGNETIC(),      //magnetic
    ELECTROMAGNETIC(MAGNETIC, LIGHTNING), //includes heat dissipated 

    DARKNESS(UMBRAL, SHADOW), 
    CANDELA(RADIANT, DARKNESS, STELLAR), 
    
    PRINCIPAL_ELEMENTAL(ELEMENTAL, NECROTIC, PSYCHIC, FORCE, ASTRAL, BLOOD, METALLURGIC, MECHANICAL, CANDELA),
    SIX_FORCES(FORCE, MECHANICAL, NUCLEAR, ELECTROMAGNETIC, GRAVITATIONAL, NEOMECHANICAL), 

    STEAM(FIRE, WATER),
    PURE_WATER(WATER, COLD), 
    ALETA(FIRE, LIGHTNING), //aka Pure Alpha-Betas
    // CRACKLING(LIGHTNING, THUNDER), 
    MAGMA(FIRE, EARTH),       //aka volcanic 
    SCORCH(FIRE, BEASTWOOD), 


// Scorch
// Writhing
// Bluefire
// Frost
// Modulated
// Ripple
// Mud
// Oceanic
// Fluison
// Mist
// Blue Lichten
// Echoing Moment
// Wilting
// Breeze
// Storm
// Lichten
// Charged
// Strong Culgre
// Tempest
// Reserved
// Proclaiming
// Persisting Culgre
// Whispering
// Pure Life
// Accepting Decay
// Vitriolic
// Strong Breakdown
// Zen
// Regretful Decay

//smoke
//dust
//sand 

    WILTING(), 
    FADING(), 

    COMPOUND_ELEMENTAL(), 
    TRUE_ELEMENTAL(ELEMENTAL, COMPOUND_ELEMENTAL),
    
    IRONED(METALLURGIC),  //against fey
    BRONZED(METALLURGIC), 
    SILVERED(METALLURGIC), 
    GOLDEN(METALLURGIC),
    PLATINA(METALLURGIC), //platinum
    BRASSED(METALLURGIC),
    JEWELLED(EARTH), 

    CHRONAL(SOLAR, LUNAR), //aka time 
    WILLFIRE(),            //from willpower
    COMPLEX_IMAGINARY(),   //higher dimensional 
    SPATIAL(CHRONAL, ASTRAL, SIX_FORCES, COMPLEX_IMAGINARY), //dunamancy - control over spacetime, aka topological  
    PLANAR(),
    EXTRA_PLANAR(), //portal snap + spatial fissure + exotic etc.
    ILLOGICAL(),    //aka chaos 

    WILDFIRE(), 
    HELLFIRE(), 
    BLACK_BLOOD(),
    ABYSS(), 

    METALLIC(),
    CHROMATIC(),
    TRI_CHROMA(), 
    DRACONIC_ELEMENTAL(METALLIC, CHROMATIC, TRI_CHROMA), 

    DEMONIC(),    //aka abyssal
    DEVILISH(),
    DAEMONIC(),   //aka lothic
    RAKSHIAN(),
    SLAADIC(),
    SHRIEKING(),  //pandemonium
    NETHER(), 
    FIENDISH(DEMONIC, DEVILISH, DAEMONIC, RAKSHIAN, SLAADIC, SHRIEKING, NETHER),
    
    PROGREDIAN(),
    ARCADIAN(),
    BYTOPIC(),
    ELYSIAN(),
    INTELLIGENCE_ENERGY(),
    VIBRANT(),
    RIGHTEOUS(),
    ALEAN(PROGREDIAN, ARCADIAN, BYTOPIC, ELYSIAN, INTELLIGENCE_ENERGY, VIBRANT, RIGHTEOUS), //is often paired with divine.

    //different kinds of poison damage 
    //bugs 

    FRENZIED(ALETA), //fire, necrotic
    PRIMAL(ALETA, SCORCH),   //fire, beastwood, lightning, necrotic <-

    RACIAL(DRACONIC_ELEMENTAL, FIENDISH, ALEAN, PRIMAL),

    EMOTIONAL(),
    VISCERAL(),
    WONDROUS(),
    FAINT(), 
    BITTERNESS(), 
    BASED(),             //aka gigachad energy or confidence or aura etc.
    SOCIAL(EMOTIONAL, VISCERAL, WONDROUS, FAINT, BITTERNESS, BASED),

    DIVINE(), //aka holy or sacred 
    BLASPHEMOUS(), //aka unholy or evil

    MAGIC(), 
    DANEN(THUNDER, MAGIC),       //dancing energy 

    PHYSICAL(MARTIAL, WEIGHT, EARTH, THUNDER, WIND, FORCE, METALLURGIC, MECHANICAL, UMBRAL, GRAVITATIONAL, MAGNETIC),
    ENERGY(TRUE_ELEMENTAL, PRINCIPAL_ELEMENTAL, SIX_FORCES, DIVINE, BLASPHEMOUS, MAGIC, BASED), 
    MAGICAL(MAGIC, ENERGY, DANEN),

    ALL(PHYSICAL, MAGICAL, ENERGY, SOCIAL, RACIAL);

    private final List<DamageType> subsets;

    DamageType(DamageType... subsets) {
        this.subsets = Arrays.asList(subsets);
    }

    public List<DamageType> getSubsets() {
        return subsets;
    }

    public boolean includes(DamageType other) {
        if (this == other) return true;
        for (DamageType subset : subsets) {
            if (subset.includes(other)) return true;
        }
        return false;
    }
}
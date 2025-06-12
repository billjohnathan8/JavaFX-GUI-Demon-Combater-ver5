//LOAD FROM JSON

// package guidemon.model.combat;

// import java.util.List;
// import java.util.Arrays;


// public enum DamageType {
//     PHYSICAL(),
//     FIRE(),
//     COLD(),
//     LIGHTNING(),
//     POISON(),
//     NECROTIC(),
//     RADIANT(),
//     MAGIC(FIRE, COLD, LIGHTNING, NECROTIC, RADIANT),
//     ELEMENTAL(FIRE, COLD, LIGHTNING),
//     ALL(PHYSICAL, MAGIC, POISON);

//     private final List<DamageType> subsetDamageTypes;

//     DamageType(DamageType... subsetDamageTypess) {
//         this.subsetDamageTypess = Arrays.asList(subsetDamageTypess);
//     }

//     public List<DamageType> getsubsetDamageTypess() {
//         return this.subsetDamageTypess;
//     }

//     public boolean includes(DamageType other) {
//         if (this == other) { 
//             return true;
//         }

//         for (DamageType subsetDamageTypes : subsetDamageTypess) {
//             if (subsetDamageTypes.includes(other)) return true;
//         }
//         return false;
//     }
// }
package guidemon.model.ability;

import java.util.List; 

//linchpins are always passive 
public class LinchpinAbility extends Ability {
    //list of abilities that it grants
    private List<Ability> grantedAbilities;  

    public LinchpinAbility(String name, List<Ability> grantedAbilities) {
        super(name);
        this.grantedAbilities = grantedAbilities;  
    }
}
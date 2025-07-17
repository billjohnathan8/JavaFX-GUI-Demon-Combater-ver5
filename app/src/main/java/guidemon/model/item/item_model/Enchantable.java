package guidemon.model.item.item_model;

import java.util.List;

import guidemon.model.effect.Effect;

//item that stores temporary or aspect enchantments 
public interface Enchantable {
    List<Effect> getEnchantments(); 
}
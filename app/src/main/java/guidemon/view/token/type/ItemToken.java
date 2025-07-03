package guidemon.view.token.type;

import java.util.List;

import guidemon.model.actor.Actor;
import guidemon.model.item.Item;
import guidemon.view.token.Token;

public class ItemToken extends Token {
    private List<String> owners; 
    private Item item; 
    //drag coefficient

    //elevation is determined by which tile it is spawned on. 
    public ItemToken(String name, double x, double z, Item item, List<String> owners) {
        super(name, x, z); 
        this.item = item; 
        this.owners = owners; 
    }
}

//usually in item layer
//can be interacted with and picked up
//e.g. ItemPile or Storage

//takes fall damage 
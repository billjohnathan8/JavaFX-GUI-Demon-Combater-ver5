package guidemon.view.token.type;

import java.util.List; 

import guidemon.model.actor.Actor;
import guidemon.view.token.Token;

public class ActorToken extends Token {
    private List<String> owners; 
    private Actor actor; 
    //drag coefficient

    //display dependent on knowledge:
    private int threatRange; //draws a circle for the threat range - based on what longest range attack the player has seen, i.e, what this creature has revealed - can be configured by the player 
    //display on the statblocks etc.


    //elevation is determined by which tile it is spawned on. 
    public ActorToken(String name, double x, double z, Actor actor, List<String> owners) {
        super(name, x, z); 
        this.actor = actor; 
        this.owners = owners; 
    }
}

//takes fall damage 

//Out of Combat: 
//moves freely via drag


//During Combat: 
//belongs to a combat encounter 
//moves according to combat tempo
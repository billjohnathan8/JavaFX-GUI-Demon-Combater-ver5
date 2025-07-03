package guidemon.view.token;

import java.util.List; 

import guidemon.model.actor.Actor;

public class ActorToken extends Token {
    private List<String> owners; 
    private Actor actor; 

    //elevation is determined by which tile it is spawned on. 
    public ActorToken(String name, double x, double z, Actor actor, List<String> owners) {
        super(name, x, z); 
        this.actor =actor; 
        this.owners = owners; 
    }
}
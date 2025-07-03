package guidemon.view.token;

import java.util.UUID; 

/** Base for anything you can place on a scene (PCs, monsters, lair features, props). */
public abstract class Token {
    private final UUID id = UUID.randomUUID(); 
    private String name; 
    private double x;
    private double z; 

    //elevation
    private double y; 

    //elevation is determined by which tile it is spawned on. 
    public Token(String name, double x, double z) {
        this.name = name;
        this.x = x;
        this.z = z; 
    }

    //getters and setters; 
}
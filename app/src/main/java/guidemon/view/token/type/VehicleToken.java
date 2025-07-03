package guidemon.view.token.type;

import guidemon.view.token.Token;

//TODO: Rooms that move and have other tokens on them that move by proxy
public class VehicleToken extends Token {
    public VehicleToken(String name, double x, double z) {
        super(name, x, z);  
    }
}
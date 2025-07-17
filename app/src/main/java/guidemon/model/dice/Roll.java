package guidemon.model.dice;

import java.util.List; 

public class Roll {
    private List<Dice> dice; 
    private List<Integer> bonuses; //additional or subtractive bonuses

    public Roll(List<Dice> dice, List<Integer> bonuses) {
        this.dice = dice; 
        this.bonuses = bonuses; 
    }

    //TODO: 
    // public Roll(String rollExpession) {
    //     String[] tokeniseByBonus = rollExpression.split("+").split("-"); //Delimiter is 'd' e.g. 3d6 -> 3, 6


    //     // 3d6+1+2-4+6
    // }

    //only getters, no setters - because rare to update the entire contents. just replace an entire Roll for that.
    //add and remove from the list via getter instead. 
    public List<Dice> getDice() {
        return this.dice;
    }

    public List<Integer> getBonuses() {
        return this.bonuses; 
    }
}

package guidemon.model.dice;

public class Dice {
    private int diceFace; 
    private int numberOfDice;  
    
    public Dice(int diceFace, int numberOfDice) {
        this.diceFace = diceFace;
        this.numberOfDice = numberOfDice; 
    }

    public Dice(String diceExpression) {
        String[] tokens = diceExpression.split("d"); //Delimiter is 'd' e.g. 3d6 -> 3, 6

        //Check for Type Safety: 
        try {
            this.diceFace = Integer.parseInt(tokens[1]);
            this.numberOfDice = Integer.parseInt(tokens[0]);

        } catch (NumberFormatException e) {
            e.getMessage();
        }
    }

    //getters and setters:
    public int getDiceFace() {
        return this.diceFace; 
    }

    public void setDiceFace(int diceFace) {
        this.diceFace = diceFace; 
    }

    public int getNumberOfDice() {
        return this.numberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }
}
// package guidemon.engine.dice;

// import java.util.*; 

//TODO: to be implemented 
// public class Roll {
//     private int rolledValue;
//     private int rollModifier; 
    
//     public Roll(String rollCommand) { 
//         try { 
//             String[] splitRollCommandAndAdvantage = rollCommand.split("a"); //e.g. 1d20a1 (advantage 1, advantage = -1 is disadvatange)
//             int numAdvantage = Integer.parseInt(splitRollCommandAndAdvantage[1]); 
//             String[] splitRollCommand = splitRollCommandAndAdvantage[0].split("d"); //e.g. 4d20

//             int numDice = Integer.parseInt(splitRollCommand[0]); 
//             int diceFace = Integer.parseInt(splitRollCommand[1]); 

//             this.rolledValue = executeRoll(numAdvantage, numDice, diceFace); 
        
//         } catch (NumberFormatException e) { 
//             e.getMessage(); 
//         }
//     }

//     public int executeRoll(int numDice, int diceFace) { 
//         Random rand = new Random(); 
//         int max = diceFace; 
//         int min = 1; 
//         int numRolls = numDice; 

//         int result = 0; 
//         for (int i = 0 ; numRolls > i ; i++) { 
//             result += rand.nextInt((max - min + 1) + min); 
//         }

//         return result; 
//     }

//     //execute 2 rolls and take the higher resulting number. - advantage
//     //execute 2 rolls and take the lower resulting number. - disadvantage
//     // public int resolveAdvantage() { 

//     // }

//     public int getRolledValue() { 
//         return this.rolledValue;
//     }
// }

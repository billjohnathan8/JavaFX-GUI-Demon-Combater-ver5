package guidemon.model.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; 
import java.util.Collections; 
import java.util.stream.*; 

/**
 * TODO: Utility Class (?)
 * 
 * Purely used for number values (not booleans)
 * 
 * Evaluate success in a different roller class entirely. 
 */
public class DiceRoller {
    private static final Random RNG = new Random(); 
    private String input; 
    private int pos; 

    /**
     * Evaluate an expression like: 
     * "2d6 + floor(1d4/2) * (abs(-3) + 5)**2 % 7"
     * @param expr (whitespace is ignored)
     * @return final value as a double (you can cast it to int if you know it's an integer)
     */
    public double evaluate(String expr) {
        input = expr.replaceAll("\\s+", "");
        pos = 0; 

        double result = parseExpression(); 


        if(pos < input.length()) {
            throw new IllegalArgumentException("Unexpected character at position " + pos + ": '" + input.charAt(pos) + "'");
        }

        return result; 
    }
    
    //expression = term (( '+' | '-' ) term)*
    private double parseExpression() { 
        double value = parseTerm();

        while(true) {
            if (match('+')) {
                value += parseTerm(); 

            } else if (match('-')) {
                value -= parseTerm(); 

            } else {
                break; 

            }
        }

        return value; 
    }

    //term = factor (( '*' | '/' | '%') factor)*
    private double parseTerm() {
        double value = parseFactor();

        while(true) {
            if(match('*')) {
                value *= parseFactor();

            } else if (match('/')) {
                value /= parseFactor();

            } else if (match('%')) {
                value %= parseFactor(); 
            
            } else {
                break;

            }
        }

        return value; 
    }

    //factor  = unary ('**' factor)?
    private double parseFactor() {
        double base = parseUnary(); 

        if(match("**")) {
            double exp = parseFactor();              //right-associative
            return Math.pow(base, exp); 
        }

        return base; 
    }

    //unary - ('+'|'-')? primary | functionCall
    private double parseUnary() {
        if(match('+')) {
            return parseUnary(); 
        }

        if(match('-')) {
            return -parseUnary(); 
        }

        //function calls: floor(), ceil(), round(), abs()
        String name = peekIdentifier(); 
        if(name != null && match(name) && match('(')) {
            double arg = parseExpression();

            if(!match(')')) {
                throw new IllegalArgumentException("Missing ')' after " + name);
            }

            switch(name) {
                case "floor": return Math.floor(arg); 

                case "ceil": return Math.ceil(arg);

                case "round": return Math.rint(arg);

                case "abs": return Math.abs(arg); 
            }
        }

        return parsePrimary(); 
    }

    //primary = dice | number | '(' expression ')'
    private double parsePrimary() {
        if(match('(')) {
            double val = parseExpression(); 

            if(!match(')')) {
                throw new IllegalArgumentException("Missing ')'");
            }

            return val; 
        }

        // --- parse a dice term: [count]'d'sides[modifier][modCount] ---
        int start = pos; 
        
        //must see either a digit or 'd'
        if(match('d') || Character.isDigit(peek())) { 
            //1) read the count (if any)
            int count = 0; 
            if(input.charAt(start) == 'd') {
                count = 1; 

            } else { 
                while(Character.isDigit(peek())) {
                    pos++; 
                }

                count = Integer.parseInt(input.substring(start, pos));

                if(!match('d')) {
                    //it was just a number, not dice
                    return count; 
                }
            }

            //2) read the sides
            int sides = readInt(); 

            //3) explosion parsing
            ExplosionType expType = ExplosionType.NONE; 
            ThresholdType threshType = ThresholdType.EQUAL;
            int threshValue = sides; 

            if(input.startsWith("!!", pos)) {
                expType = ExplosionType.COMPOUND;
                pos += 2;

            } else if (match('!')) {
                expType = ExplosionType.EXPLODE; 

            }

            if(expType != ExplosionType.NONE) {
                if(match('>')) {
                    threshType = ThresholdType.GE; 
                    threshValue = readInt();

                } else if (match('<')) {
                    threshType = ThresholdType.LE; 
                    threshValue = readInt(); 

                } else if (Character.isDigit(peek())) {
                    threshType = ThresholdType.EQUAL;
                    threshValue = readInt(); 

                } else { 
                    //leave threshValue == sides (explode on max)
                }
            }

            //4) parse any number of reroll clauses
            List<RerollCondition> rerolls = new ArrayList<>(); 
            while(match('r')) {
                boolean once = match('o');    //ro vs r
                ThresholdType rtt = ThresholdType.EQUAL;
                if(match('<')) {
                    rtt = ThresholdType.LT;

                } else if(match('>')) {
                    rtt = ThresholdType.GT;

                }

                int rerollThreshValue = readInt();
                rerolls.add(new RerollCondition(rtt, rerollThreshValue, once));
            }

            //5) roll + apply rerolls + explode into list  
            List<Double> rolls = new ArrayList<>();
            for (int i = 0 ; i < count ; i++) {
                //initial roll
                int roll = rollDice(sides); 

                //reroll loop
                boolean didOnce = false;
                while (true) { 
                    boolean wantsReroll = false; 
                    for (var rc : rerolls) {
                        if(rc.matches(roll) && (!rc.once || !didOnce)) {
                            if(rc.once) {
                                didOnce = true;
                            }  

                            break; 
                        }
                    }

                    if(!wantsReroll) {
                        break;
                    }

                    roll = rollDice(sides); 
                }

                //handle explosion, using roll as the "base" value you compare
                if (expType == ExplosionType.COMPOUND) {
                    int sum = roll;

                    while(shouldExplode(roll, threshType, threshValue)) {
                        roll = rollDice(sides);
                        sum += roll; 
                    }

                    rolls.add((double) sum);

                } else if (expType == ExplosionType.EXPLODE) {
                    rolls.add((double) roll);

                    while(shouldExplode(roll, threshType, threshValue)) {
                        roll = rollDice(sides);
                        rolls.add((double) roll);
                    }

                } else {
                    rolls.add((double) roll);
                }
            }

            //6) check for optional drop/keep modifier
            // k   = keep highest,    kh = keep highest
            // kl  = keep lowest
            // d   = drop lowest,     dl = drop lowest
            // dh  = drop highest
            String mod = null;
            if(match('k')) { 
                if(match('h')) {
                    mod = "kh";

                } else if(match('l')) {
                    mod = "kl";

                } else { 
                    mod = "kh";

                }
            } else if(match('d')) {
                if(match('h')) {
                    mod = "dh";

                } else if(match('l')) {
                    mod = "dl";

                } else { 
                    mod = "dl";

                }
            }

            int modCount = 0; 
            if(mod != null) {
                int mStart = pos; 

                while(Character.isDigit(peek())) {
                    pos++;
                }

                modCount = Integer.parseInt(input.substring(mStart, pos));
            }

            //7) apply the modifier slice
            return applyDropKeep(rolls, mod, modCount); 
        }

        //8) otherwise: parse a decimal number
        if(Character.isDigit(peek())) {
            int dotCount = 0; 

            while(Character.isDigit(peek()) || (peek() == '.' && dotCount++==0)) {
                pos++;
            }

            return Double.parseDouble(input.substring(start, pos));
        }

        throw new IllegalArgumentException("Unexpected token at " + pos); 
    }

    private double applyDropKeep(List<Double> rolls, String mod, int modCount) {
        if(mod == null || modCount >= rolls.size()) {
            //no modifier or dropping/keeping more than exist -> sum all
            return rolls.stream()
                        .mapToDouble(d -> d)
                        .sum();
        } 

        List<Double> sorted = new ArrayList<>();
        sorted.sort(Double::compareTo); 

        List<Double> selected; 
        switch(mod) {
            case "k":   // keep highest
            case "kh":
                selected = sorted.subList(sorted.size() - modCount, sorted.size());
                break; 
            case "kl":  // keep lowest
                selected = sorted.subList(0, modCount);
                break; 
            case "d":   // drop lowest
            case "dl":
                selected = sorted.subList(modCount, sorted.size());
                break; 
            case "dh":  // drop highest
                selected = sorted.subList(0, sorted.size() - modCount);
                break; 
            default:
                throw new IllegalArgumentException("Unknown modifier: " + mod);  
        }

        return selected.stream()
                       .mapToDouble(d -> d)
                       .sum(); 
    }

    //explosion condition check
    private boolean shouldExplode(int roll, ThresholdType tt, int threshValue) {
        //"->" prevents fall-through, because it has a break at the end each time. 
        return switch (tt) {
            case EQUAL -> roll == threshValue; 
            case GE    -> roll >= threshValue;
            case LE    -> roll <= threshValue;
            case LT    -> roll <  threshValue; 
            case GT    -> roll >  threshValue; 
        };
    }

    //Helper Functions: 

    // helper to read an integer at current pos (advances pos)
    private int readInt() {
        int start = pos;

        while (pos < input.length() && Character.isDigit(input.charAt(pos))) { 
            pos++;
        } 

        if(start == pos) {
            throw new IllegalArgumentException();

            /* For DiceSides: 
            int sideStart = pos;
            while(Character.isDigit(peek())) {
                pos++; 
            }

            if(sideStart == pos) {
                throw new IllegalArgumentException("Missing sides after 'd'");
            }
             */
        }

        return Integer.parseInt(input.substring(start, pos));
    }

    // your uniform 1…sides roll
    private int rollDice(int sides) {
        return RNG.nextInt(sides) + 1;
    }

    private char peek() {
        return pos < input.length() ? input.charAt(pos) : '\0';
    }

    //Utility Method: If next char equals c, consume it and return true
    private boolean match(char c) {
        if(peek() == c) {
            pos++;
            return true; 
        }

        return false; 
    }

    //Utility Method: If next string starts with s, consume it and return true
    private boolean match(String s) {
        if (input.startsWith(s, pos)) {
            pos += s.length();
            return true;
        }

        return false;
    }

    /** 
     * If next chars form an identifier [a-z]+, return it (without consuming);
     * otherwise null. 
     */
    private String peekIdentifier() {
        int i = pos;
        while (i < input.length() && Character.isLetter(input.charAt(i))) i++;
        if (i>pos) return input.substring(pos, i);
        return null;
    }
}
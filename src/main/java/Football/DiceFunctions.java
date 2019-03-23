package Football;

import java.util.ArrayList;

public class DiceFunctions {

    public static ArrayList<Dice> decodeDice(ArrayList<String> diceData) {
        
        ArrayList<Dice> diceSet = new ArrayList<>();
        
        for (String d: diceData) {
            boolean stillParsing = true;

            int currSpace = d.indexOf(" ");
            int nextSpace;

            String dieColour = d.substring(0, currSpace);
            ArrayList<Integer> dieValues = new ArrayList<>();

            String diceValues = d.substring(currSpace).trim();
            String[] splitDiceValues = diceValues.split(" ");
            
            for (String value: splitDiceValues) {
                dieValues.add(Integer.parseInt(value));
            }
            
            Dice newDie = new Dice(dieColour, dieValues);
            diceSet.add(newDie);

        }
        return diceSet;
    }
    
    public static ArrayList<DiceSelection> decodeChart(ArrayList<String> chartData, ArrayList<Dice> diceSet) {

        ArrayList<DiceSelection> diceChart = new ArrayList<>();
        
        for (String d: chartData) {
            String[] ratingGapAndDiceData = d.split(": ");
            int ratingGap = Integer.parseInt(ratingGapAndDiceData[0]);
            String[] homeAndAwayDice = ratingGapAndDiceData[1].split(" v ");
            
            String[] homeDiceNames = homeAndAwayDice[0].split(" ");
            String[] awayDiceNames = homeAndAwayDice[1].split(" ");
            
            ArrayList<Dice> homeDice = diceNamesListIntoDiceArray(homeDiceNames, diceSet);
            ArrayList<Dice> awayDice = diceNamesListIntoDiceArray(awayDiceNames, diceSet);
            
            DiceSelection newDiceSelection = new DiceSelection(ratingGap, homeDice, awayDice);
            diceChart.add(newDiceSelection);
        }
        return diceChart;
    }
    
    private static ArrayList<Dice> diceNamesListIntoDiceArray(String[] diceNames, ArrayList<Dice> diceSet) {
        ArrayList<Dice> diceGroup = new ArrayList<>();
        for (String s: diceNames) {
            Dice die = findDie(s.trim(), diceSet);
            diceGroup.add(die);
        }
        return diceGroup;
    }
    
    private static Dice findDie(String diceName, ArrayList<Dice> diceSet) {
        for (Dice d: diceSet) {
            if (diceName.equals(d.getColour())) {
                return d;
            }
        }
        return null;
    }
}
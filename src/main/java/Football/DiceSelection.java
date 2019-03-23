package Football;

import java.util.ArrayList;

public class DiceSelection {

    private int diff;
    private ArrayList<Dice> team1Dice;
    private ArrayList<Dice> team2Dice;

    public DiceSelection(int diff, ArrayList<Dice> team1, ArrayList<Dice> team2) {

        this.diff = diff;
        team1Dice = new ArrayList<Dice>();
        team2Dice = new ArrayList<Dice>();

        team1Dice.addAll(team1);
        team2Dice.addAll(team2);
    }

    public int rollDice1() {

        int teamScore = 0;

        for (Dice d : team1Dice) {
            int dieRoll = d.rollDie();
            teamScore += dieRoll;
        }

        return teamScore;
    }

    public int rollDice2() {

        int teamScore = 0;

        for (Dice d : team2Dice) {
            int dieRoll = d.rollDie();
            teamScore += dieRoll;
        }

        return teamScore;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int newDiff) {
        diff = newDiff;
    }

    public ArrayList<Dice> getTeam1Dice() {
        return team1Dice;
    }

    public ArrayList<Dice> getTeam2Dice() {
        return team2Dice;
    }

    public void addDice(Dice dice, int team) {
        if (team == 1) {
            addDice1(dice);
        } else if (team == 2) {
            addDice2(dice);
        } else {
            //throw exception
        }
    }

    public void addDice1(Dice dice) {
        team1Dice.add(dice);
    }

    public void addDice2(Dice dice) {
        team2Dice.add(dice);
    }

    @Override
    public String toString() {

        String outString = "";

        for (Dice d : team1Dice) {
            outString = outString + d.getColour() + " ";
        }

        outString = outString + "v";

        for (Dice d : team2Dice) {
            outString = outString + " " + d.getColour();
        }

        return outString;

    }

}

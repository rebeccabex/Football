package Football;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Dice {

    private String colour;
    private int faces;
    private ArrayList<Integer> values;

    public Dice(String inColour, int inFaces, ArrayList<Integer> inValues) {
        colour = inColour;
        faces = inFaces;

        values = inValues;
    }

    public int rollDie() {

        int dieRoll = values.get(randIntGen(faces));

        return dieRoll;

    }

    public String getColour() {
        return colour;
    }

    public int getFaces() {
        return faces;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public String getValuesString() {
        String returnString = "";

        for (int i = 0; i < faces; i++) {
            returnString = returnString + Integer.toString(values.get(i)) + ";";
        }

        return returnString;

    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setFaces(int faces) {
        this.faces = faces;
    }


    public void setValues(int[] values) {
        for (int i=0; i<faces; i++) {
            this.values.set(i, values[i]);
        }
    }

    public int randIntGen(int bound) {
        Random randomIntGen = new Random();
        return randomIntGen.nextInt(bound);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Dice)) {
            return false;
        } else {
            Dice other = (Dice) obj;

            return colour.equals(other.getColour());
        }

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

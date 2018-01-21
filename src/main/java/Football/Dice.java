package Football;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Dice {

    private String colour;
    private int faces;
    private int[] values;

    private int dieRoll;

    public Dice(String inColour, int inFaces, int[] inValues) {
        colour = inColour;
        faces = inFaces;

        values = new int[faces];

        for (int i = 0; i < faces; i++)
        {
            values[i] = inValues[i];
        }

    }

    public int rollDie() {

        dieRoll = values[randIntGen(faces)];

        return dieRoll;

    }

    public String getColour() {
        return colour;
    }

    public int getFaces() {
        return faces;
    }


    public int[] getValues() {
        return values;
    }

    public String getValuesString() {
        String returnString = "";

        for (int i = 0; i < faces; i++) {
            returnString = returnString + Integer.toString(values[i]) + ";";
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
            this.values[i] = values[i];
        }
    }

    public int randIntGen(int bound) {
        Random randomIntGen = new Random();
        int randVal = randomIntGen.nextInt(bound);
        return randVal;
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

            if (!colour.equals(other.getColour())) {
                return false;
            } else {
                return true;
            }
        }

    }

    @Override
    public int hashCode() {

        int result = Objects.hash(colour, faces, dieRoll);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }
}

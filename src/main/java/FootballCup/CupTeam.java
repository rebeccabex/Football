package FootballCup;

import Football.Team;

public class CupTeam extends Team {

    private int startRound;
    private int roundReached;

    public CupTeam(String inName, int inRating, int inRound) {

        super(inName, inRating);
        startRound = inRound;
        roundReached = 0;

    }

    public int getStartRound() {
        return startRound;
    }

    public void setRoundReached(int roundNo) {
        roundReached = roundNo;
    }

    @Override
    public String toString() {
        return super.toString() + roundReached;
    }
}

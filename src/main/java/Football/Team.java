package Football;

import java.util.Objects;

public class Team {

    private String teamName;
    private int rating;
    // private int rankPtsGained;

    public Team(String inName, int inRating) { //, int inRound) {
        teamName = inName;
        rating = inRating;
        // rankPtsGained = 0;
    }

    public String getName() {
        return teamName;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "\"" + teamName + "\";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return rating == team.rating &&
                Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teamName, rating);
    }
}
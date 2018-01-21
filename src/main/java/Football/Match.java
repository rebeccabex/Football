package Football;

public class Match {


    private Team homeTeam;
    private Team awayTeam;

    private int homeScore;
    private int awayScore;
    private String result;

    private boolean neutral;

    public Match(Team inHomeTeam, Team inAwayTeam, boolean inNeutral) {
        homeTeam = inHomeTeam;
        awayTeam = inAwayTeam;

        homeScore = 0;
        awayScore = 0;

        neutral = inNeutral;

        calcResult();
    }

    private void calcResult() {
        if (homeScore > awayScore) {
            result = "H";
        }
        else {
            if (awayScore > homeScore) {
                result = "A";
            }
            else {
                result = "D";
            }
        }
    }

    private String homeOrAway(String teamName) {
        if (homeTeam.getName().equals(teamName)) {
            return "H";
        } else
        if (awayTeam.getName().equals(teamName)) {
            return "A";
        } else {
// throw exception
            return "";
        }

    }

    public String getResult() {
        return result;
    }

    public Team getTeam(String teamSel) {
        if (teamSel.equals("H")) {
            return homeTeam;
        }
        else {
            if (teamSel.equals("A")) {
                return awayTeam;
            }
            else {
// throw exception
                return null;
            }
        }
    }

    public String getTeamName(String teamSel) {
        if (teamSel.equals("H")) {
            return homeTeam.getName();
        }
        else {
            if (teamSel.equals("A")) {
                return awayTeam.getName();
            }
            else {
// throw exception
                return "";
            }
        }
    }

    public boolean isHomeTeam(Team inTeam) {
        if (neutral) {
            return false;
        } else {
            if (homeTeam.equals(inTeam)) {
                return true;
            } else {
                if (awayTeam.equals(inTeam)) {
                    return false;
                } else {
// throw exception
                    return false;
                }
            }
        }
    }

    public boolean isNeutral() {
        return neutral;
    }

    public void setTeam(String teamSel, Team newTeam) {
        if (teamSel.equals("H")) {
            homeTeam = newTeam;
        }
        else {
            if (teamSel.equals("A")) {
                awayTeam = newTeam;
            }
            else {
// throw exception
            }
        }
    }

    public void setScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        calcResult();
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    @Override
    public String toString() {

        String matchString = "\"" + homeTeam.getName() + "\";" + homeScore + ";" + awayScore + ";\"" + awayTeam.getName() + "\"";

        return matchString;

    }








}

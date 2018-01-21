package FootballLeague;

import Football.Team;

public class LeagueTeam extends Team {

    private int[] tableData;

    public LeagueTeam(String inName, int inRating, int[] inResults) {
        super(inName, inRating);

        tableData = new int[inResults.length];

        for (int i=0; i<8; i++) {
            tableData[i] = inResults[i];
        }
    }


    public void addMatch(LeagueMatch match) {

        boolean atHome = match.isHomeTeam(this);

        addResult(match, atHome);
        addGoals(match, atHome);

        String output = getName();
        for (int i = 0; i < 8; i++) {
            output += " " + tableData[i];
        }

    }

    public void addResult(LeagueMatch match, boolean atHome) {

        switch (match.getResult()) {
            case "H":
                if (atHome) {
                    tableData[1]++;
                } else {
                    tableData[3]++;
                }
                break;
            case "D":
                tableData[2]++;
                break;
            case "A":
                if (atHome) {
                    tableData[3]++;
                } else {
                    tableData[1]++;
                }
                break;
            default:
                //throw exception
        }

        calcPts();
        calcPld();

    }

    public void addGoals(LeagueMatch match, boolean atHome) {

        int goalsScored = 0;
        int goalsConc = 0;

        if (atHome) {
            goalsScored = match.getHomeScore();
            goalsConc = match.getAwayScore();
        } else {
            goalsScored = match.getAwayScore();
            goalsConc = match.getHomeScore();
        }

        tableData[4] += goalsScored;
        tableData[5] += goalsConc;

        calcGD();
    }

    public void calcPts() {
        tableData[6] = tableData[1] * 3 + tableData[2];
    }

    public void calcPld() {
        tableData[0] = tableData[1] + tableData[2] + tableData[3];
    }

    public void calcGD() {
        tableData[7] = tableData[4] - tableData[5];
    }

    public int getStat(int statPsn) {
        return tableData[statPsn];
    }

    public int getStat(String statName) {
        return tableData[getStatPsn(statName)];
    }

    // will return array index in teamstats of required
    private int getStatPsn(String statName) {

        int statPsn = 0;

        switch (statName.toLowerCase()) {
            case "pld": case "played":
                statPsn = 0;
                break;
            case "w": case "won":
                statPsn = 1;
                break;
            case "d": case "drawn":
                statPsn = 2;
                break;
            case "l": case "lost":
                statPsn = 3;
                break;
            case "f": case "scored": case "goals scored": case "goals for":
                statPsn = 4;
                break;
            case "a": case "conceded": case "goals conceded": case "goals against":
                statPsn = 5;
                break;
            case "pts": case "points":
                statPsn = 6;
                break;
            case "gd": case "goal difference":
                statPsn = 7;
                break;
            default:
                statPsn = 0;
                break;
        }
        return statPsn;
    }

    @Override
    public String toString() {

        String teamString = super.toString();

        for (int i = 0; i < 8; i++) {
            teamString += ";" + tableData[i];
        }

        return teamString;
    }

}

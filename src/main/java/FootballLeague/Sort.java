package FootballLeague;

import java.util.ArrayList;

public class Sort {

    private ArrayList<LeagueTeam> teamList;

    public Sort(ArrayList<LeagueTeam> teamList) {
        this.teamList = teamList;
    }


    public ArrayList<LeagueTeam> sortTeams() {

        boolean unsorted = true;
        int teamsToSort = teamList.size() - 1;

        while (unsorted) {
            int swaps = 0;
            for (int i = 0; i < teamsToSort; i++) {
                LeagueTeam team1 = teamList.get(i);
                LeagueTeam team2 = teamList.get(i+1);
                Comparison compPts = compareByStat(team1, team2, 6); // TODO change to passing Pts
                switch (compPts) {
                    case HIGHER: // does this need anything?
                        break;
                    case LOWER: swapTeams(i, i+1);
                        swaps++;
                        break;
                    case EQUAL: Comparison compGD = compareByStat(team1, team2, 7); // TODO change to passing GD
                        switch (compGD) {
                            case HIGHER: //?
                                break;
                            case LOWER: swapTeams(i, i+1);
                                swaps++;
                                break;
                            case EQUAL: Comparison compGF = compareByStat(team1, team2, 4); // TODO change to passing GF
                                switch (compGF) {
                                    case HIGHER:
                                    case EQUAL:
                                        break;
                                    case LOWER: swapTeams(i, i+1);
                                        swaps++;
                                        break;
                                }
                                break;
                        }
                        break;
                }
            }
            teamsToSort--;
            if (swaps == 0) {
                unsorted = false;
            }
        }

        return teamList;

    }

    private void swapTeams(int teamNo1, int teamNo2) {
        LeagueTeam temp = teamList.get(teamNo1);
        teamList.set(teamNo1, teamList.get(teamNo2));
        teamList.set(teamNo2, temp);
    }

    public Comparison compareByStat(LeagueTeam  team1, LeagueTeam team2, int stat) {

        if (team1.getStat(stat) > team2.getStat(stat)) {
            return Comparison.HIGHER;
        } else {
            if (team1.getStat(stat) < team2.getStat(stat)) {
                return Comparison.LOWER;
            } else {
                if (team1.getStat(stat) == team2.getStat(stat)) {
                    return Comparison.EQUAL;
                } else {
                    return null;
// throw error
                }
            }
        }

    }


}

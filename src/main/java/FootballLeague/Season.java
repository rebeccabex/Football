package FootballLeague;

import Football.Dice;
import Football.DiceFunctions;
import Football.DiceSelection;
import Football.ReadFile;
import Football.WriteFile;

import java.util.ArrayList;

public class Season {

    private ArrayList<Dice> diceSet;

    private int noOfTeams;
    private ArrayList<LeagueTeam> teamList;

    private ArrayList<DiceSelection> diceChart;

    private int timesPlay;

    private ArrayList<LeagueMatch> matchList;

    private String chartFileName;

    public Season(ArrayList<String> diceData, ArrayList<String> chartData, ArrayList<String> teamData,
                  ArrayList<String> optionsData)
    {

        parseOptions(optionsData);

        diceSet = decodeDice(diceData);
        diceChart = decodeChart(chartData);

        teamList = new ArrayList<>();
        decodeTeams(teamData);
        noOfTeams = teamList.size();

        matchList = new ArrayList<>();

        runSeason();

        Sort sort = new Sort(teamList);

        printSeason(sort.sortTeams());
    }

    public void printSeason(ArrayList<LeagueTeam> sortedTeams) {
        String fileName = "..\\..\\..\\FinalTable.txt";

        WriteFile data = new WriteFile(fileName, true);
        String text = "";

        for (LeagueTeam t : sortedTeams) {
            text += t.toString() + "\n";
        }

        text += "\n";

        for (LeagueMatch m : matchList) {
            text += m.toString() + "\n";
        }

        data.writeToFile(text);
    }

    public static void main(String[] args) {

        String filepath = "..\\..\\..\\";
    
        String tempChartFileName = "StdHA.txt"; // TODO need to implement Options file to set chart file name first

        String diceFilePath = filepath + "Dice Values.txt";
        String chartFilePath = filepath + tempChartFileName;
        String teamFilePath = filepath + "Teams.txt";
        String optionsPath = filepath + "Options.txt";

        ReadFile diceFile = new ReadFile(diceFilePath);
        ReadFile chartFile = new ReadFile(chartFilePath);
        ReadFile teamFile = new ReadFile(teamFilePath);
        ReadFile optionsFile = new ReadFile(optionsPath);

        ArrayList<String> diceData = diceFile.readFromFile();
        ArrayList<String> chartData = chartFile.readFromFile();
        ArrayList<String> teamData = teamFile.readFromFile();
        ArrayList<String> optionsData = optionsFile.readFromFile();

        Season season = new Season(diceData, chartData, teamData, optionsData);

    }


    private void runSeason() {

        int rounds = (isEven(timesPlay)) ? timesPlay / 2 : (timesPlay + 1) / 2;

        for (int k = 0; k < rounds; k++) {
            for (int i = 0; i < noOfTeams; i++) {
                for (int j = 0; j < noOfTeams; j++) {
                    if (i != j) {
                        if (i < j || isEven(timesPlay)) {
                            playMatch(teamList.get(i), teamList.get(j));
                        }
                    }
                }
            }
        }
    }

    private boolean isEven(int inVal) {
         return inVal % 2 == 0;
    }

    private void playMatch(LeagueTeam team1, LeagueTeam team2) {

        LeagueMatch match = new LeagueMatch(team1, team2, false);

        int rating1 = team1.getRating();
        int rating2 = team2.getRating();

        int ratingDiff = rating1 - rating2;

        DiceSelection matchDice = calcDice(ratingDiff, false);

        int team1Score = matchDice.rollDice1();
        int team2Score = matchDice.rollDice2();

        match.setScore(team1Score, team2Score);

        team1.addMatch(match);
        team2.addMatch(match);

        // add ranking pts

        matchList.add(match);

    }

    private DiceSelection calcDice(int ratingDiff, boolean isNeutral) {

        boolean negRD = ratingDiff < 0;

        float fRatingDiff = Math.abs((float) ratingDiff);

        int ratingGap = Math.round(fRatingDiff/100);

        ratingGap = negRD ? ratingGap * -1 : ratingGap;

//deal with null

        return findDiceSelection(ratingGap);

    }

//	private ? rollDice() {

//	}


    private Dice findDie(String diceName) {
        for (Dice d : diceSet) {
            if (diceName.equals(d.getColour())) {
                return d;
            }
        }
        return null;
    }

    private DiceSelection findDiceSelection(int ratingDiff) {
        for (DiceSelection ds : diceChart) {
            if (ratingDiff == ds.getDiff()) {
                return ds;
            }
        }
        return null;
    }

    private ArrayList<Dice> decodeDice(ArrayList<String> diceData) {
        return DiceFunctions.decodeDice(diceData);
    }

    private ArrayList<DiceSelection> decodeChart(ArrayList<String> chartData) {
        return DiceFunctions.decodeChart(chartData, diceSet);
    }

    private void decodeTeams(ArrayList<String> teamData) {
        for (String d : teamData) {

            int startName = d.indexOf("$");
            int endName = d.indexOf("$", startName + 1);
            String teamName = d.substring(startName+1, endName);

            int currSpace = d.indexOf(" ", endName);
            int nextSpace = d.indexOf(" ", currSpace + 1);

            int teamRating;
            int[] teamRes = new int[8];

            if (nextSpace == -1) {
                teamRating = Integer.parseInt(d.substring(currSpace + 1));
            } else {
                teamRating = Integer.parseInt(d.substring(currSpace + 1, nextSpace));

                currSpace = nextSpace;

                for (int i = 0; i < 8; i++) {

                    nextSpace = d.indexOf(" ", currSpace + 1);

                    int nextVal;

                    if (nextSpace != -1) {
                        nextVal = Integer.parseInt(d.substring(currSpace + 1, nextSpace));
                    } else {
                        nextVal = Integer.parseInt(d.substring(currSpace + 1, d.length()));
                    }

                    teamRes[i] = nextVal;
                    currSpace = nextSpace;
                }
            }

            LeagueTeam newTeam = new LeagueTeam(teamName, teamRating, teamRes);
            teamList.add(newTeam);
        }
    }

    private void parseOptions(ArrayList<String> optionsData) {

        chartFileName = "StdHA.txt";
        timesPlay = 2;

        for (String d : optionsData) {

            int separator = d.indexOf(":");

            if (separator > -1) {

                String optionName = d.substring(0, separator);
                String optionValue = d.substring(separator + 2, d.length());

                switch (optionName) {
                    case "Dice Chart":
                        chartFileName = optionValue;
                        break;
                    case "Play Each Team":
                        timesPlay = Integer.parseInt(optionValue);
                        break;
                    default:
                        System.out.println("Invalid Option: " + optionName);
                        break;
                }
            }
        }
    }
}

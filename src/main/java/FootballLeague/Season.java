package FootballLeague;


import Football.Dice;
import Football.DiceSelection;
import Football.ReadFile;
import Football.WriteFile;

import java.util.ArrayList;

public class Season {

    private int noOfDice;
    private String[] diceColours;
    private int[] diceFaces;
    private int[][] diceValues;

    private ArrayList<Dice> diceSet;

    private int noOfTeams;
    private ArrayList<LeagueTeam> teamList;

    private ArrayList<DiceSelection> diceChart;
    private int chartRows;

    private int timesPlay;

    private ArrayList<LeagueMatch> matchList;

    private String chartFileName;

    public Season(ArrayList<String> diceData, int inNoOfDice, ArrayList<String> chartData, int chartLines, ArrayList<String> teamData,
                  int inNoOfTeams, ArrayList<String> optionsData, int optionsLines)
    {

        parseOptions(optionsData, optionsLines);

        // timesPlay = 1; // TODO change to allow input

        noOfDice = inNoOfDice;

        diceColours = new String[noOfDice];
        diceValues = new int[noOfDice][6];
        diceFaces = new int[noOfDice];
        decodeDice(diceData);
        diceSet = new ArrayList<Dice>();

        for (int i = 0; i < noOfDice; i++) {
            int[] thisDieVals = new int[diceFaces[i]];
            for (int j = 0; j < diceFaces[i]; j++) {
                thisDieVals[j] = diceValues[i][j];
            }

            Dice newDie = new Dice(diceColours[i], diceFaces[i], thisDieVals);
            diceSet.add(newDie);
        }

        diceChart = new ArrayList<DiceSelection>();
        chartRows = chartLines;
        decodeChart(chartData);

        noOfTeams = inNoOfTeams;

        teamList = new ArrayList<LeagueTeam>();
        decodeTeams(teamData);

        matchList = new ArrayList<LeagueMatch>();

        runSeason();

        Sort sort = new Sort(teamList);

        printSeason(sort.sortTeams());
    }

    public void printSeason(ArrayList<LeagueTeam> sortedTeams) {
        String fileName = "C:\\Users\\User\\Documents\\Other\\Programmes\\footballSeason\\FinalTable.txt";

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

        String tempChartFileName = "StdHA.txt"; // need to implement Options file properly

        String diceFilePath = "C:\\Users\\User\\Documents\\Other\\Programmes\\footballSeason\\Dice Values.txt";
        String chartFilePath = "C:\\Users\\User\\Documents\\Other\\Programmes\\footballSeason\\" + tempChartFileName;
        String teamFilePath = "C:\\Users\\User\\Documents\\Other\\Programmes\\footballSeason\\Teams.txt";
        String optionsPath = System.getProperty("user.dir") + "\\footballSeason\\Options.txt";

        ArrayList<String> diceData;
        int inNoOfDice;
        ArrayList<String> chartData;
        int chartLines;
        ArrayList<String> teamData;
        int inNoOfTeams;
        ArrayList<String> optionsData;
        int optionsLines;

        ReadFile diceFile = new ReadFile(diceFilePath);
        ReadFile chartFile = new ReadFile(chartFilePath);
        ReadFile teamFile = new ReadFile(teamFilePath);
        ReadFile optionsFile = new ReadFile(optionsPath);

        diceData = diceFile.readFromFile();
        inNoOfDice = diceData.size();

        chartData = chartFile.readFromFile();
        chartLines = chartData.size();

        teamData = teamFile.readFromFile();
        inNoOfTeams = teamData.size();

        optionsData = optionsFile.readFromFile();
        optionsLines = optionsData.size();

        Season season = new Season(diceData, inNoOfDice, chartData, chartLines, teamData, inNoOfTeams, optionsData, optionsLines);

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

        // System.out.println(team1.getName() + " (" + rating1 + ") v (" + rating2 + ") " + team2.getName() + "; DiceSelection: " + matchDice.toString());

        match.setScore(team1Score, team2Score);

        team1.addMatch(match);
        team2.addMatch(match);

        // add ranking pts

        matchList.add(match);

    }

    private DiceSelection calcDice(int ratingDiff, boolean isNeutral) {

        boolean negRD = (ratingDiff < 0) ? true : false;

        float fRatingDiff = Math.abs((float) ratingDiff);

        int ratingGap = Math.round(fRatingDiff/100);

        ratingGap = negRD ? ratingGap * -1 : ratingGap;

//deal with null

        DiceSelection diceSelection = findDiceSelection(ratingGap);

        return diceSelection;

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

    private void decodeDice(ArrayList<String> diceData) {
        for (int i = 0; i < noOfDice; i++) {

            boolean stillParsing = true;

            int counter = 0;

            int currSpace = diceData.get(i).indexOf(" ");
            int nextSpace = currSpace;

            diceColours[i] = diceData.get(i).substring(0, currSpace);

            while (stillParsing) {

                nextSpace = diceData.get(i).indexOf(" ", currSpace + 1);
                int nextVal = 0;
                if (nextSpace != -1) {
                    nextVal = Integer.parseInt(diceData.get(i).substring(currSpace+1, nextSpace));
                    currSpace = nextSpace;
                } else {
                    nextVal = Integer.parseInt(diceData.get(i).substring(currSpace + 1));
                    stillParsing = false;
                }

                diceValues[i][counter] = nextVal;

                counter++;
            }

            diceFaces[i] = counter;

        }
    }

    private void decodeChart(ArrayList<String> chartData) {

        for (String d : chartData) {

            ArrayList<Dice> currLineHome = new ArrayList<Dice>();
            ArrayList<Dice> currLineAway = new ArrayList<Dice>();

            boolean stillParsing = true;

            int currSpace = d.indexOf(" ");
            int nextSpace = currSpace;

            int versus = d.indexOf("v");

            int ratingGap = Integer.parseInt(d.substring(0, currSpace));

            while (stillParsing) {
                nextSpace = d.indexOf(" ", currSpace + 1);
                String diceName = d.substring(currSpace+1, nextSpace);
                if (!diceName.equals("v")) {
                    Dice die = findDie(diceName); // TODO Need to add check in case of error
                    currLineHome.add(die);
                } else {
                    stillParsing = false;
                }
                currSpace = nextSpace;
            }

            stillParsing = true;

            while (stillParsing) {
                nextSpace = d.indexOf(" ", currSpace + 1);

                String diceName = "";
                if (nextSpace != -1) {
                    diceName = d.substring(currSpace+1, nextSpace);
                } else {
                    diceName = d.substring(currSpace+1);
                    stillParsing = false;
                }

                Dice die = findDie(diceName); // Need to add check in case of error
                if (die.equals(null)) {
                }

                currLineAway.add(die);
                currSpace = nextSpace;
            }

            DiceSelection newDiceSelection = new DiceSelection(ratingGap, currLineHome, currLineAway);
            diceChart.add(newDiceSelection);

        }
    }

    private void decodeTeams(ArrayList<String> teamData) {
        for (String d : teamData) {

            int startName = d.indexOf("$");
            int endName = d.indexOf("$", startName + 1);

            String teamName = d.substring(startName+1, endName);

            int currSpace = d.indexOf(" ", endName);

            int nextSpace = d.indexOf(" ", currSpace + 1);

            int teamRating = Integer.parseInt(d.substring(currSpace + 1, nextSpace));

            currSpace = nextSpace;

            int[] teamRes = new int[8];
            for (int i = 0; i < 8; i++) {

                nextSpace = d.indexOf(" ", currSpace + 1);

                int nextVal = 0;

                if (nextSpace != -1) {
                    nextVal = Integer.parseInt(d.substring(currSpace + 1, nextSpace));
                } else {
                    nextVal = Integer.parseInt(d.substring(currSpace + 1, d.length()));
                }

                teamRes[i] = nextVal;
                currSpace = nextSpace;

            }
            LeagueTeam newTeam = new LeagueTeam(teamName, teamRating, teamRes);

            teamList.add(newTeam);

        }
    }

    private void parseOptions(ArrayList<String> optionsData, int optionsLines) {

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

package FootballCup;

import Football.Dice;
import Football.DiceFunctions;
import Football.DiceSelection;
import Football.ReadFile;
import Football.WriteFile;

import java.util.ArrayList;

public class Cup {

    private ArrayList<Dice> diceSet;
    private ArrayList<CupTeam>;
    private ArrayList<DiceSelection> homeAwayDiceChart;
    private ArrayList<DiceSelection> neutralDiceChart;
    private ArrayList<CupMatch> matchList;

    public Cup(ArrayList<String> diceData, ArrayList<String> homeAwayChartData, ArrayList<String> neutralChartData,
                  ArrayList<String> teamData, ArrayList<String> optionsData) {

        parseOptions(optionsData);

        diceSet = decodeDice(diceData);
        homeAwayDiceChart = decodeChart(homeAwayChartData);
        neutralDiceChart = decodeChart(neutralChartData);
        
        teamList = decodeTeams(teamData);
        
        matchList = new ArrayResult<>();
        
        runCup();
    }

    public static void main(String[] args) throws IOException {

        String filepath = "..\\..\\..\\";
    
        String diceFilePath = filepath + "Dice Values.txt";
        String homeAwayChartFilePath = filepath + "StdHA.txt";
        String neutralChartFilePath = filepath + "StdN.txt";
        String teamFilePath = filepath + "Cup Teams.txt";
        String optionsPath = filepath + "Options.txt";

        ReadFile diceFile = new ReadFile(diceFilePath);
        ReadFile homeAwayChartFile = new ReadFile(homeAwayChartFilePath);
        ReadFile neutralChartFile = new ReadFile(neutralChartFilePath);
        ReadFile teamFile = new ReadFile(teamFilePath);
        ReadFile optionsFile = new ReadFile(optionsPath);

        ArrayList<String> diceData = diceFile.readFromFile();
        ArrayList<String> homeAwayChartData = homeAwayChartFile.readFromFile();
        ArrayList<String> neutralChartData = neutralChartFile.readFromFile();
        ArrayList<String> teamData = teamFile.readFromFile();
        ArrayList<String> optionsData = optionsFile.readFromFile();

		Cup cup = new Cup(diceData, homeAwayChartData, neutralChartData, teamData, optionsData);
        
    }
    
    private void runCup() {
        
        
        
    }

    private void playMatch(CupTeam team1, CupTeam team2) {
        
    }
    
    
    private ArrayList<Dice> decodeDice(ArrayList<String> diceData) {
        return DiceFunctions.decodeDice(diceData);
    }
    
    private ArrayList<DiceSelection> decodeChart(ArrayList<String> chartData) {
        return DiceFunctions.decodeChart(chartData, diceSet);
    }
    
    private void parseOptions(ArrayList<String> optionsData) {
        
    }
    
    private ArrayList<CupTeam> decodeTeams(ArrayList<String> teamData) {
        
    }
}

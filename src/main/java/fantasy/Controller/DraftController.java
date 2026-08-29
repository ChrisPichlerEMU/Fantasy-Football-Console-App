package fantasy.Controller;

import fantasy.Enums.Position;
import fantasy.Models.DraftBoard;
import fantasy.Models.Player;
import fantasy.Models.Team;
import fantasy.Utility.UserInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public record DraftController(DraftBoard draft, UserInput userInput) {

    public void setInitialDraftBoard() throws FileNotFoundException {
        String inputFileName = "Draft Board Input File.txt";
        Scanner inputFileScanner = new Scanner(new File(inputFileName));

        ArrayList<Player> playersInInputFile = new ArrayList<>();

        while (inputFileScanner.hasNext()) {
            String nextLine = inputFileScanner.nextLine();
            String[] nextLineSeparatedByComma = nextLine.split(",");
            playersInInputFile.add(getPlayerFromSplitLineOfInput(nextLineSeparatedByComma));
        }

        draft.setUndraftedPlayers(playersInInputFile);

        Team[] teamsInDraft = new Team[draft.getNumberOfTeams()];

        for (int i = 0; i < teamsInDraft.length; i++) {
            teamsInDraft[i] = new Team("Team " + (i + 1));
        }

        draft.setTeams(teamsInDraft);
    }

    public void run() {
        explainRulesOfProgram();

        while (!draft.getUndraftedPlayers().isEmpty()) {
            System.out.println("\nRound: " + draft.getRound() + ", Pick: " + draft.getCurrentPickInRound() + " - " + draft.getTeams()[draft.getCurrentTeamPicking() - 1].getName());

            String nextAction = userInput().getNextActionFromUser();

            boolean isValidInput = doUserInputAction(nextAction);
        }
    }

    private void explainRulesOfProgram() {
        System.out.println("RULES OF PROGRAM:" +
                "\nAfter each action is complete, you will be asked for user input, the following are your options for input:" +
                "\nType \"Show Players\" or just click Enter to show the top 30 players in the rankings." +
                "\nType a player's last name to draft a player to the next team picking." +
                "\nType a position (\"QB\", \"RB\", \"WR\", \"TE\", or \"DEF\") to print the top 20 players in that position. " +
                "\nType \"Show Teams\" to view the number of each position that each team in the draft currently has on their team." +
                "\nType \"Name Team\" followed by an eligible draft position to name a specific team for when \"Show Teams\" is selected." +
                "\nType \"Rules\" at any time to view this rule block again.\n");
    }

    private boolean doUserInputAction(String input) {
        if (input.equalsIgnoreCase("Show Players") || input.isEmpty()) {
            printTopOfDraftBoard();
            return true;
        }
        else if (input.equalsIgnoreCase("Show Teams")) {
            showPositionGroupOfEachTeam();
            return true;
        }
        else if (input.equalsIgnoreCase("Name Team")) {
            setNameOfTeam();
            return true;
        }
        else if (input.equalsIgnoreCase("Rules")) {
            explainRulesOfProgram();
            return true;
        }
        else if (checkIfInputIsAPosition(input)) {
            return true;
        }

        ArrayList<Player> undraftedPlayersInDraft = draft.getUndraftedPlayers();
        ArrayList<Integer> indexesOfPlayersWithLastNameOfUserInput = new ArrayList<>();
        ArrayList<String> firstNamesOfPlayersWithLastNameOfUserInput = new ArrayList<>();

        for (int i = 0; i < undraftedPlayersInDraft.size(); i++) {
            if (input.equalsIgnoreCase(undraftedPlayersInDraft.get(i).getLastName())) {
                indexesOfPlayersWithLastNameOfUserInput.add(i);
                firstNamesOfPlayersWithLastNameOfUserInput.add(undraftedPlayersInDraft.get(i).getFirstName());
            }
        }

        if (indexesOfPlayersWithLastNameOfUserInput.size() == 1) {
            removePlayerFromDraftBoard(indexesOfPlayersWithLastNameOfUserInput.getFirst());
            return true;
        }
        else if (indexesOfPlayersWithLastNameOfUserInput.isEmpty()) {
            System.out.println(input + " is not a valid input. Please try again.");
            return false;
        }

        return resolveMultiplePlayersHavingLastNameOfDraftedPlayer(indexesOfPlayersWithLastNameOfUserInput, firstNamesOfPlayersWithLastNameOfUserInput, input);
    }

    private void printTopOfDraftBoard() {
        int numberOfPlayersPrinted = Math.min(draft.getUndraftedPlayers().size(), 30);

        System.out.println("-----------------------------------------------");

        for (int i = 0; i < numberOfPlayersPrinted; i++) {
            System.out.println(draft.getUndraftedPlayers().get(i));
        }
    }

    private void showPositionGroupOfEachTeam() {
        int x = 5;
    }

    private void setNameOfTeam() {
        int x = 5;
    }

    private boolean checkIfInputIsAPosition(String input) {
        switch (input) {
            case "QB":
                printSpecificPosition(Position.QB);
                return true;
            case "RB":
                printSpecificPosition(Position.RB);
                return true;
            case "WR" :
                printSpecificPosition(Position.WR);
                return true;
            case "TE":
                printSpecificPosition(Position.TE);
                return true;
            case "DEF":
                printSpecificPosition(Position.DEF);
                return true;
        };

        return false;
    }

    private void printSpecificPosition(Position position) {
        int playersPrinted = 0;

        ArrayList<Player> undraftedPlayers = draft.getUndraftedPlayers();

        for (int i = 0; i < undraftedPlayers.size() && playersPrinted < 20; i++) {
            Player nextPlayer = undraftedPlayers.get(i);

            if (nextPlayer.getPosition() == position) {
                System.out.println(nextPlayer);
                playersPrinted++;
            }
        }
    }

    private Player getPlayerFromSplitLineOfInput(String[] lineOfInputText) {
        String lastName = lineOfInputText[0];
        String firstName = lineOfInputText[1].trim();
        String team = lineOfInputText[2].trim();
        int rank = Integer.parseInt(lineOfInputText[3].trim());
        int tier = Integer.parseInt(lineOfInputText[4].trim());
        String positionString = lineOfInputText[5].trim();
        Position position = getPositionFromString(positionString);
        String[] attributes;

        if (lineOfInputText.length > 6) {
            attributes = Arrays.copyOfRange(lineOfInputText, 6, lineOfInputText.length);
        } else {
            attributes = new String[0];
        }

        return new Player(firstName, lastName, team, rank, tier, position, attributes);
    }

    private Position getPositionFromString(String str) {
        return switch (str) {
            case "QB" -> Position.QB;
            case "RB" -> Position.RB;
            case "WR" -> Position.WR;
            case "TE" -> Position.TE;
            case "DEF" -> Position.DEF;
            default -> throw new InputMismatchException(str + " is not a valid position passed into the GetPositionFromString method.");
        };
    }

    private void removePlayerFromDraftBoard(int indexOfPlayerToBeRemoved) {
        Player playerThatJustGotDrafted = draft.getUndraftedPlayers().get(indexOfPlayerToBeRemoved);
        draft.getTeams()[draft.getCurrentTeamPicking() - 1].getTeam().add(playerThatJustGotDrafted);
        draft.getUndraftedPlayers().remove(indexOfPlayerToBeRemoved);
        System.out.println(playerThatJustGotDrafted.getFullName() + " has been drafted.");
        setValueForNextPick();
    }

    private boolean resolveMultiplePlayersHavingLastNameOfDraftedPlayer(
            ArrayList<Integer> indexesOfPlayersWithLastNameOfUserInput,
            ArrayList<String> firstNamesOfPlayersWithLastNameOfUserInput,
            String input)
    {
        String firstNameOfDraftedPlayer = userInput.resolvePlayersHavingSameLastName(indexesOfPlayersWithLastNameOfUserInput, firstNamesOfPlayersWithLastNameOfUserInput, input);

        for (int i = 0; i < firstNamesOfPlayersWithLastNameOfUserInput.size(); i++) {
            if (firstNamesOfPlayersWithLastNameOfUserInput.get(i).equalsIgnoreCase(firstNameOfDraftedPlayer)) {
                removePlayerFromDraftBoard(indexesOfPlayersWithLastNameOfUserInput.get(i));
                return true;
            }
        }

        return false;
    }

    private void setValueForNextPick() {
        if (draft.isSnakeDraft()) {
            if (draft.getCurrentTeamPicking() < draft.getNumberOfTeams() && draft.isDraftOrderIsAscending()) {
                draft.setCurrentTeamPicking(draft.getCurrentTeamPicking() + 1);
                draft.setCurrentPickInRound(draft.getCurrentPickInRound() + 1);
            } else if (draft.getCurrentTeamPicking() > 1 && !draft.isDraftOrderIsAscending()) {
                draft.setCurrentTeamPicking(draft.getCurrentTeamPicking() - 1);
                draft.setCurrentPickInRound(draft.getCurrentPickInRound() + 1);
            } else if (draft.getCurrentTeamPicking() == draft.getNumberOfTeams() && draft.isDraftOrderIsAscending()) {
                draft.setDraftOrderIsAscending(false);
                draft.setRound(draft.getRound() + 1);
                draft.setCurrentPickInRound(1);
            } else if (draft.getCurrentTeamPicking() == 1 && !draft.isDraftOrderIsAscending()) {
                draft.setDraftOrderIsAscending(true);
                draft.setRound(draft.getRound() + 1);
                draft.setCurrentPickInRound(1);
            } else {
                throw new RuntimeException("The SetValueForNextPick method reached code that should be unreachable during a snake draft. Current team picking = " + draft.getCurrentTeamPicking()
                        + ", Number of teams = " + draft.getNumberOfTeams() + ", DraftOrderIsAscending = " + draft.isDraftOrderIsAscending());
            }
        } else {
            if (draft.getCurrentTeamPicking() < draft.getNumberOfTeams()) {
                draft.setCurrentTeamPicking(draft.getCurrentTeamPicking() + 1);
            } else if (draft.getCurrentTeamPicking() == draft.getNumberOfTeams()) {
                draft.setCurrentTeamPicking(1);
                draft.setRound(draft.getRound() + 1);
            } else {
                throw new RuntimeException("The SetValueForNextPick method reached code that should be unreachable during a non-snake draft. Current team picking = " + draft.getCurrentTeamPicking()
                        + ", Number of teams = " + draft.getNumberOfTeams());
            }
        }
    }
}

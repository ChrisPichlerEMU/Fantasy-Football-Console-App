package fantasy.Controller;

import fantasy.Enums.Position;
import fantasy.Models.DraftBoard;
import fantasy.Models.Player;
import fantasy.Models.Team;
import fantasy.Utility.UserInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public record DraftController(DraftBoard draft, UserInput userInput) {

    public void setInitialDraftBoard() throws FileNotFoundException {
        String inputFileName = "Draft Board Input File.txt";
        Scanner inputFileScanner = new Scanner(new File(inputFileName));

        while (inputFileScanner.hasNext()) {
            String nextLine = inputFileScanner.nextLine();
            String[] nextLineSeparatedByComma = nextLine.split(",");
            draft.getUndraftedPlayers().add(getPlayerFromSplitLineOfInput(nextLineSeparatedByComma));
        }

        Team[] teamsInDraft = new Team[draft.getNumberOfTeams()];

        for (int i = 0; i < teamsInDraft.length; i++) {
            teamsInDraft[i] = new Team();
        }

        draft.setTeams(teamsInDraft);
    }

    public void run() {
        if (draft.isSnakeDraft())
            System.out.println("Starting the snake draft, good luck!");
        else
            System.out.println("Starting the non-snake draft, good luck!");

        while (!draft.getUndraftedPlayers().isEmpty()) {
            boolean isValidInput = getInputFromUser();

            if (!isValidInput)
                continue;

            setValueForNextPick();
        }
    }

    private Player getPlayerFromSplitLineOfInput(String[] lineOfInputText) {
        String firstName = lineOfInputText[0];
        String lastName = lineOfInputText[1].trim();
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

    private boolean getInputFromUser() {
        System.out.println("\nRound: " + draft.getRound() + ", Pick: " + draft.getCurrentTeamPicking());
        printTopOfDraftBoard();

        String input = userInput.getNameOfNextDraftedPlayer();

        int indexOfChosenPlayer = getIndexOfInputString(input.trim());

        if (indexOfChosenPlayer == -1) {
            System.out.println("The input: " + input + " is invalid. Please try again.");
            return false;
        } else {
            removePlayerFromDraftBoard(indexOfChosenPlayer);
            return true;
        }
    }

    private void printTopOfDraftBoard() {
        int numberOfPlayersPrinted = Math.min(draft.getUndraftedPlayers().size(), 20);

        System.out.println("-----------------------------------------------");

        for (int i = 0; i < numberOfPlayersPrinted; i++) {
            System.out.println(draft.getUndraftedPlayers().get(i));
        }
    }

    private int getIndexOfInputString(String input) {
        for (int i = 0; i < draft.getUndraftedPlayers().size(); i++) {
            if (input.equalsIgnoreCase(draft.getUndraftedPlayers().get(i).getLastName()))
                return i;
        }

        return -1;
    }

    private void removePlayerFromDraftBoard(int indexOfPlayerToBeRemoved) {
        Player playerThatJustGotDrafted = draft.getUndraftedPlayers().get(indexOfPlayerToBeRemoved);
        draft.getTeams()[draft.getCurrentTeamPicking() - 1].getTeam().add(playerThatJustGotDrafted);
        draft.getUndraftedPlayers().remove(indexOfPlayerToBeRemoved);
        System.out.println(playerThatJustGotDrafted.getFullName() + " has been drafted.");
    }

    private void setValueForNextPick() {
        if (draft.isSnakeDraft()) {
            if (draft.getCurrentTeamPicking() < draft.getNumberOfTeams() && draft.isDraftOrderIsAscending()) {
                draft.setCurrentTeamPicking(draft.getCurrentTeamPicking() + 1);
            } else if (draft.getCurrentTeamPicking() > 1 && !draft.isDraftOrderIsAscending()) {
                draft.setCurrentTeamPicking(draft.getCurrentTeamPicking() - 1);
            } else if (draft.getCurrentTeamPicking() == draft.getNumberOfTeams() && draft.isDraftOrderIsAscending()) {
                draft.setDraftOrderIsAscending(false);
                draft.setRound(draft.getRound() + 1);
            } else if (draft.getCurrentTeamPicking() == 1 && !draft.isDraftOrderIsAscending()) {
                draft.setDraftOrderIsAscending(true);
                draft.setRound(draft.getRound() + 1);
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

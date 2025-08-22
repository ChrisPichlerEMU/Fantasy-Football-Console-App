package fantasy.Utility;

import fantasy.Models.DraftSettings;

import java.util.Scanner;

public class UserInput {
    private final Scanner scanner;

    public UserInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public DraftSettings getDraftSettings() {
        int numberOfTeamsInDraft = getNumberOfTeamsInDraft();
        boolean isSnakeDraft = getIsSnakeDraft();

        return new DraftSettings(numberOfTeamsInDraft, isSnakeDraft);
    }

    public String getNameOfNextDraftedPlayer() {
        System.out.print("Please enter the last name of a player that's been drafted: ");
        return scanner.nextLine();
    }

    private int getNumberOfTeamsInDraft() {
        while (true) {
            System.out.print("Please enter the number of teams in the league: ");
            String input = scanner.next();
            try {
                int inputAsInteger = Integer.parseInt(input);

                if (inputAsInteger > 1)
                    return inputAsInteger;

                System.out.println("Please enter a number greater than 1.");
            } catch (NumberFormatException ex) {
                System.out.println(input + " is not a valid number. Please try again.");
            }
        }
    }

    private boolean getIsSnakeDraft() {
        while (true) {
            System.out.print("Please enter 'true' if the draft is a snake draft, or 'false' if the draft is not a snake draft: ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("true")) {
                scanner.nextLine(); // Throw away empty line before starting draft
                return true;
            }
            else if (input.equalsIgnoreCase("false")) {
                scanner.nextLine(); // Throw away empty line before starting draft
                return false;
            }

            System.out.println(input + " is not a valid input. Either type 'true' or 'false'. Please try again.");
        }
    }
}

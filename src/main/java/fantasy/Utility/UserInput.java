package fantasy.Utility;

import fantasy.Models.DraftSettings;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInput {
    private final Scanner scanner;

    public UserInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public DraftSettings getDraftSettings() {
        int numberOfTeamsInDraft = getNumberOfTeamsInDraft();
        boolean isSnakeDraft = getIsSnakeDraft();
        int draftPickOfUser = getDraftPickOfUser(numberOfTeamsInDraft);

        return new DraftSettings(numberOfTeamsInDraft, isSnakeDraft, draftPickOfUser);
    }

    public String getNextActionFromUser() {
        System.out.print("\nPlease type the last name of the next drafted player, a position, \"Show Players\" (or click Enter), \"Show Teams\", \"Name Team\" or \"Rules\": ");
        return scanner.nextLine();
    }

    public String resolvePlayersHavingSameLastName(ArrayList<Integer> indexesOfPlayersWithSameLastName, ArrayList<String> firstNames, String lastName) {
        System.out.print("There are " +  indexesOfPlayersWithSameLastName.size() + " players with the last name of " + lastName + ". " +
                "Valid options are: " + String.join(", ", firstNames) + ". Please enter the first name of the player you want to be drafted next " +
                "or type anything else to return to the main menu: ");

        return scanner.nextLine();
    }

    private int getNumberOfTeamsInDraft() {
        while (true) {
            System.out.print("Please enter the number of teams in the league: ");
            String input = scanner.next();
            try {
                int inputAsInteger = Integer.parseInt(input);

                if (inputAsInteger > 1) {
                    scanner.nextLine(); // Throw away empty line
                    return inputAsInteger;
                }

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
                scanner.nextLine(); // Throw away empty line
                return true;
            }
            else if (input.equalsIgnoreCase("false")) {
                scanner.nextLine(); // Throw away empty line
                return false;
            }

            System.out.println(input + " is not a valid input. Either type 'true' or 'false'. Please try again.");
        }
    }

    private int getDraftPickOfUser(int numberOfTeams) {
        while (true) {
            System.out.print("Please enter the number draft pick you have in the first round: ");
            String input = scanner.next();
            try {
                int inputAsInteger = Integer.parseInt(input);

                if (!(inputAsInteger < 1 || inputAsInteger > numberOfTeams)) {
                    scanner.nextLine(); // Throw away empty line
                    return inputAsInteger;
                }

                System.out.println("Please enter a number between 1 and " + numberOfTeams);
            } catch (NumberFormatException ex) {
                System.out.println(input + " is not a valid number. Please try again.");
            }
        }
    }
}

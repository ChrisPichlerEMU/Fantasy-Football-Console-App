package fantasy;

import fantasy.Controller.DraftController;
import fantasy.Models.DraftBoard;
import fantasy.Models.DraftSettings;
import fantasy.Utility.UserInput;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        UserInput userInput = new UserInput(new Scanner(System.in));
        DraftSettings draftSettings = userInput.getDraftSettings();
        DraftController draft = new DraftController(new DraftBoard(draftSettings), userInput);
        draft.setInitialDraftBoard();
        draft.run();

    }
}

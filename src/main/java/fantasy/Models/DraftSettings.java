package fantasy.Models;

public class DraftSettings {
    private int numberOfTeams;
    private boolean isSnakeDraft;

    public DraftSettings(int numberOfTeams, boolean isSnakeDraft) {
        this.numberOfTeams = numberOfTeams;
        this.isSnakeDraft = isSnakeDraft;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public boolean isSnakeDraft() {
        return isSnakeDraft;
    }
}

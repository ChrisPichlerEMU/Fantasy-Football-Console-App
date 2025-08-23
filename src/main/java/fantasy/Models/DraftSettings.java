package fantasy.Models;

public class DraftSettings {
    private int numberOfTeams;
    private boolean isSnakeDraft;
    private int draftPickOfUser;

    public DraftSettings(int numberOfTeams, boolean isSnakeDraft, int draftPickOfUser) {
        this.numberOfTeams = numberOfTeams;
        this.isSnakeDraft = isSnakeDraft;
        this.draftPickOfUser = draftPickOfUser;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public boolean isSnakeDraft() {
        return isSnakeDraft;
    }

    public int getDraftPickOfUser() {
        return draftPickOfUser;
    }
}

package fantasy.Models;

import java.util.ArrayList;

public class DraftBoard {
    private ArrayList<Player> undraftedPlayers;
    private Team[] teams;
    private int draftPickOfUser;
    private int round;
    private int currentPickInRound;
    private int currentTeamPicking;
    private int numberOfTeams;
    private final boolean isSnakeDraft;
    private boolean draftOrderIsAscending;

    public DraftBoard(DraftSettings draftSettings) {
        undraftedPlayers = new ArrayList<>();
        draftPickOfUser = draftSettings.getDraftPickOfUser();
        round = 1;
        currentPickInRound = 1;
        currentTeamPicking = 1;
        this.numberOfTeams = draftSettings.getNumberOfTeams();
        teams = new Team[numberOfTeams];
        this.isSnakeDraft = draftSettings.isSnakeDraft();
        draftOrderIsAscending = true;
    }

    public ArrayList<Player> getUndraftedPlayers() {
        return undraftedPlayers;
    }

    public void setUndraftedPlayers(ArrayList<Player> undraftedPlayers) {
        this.undraftedPlayers = undraftedPlayers;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public int getDraftPickOfUser() {
        return draftPickOfUser;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getCurrentPickInRound() {
        return currentPickInRound;
    }

    public void setCurrentPickInRound(int currentPickInRound) {
        this.currentPickInRound = currentPickInRound;
    }

    public int getCurrentTeamPicking() {
        return currentTeamPicking;
    }

    public void setCurrentTeamPicking(int currentTeamPicking) {
        this.currentTeamPicking = currentTeamPicking;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public boolean isSnakeDraft() {
        return isSnakeDraft;
    }

    public boolean isDraftOrderIsAscending() {
        return draftOrderIsAscending;
    }

    public void setDraftOrderIsAscending(boolean draftOrderIsAscending) {
        this.draftOrderIsAscending = draftOrderIsAscending;
    }
}

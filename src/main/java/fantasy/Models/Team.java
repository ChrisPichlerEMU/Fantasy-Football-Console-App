package fantasy.Models;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> team;

    public Team() {
        team = new ArrayList<>();
    }

    public ArrayList<Player> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Player> team) {
        this.team = team;
    }
}
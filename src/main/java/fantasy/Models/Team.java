package fantasy.Models;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> team;
    private String name;

    public Team(String name) {
        team = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<Player> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Player> team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
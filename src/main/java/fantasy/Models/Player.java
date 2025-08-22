package fantasy.Models;

import fantasy.Enums.Position;

public class Player {
    private String firstName;
    private String lastName;
    private String team;
    private int rank;
    private int tier;
    private Position position;
    private String[] attributes;

    public Player() {}

    public Player(String firstName, String lastName, String team, int rank, int tier, Position position, String[] attributes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.rank = rank;
        this.tier = tier;
        this.position = position;
        this.attributes = attributes;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        String playerMinusAttributes = firstName + " " + lastName + ", " + position.toString() + ", " + team + ", Rank: " + rank + ", Tier: " + tier;

        if (attributes.length == 0) {
            return playerMinusAttributes;
        }

        String playerAttributes = String.join(",", attributes);

        return playerMinusAttributes + ", Attributes:" + playerAttributes;
    }
}

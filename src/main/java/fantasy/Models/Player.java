package fantasy.Models;

import fantasy.Enums.Position;

public class Player {
    private String firstName;
    private String lastName;
    private String team;
    private int rank;
    private int positionalRanking;
    private Position position;
    private String[] attributes;

    public Player() {}

    public Player(String firstName, String lastName, String team, int rank, int positionalRanking, Position position, String[] attributes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.rank = rank;
        this.positionalRanking = positionalRanking;
        this.position = position;
        this.attributes = attributes;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        String playerMinusAttributes = firstName + " " + lastName + ", " + position.toString() + ", " + team + ", Rank: " + rank + ", Positional Rank: " + positionalRanking;

        if (attributes.length == 0) {
            return playerMinusAttributes;
        }

        String playerAttributes = String.join(",", attributes);

        return playerMinusAttributes + ", Attributes:" + playerAttributes;
    }
}

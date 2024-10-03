
public class Player {
    private int playerId;
    private int nationalId;
    private String playerName;
    private int highScore;
    private int level;

    public Player(int nationalId, String playerName, int highScore, int level) {
        this.nationalId = nationalId;
        this.playerName = playerName;
        this.highScore = highScore;
        this.level = level;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getNationalId() {
        return nationalId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLevel() {
        return level;
    }
}


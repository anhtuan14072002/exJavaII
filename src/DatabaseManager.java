import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/herogame";
        String user = "root";
        String password = "root"; // replace with your DB password
        connection = DriverManager.getConnection(url, user, password);
    }

    public void insertPlayer(Player player) throws SQLException {
        String query = "INSERT INTO Player (NationalId, PlayerName, HighScore, Level) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, player.getNationalId());
            ps.setString(2, player.getPlayerName());
            ps.setInt(3, player.getHighScore());
            ps.setInt(4, player.getLevel());
            ps.executeUpdate();
        }
    }

    public void deletePlayer(int playerId) throws SQLException {
        String query = "DELETE FROM Player WHERE PlayerId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, playerId);
            ps.executeUpdate();
        }
    }

    public List<Player> displayAll() throws SQLException {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM Player";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Player player = new Player(rs.getInt("NationalId"), rs.getString("PlayerName"),
                        rs.getInt("HighScore"), rs.getInt("Level"));
                player.setPlayerId(rs.getInt("PlayerId"));
                players.add(player);
            }
        }
        return players;
    }

    public List<Player> displayAllByPlayerName(String playerName) throws SQLException {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM Player WHERE PlayerName LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + playerName + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Player player = new Player(rs.getInt("NationalId"), rs.getString("PlayerName"),
                            rs.getInt("HighScore"), rs.getInt("Level"));
                    player.setPlayerId(rs.getInt("PlayerId"));
                    players.add(player);
                }
            }
        }
        return players;
    }

    public List<Player> displayTop10() throws SQLException {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM Player ORDER BY HighScore DESC LIMIT 10";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Player player = new Player(rs.getInt("NationalId"), rs.getString("PlayerName"),
                        rs.getInt("HighScore"), rs.getInt("Level"));
                player.setPlayerId(rs.getInt("PlayerId"));
                players.add(player);
            }
        }
        return players;
    }
}

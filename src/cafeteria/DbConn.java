package cafeteria;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConn {
        private static final String URL = "jdbc:postgresql://localhost:5438/cafeteria";
        private static final String USER = "senai";
        private static final String PASSWORD = "senai";
        private static Connection connection = null;

        public static Connection getConnection() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                }
            }
            return connection;
        }
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}

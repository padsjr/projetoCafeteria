package cafeteria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {
    private static final String URL = "jdbc:postgresql://localhost:5438/cafeteria";
    private static final String USER = "senai";
    private static final String PASSWORD = "senai";
    private static Connection connection = null;

    /**
     * Obtém uma conexão com o banco de dados. Reutiliza a conexão existente ou cria uma nova, se necessário.
     *
     * @return Connection - conexão com o banco de dados.
     */
    public static Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Fecha a conexão com o banco de dados, se estiver aberta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Libera a referência para que possa ser recriada, se necessário.
                System.out.println("Conexão com o banco de dados fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica se a conexão está fechada.
     *
     * @return boolean - true se a conexão estiver fechada ou nula, false caso contrário.
     */
    private static boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar o estado da conexão: " + e.getMessage());
            return true;
        }
    }
}

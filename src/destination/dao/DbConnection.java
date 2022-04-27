package destination.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import destination.exception.DestinationException;

/**
 * This class connects to the destinations schema in a MySQL database. See below
 * for valid credentials.
 * 
 * @author Promineo
 *
 */
public class DbConnection {
  private static final String HOST = "localhost";
  private static final int PORT = 3306;
  private static final String USERNAME = "destinations";
  private static final String PASSWORD = "destinations";
  private static final String SCHEMA = "destinations";

  /**
   * Ask DriverManager for a connection object.
   * 
   * @return The connection object.
   * @throws DestinationException Thrown if an error occurs obtaining the
   *         connection.
   */
  public static Connection getConnection() {
    // jdbc:mysql://host:port/schema?username=blah&password=blah
    String uri = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, SCHEMA);

    try {
      Connection conn = DriverManager.getConnection(uri, USERNAME, PASSWORD);
      return conn;
    }
    catch (SQLException e) {
      throw new DestinationException(e);
    }
  }
}

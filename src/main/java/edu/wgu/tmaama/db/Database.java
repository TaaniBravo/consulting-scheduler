package edu.wgu.tmaama.db;

import edu.wgu.tmaama.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Generic database class that allows for hot swapping out different service providers (MySQL,
 * PostgresQL, SQLServer, etc.)
 */
public class Database {
  private final Properties props;
  private Connection connection;

  public Database() throws SQLException {
    this.props = new Properties();
    this.establishConnection();
  }

  /**
   * If connection is null then the we will establish the database connection before returning it.
   *
   * @return
   * @throws SQLException
   */
  public Connection getConnection() throws SQLException {
    if (this.connection == null || this.connection.isClosed()) this.establishConnection();
    return connection;
  }

  /**
   * Checks if the connection is closed.
   *
   * @return
   * @throws SQLException
   */
  public boolean checkConnection() throws SQLException {
    return !this.connection.isClosed();
  }

  /**
   * Closes a database connection.
   *
   * @throws SQLException
   */
  public void closeConnection() throws SQLException {
    if (this.connection != null && this.checkConnection()) this.connection.close();
  }

  /** Loads the props from the config.properties file. */
  private void loadProps() {
    try (InputStream inputStream = Database.class.getResourceAsStream("/db/config.properties")) {
      this.props.load(inputStream);
    } catch (IOException ex) {
      ex.printStackTrace();
      System.out.println("Please create config.properties file specified in README.");
      System.exit(Constants.ERROR_FILE_NOT_FOUND_CODE);
    }
  }

  /**
   * Establishes a database connection.
   *
   * @throws SQLException
   */
  private void establishConnection() throws SQLException {
    if (this.props.size() == 0) this.loadProps();
    this.connection =
        DriverManager.getConnection(
            this.props.getProperty("db.url"),
            this.props.getProperty("db.user"),
            this.props.getProperty("db.password"));
  }
}

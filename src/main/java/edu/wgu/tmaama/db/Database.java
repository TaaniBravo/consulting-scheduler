package edu.wgu.tmaama.db;

import edu.wgu.tmaama.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
  private final Properties props;
  private Connection connection;

  public Database() throws SQLException {
    this.props = new Properties();
    this.establishConnection();
  }

  public Connection getConnection() throws SQLException {
    if (this.connection == null || this.connection.isClosed()) this.establishConnection();
    return connection;
  }

  public boolean checkConnection() throws SQLException {
    return !this.connection.isClosed();
  }

  public void closeConnection() throws SQLException {
    if (this.connection != null && this.checkConnection()) this.connection.close();
  }

  private void loadProps() {
    try (InputStream inputStream = Database.class.getResourceAsStream("/db/config.properties")) {
      this.props.load(inputStream);
    } catch (IOException ex) {
      ex.printStackTrace();
      System.out.println("Please create config.properties file specified in README.");
      System.exit(Constants.ERROR_FILE_NOT_FOUND_CODE);
    }
  }

  private void establishConnection() throws SQLException {
    if (this.props.size() == 0) this.loadProps();
    this.connection =
        DriverManager.getConnection(
            this.props.getProperty("db.url"),
            this.props.getProperty("db.user"),
            this.props.getProperty("db.password"));
  }
}

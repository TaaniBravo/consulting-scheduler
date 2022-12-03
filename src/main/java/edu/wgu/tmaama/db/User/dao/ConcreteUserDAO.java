package edu.wgu.tmaama.db.User.dao;

import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.User.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConcreteUserDAO implements UserDAO {
  private final Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteUserDAO() throws SQLException {}

  @Override
  public User insert(User user) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query = "INSERT INTO Users " + "(User_Name, Password, Created_By)" + " VALUES (?, ?, ?)";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, user.getUsername());
    stmt.setString(2, user.getPassword());
    stmt.setString(3, user.getCreatedBy());
    ResultSet results = stmt.executeQuery();
    User newUser = null;
    if (results.next()) newUser = this.getInstanceFromResultSet(results);
    this.db.closeConnection();
    return newUser;
  }

  @Override
  public User findByID(int id) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query = "SELECT * FROM Users WHERE User_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    ResultSet result = stmt.executeQuery();
    User user = null;
    if (result.next()) {
      user = this.getInstanceFromResultSet(result);
    }
    this.db.closeConnection();
    return user;
  }

  @Override
  public ArrayList<User> findAll() throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    ArrayList<User> users = new ArrayList<>();
    String query = "SELECT * FROM Users";
    ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
    while (resultSet.next()) {
      users.add(this.getInstanceFromResultSet(resultSet));
    }
    this.db.closeConnection();
    return users;
  }

  @Override
  public User update(User user) throws SQLException {
    String query =
        "UPDATE Users SET "
            + "User_Name = ?,"
            + "Password = ?,"
            + "Last_Updated_By = ?,"
            + "WHERE User_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, user.getUsername());
    stmt.setString(2, user.getPassword());
    stmt.setString(3, user.getLastUpdatedBy());
    stmt.setInt(4, user.getUserID());
    ResultSet results = stmt.executeQuery();
    User updatedUser = null;
    if (results.next()) updatedUser = this.getInstanceFromResultSet(results);
    this.db.closeConnection();
    return updatedUser;
  }

  @Override
  public boolean deleteByID(int id) throws SQLException {
    String query = "DELETE FROM Users WHERE User_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

  @Override
  public User getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new User(
        resultSet.getInt("User_ID"),
        resultSet.getString("User_Name"),
        resultSet.getString("Password"),
        resultSet.getTimestamp("Create_Date"),
        resultSet.getString("Created_By"),
        resultSet.getTimestamp("Last_Update"),
        resultSet.getString("Last_Updated_By"));
  }
}

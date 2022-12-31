package edu.wgu.tmaama.db.User.dao;

import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.Salt.dao.ConcreteSaltDAO;
import edu.wgu.tmaama.db.Salt.model.Salt;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.Password;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** Concrete Data Access Object for Users table. */
public class ConcreteUserDAO implements UserDAO {
  private Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteUserDAO() throws SQLException {}

  public ConcreteUserDAO(Database db) throws SQLException {
    this.db = db;
    this.cxn = this.db.getConnection();
  }

  /**
   * Tries to insert a user and the salt that was used to hash their password into the database.
   *
   * @param user
   * @return
   * @throws SQLException
   */
  @Override
  public User insert(User user) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      this.cxn.setAutoCommit(false);
      String userQuery =
          "INSERT INTO Users " + "(User_Name, Password, Created_By)" + " VALUES (?, ?, ?)";
      PreparedStatement userStmt =
          this.cxn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
      userStmt.setString(1, user.getUsername());
      userStmt.setString(2, user.getPassword().getHash());
      userStmt.setString(3, user.getCreatedBy());
      int affectedRows = userStmt.executeUpdate();
      if (affectedRows == 0)
        throw new SQLException("Creating a new user failed, please try again.");
      ResultSet results = userStmt.getGeneratedKeys();
      assert results != null;
      if (results.next()) user.setUserID(results.getInt(1));
      Salt salt = user.getPassword().getSalt();
      salt.setUserID(user.getUserID());
      ConcreteSaltDAO saltDAO = new ConcreteSaltDAO(this.db);
      saltDAO.insert(salt);
      this.cxn.commit();
      return user;
    } catch (SQLException | AssertionError ex) {
      this.cxn.rollback();
      throw ex;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find a user by their userID.
   *
   * @param id
   * @return
   * @throws SQLException
   */
  @Override
  public User findByID(int id) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      String query = "SELECT * FROM Users WHERE User_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      ResultSet result = stmt.executeQuery();
      User user = null;
      if (result.next()) {
        user = this.getInstanceFromResultSet(result);
      }
      return user;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find a user by their username.
   *
   * @param username
   * @return
   * @throws SQLException
   */
  public User findByUsername(String username) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      String query = "SELECT * FROM Users WHERE User_Name = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, username);
      ResultSet result = stmt.executeQuery();
      User user = null;
      if (result.next()) {
        user = this.getInstanceFromResultSet(result);
      }
      return user;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Finds all users in the database.
   *
   * @return
   * @throws SQLException
   */
  @Override
  public ArrayList<User> findAll() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      ArrayList<User> users = new ArrayList<>();
      String query = "SELECT * FROM Users";
      ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
      while (resultSet.next()) {
        users.add(this.getInstanceFromResultSet(resultSet));
      }
      return users;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Tries to update a user by the user object passed in.
   *
   * @param user
   * @return
   * @throws SQLException
   */
  @Override
  public User update(User user) throws SQLException {
    try {
      String query =
          "UPDATE Users SET "
              + "User_Name = ?,"
              + "Password = ?,"
              + "Last_Updated_By = ?,"
              + "WHERE User_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword().getHash());
      stmt.setString(3, user.getLastUpdatedBy());
      stmt.setInt(4, user.getUserID());
      ResultSet results = stmt.executeQuery();
      User updatedUser = null;
      if (results.next()) updatedUser = this.getInstanceFromResultSet(results);
      return updatedUser;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Tries to delete a user by their userID.
   *
   * @param id
   * @return
   * @throws SQLException
   */
  @Override
  public boolean deleteByID(int id) throws SQLException {
    try {
      String query = "DELETE FROM Users WHERE User_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      return stmt.execute();
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Returns a user object based on the resultSet passed in.
   *
   * @param resultSet
   * @return
   * @throws SQLException
   */
  @Override
  public User getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    Password password = new Password();
    password.setPassword(resultSet.getString("Password"), false);
    return new User(
        resultSet.getInt("User_ID"),
        resultSet.getString("User_Name"),
        password,
        resultSet.getTimestamp("Create_Date"),
        resultSet.getString("Created_By"),
        resultSet.getTimestamp("Last_Update"),
        resultSet.getString("Last_Updated_By"));
  }
}

package edu.wgu.tmaama.db.User.dao;

import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.Salt.dao.ConcreteSaltDAO;
import edu.wgu.tmaama.db.Salt.model.Salt;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.Password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConcreteUserDAO implements UserDAO {
  private Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteUserDAO() throws SQLException {}

  public ConcreteUserDAO(Database db) throws SQLException {
    this.db = db;
    this.cxn = this.db.getConnection();
  }

  @Override
  public User insert(User user) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      this.cxn.setAutoCommit(false);
      String userQuery = "INSERT INTO Users " + "(User_Name, Password, Created_By)" + " VALUES (?, ?, ?)";
      PreparedStatement userStmt = this.cxn.prepareStatement(userQuery);
      userStmt.setString(1, user.getUsername());
      userStmt.setString(2, user.getPassword().getHash());
      userStmt.setString(3, user.getCreatedBy());
      ResultSet results = userStmt.executeQuery();
      User newUser = null;
      if (results.next()) newUser = this.getInstanceFromResultSet(results);
      assert newUser != null;
      Salt salt = user.getPassword().getSalt();
      ConcreteSaltDAO saltDAO = new ConcreteSaltDAO(this.db);
      Salt insertedSalt = saltDAO.insert(salt);
      newUser.getPassword().setSalt(insertedSalt);
      return newUser;
    } catch (SQLException|AssertionError ex) {
      this.cxn.rollback();
      throw ex;
    } finally {
      this.db.closeConnection();
    }
  }

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

//  @Override
  public ArrayList<User> find(ArrayList<String> attributes, HashMap<String, String> where) throws SQLException {
    try {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    ArrayList<User> users = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder("SELECT ");
    for (int i = 0; i < attributes.size(); i++) {
      String attr = attributes.get(i);
      if (i == 0) {
        stringBuilder.append(attr).append(" ");
      } else {
        stringBuilder.append(", ").append(attr).append(" ");
      }
    }

    stringBuilder.append("FROM Users ");

    if (!where.isEmpty()) {
      stringBuilder.append("WHERE ");
      Iterator<Map.Entry<String, String>> iterator = where.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, String> clause = iterator.next();
        stringBuilder.append(clause.getKey()).append(" = '").append(clause.getValue()).append("'");
        if (iterator.hasNext()) stringBuilder.append(" AND ");
      }
    }

    ResultSet resultSet = this.cxn.createStatement().executeQuery(stringBuilder.toString());

    while (resultSet.next()) {
      users.add(this.getDynamicInstanceFromResultSet(resultSet, attributes));
    }

    return users;
    } finally {
      this.db.closeConnection();
    }
  }

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

  public User getDynamicInstanceFromResultSet(ResultSet resultSet, ArrayList<String> attributes) throws SQLException {
    if (attributes.size() == 0) return null;
    if (attributes.contains("*")) return this.getInstanceFromResultSet(resultSet);

    User user = new User();
    for (String attr : attributes) {
      switch (attr) {
        case "User_ID" -> user.setUserID(resultSet.getInt(attr));
        case "User_Name" -> user.setUsername(resultSet.getString(attr));
        case "Password" -> {
          Password password = new Password();
          password.setPassword(resultSet.getString("Password"), false);
        }
        case "Create_Date" -> user.setCreateDate(resultSet.getTimestamp(attr));
        case "Created_By" -> user.setCreatedBy(resultSet.getString(attr));
        case "Last_Update" -> user.setLastUpdate(resultSet.getTimestamp(attr));
        case "Last_Updated_By" -> user.setLastUpdatedBy(resultSet.getString(attr));
        default -> throw new RuntimeException("Attribute unknown to table Users");
      }
    }
    return user;
  }
}

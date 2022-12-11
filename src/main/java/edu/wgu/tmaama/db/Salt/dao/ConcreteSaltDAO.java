package edu.wgu.tmaama.db.Salt.dao;

import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.Salt.model.Salt;

import java.sql.*;
import java.util.ArrayList;

public class ConcreteSaltDAO implements SaltDAO {
  private Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteSaltDAO() throws SQLException {}

  public ConcreteSaltDAO(Database db) throws SQLException {
    this.db = db;
    this.cxn = this.db.getConnection();
  }

  @Override
  public Salt insert(Salt salt) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query = "INSERT INTO Salts " + "(Salt, User_ID)" + " VALUES (?, ?)";
    PreparedStatement stmt = this.cxn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    stmt.setBytes(1, salt.getSalt());
    stmt.setInt(2, salt.getUserID());
    int affectedRows = stmt.executeUpdate();
    if (affectedRows == 0)
      throw new SQLException("ERROR: Failed to create Salt for user. Please try again.");
    ResultSet results = stmt.getGeneratedKeys();
    if (results.next()) salt.setSaltID(results.getInt(1));
    return salt;
  }

  @Override
  public Salt findByID(int id) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query = "SELECT * FROM Salts WHERE Salt_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    ResultSet result = stmt.executeQuery();
    Salt salt = null;
    if (result.next()) {
      salt = this.getInstanceFromResultSet(result);
    }
    this.db.closeConnection();
    return salt;
  }

  public Salt findByUserID(int userID) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query = "SELECT * FROM Salts WHERE User_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, userID);
    ResultSet result = stmt.executeQuery();
    Salt salt = null;
    if (result.next()) {
      salt = this.getInstanceFromResultSet(result);
    }
    this.db.closeConnection();
    return salt;
  }

  @Override
  public ArrayList<Salt> findAll() throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    ArrayList<Salt> salts = new ArrayList<>();
    String query = "SELECT * FROM Salts";
    ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
    while (resultSet.next()) {
      salts.add(this.getInstanceFromResultSet(resultSet));
    }
    this.db.closeConnection();
    return salts;
  }

  @Override
  public Salt update(Salt salt) throws SQLException {
    String query = "UPDATE Salts SET " + "Salt = ?," + "WHERE Salt_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setBytes(1, salt.getSalt());
    stmt.setInt(2, salt.getSaltID());
    int affectedRows = stmt.executeUpdate();
    if (affectedRows == 0)
      throw new SQLException("Unable to update Salt: " + salt.getSaltID());
    this.db.closeConnection();
    return salt;
  }

  @Override
  public boolean deleteByID(int id) throws SQLException {
    String query = "DELETE FROM Salt WHERE Salt_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

  @Override
  public Salt getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new Salt(
        resultSet.getInt("Salt_ID"), resultSet.getBytes("Salt"), resultSet.getInt("User_ID"));
  }
}

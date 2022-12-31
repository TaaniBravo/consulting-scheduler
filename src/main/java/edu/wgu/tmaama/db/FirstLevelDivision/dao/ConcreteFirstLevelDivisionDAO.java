package edu.wgu.tmaama.db.FirstLevelDivision.dao;

import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConcreteFirstLevelDivisionDAO implements FirstLevelDivisionDAO {
  private final Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteFirstLevelDivisionDAO() throws SQLException {}

  /**
   * Tries to insert a first level division into the database.
   *
   * @param firstLevelDivision
   * @return
   * @throws SQLException
   */
  @Override
  public FirstLevelDivision insert(FirstLevelDivision firstLevelDivision) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      String query =
          "INSERT INTO First_Level_Divisions "
              + "(Division, Created_By, Country_ID)"
              + " VALUES (?, ?, ?)";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, firstLevelDivision.getDivision());
      stmt.setString(2, firstLevelDivision.getCreatedBy());
      stmt.setInt(3, firstLevelDivision.getCountryID());
      ResultSet results = stmt.executeQuery();
      FirstLevelDivision newDivision = null;
      if (results.next()) newDivision = this.getInstanceFromResultSet(results);
      return newDivision;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find a first level division by their divisionID.
   *
   * @param id
   * @return
   * @throws SQLException
   */
  @Override
  public FirstLevelDivision findByID(int id) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      String query = "SELECT * FROM First_Level_Divisions WHERE Division_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      ResultSet result = stmt.executeQuery();
      FirstLevelDivision division = null;
      if (result.next()) division = this.getInstanceFromResultSet(result);
      return division;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Finds all first level divisions.
   *
   * @return
   * @throws SQLException
   */
  @Override
  public ArrayList<FirstLevelDivision> findAll() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      ArrayList<FirstLevelDivision> divisions = new ArrayList<>();
      String query = "SELECT * FROM First_Level_Divisions";
      ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
      while (resultSet.next()) {
        divisions.add(this.getInstanceFromResultSet(resultSet));
      }
      return divisions;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Tries to update a first level division by the object passed in.
   *
   * @param division
   * @return
   * @throws SQLException
   */
  @Override
  public FirstLevelDivision update(FirstLevelDivision division) throws SQLException {
    String query =
        "UPDATE First_Level_Divisions SET "
            + "Division = ?,"
            + "Last_Updated_By = ?,"
            + "Country_ID = ?,"
            + "WHERE Division_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, division.getDivision());
    stmt.setString(2, division.getLastUpdatedBy());
    stmt.setInt(3, division.getCountryID());
    stmt.setInt(4, division.getDivisionID());
    ResultSet results = stmt.executeQuery();
    FirstLevelDivision updatedDivision = null;
    if (results.next()) updatedDivision = this.getInstanceFromResultSet(results);
    this.db.closeConnection();
    return updatedDivision;
  }

  /**
   * Deletes a first level division from the database based on the divisionID passed in.
   *
   * @param id
   * @return
   * @throws SQLException
   */
  @Override
  public boolean deleteByID(int id) throws SQLException {
    try {
      String query = "DELETE FROM First_Level_Divisions WHERE Division_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      return stmt.execute();
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Returns a FirstLevelDivision object based on the resultSet passed in.
   * @param resultSet
   * @return
   * @throws SQLException
   */
  @Override
  public FirstLevelDivision getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new FirstLevelDivision(
        resultSet.getInt("Division_ID"),
        resultSet.getString("Division"),
        resultSet.getTimestamp("Create_Date"),
        resultSet.getString("Created_By"),
        resultSet.getTimestamp("Last_Update"),
        resultSet.getString("Last_Updated_By"),
        resultSet.getInt("Country_ID"));
  }
}

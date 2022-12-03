package edu.wgu.tmaama.db.FirstLevelDivision.dao;

import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConcreteFirstLevelDivision implements FirstLevelDivisionDAO {
  private final Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteFirstLevelDivision() throws SQLException {}

  @Override
  public FirstLevelDivision insert(FirstLevelDivision firstLevelDivision) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query =
        "INSERT INTO Appointments " + "(Division, Created_By, Country_ID)" + " VALUES (?, ?, ?)";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, firstLevelDivision.getDivision());
    stmt.setString(2, firstLevelDivision.getCreatedBy());
    stmt.setInt(3, firstLevelDivision.getCountryID());
    ResultSet results = stmt.executeQuery();
    FirstLevelDivision newDivision = null;
    if (results.next()) newDivision = this.getInstanceFromResultSet(results);
    this.db.closeConnection();
    return newDivision;
  }

  @Override
  public FirstLevelDivision findByID(int id) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query = "SELECT * FROM FirstLevelDivisions WHERE Division_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    ResultSet result = stmt.executeQuery();
    FirstLevelDivision division = null;
    if (result.next()) division = this.getInstanceFromResultSet(result);
    this.db.closeConnection();
    return division;
  }

  @Override
  public ArrayList<FirstLevelDivision> findAll() throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    ArrayList<FirstLevelDivision> divisions = new ArrayList<>();
    String query = "SELECT * FROM FirstLevelDivision";
    ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
    while (resultSet.next()) {
      divisions.add(this.getInstanceFromResultSet(resultSet));
    }
    this.db.closeConnection();
    return divisions;
  }

  @Override
  public FirstLevelDivision update(FirstLevelDivision division) throws SQLException {
    String query =
        "UPDATE FirstLevelDivisions SET "
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

  @Override
  public boolean deleteByID(int id) throws SQLException {
    String query = "DELETE FROM FirstLevelDivisions WHERE Division_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

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

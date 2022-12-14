package edu.wgu.tmaama.db.Country.dao;

import edu.wgu.tmaama.db.Country.model.Country;
import edu.wgu.tmaama.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Concrete CountryDAO implementation */
public class ConcreteCountryDAO implements CountryDAO {
  private final Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteCountryDAO() throws SQLException {}

  /**
   * Tries to insert a country into the database.
   *
   * @param country
   * @return
   */
  @Override
  public Country insert(Country country) {
    return null;
  }

  /**
   * Find a Country by it's countryID.
   *
   * @param id
   * @return
   * @throws SQLException
   */
  @Override
  public Country findByID(int id) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = db.getConnection();
      String query = "SELECT * FROM Countries WHERE Country_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      ResultSet resultSet = stmt.executeQuery();
      Country country = null;
      if (resultSet.next()) country = this.getInstanceFromResultSet(resultSet);
      return country;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find all countries in the database.
   *
   * @return
   * @throws SQLException
   */
  @Override
  public ArrayList<Country> findAll() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = db.getConnection();
      ArrayList<Country> countries = new ArrayList<>();
      String query = "SELECT * FROM Countries";
      ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
      while (resultSet.next()) {
        countries.add(this.getInstanceFromResultSet(resultSet));
      }
      return countries;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Tries to update a country based on the object passed in.
   *
   * @param country
   * @return
   * @throws SQLException
   */
  @Override
  public Country update(Country country) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = db.getConnection();
      String query =
          "UPDATE Countries SET "
              + "Country = ?, "
              + "Last_Updated_By = ? "
              + "WHERE Country_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, country.getCountry());
      stmt.setString(2, country.getLastUpdatedBy());
      stmt.setInt(3, country.getCountryID());
      Country updatedCountry = null;
      ResultSet resultSet = stmt.executeQuery();
      if (resultSet.next()) updatedCountry = this.getInstanceFromResultSet(resultSet);
      return updatedCountry;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Tries to delete a country from the database based on it's countryID.
   *
   * @param id
   * @return
   * @throws SQLException
   */
  @Override
  public boolean deleteByID(int id) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = db.getConnection();
      String query = "DELETE FROM Countries WHERE Country_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      boolean success = stmt.execute();
      return success;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Returns a Country object from the resultSet passed in.
   * @param resultSet
   * @return
   * @throws SQLException
   */
  @Override
  public Country getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new Country(
        resultSet.getInt("Country_ID"),
        resultSet.getString("Country"),
        resultSet.getTimestamp("Create_Date"),
        resultSet.getString("Created_By"),
        resultSet.getTimestamp("Last_Update"),
        resultSet.getString("Last_Updated_By"));
  }
}

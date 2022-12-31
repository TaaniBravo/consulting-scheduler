package edu.wgu.tmaama.db.FirstLevelDivision.model;

import edu.wgu.tmaama.db.Country.dao.ConcreteCountryDAO;
import edu.wgu.tmaama.db.Country.model.Country;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * FirstLevelDivision database model class.
 */
public class FirstLevelDivision {
  private int divisionID;
  private String division;
  private Timestamp createDate;
  private String createdBy;
  private Timestamp lastUpdate;
  private String lastUpdatedBy;
  private int countryID;
  private Country country;

  public FirstLevelDivision(String division, String createdBy, int countryID) {
    this.division = division;
    this.createdBy = createdBy;
    this.countryID = countryID;
  }

  public FirstLevelDivision(
      int divisionID,
      String division,
      Timestamp createDate,
      String createdBy,
      Timestamp lastUpdate,
      String lastUpdatedBy,
      int countryID) {
    this.divisionID = divisionID;
    this.division = division;
    this.createDate = createDate;
    this.createdBy = createdBy;
    this.lastUpdate = lastUpdate;
    this.lastUpdatedBy = lastUpdatedBy;
    this.countryID = countryID;
  }

  /**
   * Get a first level division's divisionID.
   * @return
   */
  public int getDivisionID() {
    return divisionID;
  }

  /**
   * Set a first level division's divisionID.
   * @return
   */
  public void setDivisionID(int divisionID) {
    this.divisionID = divisionID;
  }

  /**
   * Get a first level division's division.
   * @return
   */
  public String getDivision() {
    return division;
  }

  /**
   * Set a first level division's division.
   * @return
   */
  public void setDivision(String division) {
    this.division = division;
  }

  /**
   * Get a first level division's create date.
   * @return
   */
  public Timestamp getCreateDate() {
    return createDate;
  }

  /**
   * Get a first level division's created by user.
   * @return
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Set a first level division's created by user.
   * @return
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Get a first level division's last update timestamp.
   * @return
   */
  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  /**
   * Get a first level division's last updated by user.
   * @return
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * Set a first level division's last updated by user.
   * @return
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /**
   * Get a first level division's countryID.
   * @return
   */
  public int getCountryID() {
    return countryID;
  }

  /**
   * Set a first level division's countryID.
   * @return
   */
  public void setCountryID(int countryID) {
    this.countryID = countryID;
  }

  /**
   * Overrides toString method to return the first level division's divisionID and division.
   * @return
   */
  @Override
  public String toString() {
    return this.divisionID + ". " + this.division;
  }

  /**
   * Get a first level division's Country model.
   * @return
   * @throws SQLException
   */
  public Country getCountry() throws SQLException {
    if (country == null) {
      ConcreteCountryDAO countryDAO = new ConcreteCountryDAO();
      this.setCountry(countryDAO.findByID(this.countryID));
    }
    return country;
  }

  /**
   * Set a first level division's Country model.
   * @param country
   */
  public void setCountry(Country country) {
    this.country = country;
  }
}

package edu.wgu.tmaama.db.Country.model;

import java.sql.Timestamp;

/**
 * Country model class.
 */
public class Country {
  private int countryID;
  private String country;
  private Timestamp createDate;
  private String createdBy;
  private Timestamp lastUpdate;
  private String lastUpdatedBy;

  public Country(String country, String createdBy) {
    this.country = country;
    this.createdBy = createdBy;
  }

  public Country(
      int countryID,
      String country,
      Timestamp createDate,
      String createdBy,
      Timestamp lastUpdate,
      String lastUpdatedBy) {
    this.countryID = countryID;
    this.country = country;
    this.createDate = createDate;
    this.createdBy = createdBy;
    this.lastUpdate = lastUpdate;
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /**
   * Get a country's countryID.
   * @return
   */
  public int getCountryID() {
    return countryID;
  }

  /**
   * Set a country's countryID.
   * @param countryID
   */
  public void setCountryID(int countryID) {
    this.countryID = countryID;
  }

  /**
   * Get a country's country string.
   * @return
   */
  public String getCountry() {
    return country;
  }

  /**
   * Set a country's country string.
   * @return
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Get a country's create date.
   * @return
   */
  public Timestamp getCreateDate() {
    return createDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  @Override
  public String toString() {
    return this.country;
  }
}

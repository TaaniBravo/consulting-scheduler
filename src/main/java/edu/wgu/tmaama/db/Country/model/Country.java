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
   */
  public int getCountryID() {
    return countryID;
  }

  /**
   * Set a country's countryID.
   */
  public void setCountryID(int countryID) {
    this.countryID = countryID;
  }

  /**
   * Get a country's country string.
   */
  public String getCountry() {
    return country;
  }

  /**
   * Set a country's country string.
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Get a country's create date.
   */
  public Timestamp getCreateDate() {
    return createDate;
  }

  /**
   * Get a country's created by user.
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Set a country's created by user.
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Get a country's last update timestamp.
   */
  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  /**
   * Get a country's last updated by user.
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * Set a country's last updated by user.
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /**
   * Override method to return just the country when toString is called.
   */
  @Override
  public String toString() {
    return this.country;
  }
}

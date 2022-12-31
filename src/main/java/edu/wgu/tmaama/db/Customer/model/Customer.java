package edu.wgu.tmaama.db.Customer.model;

import edu.wgu.tmaama.db.Country.dao.ConcreteCountryDAO;
import edu.wgu.tmaama.db.FirstLevelDivision.dao.ConcreteFirstLevelDivisionDAO;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Customer model object.
 */
public class Customer {
  private int customerID;
  private String customerName;
  private String address;
  private String postalCode;
  private String phone;
  private Timestamp createDate;
  private String createdBy;
  private Timestamp lastUpdate;
  private String lastUpdatedBy;
  private int divisionID;
  private FirstLevelDivision firstLevelDivision = null;
  private String division;
  private int countryID;

  public Customer(
      String customerName,
      String address,
      String postalCode,
      String phone,
      String createdBy,
      int divisionID) {
    this.customerName = customerName;
    this.address = address;
    this.postalCode = postalCode;
    this.phone = phone;
    this.createdBy = createdBy;
    this.divisionID = divisionID;
  }

  public Customer(
      int customerID,
      String customerName,
      String address,
      String postalCode,
      String phone,
      Timestamp createDate,
      String createdBy,
      Timestamp lastUpdate,
      String lastUpdatedBy,
      int divisionID) {
    this.customerID = customerID;
    this.customerName = customerName;
    this.address = address;
    this.postalCode = postalCode;
    this.phone = phone;
    this.createDate = createDate;
    this.createdBy = createdBy;
    this.lastUpdate = lastUpdate;
    this.lastUpdatedBy = lastUpdatedBy;
    this.divisionID = divisionID;
  }

  public Customer(
      int customerID,
      String customerName,
      String address,
      String postalCode,
      String phone,
      Timestamp createDate,
      String createdBy,
      Timestamp lastUpdate,
      String lastUpdatedBy,
      int divisionID,
      String division) {
    this.customerID = customerID;
    this.customerName = customerName;
    this.address = address;
    this.postalCode = postalCode;
    this.phone = phone;
    this.createDate = createDate;
    this.createdBy = createdBy;
    this.lastUpdate = lastUpdate;
    this.lastUpdatedBy = lastUpdatedBy;
    this.divisionID = divisionID;
    this.division = division;
  }

  public Customer() {}

  /**
   * Get a customer's customerID.
   * @return
   */
  public int getCustomerID() {
    return customerID;
  }

  /**
   * Set a customer's customerID.
   * @param customerID
   */
  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  /**
   * Get a customer's customerName.
   * @return
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * Set a customer's customerName.
   * @return
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * Get a customer's address.
   * @return
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set a customer's address.
   * @return
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Get a customer's postalCode
   * @return
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Set a customer's postalCode
   * @return
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * Get a customer's phone number
   * @return
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Set a customer's phone number
   * @return
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Get a customer's create date.
   * @return
   */
  public Timestamp getCreateDate() {
    return createDate;
  }

  /**
   * Get a customer's created by date.
   * @return
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Set a customer's created by user.
   * @param createdBy
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Get a customer's last update timestamp
   * @return
   */
  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  /**
   * Get a customer's last updated by user.
   * @return
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * Set a customer's last updated by user.
   * @param lastUpdatedBy
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /**
   * Get a customer's divisionID.
   * @return
   */
  public int getDivisionID() {
    return divisionID;
  }

  /**
   * Set a customer's divisionID.
   * @param divisionID
   */
  public void setDivisionID(int divisionID) {
    this.divisionID = divisionID;
  }

  /**
   * Get a customer's division.
   * @return
   */
  public String getDivision() {
    return this.division;
  }

  /**
   * Get a customer's division.
   * @param division
   */
  public void setDivision(String division) {
    this.division = division;
  }

  /**
   * Override method so toString returns the customerID and customerName.
   * @return
   */
  @Override
  public String toString() {
    return this.customerID + ". " + this.customerName;
  }

  /**
   * Get a customer's FirstLevelDivision object from database if this field is null. Otherwise just return the object
   * @return
   * @throws SQLException
   */
  public FirstLevelDivision getFirstLevelDivision() throws SQLException {
    if (this.firstLevelDivision == null) {
      ConcreteFirstLevelDivisionDAO fldDAO = new ConcreteFirstLevelDivisionDAO();
      this.setFirstLevelDivision(fldDAO.findByID(this.divisionID));
    }
    return this.firstLevelDivision;
  }

  /**
   * Sets a customer's firstLevelDivision
   * @param firstLevelDivision
   */
  public void setFirstLevelDivision(FirstLevelDivision firstLevelDivision) {
    this.firstLevelDivision = firstLevelDivision;
  }

  /**
   * Get the countryID that is associated with the division associated with the customer.
   * @return
   */
  public int getCountryID() {
    return countryID;
  }

  /**
   * Set a customer's countryID.
   * @param countryID
   */
  public void setCountryID(int countryID) {
    this.countryID = countryID;
  }
}

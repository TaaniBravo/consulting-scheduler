package edu.wgu.tmaama.db.Customer.model;

import java.sql.Timestamp;

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
  private String division;

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

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
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

  public void setLastUpdate(Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public int getDivisionID() {
    return divisionID;
  }

  public void setDivisionID(int divisionID) {
    this.divisionID = divisionID;
  }

  public String getDivision() {
    return this.division;
  }

  public void setDivision(String division) {
    this.division = division;
  }
}

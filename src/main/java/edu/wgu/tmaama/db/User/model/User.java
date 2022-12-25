package edu.wgu.tmaama.db.User.model;

import edu.wgu.tmaama.utils.Password;

import java.sql.Timestamp;

public class User {
  private int userID;
  private String username;
  private Password password;
  private Timestamp createDate;
  private String createdBy;
  private Timestamp lastUpdate;
  private String lastUpdatedBy;

  public User() {}

  public User(String username, Password password, String createdBy) {
    this.username = username;
    this.password = password;
    this.createdBy = createdBy;
  }

  public User(
      int userID,
      String username,
      Password password,
      Timestamp createDate,
      String createdBy,
      Timestamp lastUpdate,
      String lastUpdatedBy) {
    this.userID = userID;
    this.username = username;
    this.password = password;
    this.createDate = createDate;
    this.createdBy = createdBy;
    this.lastUpdate = lastUpdate;
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public Password getPassword() {
    return password;
  }

  public void setPassword(Password password) {
    this.password = password;
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

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return this.getUsername();
  }
}

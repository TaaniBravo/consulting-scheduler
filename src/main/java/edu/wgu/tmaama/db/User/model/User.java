package edu.wgu.tmaama.db.User.model;

import edu.wgu.tmaama.utils.Password;

import java.sql.Timestamp;

/**
 * Database model for the Users table.
 */
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

  /**
   * Gets a user's password.
   */
  public Password getPassword() {
    return password;
  }

  /**
   * Sets a user's password.
   */
  public void setPassword(Password password) {
    this.password = password;
  }

  /**
   * Get a user's create date.
   */
  public Timestamp getCreateDate() {
    return createDate;
  }

  /**
   * Get a user's created by user.
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Set a user's created by user.
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Get a user's last update timestamp.
   */
  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  /**
   * Get a user's last updated by user.
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * Set a user's last updated by user.
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /**
   * Get a user's userID.
   */
  public int getUserID() {
    return userID;
  }

  /**
   * Set a user's userID.
   */
  public void setUserID(int userID) {
    this.userID = userID;
  }

  /**
   * Get a user's username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set a user's username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Override method that returns just the user's username.
   */
  @Override
  public String toString() {
    return this.getUsername();
  }
}

package edu.wgu.tmaama.db.Salt.model;

/**
 * Database model for Salts table. A salt is a term used for hashing sensitive information (namely
 * passwords) so that we don't have to store a user's password directly. Rather we store the hash
 * and the salt used to hash the password so that if the database was to be leaked the chance of a
 * user being hacked is less likely.
 */
public class Salt {
  private int saltID;
  private byte[] salt;
  private int userID;

  public Salt(byte[] salt) {
    this.salt = salt;
  }

  public Salt(byte[] salt, int userID) {
    this.salt = salt;
    this.userID = userID;
  }

  public Salt(int saltID, byte[] salt, int userID) {
    this.saltID = saltID;
    this.salt = salt;
    this.userID = userID;
  }

  /**
   * Get a salt's saltID.
   *
   */
  public int getSaltID() {
    return saltID;
  }

  /**
   * Set a salt's saltID.
   *
   */
  public void setSaltID(int saltID) {
    this.saltID = saltID;
  }

  /**
   * Get a salt's salt.
   *
   */
  public byte[] getSalt() {
    return salt;
  }

  /**
   * Set a salt's salt.
   *
   */
  public void setSalt(byte[] salt) {
    this.salt = salt;
  }

  /**
   * Get a salt's userID.
   *
   */
  public int getUserID() {
    return userID;
  }

  /**
   * Set a salt's userID.
   *
   */
  public void setUserID(int userID) {
    this.userID = userID;
  }
}

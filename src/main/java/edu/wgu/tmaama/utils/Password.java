package edu.wgu.tmaama.utils;

import edu.wgu.tmaama.db.Salt.model.Salt;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Class for properly holding onto a password hash and salt.
 */
public class Password {
  private final Random RANDOM = new SecureRandom();
  private String hash;
  private Salt salt;

  public Password() {}

  public Password(String password) {
    this.salt = new Salt(this.getNextSalt());
    this.hash = this.genSaltedPassword(password.toCharArray(), this.salt.getSalt());
  }

  public Password(String password, Salt salt) {
    this.salt = salt;
    this.hash = this.genSaltedPassword(password.toCharArray(), this.salt.getSalt());
  }

  public String getHash() {
    return this.hash;
  }

  public void setPassword(String password, boolean useHash) {
    if (!useHash) {
      this.hash = password;
      return;
    }

    if (this.salt == null) this.salt = new Salt(this.getNextSalt());
    this.hash = this.genSaltedPassword(password.toCharArray(), this.salt.getSalt());
  }

  public void setPassword(String password, boolean useHash, int userID) {
    if (!useHash) {
      this.hash = password;
      return;
    }

    if (this.salt == null) this.salt = new Salt(this.getNextSalt(), userID);
    this.hash = this.genSaltedPassword(password.toCharArray(), this.salt.getSalt());
  }

  public Salt getSalt() {
    return this.salt;
  }

  public void setSalt(Salt salt) {
    this.salt = salt;
  }

  /**
   * Generates a salt for the password to be hashed with.
   * @return
   */
  private byte[] getNextSalt() {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);
    return salt;
  }

  /**
   * Generates the hashed/salted password for safekeeping.
   * @param password
   * @param salt
   * @return
   */
  private String genSaltedPassword(char[] password, byte[] salt) {
    int KEY_LENGTH = 50;
    int ITERATIONS = 1000;
    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
    Arrays.fill(password, Character.MIN_VALUE);
    try {
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return Arrays.toString(secretKeyFactory.generateSecret(spec).getEncoded());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new AssertionError("Error: While hashing password: " + ex.getMessage(), ex);
    }
  }

  /**
   * Compares to password hashes and sees if they are equal or not.
   * @param password1
   * @param password2
   * @return
   */
  public static boolean comparePasswords(Password password1, Password password2) {
    return Objects.equals(password1.getHash(), password2.getHash());
  }
}

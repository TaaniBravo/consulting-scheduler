package edu.wgu.tmaama.db.Salt.model;

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

    public int getSaltID() {
        return saltID;
    }

    public void setSaltID(int saltID) {
        this.saltID = saltID;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

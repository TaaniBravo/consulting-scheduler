package edu.wgu.tmaama.models.user;

public interface UserDAO {
    public void insert(User user);
    public User findUserByID(int userID);
    public User updateUserByID(int userID, User user);
}

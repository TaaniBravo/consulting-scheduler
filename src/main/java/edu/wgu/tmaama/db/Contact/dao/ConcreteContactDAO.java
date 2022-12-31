package edu.wgu.tmaama.db.Contact.dao;

import edu.wgu.tmaama.db.Contact.model.Contact;
import edu.wgu.tmaama.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Concrete class of ContactDAO */
public class ConcreteContactDAO implements ContactDAO {
  private Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteContactDAO() throws SQLException {}

  /**
   * This initializer allows for reuse of a one connection.
   *
   * @param db - Use this if you want to reuse the database connection amongst different DAOs.
   * @throws SQLException - when there is an issue with the query.
   */
  public ConcreteContactDAO(Database db) throws SQLException {
    this.db = db;
  }

  /**
   * Tries to insert a new contact into the database.
   *
   * @param contact - the contact object to insert into the database.
   * @return - The new Contact that was inserted into the database.
   * @throws SQLException - when there is an issue with the query.
   */
  @Override
  public Contact insert(Contact contact) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = db.getConnection();
      String query = "INSERT INTO Contacts (Contact_Name, Email)" + "VALUES (?, ?)";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, contact.getContactName());
      stmt.setString(1, contact.getEmail());
      ResultSet resultSet = stmt.executeQuery();
      Contact newContact = null;
      if (resultSet.next()) newContact = this.getInstanceFromResultSet(resultSet);
      return newContact;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find a contact by their Contact_ID.
   *
   * @param contactID - the contactID to search for.
   * @return - returns the contact found.
   * @throws SQLException - when there is an issue with the query.
   */
  @Override
  public Contact findByID(int contactID) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = db.getConnection();
    String query = "SELECT * FROM Contacts WHERE Contact_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, contactID);
    ResultSet resultSet = stmt.executeQuery();
    Contact contact = null;
    if (resultSet.next()) contact = this.getInstanceFromResultSet(resultSet);
    this.db.closeConnection();
    return contact;
  }

  /**
   * Finds all contacts in the database.
   *
   * @return - an arraylist of all the contacts.
   * @throws SQLException - when there is an issue with the query.
   */
  @Override
  public ArrayList<Contact> findAll() throws SQLException {
    if (!this.db.checkConnection()) this.cxn = db.getConnection();
    ArrayList<Contact> contacts = new ArrayList<>();
    String query = "SELECT * FROM Contacts";
    ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
    while (resultSet.next()) {
      contacts.add(this.getInstanceFromResultSet(resultSet));
    }
    this.db.closeConnection();
    return contacts;
  }

  /**
   * Tries to update a contact based on the object passed in.
   *
   * @param contact - The contact to update.
   * @return - Returns the contact if successfully updated.
   * @throws SQLException - when there is an issue with the query.
   */
  @Override
  public Contact update(Contact contact) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = db.getConnection();
      String query =
          "UPDATE Contacts SET " + "Contact_Name = ?," + "Email = ? " + "WHERE Contact_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, contact.getContactName());
      stmt.setString(2, contact.getEmail());
      stmt.setInt(3, contact.getContactID());
      ResultSet resultSet = stmt.executeQuery();
      Contact updatedContact = null;
      if (resultSet.next()) updatedContact = this.getInstanceFromResultSet(resultSet);
      return updatedContact;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Tries to delete a contact based on the ID given.
   *
   * @param contactID - the contact's ID to delete by.
   * @return - Returns true if the contact was deleted.
   * @throws SQLException - when there is an issue with the query.
   */
  @Override
  public boolean deleteByID(int contactID) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = db.getConnection();
    String query = "DELETE FROM Contacts WHERE Contact_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, contactID);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

  /**
   * Returns a Contact object based on the resultSet passed in.
   *
   * @param resultSet the resultSet to extract row data from.
   * @return - returns a new Contact object with the row data from resultSet.
   * @throws SQLException - when there is an issue with the query.
   */
  @Override
  public Contact getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new Contact(
        resultSet.getInt("Contact_ID"),
        resultSet.getString("Contact_Name"),
        resultSet.getString("Email"));
  }
}

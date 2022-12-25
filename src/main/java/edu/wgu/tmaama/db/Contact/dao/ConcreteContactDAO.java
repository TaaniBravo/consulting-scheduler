package edu.wgu.tmaama.db.Contact.dao;

import edu.wgu.tmaama.db.Contact.model.Contact;
import edu.wgu.tmaama.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConcreteContactDAO implements ContactDAO {
  private Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteContactDAO() throws SQLException {}

  public ConcreteContactDAO(Database db) throws SQLException {
    this.db = db;
  }

  @Override
  public Contact insert(Contact contact) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = db.getConnection();
    String query = "INSERT INTO Contacts (Contact_Name, Email)" + "VALUES (?, ?)";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, contact.getContactName());
    stmt.setString(1, contact.getEmail());
    ResultSet resultSet = stmt.executeQuery();
    Contact newContact = null;
    if (resultSet.next()) newContact = this.getInstanceFromResultSet(resultSet);
    this.db.closeConnection();
    return newContact;
  }

  @Override
  public Contact findByID(int id) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = db.getConnection();
    String query = "SELECT * FROM Contacts WHERE Contact_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    ResultSet resultSet = stmt.executeQuery();
    Contact contact = null;
    if (resultSet.next()) contact = this.getInstanceFromResultSet(resultSet);
    this.db.closeConnection();
    return contact;
  }

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

  @Override
  public Contact update(Contact contact) throws SQLException {
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
    this.db.closeConnection();
    return updatedContact;
  }

  @Override
  public boolean deleteByID(int id) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = db.getConnection();
    String query = "DELETE FROM Contacts WHERE Contact_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

  @Override
  public Contact getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    return new Contact(
        resultSet.getInt("Contact_ID"),
        resultSet.getString("Contact_Name"),
        resultSet.getString("Email"));
  }
}

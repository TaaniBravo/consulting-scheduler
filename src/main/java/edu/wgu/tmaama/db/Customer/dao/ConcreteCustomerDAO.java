package edu.wgu.tmaama.db.Customer.dao;

import edu.wgu.tmaama.db.Customer.model.Customer;
import edu.wgu.tmaama.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConcreteCustomerDAO implements CustomerDAO {
  public static final String CUSTOMER_ID = "Customer_ID";
  public static final String CUSTOMER_NAME = "Customer_Name";
  public static final String LIMIT = "LIMIT";
  public static final String OFFSET = "OFFSET";
  private final Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteCustomerDAO() throws SQLException {}

  @Override
  public Customer insert(Customer customer) throws SQLException {
    if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
    String query =
        "INSERT INTO Customers "
            + "(Customer_Name, Address, Postal_Code, Phone, Created_By, Division_ID) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, customer.getCustomerName());
    stmt.setString(2, customer.getAddress());
    stmt.setString(3, customer.getPostalCode());
    stmt.setString(4, customer.getPhone());
    stmt.setString(5, customer.getCreatedBy());
    stmt.setInt(6, customer.getDivisionID());
    ResultSet resultSet = stmt.executeQuery();
    Customer newCustomer = null;
    if (resultSet.next()) newCustomer = this.getInstanceFromResultSet(resultSet);
    this.db.closeConnection();
    return newCustomer;
  }

  @Override
  public Customer findByID(int id) throws SQLException {
    String query = "SELECT * FROM Customers WHERE Customer_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    ResultSet result = stmt.executeQuery();
    Customer customer = null;
    if (result.next()) {
      customer = this.getInstanceFromResultSet(result);
    }
    db.closeConnection();
    return null;
  }

  @Override
  public ArrayList<Customer> findAll() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Customer> customers = new ArrayList<>();
      String query = "SELECT * FROM Customers";
      ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
      while (resultSet.next()) {
        customers.add(this.getInstanceFromResultSet(resultSet));
      }
      return customers;
    } finally {
      this.db.closeConnection();
    }
  }

  public List<Customer> findAll(HashMap<String, String> options) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Customer> customers = new ArrayList<>();
      StringBuilder query =
          new StringBuilder(
              "SELECT c.*, fld.Division "
                  + "FROM Customers c "
                  + "JOIN First_Level_Divisions fld "
                  + "ON c.Division_ID = fld.Division_ID");

      String queryOptions = this.buildOptions(options);
      query.append(queryOptions);

      System.out.println(query);
      ResultSet resultSet = this.cxn.createStatement().executeQuery(query.toString());
      while (resultSet.next()) {
        customers.add(this.getInstanceFromResultSet(resultSet));
      }
      return customers;
    } finally {
      this.db.closeConnection();
    }
  }

  @Override
  public Customer update(Customer customer) throws SQLException {
    String query =
        "UPDATE Customers SET "
            + "Customer_Name = ?,"
            + "Address = ?,"
            + "Postal_Code = ?,"
            + "Phone = ?,"
            + "Last_Updated_By = ?,"
            + "Division_ID = ?,"
            + "WHERE Customer_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, customer.getCustomerName());
    stmt.setString(2, customer.getAddress());
    stmt.setString(3, customer.getPostalCode());
    stmt.setString(4, customer.getPhone());
    stmt.setString(5, customer.getLastUpdatedBy());
    stmt.setInt(6, customer.getDivisionID());
    stmt.setInt(7, customer.getCustomerID());
    ResultSet results = stmt.executeQuery();
    Customer updatedCustomer = null;
    if (results.next()) updatedCustomer = this.getInstanceFromResultSet(results);
    this.db.closeConnection();
    return updatedCustomer;
  }

  @Override
  public boolean deleteByID(int id) throws SQLException {
    String query = "DELETE FROM Customer WHERE Customer_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

  @Override
  public Customer getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    Customer customer = new Customer(
        resultSet.getInt("Customer_ID"),
        resultSet.getString("Customer_Name"),
        resultSet.getString("Address"),
        resultSet.getString("Postal_Code"),
        resultSet.getString("Phone"),
        resultSet.getTimestamp("Create_Date"),
        resultSet.getString("Created_By"),
        resultSet.getTimestamp("Last_Update"),
        resultSet.getString("Last_Updated_By"),
        resultSet.getInt("Division_ID"));

    String division = resultSet.getString("Division");
    if (division != null) customer.setDivision(division);
    return customer;
  }

  private String buildOptions(HashMap<String, String> options) {
    if (options == null) return "";
    StringBuilder queryOptions = new StringBuilder();

    if (options.containsKey(CUSTOMER_NAME)) {
      if (!queryOptions.toString().contains("WHERE")) queryOptions.append(" WHERE ");
      else queryOptions.append("AND ");

      queryOptions.append(CUSTOMER_NAME).append(" LIKE '%").append(options.get(CUSTOMER_NAME)).append("%'");
    }

    if (options.containsKey(LIMIT))
      try {
        String limit = options.get(LIMIT);
        int value = Integer.parseInt(limit);
        queryOptions.append(" LIMIT ").append(value);
        if (options.containsKey(OFFSET))
          queryOptions.append(", ").append(options.get(OFFSET));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }

    return queryOptions.toString();
  }
}

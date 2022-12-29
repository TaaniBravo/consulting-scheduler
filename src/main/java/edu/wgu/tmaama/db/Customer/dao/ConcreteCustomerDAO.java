package edu.wgu.tmaama.db.Customer.dao;

import edu.wgu.tmaama.db.Customer.model.Customer;
import edu.wgu.tmaama.db.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConcreteCustomerDAO implements CustomerDAO {
  public static final String CUSTOMER_ID = "Customer_ID";
  public static final String CUSTOMER_NAME = "Customer_Name";
  public static final String LIMIT = "LIMIT";
  public static final String OFFSET = "OFFSET";
  private Database db = new Database();
  private Connection cxn = db.getConnection();

  public ConcreteCustomerDAO() throws SQLException {}

  public ConcreteCustomerDAO(Database db) throws SQLException {
    this.db = db;
  }

  @Override
  public Customer insert(Customer customer) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.cxn = this.db.getConnection();
      String query =
          "INSERT INTO Customers "
              + "(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = this.cxn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, customer.getCustomerName());
      stmt.setString(2, customer.getAddress());
      stmt.setString(3, customer.getPostalCode());
      stmt.setString(4, customer.getPhone());
      stmt.setString(5, customer.getCreatedBy());
      stmt.setString(6, customer.getCreatedBy());
      stmt.setInt(7, customer.getDivisionID());
      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0)
        throw new SQLException("Creating a new customer failed, please try again.");
      ResultSet results = stmt.getGeneratedKeys();
      assert results != null;
      if (results.next()) customer.setCustomerID(results.getInt(1));
      return customer;
    } finally {
      this.db.closeConnection();
    }
  }

  @Override
  public Customer findByID(int id) throws SQLException {
    try {
      String query =
          "SELECT c.*, fld.Division, fld.Country_ID "
              + "FROM Customers c "
              + "JOIN First_Level_Divisions fld ON fld.Division_ID = c.Division_ID "
              + "WHERE Customer_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      ResultSet result = stmt.executeQuery();
      Customer customer = null;
      if (result.next()) {
        customer = this.getInstanceFromResultSet(result);
      }
      return customer;
    } finally {
      db.closeConnection();
    }
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
              "SELECT c.*, fld.Division, fld.Country_ID "
                  + "FROM Customers c "
                  + "JOIN First_Level_Divisions fld "
                  + "ON c.Division_ID = fld.Division_ID");

      String queryOptions = this.buildOptions(options);
      query.append(queryOptions);

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
    try {
      String query =
          "UPDATE Customers SET "
              + "Customer_Name = ?, "
              + "Address = ?, "
              + "Postal_Code = ?, "
              + "Phone = ?, "
              + "Last_Updated_By = ?, "
              + "Division_ID = ? "
              + "WHERE Customer_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, customer.getCustomerName());
      stmt.setString(2, customer.getAddress());
      stmt.setString(3, customer.getPostalCode());
      stmt.setString(4, customer.getPhone());
      stmt.setString(5, customer.getLastUpdatedBy());
      stmt.setInt(6, customer.getDivisionID());
      stmt.setInt(7, customer.getCustomerID());
      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0)
        throw new SQLException("Creating a new customer failed, please try again.");
      ResultSet results = stmt.getGeneratedKeys();
      assert results != null;
      return customer;
    } finally {
      this.db.closeConnection();
    }
  }

  @Override
  public boolean deleteByID(int id) throws SQLException {
    try {
      String query = "DELETE FROM Customers WHERE Customer_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      stmt.execute();
      return true;
    } catch (SQLException ex) {
      return false;
    } finally {
      this.db.closeConnection();
    }
  }

  @Override
  public Customer getInstanceFromResultSet(ResultSet resultSet) throws SQLException {
    Customer customer =
        new Customer(
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

    try {
      String division = resultSet.getString("Division");
      if (division != null) customer.setDivision(division);
      customer.setCountryID(resultSet.getInt("Country_ID"));
    } catch (SQLException ex) {
      // We don't want to handle this exception
    }
    return customer;
  }

  private String buildOptions(HashMap<String, String> options) {
    if (options == null) return "";
    StringBuilder queryOptions = new StringBuilder();

    if (options.containsKey(CUSTOMER_NAME)) {
      if (!queryOptions.toString().contains("WHERE")) queryOptions.append(" WHERE ");
      else queryOptions.append("AND ");

      queryOptions
          .append(CUSTOMER_NAME)
          .append(" LIKE '%")
          .append(options.get(CUSTOMER_NAME))
          .append("%'");
    }

    if (options.containsKey(LIMIT))
      try {
        String limit = options.get(LIMIT);
        int value = Integer.parseInt(limit);
        queryOptions.append(" LIMIT ").append(value);
        if (options.containsKey(OFFSET)) queryOptions.append(", ").append(options.get(OFFSET));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }

    return queryOptions.toString();
  }
}

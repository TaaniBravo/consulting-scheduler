package edu.wgu.tmaama.db.Appointment.dao;

import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Database;

import java.sql.*;
import java.util.ArrayList;

/** Concrete class of AppointmentDAO. */
public class ConcreteAppointmentDAO implements AppointmentDAO {
  private final Database db = new Database();
  private final Connection cxn = db.getConnection();

  public ConcreteAppointmentDAO() throws SQLException {}

  /** Insert a new appointment into the database. */
  @Override
  public Appointment insert(Appointment appointment) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      String query =
          "INSERT INTO Appointments "
              + "(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
              + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = this.cxn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, appointment.getTitle());
      stmt.setString(2, appointment.getDescription());
      stmt.setString(3, appointment.getLocation());
      stmt.setString(4, appointment.getType());
      stmt.setTimestamp(5, appointment.getStart());
      stmt.setTimestamp(6, appointment.getEnd());
      stmt.setString(7, appointment.getCreatedBy());
      stmt.setString(8, appointment.getCreatedBy());
      stmt.setInt(9, appointment.getCustomerID());
      stmt.setInt(10, appointment.getUserID());
      stmt.setInt(11, appointment.getContactID());
      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0) throw new SQLException("Unable to create appointment.");
      ResultSet results = stmt.getGeneratedKeys();
      if (results.next()) appointment.setAppointmentID(results.getInt(1));
      return appointment;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find appointment by its appointment ID. */
  @Override
  public Appointment findByID(int id) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      String query = "SELECT * FROM Appointments WHERE Appointment_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, id);
      ResultSet result = stmt.executeQuery();
      Appointment appointment = null;
      if (result.next()) {
        appointment = this.getInstanceFromResultSet(result);
      }
      return appointment;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find all appointments in database. */
  @Override
  public ArrayList<Appointment> findAll() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query = "SELECT * FROM Appointments";
      ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find all appointments by a customer's ID.
   *
   * @param customerID - the customer ID to search for.
   */
  public ArrayList<Appointment> findAppointmentsByCustomerID(int customerID) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query = "SELECT * FROM Appointments WHERE Customer_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, customerID);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Find all appointments by a user's ID
   *
   * @param userID - the user ID to search for.
   */
  public ArrayList<Appointment> findAppointmentsByUserID(int userID) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query = "SELECT * FROM Appointments WHERE User_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, userID);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find all appointments for the next 7 days. */
  public ArrayList<Appointment> findAppointmentsForCurrentWeek() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query =
          "SELECT * FROM Appointments "
              + "WHERE Start BETWEEN NOW() "
              + "AND DATE_ADD(NOW(), INTERVAL 6 DAY)";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find appointments for the next 7 days for a given customer. */
  public ArrayList<Appointment> findAppointmentsForCurrentWeek(int customerID) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query =
          "SELECT * FROM Appointments "
              + "WHERE Start BETWEEN NOW() "
              + "AND DATE_ADD(NOW(), INTERVAL 6 DAY) "
              + "AND Customer_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, customerID);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find appointments for the current month of each year. */
  public ArrayList<Appointment> findAppointmentsForCurrentMonth() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query = "SELECT * FROM Appointments " + "WHERE MONTH(Start) = MONTH(now())";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find appointments for the current month of each year for a given customer by customerID. */
  public ArrayList<Appointment> findAppointmentsForCurrentMonth(int customerID)
      throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query =
          "SELECT * FROM Appointments "
              + "WHERE MONTH(Start) = MONTH(now()) "
              + "AND Customer_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, customerID);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find appointments types */
  public ArrayList<String> findAppointmentTypes() throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<String> types = new ArrayList<>();
      String query = "SELECT DISTINCT(Type) FROM Appointments";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        types.add(resultSet.getString("Type"));
      }
      return types;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Find appointments types */
  public ArrayList<Appointment> findAllByTypeAndMonth(String type, int month) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query = "SELECT * FROM Appointments " + "WHERE Type = ? " + "AND MONTH(Start) = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setString(1, type);
      stmt.setInt(2, month);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  public ArrayList<Appointment> findAppointmentsByContactID(int contactID) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query = "SELECT * FROM Appointments WHERE Contact_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, contactID);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  public ArrayList<Appointment> findAppointmentsByDivisionID(int divisionID) throws SQLException {
    try {
      if (!this.db.checkConnection()) this.db.getConnection();
      ArrayList<Appointment> appointments = new ArrayList<>();
      String query =
          "SELECT * FROM Appointments a "
              + "JOIN Customers c ON c.Customer_ID = a.Customer_ID "
              + "JOIN First_Level_Divisions fld on fld.Division_ID = c.Division_ID "
              + "WHERE fld.Division_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query);
      stmt.setInt(1, divisionID);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        appointments.add(this.getInstanceFromResultSet(resultSet));
      }
      return appointments;
    } finally {
      this.db.closeConnection();
    }
  }

  /**
   * Update an appointment by appointmentID
   *
   * @param appointment - the appointment object to update the database with.
   */
  @Override
  public Appointment update(Appointment appointment) throws SQLException {
    try {
      String query =
          "UPDATE Appointments SET "
              + "Title = ?, "
              + "Description = ?, "
              + "Location = ?, "
              + "Type = ?, "
              + "Start = ?, "
              + "End = ?, "
              + "Last_Update = NOW(), "
              + "Last_Updated_By = ?, "
              + "Customer_ID = ?, "
              + "User_ID = ?, "
              + "Contact_ID = ? "
              + "WHERE Appointment_ID = ?";
      PreparedStatement stmt = this.cxn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, appointment.getTitle());
      stmt.setString(2, appointment.getDescription());
      stmt.setString(3, appointment.getLocation());
      stmt.setString(4, appointment.getType());
      stmt.setTimestamp(5, appointment.getStart());
      stmt.setTimestamp(6, appointment.getEnd());
      stmt.setString(7, appointment.getLastUpdatedBy());
      stmt.setInt(8, appointment.getCustomerID());
      stmt.setInt(9, appointment.getUserID());
      stmt.setInt(10, appointment.getContactID());
      stmt.setInt(11, appointment.getAppointmentID());
      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0)
        throw new SQLException("Unable to update appointment: " + appointment.getTitle());
      return appointment;
    } finally {
      this.db.closeConnection();
    }
  }

  /** Delete an appointment from the database by appointment ID. */
  @Override
  public boolean deleteByID(int id) throws SQLException {
    String query = "DELETE FROM Appointments WHERE Appointment_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

  /** Returns an Appointment object from a ResultSet. */
  @Override
  public Appointment getInstanceFromResultSet(ResultSet result) throws SQLException {
    return new Appointment(
        result.getInt("Appointment_ID"),
        result.getString("Title"),
        result.getString("Description"),
        result.getString("Location"),
        result.getString("Type"),
        result.getTimestamp("Start"),
        result.getTimestamp("End"),
        result.getTimestamp("Create_Date"),
        result.getString("Created_By"),
        result.getTimestamp("Last_Update"),
        result.getString("Last_Updated_By"),
        result.getInt("Customer_ID"),
        result.getInt("User_ID"),
        result.getInt("Contact_ID"));
  }
}

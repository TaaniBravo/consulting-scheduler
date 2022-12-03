package edu.wgu.tmaama.db.Appointment.dao;

import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Database;

import java.sql.*;
import java.util.ArrayList;

public class ConcreteAppointmentDAO implements AppointmentDAO {
  private final Database db = new Database();
  private final Connection cxn = db.getConnection();

  public ConcreteAppointmentDAO() throws SQLException {}

  @Override
  public Appointment insert(Appointment appointment) throws SQLException {
    String query =
        "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Created_By, Customer_ID, User_ID, Contact_ID)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setString(1, appointment.getTitle());
    stmt.setString(2, appointment.getDescription());
    stmt.setString(3, appointment.getLocation());
    stmt.setString(4, appointment.getType());
    stmt.setTimestamp(5, appointment.getStart());
    stmt.setTimestamp(6, appointment.getEnd());
    stmt.setString(7, appointment.getCreatedBy());
    stmt.setInt(8, appointment.getCustomerID());
    stmt.setInt(9, appointment.getUserID());
    stmt.setInt(10, appointment.getContactID());
    ResultSet results = stmt.executeQuery();
    Appointment insertedAppointment = null;
    if (results.next()) insertedAppointment = this.getInstanceFromResultSet(results);
    db.closeConnection();
    return insertedAppointment;
  }

  @Override
  public Appointment findByID(int id) throws SQLException {
    String query = "SELECT * FROM Appointments WHERE Appointment_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    ResultSet result = stmt.executeQuery();
    Appointment appointment = null;
    if (result.next()) {
      appointment = this.getInstanceFromResultSet(result);
    }
    db.closeConnection();
    return appointment;
  }

  @Override
  public ArrayList<Appointment> findAll() throws SQLException {
    if (!this.db.checkConnection()) this.db.getConnection();
    ArrayList<Appointment> appointments = new ArrayList<>();
    String query = "SELECT * FROM Appointments";
    ResultSet resultSet = this.cxn.createStatement().executeQuery(query);
    while (resultSet.next()) {
      appointments.add(this.getInstanceFromResultSet(resultSet));
    }
    this.db.closeConnection();
    return appointments;
  }

  @Override
  public Appointment update(Appointment appointment) throws SQLException {
    String query =
        "UPDATE Appointments SET "
            + "Title = ?,"
            + "Description = ?,"
            + "Location = ?,"
            + "Type = ?,"
            + "Start = ?,"
            + "End = ?,"
            + "Last_Updated_By = ?,"
            + "Customer_ID = ?,"
            + "User_ID = ?,"
            + "Contact_ID = ?"
            + "WHERE Appointment_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
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
    ResultSet results = stmt.executeQuery();
    Appointment updatedAppointment = null;
    if (results.next()) updatedAppointment = this.getInstanceFromResultSet(results);
    this.db.closeConnection();
    return updatedAppointment;
  }

  @Override
  public boolean deleteByID(int id) throws SQLException {
    String query = "DELETE FROM Appointments WHERE Appointment_ID = ?";
    PreparedStatement stmt = this.cxn.prepareStatement(query);
    stmt.setInt(1, id);
    boolean success = stmt.execute();
    this.db.closeConnection();
    return success;
  }

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

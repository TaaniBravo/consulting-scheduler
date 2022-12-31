package edu.wgu.tmaama.db.Appointment.model;

import edu.wgu.tmaama.utils.DateTimeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment model class. Extra fields include localStart and localEnd for easier time displaying
 * data in user's local time.
 */
public class Appointment {
  private int appointmentID;
  private String title;
  private String description;
  private String location;
  private String type;
  private Timestamp start;
  private Timestamp end;
  private LocalDateTime localStart;
  private LocalDateTime localEnd;
  private Timestamp createDate;
  private String createdBy;
  private Timestamp lastUpdate;
  private String lastUpdatedBy;
  private int customerID;
  private int userID;
  private int contactID;

  /** Create appointment object with uninitialized fields. */
  public Appointment() {}

  /** Create an appointment class without update fields (appointment not in database yet). */
  public Appointment(
      String title,
      String description,
      String location,
      String type,
      Timestamp start,
      Timestamp end,
      String createdBy,
      int customerID,
      int userID,
      int contactID) {
    this.title = title;
    this.description = description;
    this.location = location;
    this.type = type;
    this.start = start;
    this.end = end;
    this.createdBy = createdBy;
    this.customerID = customerID;
    this.userID = userID;
    this.contactID = contactID;

    DateTimeConverter startConverter = new DateTimeConverter(this.start);
    DateTimeConverter endConverter = new DateTimeConverter(this.end);
    this.localStart = startConverter.getLocalDateTime();
    this.localEnd = endConverter.getLocalDateTime();
  }

  /** Create appointment object with all database fields. */
  public Appointment(
      int appointmentID,
      String title,
      String description,
      String location,
      String type,
      Timestamp start,
      Timestamp end,
      Timestamp createDate,
      String createdBy,
      Timestamp lastUpdate,
      String lastUpdatedBy,
      int customerID,
      int userID,
      int contactID) {
    this.appointmentID = appointmentID;
    this.title = title;
    this.description = description;
    this.location = location;
    this.type = type;
    this.start = start;
    this.end = end;
    this.createDate = createDate;
    this.createdBy = createdBy;
    this.lastUpdate = lastUpdate;
    this.lastUpdatedBy = lastUpdatedBy;
    this.customerID = customerID;
    this.userID = userID;
    this.contactID = contactID;

    DateTimeConverter startConverter = new DateTimeConverter(this.start);
    DateTimeConverter endConverter = new DateTimeConverter(this.end);
    this.localStart = startConverter.getLocalDateTime();
    this.localEnd = endConverter.getLocalDateTime();
  }

  /** Get appointment's appointment ID. */
  public int getAppointmentID() {
    return appointmentID;
  }

  /** Set appointment's appointment ID. */
  public void setAppointmentID(int appointmentID) {
    this.appointmentID = appointmentID;
  }

  /** Get appointment's title. */
  public String getTitle() {
    return title;
  }

  /** Set appointment's title. */
  public void setTitle(String title) {
    this.title = title;
  }

  /** Get appointment's description. */
  public String getDescription() {
    return description;
  }

  /** Set appointment's description */
  public void setDescription(String description) {
    this.description = description;
  }

  /** Get appointment's location. */
  public String getLocation() {
    return location;
  }

  /** Set appointment's location */
  public void setLocation(String location) {
    this.location = location;
  }

  /** Get appointment's type. */
  public String getType() {
    return type;
  }

  /** Set appointment's type */
  public void setType(String type) {
    this.type = type;
  }

  /** Get appointment's start. */
  public Timestamp getStart() {
    return start;
  }

  /** Set appointment's start. */
  public void setStart(Timestamp start) {
    this.start = start;
    DateTimeConverter dateTimeConverter = new DateTimeConverter(this.start);
    this.localStart = dateTimeConverter.getLocalDateTime();
  }

  /** Get appointment's end. */
  public Timestamp getEnd() {
    return end;
  }

  /** Set appointment's end. */
  public void setEnd(Timestamp end) {
    this.end = end;
    DateTimeConverter dateTimeConverter = new DateTimeConverter(this.end);
    this.localEnd = dateTimeConverter.getLocalDateTime();
  }

  /** Get appointment's create timestamp. */
  public Timestamp getCreateDate() {
    return createDate;
  }

  /** Get appointment's created by user. */
  public String getCreatedBy() {
    return createdBy;
  }

  /** Set appointment's created by user. */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /** Get appointment's last update timestamp. */
  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  /** Get appointment's last updated by user. */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /** Set appointment's last updated by user. */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /** Get appointment's customer ID. */
  public int getCustomerID() {
    return customerID;
  }

  /** Set appointment's customer ID. */
  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  /** Get appointment's user ID. */
  public int getUserID() {
    return userID;
  }

  /** Set appointment's user ID. */
  public void setUserID(int userID) {
    this.userID = userID;
  }

  /** Get appointment's contact ID. */
  public int getContactID() {
    return contactID;
  }

  /** Set appointment's contact ID. */
  public void setContactID(int contactID) {
    this.contactID = contactID;
  }

  /** Returns a string of a display friendly version of the localStart */
  public String getLocalStart() {
    return localStart.format(DateTimeFormatter.ofPattern(DateTimeConverter.DISPLAY_FORMAT));
  }

  /** Get appointment's localStart. */
  public LocalDateTime getLocalStartDateTime() {
    return localStart;
  }

  /** Returns a string of a display friendly version of the localEnd. */
  public String getLocalEnd() {
    return localEnd.format(DateTimeFormatter.ofPattern(DateTimeConverter.DISPLAY_FORMAT));
  }

  /** Get appointment's localEnd. */
  public LocalDateTime getLocalEndDateTime() {
    return localEnd;
  }
}

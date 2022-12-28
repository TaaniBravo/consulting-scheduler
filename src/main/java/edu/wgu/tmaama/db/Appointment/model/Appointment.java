package edu.wgu.tmaama.db.Appointment.model;

import edu.wgu.tmaama.utils.DateTimeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public Appointment() {}

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
  }

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

  public int getAppointmentID() {
    return appointmentID;
  }

  public void setAppointmentID(int appointmentID) {
    this.appointmentID = appointmentID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Timestamp getStart() {
    return start;
  }

  public void setStart(Timestamp start) {
    this.start = start;
    DateTimeConverter dateTimeConverter = new DateTimeConverter(this.start);
    this.localStart = dateTimeConverter.getLocalDateTime();
  }

  public Timestamp getEnd() {
    return end;
  }

  public void setEnd(Timestamp end) {
    this.end = end;
    DateTimeConverter dateTimeConverter = new DateTimeConverter(this.end);
    this.localEnd = dateTimeConverter.getLocalDateTime();
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getContactID() {
    return contactID;
  }

  public void setContactID(int contactID) {
    this.contactID = contactID;
  }

  public String getLocalStart() {
    return localStart.format(DateTimeFormatter.ofPattern(DateTimeConverter.DISPLAY_FORMAT));
  }

  public LocalDateTime getLocalStartDateTime() {
    return localStart;
  }

  public String getLocalEnd() {
    return localEnd.format(DateTimeFormatter.ofPattern(DateTimeConverter.DISPLAY_FORMAT));
  }

  public LocalDateTime getLocalEndDateTime() {
    return localEnd;
  }

}

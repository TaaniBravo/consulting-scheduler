package edu.wgu.tmaama.utils;

import java.util.ResourceBundle;

/**
 * Static class with constants for the translated error messages.
 */
public class ErrorMessages {
  private static final ResourceBundle resources = ResourceBundle.getBundle("/bundles/messages");
  public static final String APPOINTMENT_BLANK_TITLE =
      resources.getString("error.appointment.title.blank");
  public static final String APPOINTMENT_BLANK_DESC =
      resources.getString("error.appointment.desc.blank");
  public static final String APPOINTMENT_BLANK_LOCATION =
      resources.getString("error.appointment.location.blank");
  public static final String APPOINTMENT_BLANK_TYPE =
      resources.getString("error.appointment.type.blank");
  public static final String APPOINTMENT_BLANK_START =
      resources.getString("error.appointment.start.blank");
  public static final String APPOINTMENT_BLANK_END =
      resources.getString("error.appointment.end.blank");
  public static final String APPOINTMENT_START_FORMAT =
      String.format(
          resources.getString("error.appointment.start.format"), DateTimeConverter.DATE_FORMAT);
  public static final String APPOINTMENT_END_FORMAT =
      String.format(
          resources.getString("error.appointment.end.format"), DateTimeConverter.DATE_FORMAT);
  public static final String APPOINTMENT_SELECT_CUSTOMER =
      resources.getString("error.appointment.customer");
  public static final String APPOINTMENT_SELECT_USER =
      resources.getString("error.appointment.user");
  public static final String APPOINTMENT_SELECT_CONTACT =
      resources.getString("error.appointment.contact");
  public static final String APPOINTMENT_START_AFTER_END =
      resources.getString("error.appointment.start.after.end");
  public static final String APPOINTMENT_START_BEFORE_NOW =
      resources.getString("error.appointment.start.after.now");
  public static final String APPOINTMENT_OUTSIDE_OF_BUSINESS_HOURS =
      resources.getString("error.appointment.outside.business.hours");
  public static final String GET_USER_APPOINTMENTS = resources.getString("error.get.user.appointments");
}

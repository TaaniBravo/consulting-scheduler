package edu.wgu.tmaama.utils;

import java.util.ResourceBundle;

public class ConfirmMessages {
  private static final ResourceBundle resources = ResourceBundle.getBundle("/bundles/messages");

  public static String confirmDeleteCustomer(String customerName) {
    return String.format(resources.getString("confirm.customer.delete"), customerName);
  }

  public static String confirmDeleteAppointment(int appointmentID, String appointmentType) {
    return String.format(
        resources.getString("confirm.appointment.delete"), appointmentID, appointmentType);
  }
}

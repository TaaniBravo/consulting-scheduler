package edu.wgu.tmaama.utils;

import java.util.ResourceBundle;

public class ConfirmMessages {
  private static final ResourceBundle resources = ResourceBundle.getBundle("/bundles/messages");

  public static String confirmDeleteCustomer(String customerName) {
    return resources.getString("confirm.customer.delete").replace("$customerName", customerName);
  }
}
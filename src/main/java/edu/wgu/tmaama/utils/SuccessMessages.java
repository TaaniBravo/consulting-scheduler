package edu.wgu.tmaama.utils;

import java.util.ResourceBundle;

/**
 * Static class that contains Success translated messages.
 */
public class SuccessMessages {
  private static final ResourceBundle resources = ResourceBundle.getBundle("/bundles/messages");
  public static final String CUSTOMER_ADDED = resources.getString("success.customer.add");
  public static final String CUSTOMER_UPDATED = resources.getString("success.customer.update");
}

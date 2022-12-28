package edu.wgu.tmaama.utils;

import java.util.ResourceBundle;

public class InfoMessages {
    private static final ResourceBundle resources = ResourceBundle.getBundle("/bundles/messages");
    public static final String NO_UPCOMING_APPOINTMENTS = resources.getString("info.no.upcoming.appointments");
    public static final String UPCOMING_APPOINTMENTS = resources.getString("info.upcoming.appointments");
}

module edu.wgu.tmaama {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens edu.wgu.tmaama to
            javafx.fxml;
    opens edu.wgu.tmaama.controllers.fxml to
            javafx.fxml;
    opens edu.wgu.tmaama.db.Customer.model to
            javafx.base;
    opens edu.wgu.tmaama.db.Appointment.model to
            javafx.base;
    opens views to
            javafx.fxml;
    opens css to
            javafx.fxml;
    opens fonts to
            javafx.fxml;
    opens db to
            java.sql;

    exports edu.wgu.tmaama;
}

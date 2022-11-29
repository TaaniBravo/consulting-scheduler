module edu.wgu.tmaama {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.wgu.tmaama to javafx.fxml;
    opens edu.wgu.tmaama.controllers to javafx.fxml;
    opens views to javafx.fxml;
    exports edu.wgu.tmaama;
}
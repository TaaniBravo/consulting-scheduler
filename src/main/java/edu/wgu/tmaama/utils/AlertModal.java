package edu.wgu.tmaama.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class AlertModal {
  private final Alert alert;

  public AlertModal(Alert.AlertType type) {
    this.alert = new Alert(type, "");
  }

  public boolean displayAndConfirm(String header, String content) {
    this.alert.initModality(Modality.APPLICATION_MODAL);
    alert.getDialogPane().setHeaderText(header);
    alert.getDialogPane().setContentText(content);
    Optional<ButtonType> result = this.alert.showAndWait();
    if (result.isEmpty()) return false;
    return result.get() == ButtonType.OK;
  }
}

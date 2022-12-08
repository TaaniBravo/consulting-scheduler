package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.db.User.model.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {
  private Parent pane;
  private Scene scene;
  private Stage primaryStage;
  private User sessionUser;

  public void initialize() {}

  public User getSessionUser() {
    return sessionUser;
  }

  public void setSessionUser(User sessionUser) {
    this.sessionUser = sessionUser;
  }
}

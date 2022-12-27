package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.Salt.dao.ConcreteSaltDAO;
import edu.wgu.tmaama.db.Salt.model.Salt;
import edu.wgu.tmaama.db.User.dao.ConcreteUserDAO;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.Password;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {
  private final ResourceBundle resources = ResourceBundle.getBundle("bundles/main");
  @FXML private TextField usernameTextField;
  @FXML private PasswordField passwordTextField;
  @FXML private Label errorLabel;
  @FXML private Label zoneLabel;

  public void initialize() {
    zoneLabel.setText(ZoneId.systemDefault().toString());
  }

  @FXML
  private void handleLogin(ActionEvent actionEvent) throws IOException {
    String username = usernameTextField.getText();
    try {
      Database db = new Database();
      ConcreteUserDAO userDAO = new ConcreteUserDAO(db);
      ConcreteSaltDAO saltDAO = new ConcreteSaltDAO(db);
      User user = userDAO.findByUsername(username);
      if (user == null) {
        this.printError(this.resources.getString("login.error.username"));
        return;
      }

      Salt salt = saltDAO.findByUserID(user.getUserID());
      Password enteredPassword = new Password(passwordTextField.getText(), salt);
      boolean isPasswordValid = Password.comparePasswords(user.getPassword(), enteredPassword);

      if (!isPasswordValid) {
        this.printError(this.resources.getString("login.error.username"));
        return;
      }

      this.redirectToHomePage(actionEvent, user);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleSignUp(ActionEvent actionEvent) {
    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    try {
      Database db = new Database();
      ConcreteUserDAO userDAO = new ConcreteUserDAO(db);
      User user = userDAO.findByUsername(username);
      if (user != null) {
        this.printError(this.resources.getString("login.error.username.taken"));
        return;
      }

      Password passwordHash = new Password(password);
      User newUser = new User(username, passwordHash, username);
      User createdUser = userDAO.insert(newUser);

      this.redirectToHomePage(actionEvent, createdUser);
    } catch (SQLException | IOException ex) {
      ex.printStackTrace();
    }
  }

  private void redirectToHomePage(ActionEvent actionEvent, User user) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Home.fxml")));
    loader.setResources(this.resources);
    Parent pane = loader.load();
    HomeController homeController = loader.getController();
    homeController.setSessionUser(user);
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  private void printError(String message) {
    this.errorLabel.setText(message);
  }
}

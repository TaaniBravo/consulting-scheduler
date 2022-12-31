package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.dao.ConcreteAppointmentDAO;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Contact.dao.ConcreteContactDAO;
import edu.wgu.tmaama.db.Contact.model.Contact;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.FXHelpers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the ContactReport stage.
 */
public class ContactReportController {
  private final ResourceBundle resources = ResourceBundle.getBundle("/bundles/main");
  private User sessionUser;
  @FXML private TableView<Appointment> appointmentTableView;
  @FXML private TableColumn<Appointment, Integer> appointmentIdTableCol;
  @FXML private TableColumn<Appointment, String> appointmentTitleTableCol;
  @FXML private TableColumn<Appointment, String> appointmentDescTableCol;
  @FXML private TableColumn<Appointment, String> appointmentLocationTableCol;
  @FXML private TableColumn<Appointment, String> appointmentTypeTableCol;
  @FXML private TableColumn<Appointment, Timestamp> appointmentStartTableCol;
  @FXML private TableColumn<Appointment, Timestamp> appointmentEndTableCol;
  @FXML private TableColumn<Appointment, Integer> appointmentCustomerIDTableCol;
  @FXML private TableColumn<Appointment, Integer> appointmentUserIDTableCol;
  @FXML private ComboBox<Contact> contactComboBox;

  /**
   * Initializes the controller.
   */
  public void initialize() {
    this.initializeTableViewCells();
    this.initializeContactComboBox();

    Platform.runLater(
        () -> {
          FXHelpers.setTableWidth(this.appointmentTableView);
        });
  }

  /**
   * Initializes the appointmentTableView.
   */
  private void initializeTableViewCells() {
    this.appointmentIdTableCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
    this.appointmentTitleTableCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    this.appointmentDescTableCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    this.appointmentLocationTableCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    this.appointmentTypeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    this.appointmentStartTableCol.setCellValueFactory(new PropertyValueFactory<>("localStart"));
    this.appointmentEndTableCol.setCellValueFactory(new PropertyValueFactory<>("localEnd"));
    this.appointmentCustomerIDTableCol.setCellValueFactory(
        new PropertyValueFactory<>("customerID"));
    this.appointmentUserIDTableCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
  }

  /**
   * Initializes the contactComboBox by fetching all contacts from the database.
   */
  private void initializeContactComboBox() {
    try {
      ConcreteContactDAO contactDAO = new ConcreteContactDAO();
      ObservableList<Contact> contacts = FXCollections.observableArrayList(contactDAO.findAll());
      this.contactComboBox.setItems(contacts);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles the Generate Report button by trying to find the accurate the data from the database.
   */
  @FXML
  private void handleGenerateReport() {
    Contact contact = this.contactComboBox.getSelectionModel().getSelectedItem();
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      ObservableList<Appointment> appointments =
          FXCollections.observableArrayList(
              appointmentDAO.findAppointmentsByContactID(contact.getContactID()));
      this.appointmentTableView.setItems(appointments);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles the Reset button by clearing the contactComboBox and setting the table to empty.
   */
  @FXML
  private void handleReset() {
    this.appointmentTableView.setItems(null);
    this.contactComboBox.getSelectionModel().clearSelection();
  }

  /**
   * Handles the Cancel button by redirecting the user to the Home stage.
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  private void handleCancel(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Home.fxml")));
    loader.setResources(this.resources);
    Parent pane = loader.load();
    HomeController homeController = loader.getController();
    homeController.setSessionUser(sessionUser);
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the sessionUser.
   * @param user
   */
  public void setSessionUser(User user) {
    this.sessionUser = user;
  }
}

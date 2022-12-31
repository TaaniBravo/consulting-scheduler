package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.dao.ConcreteAppointmentDAO;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
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
import javafx.scene.control.Label;
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
 * Controller for TypeReport stage.
 */
public class TypeReportController {
  private final ResourceBundle resources = ResourceBundle.getBundle("/bundles/main");
  private User sessionUser;
  @FXML private Label countLabel;
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
  @FXML private ComboBox<String> typeComboBox;
  @FXML private ComboBox<String> monthComboBox;

  /**
   * Initializes the controller.
   */
  public void initialize() {
    this.initializeTableViewCells();
    this.initializeComboBoxes();

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
   * Initializes the combo boxes.
   */
  private void initializeComboBoxes() {
    this.initializeMonthComboBox();
    this.initializeTypeComboBox();
  }

  /**
   * Initializes the monthComboBox by loading the resources into the combo box.
   */
  private void initializeMonthComboBox() {
    String[] monthsArray = this.resources.getString("type.report.months").split(",");
    ObservableList<String> months = FXCollections.observableArrayList(monthsArray);
    this.monthComboBox.setItems(months);
  }

  /**
   * Initializes the typeComboBox by loading the resources into the combo box from the database.
   */
  private void initializeTypeComboBox() {
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      this.typeComboBox.setItems(
          FXCollections.observableArrayList(appointmentDAO.findAppointmentTypes()));
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles the Generate Report button by trying to find the accurate the data from the database.
   */
  @FXML
  private void handleGenerateReport() {
    String type = this.typeComboBox.getSelectionModel().getSelectedItem();
    int month = this.monthComboBox.getSelectionModel().getSelectedIndex() + 1;
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      ObservableList<Appointment> appointments =
          FXCollections.observableArrayList(appointmentDAO.findAllByTypeAndMonth(type, month));
      this.countLabel.setText(
          this.resources.getString("type.report.total.count") + " " + appointments.size());
      this.appointmentTableView.setItems(appointments);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles the reset button but clearing the combo boxes and setting the table to null.
   */
  @FXML
  private void handleReset() {
    this.monthComboBox.getSelectionModel().clearSelection();
    this.typeComboBox.getSelectionModel().clearSelection();
    this.appointmentTableView.setItems(null);
  }

  /**
   * Handles the cancel button by redirecting the user to the Home stage.
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

package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.dao.ConcreteAppointmentDAO;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Country.dao.ConcreteCountryDAO;
import edu.wgu.tmaama.db.Country.model.Country;
import edu.wgu.tmaama.db.FirstLevelDivision.dao.ConcreteFirstLevelDivisionDAO;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the DivisionReport stage.
 */
public class DivisionReportController {
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
  @FXML private ComboBox<Country> countryComboBox;
  @FXML private ComboBox<FirstLevelDivision> divisionComboBox;
  private ObservableList<FirstLevelDivision> divisions;

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
   * Initializes the comboBoxes
   */
  private void initializeComboBoxes() {
    this.initializeCountryComboBox();
    this.initializeDivisionComboBox();
  }

  /**
   * Fetches the countries from the database and loads them into the ComboBox.
   */
  private void initializeCountryComboBox() {
    try {
      ConcreteCountryDAO countryDAO = new ConcreteCountryDAO();
      ObservableList<Country> countries = FXCollections.observableArrayList(countryDAO.findAll());
      this.countryComboBox.setItems(countries);
      this.countryComboBox.getSelectionModel().select(0);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Fetches the First Level Divisions from the database and then based on the country selected in
   * countryComboBox it will filter out the correct divisions to be available to be selected.
   */
  private void initializeDivisionComboBox() {
    try {
      ConcreteFirstLevelDivisionDAO divisionDAO = new ConcreteFirstLevelDivisionDAO();
      this.divisions = FXCollections.observableArrayList(divisionDAO.findAll());
      this.handleSelectedCountry();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Handles the selected country by updating the division ComboBox to only contain divisions that
   * match the selected country's countryID
   */
  @FXML
  private void handleSelectedCountry() {
    Country country = this.countryComboBox.getSelectionModel().getSelectedItem();
    if (country == null) return;
    ObservableList<FirstLevelDivision> filteredDivisions =
        this.divisions.filtered(division -> division.getCountryID() == country.getCountryID());
    this.divisionComboBox.setItems(filteredDivisions);
  }

  /**
   * Handles the Generate Report button by trying to find the accurate the data from the database.
   */
  @FXML
  private void handleGenerateReport() {
    FirstLevelDivision division = this.divisionComboBox.getSelectionModel().getSelectedItem();
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      ObservableList<Appointment> appointments =
          FXCollections.observableArrayList(
              appointmentDAO.findAppointmentsByDivisionID(division.getDivisionID()));
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
    this.countryComboBox.getSelectionModel().select(0);
    this.divisionComboBox.getSelectionModel().clearSelection();
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

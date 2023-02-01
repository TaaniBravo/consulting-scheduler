package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.dao.ConcreteAppointmentDAO;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Customer.dao.ConcreteCustomerDAO;
import edu.wgu.tmaama.db.Customer.model.Customer;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.AlertModal;
import edu.wgu.tmaama.utils.ConfirmMessages;
import edu.wgu.tmaama.utils.FXHelpers;
import edu.wgu.tmaama.utils.Modal;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

/** Controller for Home stage. */
public class HomeController {
  private final ResourceBundle bundle = ResourceBundle.getBundle("bundles/main");
  @FXML private TextField searchCustomerTextField;
  @FXML private TableView<Customer> customerTableView;
  @FXML private Button updateCustomerButton;
  @FXML private Button deleteCustomerButton;
  @FXML private TableColumn<Customer, Integer> customerIdTableCol;
  @FXML private TableColumn<Customer, String> customerNameTableCol;
  @FXML private TableColumn<Customer, String> customerAddressTableCol;
  @FXML private TableColumn<Customer, String> customerPostalCodeTableCol;
  @FXML private TableColumn<Customer, String> customerPhoneTableCol;
  @FXML private TableColumn<Customer, Integer> customerDivisionIDTableCol;
  @FXML private TableColumn<FirstLevelDivision, String> customerDivisionTableCol;
  @FXML private TableColumn<Customer, Integer> customerCountryIDTableCol;
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
  @FXML private Button updateAppointmentButton;
  @FXML private Button deleteAppointmentButton;
  @FXML private Label usernameLabel;
  @FXML private RadioButton monthRadioButton;
  @FXML private RadioButton weekRadioButton;
  @FXML private MenuBar reportMenuBar;
  private User sessionUser;
  private Customer selectedCustomer;
  private ObservableList<Customer> customers;
  private ObservableList<Appointment> appointments;

  /** Initializes the controller. */
  public void initialize() {
    this.initializeCustomerTable();
    this.initializeCustomerSearchBar();
    this.initializeAppointmentTable();
  }

  /**
   * Set customers field that will be used in the customerTableView
   *
   * @param customers
   */
  private void setCustomers(ObservableList<Customer> customers) {
    this.customers = customers;
    this.customerTableView.setItems(this.customers);
  }

  /**
   * Sets the appointments field that will be used in the appointmentTableView.
   *
   * @param appointments
   */
  private void setAppointments(ObservableList<Appointment> appointments) {
    this.appointments = appointments;
    this.appointmentTableView.setItems(this.appointments);
  }

  /**
   * Returns the current session user.
   *
   * @return
   */
  public User getSessionUser() {
    return sessionUser;
  }

  /**
   * Sets the current session user.
   *
   * @param sessionUser
   */
  public void setSessionUser(User sessionUser) {
    this.sessionUser = sessionUser;
    this.usernameLabel.setText(sessionUser.getUsername());
  }

  /**
   * Signs out the user and returns them to the login page.
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  private void handleSignOut(ActionEvent actionEvent) throws IOException {
    this.redirectToLoginPage(actionEvent);
  }

  /**
   * Redirects the user to the login stage.
   *
   * @param actionEvent
   * @throws IOException
   */
  private void redirectToLoginPage(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Login.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Redirects the user to the Customer stage.
   *
   * @param actionEvent
   * @param customer
   * @throws IOException
   */
  private void redirectToCustomerPage(ActionEvent actionEvent, Customer customer)
      throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Customer.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    CustomerController customerController = loader.getController();
    if (customer != null) {
      customerController.setCustomer(customer);
    }

    customerController.setSessionUser(this.sessionUser);
    loader.setResources(this.bundle);
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Redirects the user to the appointment page.
   *
   * @param actionEvent
   * @param appointment
   * @throws IOException
   */
  private void redirectToAppointmentPage(ActionEvent actionEvent, Appointment appointment)
      throws IOException {
    FXMLLoader loader =
        new FXMLLoader(
            Objects.requireNonNull(Scheduler.class.getResource("/views/Appointment.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    AppointmentController appointmentController = loader.getController();
    if (appointment != null) {
      appointmentController.setAppointment(appointment);
      appointmentController.setIsUpdating(true);
    }
    appointmentController.setSessionUser(this.getSessionUser());
    Customer customer = this.customerTableView.getSelectionModel().getSelectedItem();
    appointmentController.setCustomer(customer);
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void redirectToTypeReportPage() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(
            Objects.requireNonNull(Scheduler.class.getResource("/views/TypeReport.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    TypeReportController typeReportController = loader.getController();
    typeReportController.setSessionUser(this.sessionUser);
    Stage stage = (Stage) reportMenuBar.getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void redirectToContactReportPage() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(
            Objects.requireNonNull(Scheduler.class.getResource("/views/ContactReport.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    ContactReportController contactReportController = loader.getController();
    contactReportController.setSessionUser(this.sessionUser);
    Stage stage = (Stage) reportMenuBar.getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void redirectToDivisionReportPage() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(
            Objects.requireNonNull(Scheduler.class.getResource("/views/DivisionReport.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    DivisionReportController divisionReportController = loader.getController();
    divisionReportController.setSessionUser(this.sessionUser);
    Stage stage = (Stage) reportMenuBar.getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Handles the Add Customer button.
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  private void handleAddCustomer(ActionEvent actionEvent) throws IOException {
    this.redirectToCustomerPage(actionEvent, null);
  }

  /**
   * Handles the Update Customer button.
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  private void handleUpdateCustomer(ActionEvent actionEvent) throws IOException {
    Customer customer = this.customerTableView.getSelectionModel().getSelectedItem();
    if (customer != null) {
      this.redirectToCustomerPage(actionEvent, customer);
    }
  }

  /**
   * Handles the Delete Customer button.
   *
   * @throws SQLException
   */
  @FXML
  private void handleDeleteCustomer() throws SQLException {
    Customer customer = this.customerTableView.getSelectionModel().getSelectedItem();
    if (customer == null) return;
    AlertModal alertModal = new AlertModal(Alert.AlertType.CONFIRMATION);
    String header = Modal.DELETE;
    String content = ConfirmMessages.confirmDeleteCustomer(customer.getCustomerName());
    if (!alertModal.displayAndConfirm(header, content)) return;
    ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO();
    boolean success = customerDAO.deleteByID(customer.getCustomerID());
    if (!success) {
      return;
    }

    this.customers.remove(customer);
    this.customerTableView.getItems().remove(customer);
    this.fetchAppointments();
  }

  /**
   * Initializes the customer table by getting the customer list from the database and preparing the
   * TableView cells. A simple lambda allows us to use our method setTableWidth and pass in the
   * argument that we want.
   */
  private void initializeCustomerTable() {
    this.fetchCustomersListFromDB();

    this.customerIdTableCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    this.customerNameTableCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    this.customerAddressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    this.customerPostalCodeTableCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    this.customerPhoneTableCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    this.customerDivisionIDTableCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    this.customerDivisionTableCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    this.customerCountryIDTableCol.setCellValueFactory(new PropertyValueFactory<>("countryID"));

    Platform.runLater(
        () -> {
          FXHelpers.setTableWidth(this.customerTableView);
        });
  }

  /** Fetches the customer list from the database. */
  private void fetchCustomersListFromDB() {
    try {
      // Get Customers
      ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO();
      HashMap<String, String> options = new HashMap<>();
      options.put("LIMIT", "50");
      ObservableList<Customer> customers =
          FXCollections.observableArrayList(customerDAO.findAll(options));
      this.setCustomers(customers);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Initializes the customer search bar. Using a lambda here allows us to write one time code that
   * we need to use only for this search bar.
   */
  private void initializeCustomerSearchBar() {
    this.searchCustomerTextField
        .textProperty()
        .addListener(
            ((observableValue, oldValue, newValue) -> {
              try {
                ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO();
                if (!newValue.equals("")) {
                  HashMap<String, String> options = new HashMap<>();
                  options.put(ConcreteCustomerDAO.LIMIT, "50");
                  try {
                    Integer.parseInt(newValue);
                    options.put(ConcreteCustomerDAO.CUSTOMER_ID, newValue);
                  } catch (NumberFormatException ex) {
                    options.put(ConcreteCustomerDAO.CUSTOMER_NAME, newValue);
                  }
                  ObservableList<Customer> filteredList =
                      FXCollections.observableArrayList(customerDAO.findAll(options));
                  filteredList.filtered(
                      customer ->
                          newValue.contains(String.valueOf(customer.getCustomerID()))
                              || customer.getCustomerName().contains(newValue));

                  this.customerTableView.setItems(filteredList);
                } else {
                  this.customerTableView.setItems(this.customers);
                }
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }
            }));
  }

  /**
   * Initializes the appointment table and its cells. A lambda is useful here as the method we want to use
   * setTableWidth needs to be passed in a TableView and to write a method for this specific reason would
   * make the code less readable with the abstraction.
   */
  private void initializeAppointmentTable() {
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

    Platform.runLater(
        () -> {
          FXHelpers.setTableWidth(this.appointmentTableView);
        });
  }

  /**
   * Handles the selection of a customer on the customerTableView.
   */
  @FXML
  private void handleSelectedCustomer() {
    Customer customer = this.customerTableView.getSelectionModel().getSelectedItem();
    if (customer == null) return;
    if (customer.equals(this.selectedCustomer)) {
      this.selectedCustomer = null;
      this.customerTableView.getSelectionModel().clearSelection();
    } else {
      this.selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
    }

    this.updateCustomerButton.setDisable(this.selectedCustomer == null);
    this.deleteCustomerButton.setDisable(this.selectedCustomer == null);
    this.fetchAppointments();
  }

  /**
   * Handles the selection of an appointment on the appointmentTableView.
   */
  @FXML
  private void handleSelectedAppointment() {
    Appointment selectedAppointment =
        this.appointmentTableView.getSelectionModel().getSelectedItem();
    if (selectedAppointment == null) {
      this.updateAppointmentButton.setDisable(true);
      this.deleteAppointmentButton.setDisable(true);
      return;
    }

    this.updateAppointmentButton.setDisable(false);
    this.deleteAppointmentButton.setDisable(false);
  }

  /**
   * Handles the add appointment button.
   * @param event
   * @throws IOException
   */
  @FXML
  private void handleAddAppointment(ActionEvent event) throws IOException {
    this.redirectToAppointmentPage(event, null);
  }

  /**
   * Handles the update appointment button.
   * @param event
   * @throws IOException
   */
  @FXML
  private void handleUpdateAppointment(ActionEvent event) throws IOException {
    Appointment selectedAppointment =
        this.appointmentTableView.getSelectionModel().getSelectedItem();
    if (selectedAppointment != null) {
      this.redirectToAppointmentPage(event, selectedAppointment);
    }
  }

  /**
   * Handles the delete appointment button.
   */
  @FXML
  private void handleDeleteAppointment() {
    try {
      Appointment appointment = this.appointmentTableView.getSelectionModel().getSelectedItem();
      assert appointment != null;
      AlertModal alertModal = new AlertModal(Alert.AlertType.CONFIRMATION);
      String header = Modal.DELETE;
      String content =
          ConfirmMessages.confirmDeleteAppointment(
              appointment.getAppointmentID(), appointment.getType());
      if (!alertModal.displayAndConfirm(header, content)) return;
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      boolean success = appointmentDAO.deleteByID(appointment.getAppointmentID());
      if (!success) return;

      this.appointments.remove(appointment);
      this.appointmentTableView.getItems().remove(appointment);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles the Appointment Month RadioButton.
   */
  @FXML
  private void handleAppointmentMonthRadio() {
    if (weekRadioButton.isSelected()) weekRadioButton.setSelected(false);
    this.fetchAppointments();
  }

  /**
   * Handles the Appointment Week RadioButton.
   */
  @FXML
  private void handleAppointmentWeekRadio() {
    if (monthRadioButton.isSelected()) monthRadioButton.setSelected(false);
    this.fetchAppointments();
  }

  /**
   * Fetches the appointment based on the filters selected from the database.
   */
  private void fetchAppointments() {
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      ArrayList<Appointment> appointments;

      if (monthRadioButton.isSelected()) {
        appointments =
            this.selectedCustomer == null
                ? appointmentDAO.findAppointmentsForCurrentMonth()
                : appointmentDAO.findAppointmentsForCurrentMonth(
                    this.selectedCustomer.getCustomerID());
      } else if (weekRadioButton.isSelected()) {
        appointments =
            this.selectedCustomer == null
                ? appointmentDAO.findAppointmentsForCurrentWeek()
                : appointmentDAO.findAppointmentsForCurrentWeek(
                    this.selectedCustomer.getCustomerID());
      } else {
        appointments =
            this.selectedCustomer == null
                ? appointmentDAO.findAll()
                : appointmentDAO.findAppointmentsByCustomerID(
                    this.selectedCustomer.getCustomerID());
      }

      this.setAppointments(FXCollections.observableArrayList(appointments));
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController {
  private final ResourceBundle bundle = ResourceBundle.getBundle("bundles/main");
  @FXML private TextField searchCustomerTextField;
  @FXML private TextField searchAppointmentTextField;
  @FXML private TableView<Customer> customerTableView;
  @FXML private VBox customerButtonPane;
  @FXML private Button updateCustomerButton;
  @FXML private Button deleteCustomerButton;
  @FXML private TableColumn<Customer, Integer> customerIdTableCol;
  @FXML private TableColumn<Customer, String> customerNameTableCol;
  @FXML private TableColumn<Customer, String> customerAddressTableCol;
  @FXML private TableColumn<Customer, String> customerPostalCodeTableCol;
  @FXML private TableColumn<Customer, String> customerPhoneTableCol;
  @FXML private TableColumn<FirstLevelDivision, String> customerDivisionTableCol;
  @FXML private VBox appointmentButtonPane;
  @FXML private TableView<Appointment> appointmentTableView;
  @FXML private TableColumn<Appointment, Integer> appointmentIdTableCol;
  @FXML private TableColumn<Appointment, String> appointmentTitleTableCol;
  @FXML private TableColumn<Appointment, String> appointmentDescTableCol;
  @FXML private TableColumn<Appointment, String> appointmentLocationTableCol;
  @FXML private TableColumn<Appointment, String> appointmentTypeTableCol;
  @FXML private TableColumn<Appointment, Timestamp> appointmentStartTableCol;
  @FXML private TableColumn<Appointment, Timestamp> appointmentEndTableCol;
  @FXML private Button addAppointmentButton;
  @FXML private Button updateAppointmentButton;
  @FXML private Button deleteAppointmentButton;
  @FXML private Label usernameLabel;
  private User sessionUser;
  private Customer selectedCustomer;
  private ObservableList<Customer> customers;
  private ObservableList<Appointment> appointments;
  private Appointment selectedAppointment;

  public void initialize() {
    this.initializeCustomerTable();
    this.initializeCustomerSearchBar();
    this.initializeAppointmentTable();
    this.initializeAppointmentSearchBar();
  }

  private void setCustomers(ObservableList<Customer> customers) {
    this.customers = customers;
    this.customerTableView.setItems(this.customers);
  }

  private void setAppointments(ObservableList<Appointment> appointments) {
    this.appointments = appointments;
    this.appointmentTableView.setItems(this.appointments);
  }

  public User getSessionUser() {
    return sessionUser;
  }

  public void setSessionUser(User sessionUser) {
    this.sessionUser = sessionUser;
    this.usernameLabel.setText(sessionUser.getUsername());
  }

  @FXML
  private void handleSignOut(ActionEvent actionEvent) throws IOException {
    this.redirectToLoginPage(actionEvent);
  }

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
  private void handleAddCustomer(ActionEvent actionEvent) throws IOException {
    this.redirectToCustomerPage(actionEvent, null);
  }

  @FXML
  private void handleUpdateCustomer(ActionEvent actionEvent) throws IOException {
    Customer customer = this.customerTableView.getSelectionModel().getSelectedItem();
    if (customer != null) {
      this.redirectToCustomerPage(actionEvent, customer);
    }
  }

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
      // TODO: Display error.
      return;
    }

    this.customers.remove(customer);
    this.customerTableView.getItems().remove(customer);
  }

  private void initializeCustomerTable() {
    this.getCustomersListFromDB();

    this.customerIdTableCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    this.customerNameTableCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    this.customerAddressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    this.customerPostalCodeTableCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    this.customerPhoneTableCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    this.customerDivisionTableCol.setCellValueFactory(new PropertyValueFactory<>("division"));

    Platform.runLater(
        () -> {
          this.setTableWidth(this.customerTableView);
        });
  }

  private void getCustomersListFromDB() {
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

  private void initializeAppointmentTable() {
    this.appointmentIdTableCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
    this.appointmentTitleTableCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    this.appointmentDescTableCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    this.appointmentLocationTableCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    this.appointmentTypeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    this.appointmentStartTableCol.setCellValueFactory(new PropertyValueFactory<>("localStart"));
    this.appointmentEndTableCol.setCellValueFactory(new PropertyValueFactory<>("localEnd"));

    Platform.runLater(
        () -> {
          this.setTableWidth(this.appointmentTableView);
        });
  }

  private void initializeAppointmentSearchBar() {}

  private void setTableWidth(TableView<?> table) {
    // Fill the columns to the correct lengths.
    double tableWidth = table.getWidth();
    var columns = table.getColumns();
    double calcColWidth = tableWidth / columns.size();
    for (var col : columns) {
      col.setPrefWidth(calcColWidth);
    }
  }

  @FXML
  private void handleSelectedCustomer() {
    this.selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
    if (this.selectedCustomer == null) {
      this.updateCustomerButton.setDisable(true);
      this.deleteCustomerButton.setDisable(true);
      this.appointmentTableView.getItems().clear();
      return;
    }

    this.updateCustomerButton.setDisable(false);
    this.deleteCustomerButton.setDisable(false);
    this.loadCustomerAppointments();
  }

  @FXML
  private void handleSelectedAppointment() {
    this.selectedAppointment = this.appointmentTableView.getSelectionModel().getSelectedItem();
    if (this.selectedAppointment == null) {
      this.updateAppointmentButton.setDisable(true);
      this.deleteAppointmentButton.setDisable(true);
      return;
    }

    this.updateAppointmentButton.setDisable(false);
    this.deleteAppointmentButton.setDisable(false);
  }

  @FXML
  private void handleAddAppointment(ActionEvent event) throws IOException {
    this.redirectToAppointmentPage(event, null);
  }

  @FXML
  private void handleUpdateAppointment(ActionEvent event) throws IOException {
    Appointment selectedAppointment =
        this.appointmentTableView.getSelectionModel().getSelectedItem();
    if (selectedAppointment != null) {
      this.redirectToAppointmentPage(event, selectedAppointment);
    }
  }

  @FXML
  private void handleDeleteAppointment(ActionEvent event) {
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
      // TODO: Display error.
      if (!success) return;

      this.appointments.remove(appointment);
      this.appointmentTableView.getItems().remove(appointment);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  private void loadCustomerAppointments() {
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      ObservableList<Appointment> appointments =
          FXCollections.observableArrayList(
              appointmentDAO.findAppointmentsByCustomerID(this.selectedCustomer.getCustomerID()));
      this.setAppointments(appointments);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}

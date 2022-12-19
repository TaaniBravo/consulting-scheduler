package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Customer.dao.ConcreteCustomerDAO;
import edu.wgu.tmaama.db.Customer.model.Customer;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;
import edu.wgu.tmaama.db.User.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController {
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles/translate");
    @FXML
    private TextField searchCustomerTextField;
    @FXML
    private TextField searchAppointmentTextField;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private VBox customerButtonPane;
    @FXML
    private TableColumn<Customer, Integer> customerIdTableCol;
    @FXML
    private TableColumn<Customer, String> customerNameTableCol;
    @FXML
    private TableColumn<Customer, String> customerAddressTableCol;
    @FXML
    private TableColumn<Customer, String> customerPostalCodeTableCol;
    @FXML
    private TableColumn<Customer, String> customerPhoneTableCol;
    @FXML
    private TableColumn<FirstLevelDivision, String> customerDivisionTableCol;
    @FXML
    private VBox appointmentButtonPane;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdTableCol;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleTableCol;
    @FXML
    private TableColumn<Appointment, String> appointmentDescTableCol;
    @FXML
    private TableColumn<Appointment, String> appointmentLocationTableCol;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeTableCol;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentStartTableCol;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentEndTableCol;
    @FXML
    private Label usernameLabel;
    private Parent pane;
    private Scene scene;
    private Stage primaryStage;
    private User sessionUser;
    private ObservableList<Customer> customers;
    private ArrayList<Appointment> appointments;

    public void initialize() {
        this.initializeCustomerTable();
        this.initializeCustomerSearchBar();
        this.initializeAppointmentTable();

        //    this.searchAppointmentTextField.textProperty().addListener();
    }

    private void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
        this.customerTableView.setItems(customers);
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
        this.pane = loader.load();
        this.primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(pane);
        this.primaryStage.setScene(this.scene);
        this.primaryStage.show();
    }

    private void redirectToCustomerPage(ActionEvent actionEvent, Customer customer)
            throws IOException {
        FXMLLoader loader =
                new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Customer.fxml")));
        if (customer != null) {
            CustomerController customerController = loader.getController();
            customerController.setCustomer(customer);
        }

        loader.setResources(this.bundle);
        this.pane = loader.load();
        this.primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(pane);
        this.primaryStage.setScene(this.scene);
        this.primaryStage.show();
    }

    private void redirectToAppointmentPage(ActionEvent actionEvent, Appointment appointment)
            throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        Objects.requireNonNull(Scheduler.class.getResource("/views/Appointment.fxml")));
        if (appointment != null) {
            AppointmentController appointmentController = loader.getController();
            appointmentController.setAppointment(appointment);
        }
        loader.setResources(this.bundle);
        this.pane = loader.load();
        this.primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(pane);
        this.primaryStage.setScene(this.scene);
        this.primaryStage.show();
    }

    @FXML
    private void handleAddCustomer(ActionEvent actionEvent) throws IOException {
        this.redirectToCustomerPage(actionEvent, null);
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent actionEvent) {
        //    Customer customer = new Customer();
    }

    @FXML
    private void handleDeleteCustomer(ActionEvent actionEvent) {
    }

    private void initializeCustomerTable() {
        try {
            // Get Customers
            ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO();
            HashMap<String, String> options = new HashMap<>();
            options.put("LIMIT", "50");
            ObservableList<Customer> customers =
                    FXCollections.observableArrayList(customerDAO.findAll(options));
            this.setCustomers(customers);

            this.customerIdTableCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            this.customerNameTableCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            this.customerAddressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            this.customerPostalCodeTableCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            this.customerPhoneTableCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            this.customerDivisionTableCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Platform.runLater(() -> {
            this.setTableWidth(this.customerTableView);
        });
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
        Platform.runLater(() -> {
            this.setTableWidth(this.appointmentTableView);
        });
    }

    private void setTableWidth(TableView<?> table) {
        // Fill the columns to the correct lengths.
        double tableWidth = table.getWidth();
        var columns = table.getColumns();
        double calcColWidth = tableWidth / columns.size();
        for (var col : columns) {
            col.setPrefWidth(calcColWidth);
        }
    }
}

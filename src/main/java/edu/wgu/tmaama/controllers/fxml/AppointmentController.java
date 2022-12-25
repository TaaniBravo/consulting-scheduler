package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.dao.ConcreteAppointmentDAO;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Contact.dao.ConcreteContactDAO;
import edu.wgu.tmaama.db.Contact.model.Contact;
import edu.wgu.tmaama.db.Customer.dao.ConcreteCustomerDAO;
import edu.wgu.tmaama.db.Customer.model.Customer;
import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.User.dao.ConcreteUserDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppointmentController {
  private Appointment appointment = new Appointment();
  @FXML private TextField idTextField;
  @FXML private TextField titleTextField;
  @FXML private TextField descTextField;
  @FXML private TextField locationTextField;
  @FXML private TextField typeTextField;
  @FXML private ComboBox<Customer> customerComboBox;
  @FXML private ComboBox<User> userComboBox;
  @FXML private ComboBox<Contact> contactComboBox;
  @FXML private TextField startDate;
  @FXML private TextField endDate;
  @FXML private Label appointmentTitleLabel;
  private ResourceBundle bundle;
  private boolean isUpdating = false;
  private User sessionUser;
  private Customer customer;

  public void initialize() {
    Platform.runLater(this::loadComboBoxes);
  }

  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
    this.idTextField.setText(String.valueOf(this.appointment.getAppointmentID()));
    this.titleTextField.setText(this.appointment.getTitle());
    this.descTextField.setText(this.appointment.getDescription());
    this.locationTextField.setText(this.appointment.getLocation());
    this.typeTextField.setText(this.appointment.getType());
    this.startDate.setText(this.appointment.getStart().toString());
    this.endDate.setText(this.appointment.getEnd().toString());
  }

  public void setIsUpdating(boolean isUpdating) {
    this.isUpdating = isUpdating;
    this.appointmentTitleLabel.setText(this.bundle.getString("appointment.title.update"));
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  private void loadComboBoxes() {
    try {
      Database db = new Database();
      this.loadCustomerComboBox(db);
      this.loadUserComboBox(db);
      this.loadContactComboBox(db);
    } catch (SQLException ex) {
      // TODO: Handle display db connection error
    }
  }

  private void loadCustomerComboBox(Database db) {
    try {
      // Load customers. Only load the customer selected if applicable.
      ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO(db);
      ObservableList<Customer> customers;

      if (this.isUpdating) {
        customers = FXCollections.observableArrayList(customerDAO.findAll());
      } else {
        Customer customer = customerDAO.findByID(this.customer.getCustomerID());
        customers = FXCollections.observableArrayList();
        customers.add(customer);
        this.customerComboBox.getSelectionModel().select(customer);
        this.customerComboBox.setDisable(true);
      }

      this.customerComboBox.setItems(customers);
    } catch (SQLException ex) {
      // TODO: Handle errors;
      ex.printStackTrace();
    }
  }

  private void loadUserComboBox(Database db) {
    try {
      ConcreteUserDAO userDAO = new ConcreteUserDAO(db);
      ObservableList<User> users = FXCollections.observableArrayList(userDAO.findAll());
      this.userComboBox.setItems(users);

      Optional<User> optional =
          this.userComboBox
              .getItems()
              .filtered(user -> user.getUserID() == this.appointment.getUserID())
              .stream()
              .findFirst();
      optional.ifPresent(user -> this.userComboBox.getSelectionModel().select(user));
    } catch (SQLException ex) {
      // TODO: Handle errors;
    }
  }

  private void loadContactComboBox(Database db) {
    try {
      ConcreteContactDAO contactDAO = new ConcreteContactDAO(db);
      ObservableList<Contact> contacts = FXCollections.observableArrayList(contactDAO.findAll());
      this.contactComboBox.setItems(contacts);

      Optional<Contact> optional =
          this.contactComboBox
              .getItems()
              .filtered(contact -> contact.getContactID() == this.appointment.getContactID())
              .stream()
              .findFirst();
      optional.ifPresent(contact -> this.contactComboBox.getSelectionModel().select(contact));
    } catch (SQLException ex) {
      // TODO: Handle display loading contacts errors;
    }
  }

  @FXML
  private void handleSubmit(ActionEvent event) {
    this.appointment.setTitle(this.titleTextField.getText());
    this.appointment.setDescription(this.descTextField.getText());
    this.appointment.setLocation(this.locationTextField.getText());
    this.appointment.setType(this.typeTextField.getText());
    this.appointment.setCustomerID(
        this.customerComboBox.getSelectionModel().getSelectedItem().getCustomerID());
    this.appointment.setUserID(this.userComboBox.getSelectionModel().getSelectedItem().getUserID());
    this.appointment.setContactID(
        this.contactComboBox.getSelectionModel().getSelectedItem().getContactID());
    try {
      this.appointment.setStart(this.convertStringIntoTimestamp(this.startDate.getText()));
      this.appointment.setEnd(this.convertStringIntoTimestamp(this.endDate.getText()));
    } catch (Exception ex) {
      // TODO: Handle error message to user.
    }

    if (this.isUpdating) this.updateAppointment(event);
    else this.addAppointment(event);
  }

  @FXML
  private void handleReset() {}

  @FXML
  private void handleCancel() {}

  private Timestamp convertStringIntoTimestamp(String datetime) throws Exception {
    Pattern timestampPattern =
        Pattern.compile("\\d{4}-[0-1]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d");
    Matcher matcher = timestampPattern.matcher(datetime);
    boolean isValidTimestamp = matcher.find();
    if (!isValidTimestamp) throw new Exception("Date and time given are not valid.");
    return Timestamp.valueOf(datetime);
  }

  private void addAppointment(ActionEvent event) {
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      appointmentDAO.insert(this.appointment);
      this.redirectToHomePage(event);
    } catch (SQLException | IOException ex) {
      // TODO: display error message.
    }
  }

  private void updateAppointment(ActionEvent event) {
    try {
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      appointmentDAO.update(this.appointment);
      this.redirectToHomePage(event);
    } catch (SQLException | IOException ex) {
      // TODO: display error message.
    }
  }

  private void redirectToHomePage(ActionEvent event) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Home.fxml")));
    loader.setResources(this.bundle);
    Parent pane = loader.load();
    HomeController homeController = loader.getController();
    homeController.setSessionUser(this.sessionUser);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  public void setSessionUser(User sessionUser) {
    this.sessionUser = sessionUser;
  }
}

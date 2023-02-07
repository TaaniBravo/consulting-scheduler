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
import edu.wgu.tmaama.utils.DateTimeConverter;
import edu.wgu.tmaama.utils.ErrorMessages;
import edu.wgu.tmaama.utils.Modal;
import edu.wgu.tmaama.utils.SuccessMessages;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for Appointment.fxml stage. */
public class AppointmentController {
  private final ResourceBundle bundle = ResourceBundle.getBundle("/bundles/main");
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
  private boolean isUpdating = false;
  private User sessionUser;
  private Customer customer;

  /** Initializes the controller by loading the combo boxes with the correct data. */
  public void initialize() {
    Platform.runLater(this::loadComboBoxes);
  }

  /**
   * Sets an appointment.
   *
   * @param appointment - The appointment to be updated
   */
  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
    this.idTextField.setText(String.valueOf(this.appointment.getAppointmentID()));
    this.titleTextField.setText(this.appointment.getTitle());
    this.descTextField.setText(this.appointment.getDescription());
    this.locationTextField.setText(this.appointment.getLocation());
    this.typeTextField.setText(this.appointment.getType());
    DateTimeConverter startConverter = new DateTimeConverter(this.appointment.getStart());
    this.startDate.setText(
        startConverter
            .getLocalDateTime()
            .format(DateTimeFormatter.ofPattern(DateTimeConverter.DISPLAY_FORMAT)));
    DateTimeConverter endConverter = new DateTimeConverter(this.appointment.getEnd());
    this.endDate.setText(
        endConverter
            .getLocalDateTime()
            .format(DateTimeFormatter.ofPattern(DateTimeConverter.DISPLAY_FORMAT)));
  }

  /** Sets the flag isUpdating */
  public void setIsUpdating(boolean isUpdating) {
    this.isUpdating = isUpdating;
    this.appointmentTitleLabel.setText(this.bundle.getString("appointment.title.update"));
  }

  /** If there was a customer selected from the Home.fxml stage then set this customer. */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /** Load all three ComboBoxes */
  private void loadComboBoxes() {
    try {
      Database db = new Database();
      this.loadCustomerComboBox(db);
      this.loadUserComboBox(db);
      this.loadContactComboBox(db);
    } catch (SQLException ex) {
      Modal modal =
          new Modal(
              Modal.ERROR,
              "Cannot access database. Contact administration to fix:\n" + ex.getMessage());
      modal.display();
    }
  }

  /** Fetches the customers from the database and loads them into the Customer ComboBox */
  private void loadCustomerComboBox(Database db) {
    try {
      // Load customers. Only load the customer selected if applicable.
      ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO(db);
      ObservableList<Customer> customers;
      customers = FXCollections.observableArrayList(customerDAO.findAll());
      this.customerComboBox.setItems(customers);
      assert this.customer != null;
      this.customerComboBox.getSelectionModel().select(this.customer);
    } catch (SQLException ex) {
      // TODO: Handle errors;
      ex.printStackTrace();
    }
  }

  /** Fetches the users from the database and loads them into the User ComboBox */
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
      ex.printStackTrace();
    }
  }

  /** Fetches the contacts from the database and loads them into the Contact ComboBox */
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
      ex.printStackTrace();
    }
  }

  /**
   * Handles when the submit button is clicked. Will validate the form before submitting to
   * database.
   */
  @FXML
  private void handleSubmit(ActionEvent event) throws IOException {
    String validationStack = this.validateForm();
    if (!validationStack.isBlank()) {
      Modal modal = new Modal(Modal.ERROR, validationStack);
      modal.display();
      return;
    }

    this.appointment.setTitle(this.titleTextField.getText());
    this.appointment.setDescription(this.descTextField.getText());
    this.appointment.setLocation(this.locationTextField.getText());
    this.appointment.setType(this.typeTextField.getText());
    this.appointment.setCustomerID(
        this.customerComboBox.getSelectionModel().getSelectedItem().getCustomerID());
    this.appointment.setUserID(this.userComboBox.getSelectionModel().getSelectedItem().getUserID());
    this.appointment.setContactID(
        this.contactComboBox.getSelectionModel().getSelectedItem().getContactID());
    DateTimeConverter startConverter = new DateTimeConverter(this.startDate.getText(), false);
    DateTimeConverter endConverter = new DateTimeConverter(this.endDate.getText(), false);
    this.appointment.setStart(
        DateTimeConverter.convertDateTimeToTimestamp(
            startConverter.getUtcDateTime().toLocalDateTime()));
    this.appointment.setEnd(
        DateTimeConverter.convertDateTimeToTimestamp(
            endConverter.getUtcDateTime().toLocalDateTime()));

    if (this.isUpdating) this.updateAppointment(event);
    else this.addAppointment(event);
  }

  /**
   * Handles the reset of the form, clearing out any text and selected ComboBoxes besides the
   * Appointment's ID.
   */
  @FXML
  private void handleReset() {
    this.titleTextField.setText("");
    this.descTextField.setText("");
    this.locationTextField.setText("");
    this.typeTextField.setText("");
    this.startDate.setText("");
    this.endDate.setText("");
    if (!this.customerComboBox.isDisabled())
      this.customerComboBox.getSelectionModel().clearSelection();
    this.userComboBox.getSelectionModel().clearSelection();
    this.contactComboBox.getSelectionModel().clearSelection();
  }

  /** Handles the cancel button. Will send the user back to the Home stage. */
  @FXML
  private void handleCancel(ActionEvent event) throws IOException {
    this.redirectToHomePage(event);
  }

  /** Handles the actions of INSERTING an appointment into the database. */
  private void addAppointment(ActionEvent event) {
    try {
      this.appointment.setCreatedBy(this.sessionUser.getUsername());
      this.appointment.setLastUpdatedBy(this.sessionUser.getUsername());
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      appointmentDAO.insert(this.appointment);
      Modal modal = new Modal(Modal.SUCCESS, SuccessMessages.APPOINTMENT_ADDED);
      modal.display();
      this.redirectToHomePage(event);
    } catch (SQLException | IOException ex) {
      // TODO: display error message.
      ex.printStackTrace();
    }
  }

  /** Handles the action of UPDATING an existing appointment into the database. */
  private void updateAppointment(ActionEvent event) throws IOException {
    try {
      this.appointment.setLastUpdatedBy(this.sessionUser.getUsername());
      ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
      appointmentDAO.update(this.appointment);
      Modal modal = new Modal(Modal.SUCCESS, SuccessMessages.APPOINTMENT_UPDATED);
      modal.display();
    } catch (SQLException ex) {
      ex.printStackTrace();
      String message =
          ex.getMessage().equals(ErrorMessages.OVERLAPPING_APPOINTMENT_TIMES)
              ? ErrorMessages.OVERLAPPING_APPOINTMENT_TIMES
              : ex.getMessage();
      Modal modal = new Modal(Modal.ERROR, message);
      modal.display();
    } finally {
      this.redirectToHomePage(event);
    }
  }

  /** Redirects the user to the Home stage. */
  private void redirectToHomePage(ActionEvent event) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(Objects.requireNonNull(Scheduler.class.getResource("/views/Home.fxml")));
    loader.setResources(ResourceBundle.getBundle("bundles/main"));
    Parent pane = loader.load();
    HomeController homeController = loader.getController();
    homeController.setSessionUser(this.sessionUser);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  /** Entry level validation function that returns a string of the errors to present to the user. */
  private String validateForm() {
    StringBuilder stringBuilder = new StringBuilder();
    this.validateTextFields(stringBuilder);
    this.validateComboBoxes(stringBuilder);
    this.validateDates(stringBuilder);

    return stringBuilder.toString();
  }

  /** Handles the validation of the TextFields (minus the dates). */
  private void validateTextFields(StringBuilder stringBuilder) {
    if (this.titleTextField.getText().isBlank())
      stringBuilder.append(ErrorMessages.APPOINTMENT_BLANK_TITLE).append("\n");
    if (this.descTextField.getText().isBlank())
      stringBuilder.append(ErrorMessages.APPOINTMENT_BLANK_DESC).append("\n");
    if (this.locationTextField.getText().isBlank())
      stringBuilder.append(ErrorMessages.APPOINTMENT_BLANK_LOCATION).append("\n");
    if (this.typeTextField.getText().isBlank())
      stringBuilder.append(ErrorMessages.APPOINTMENT_BLANK_TYPE).append("\n");
  }

  /** Handles the validation of the ComboBoxes */
  private void validateComboBoxes(StringBuilder stringBuilder) {
    if (this.customerComboBox.getSelectionModel().getSelectedItem() == null)
      stringBuilder.append(ErrorMessages.APPOINTMENT_SELECT_CUSTOMER).append("\n");
    if (this.userComboBox.getSelectionModel().getSelectedItem() == null)
      stringBuilder.append(ErrorMessages.APPOINTMENT_SELECT_USER).append("\n");
    if (this.contactComboBox.getSelectionModel().getSelectedItem() == null)
      stringBuilder.append(ErrorMessages.APPOINTMENT_SELECT_CONTACT).append("\n");
  }

  /**
   * Handles the validation of the dates are the logic around this is much more complex than the
   * other validations.
   */
  private void validateDates(StringBuilder stringBuilder) {
    DateTimeConverter startConverter = null;
    DateTimeConverter endConverter = null;
    DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(DateTimeConverter.DATE_FORMAT);
    ChronoZonedDateTime<LocalDate> chronoZonedDateTime = LocalDateTime.now().atZone(ZoneOffset.UTC);

    // Validate start date
    if (this.startDate.getText().isBlank()) {
      stringBuilder.append(ErrorMessages.APPOINTMENT_BLANK_START).append("\n");
    } else {
      try {
        LocalDateTime localDateTime =
            LocalDateTime.parse(this.startDate.getText(), dateTimeFormatter);
        startConverter = new DateTimeConverter(localDateTime);
        if (startConverter.getUtcDateTime().isBefore(chronoZonedDateTime))
          stringBuilder.append(ErrorMessages.APPOINTMENT_START_BEFORE_NOW).append("\n");
      } catch (Exception ex) {
        ex.printStackTrace();
        stringBuilder.append(ErrorMessages.APPOINTMENT_START_FORMAT).append("\n");
      }
    }

    // Validate end date.
    if (this.endDate.getText().isBlank()) {
      stringBuilder.append(ErrorMessages.APPOINTMENT_BLANK_END).append("\n");
    } else {
      try {
        LocalDateTime localDateTime =
            LocalDateTime.parse(this.endDate.getText(), dateTimeFormatter);
        endConverter = new DateTimeConverter(localDateTime);
        if (endConverter.getUtcDateTime().isBefore(chronoZonedDateTime))
          stringBuilder
              .append(ErrorMessages.APPOINTMENT_START_BEFORE_NOW.replace("Start", "End"))
              .append("\n");
      } catch (Exception ex) {
        ex.printStackTrace();
        stringBuilder.append(ErrorMessages.APPOINTMENT_END_FORMAT).append("\n");
      }
    }

    assert startConverter != null;
    assert endConverter != null;

    // Validation with both converters successfully parsing string.
    if (startConverter.getUtcDateTime().isAfter(endConverter.getUtcDateTime()))
      stringBuilder.append(ErrorMessages.APPOINTMENT_START_AFTER_END).append("\n");

    LocalTime startTime = startConverter.getEstDateTime().toLocalTime();
    LocalTime endTime = endConverter.getEstDateTime().toLocalTime();
    if (this.isLocalTimeOutsideBusinessHours(startTime)
        || this.isLocalTimeOutsideBusinessHours(endTime))
      stringBuilder.append(ErrorMessages.APPOINTMENT_OUTSIDE_OF_BUSINESS_HOURS).append("\n");
  }

  /**
   * Business hours are 8:00 AM - 10:00 PM EST.
   *
   * @param time - The time to compare to the opening and closing times of the business.
   * @return true if the appointment time is outside business hours and false if it falls within.
   */
  private boolean isLocalTimeOutsideBusinessHours(LocalTime time) {
    LocalTime openingTime = LocalTime.of(8, 0);
    LocalTime closingTime = LocalTime.of(20, 0);
    return time.isBefore(openingTime) || time.isAfter(closingTime);
  }

  /**
   * Sets the session user for the current stage.
   * @param sessionUser - User that is currently logged in.
   */

  public void setSessionUser(User sessionUser) {
    this.sessionUser = sessionUser;
  }
}

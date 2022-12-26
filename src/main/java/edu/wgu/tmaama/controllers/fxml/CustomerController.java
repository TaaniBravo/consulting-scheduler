package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Country.dao.ConcreteCountryDAO;
import edu.wgu.tmaama.db.Country.model.Country;
import edu.wgu.tmaama.db.Customer.dao.ConcreteCustomerDAO;
import edu.wgu.tmaama.db.Customer.model.Customer;
import edu.wgu.tmaama.db.FirstLevelDivision.dao.ConcreteFirstLevelDivisionDAO;
import edu.wgu.tmaama.db.FirstLevelDivision.model.FirstLevelDivision;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.Modal;
import edu.wgu.tmaama.utils.SuccessMessages;
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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController {
  @FXML private Label titleLabel;
  @FXML private TextField idTextField;
  @FXML private TextField nameTextField;
  @FXML private TextField addressTextField;
  @FXML private TextField phoneTextField;
  @FXML private TextField postalCodeTextField;
  @FXML private ComboBox<FirstLevelDivision> divisionComboBox;
  @FXML private ComboBox<Country> countryComboBox;
  private Customer customer;
  private User sessionUser;
  private boolean isUpdating = false;
  private ObservableList<FirstLevelDivision> divisions;
  private ObservableList<Country> countries;
  private final ResourceBundle resources = ResourceBundle.getBundle("/bundles/main");

  public void initialize() {
    this.customer = new Customer();
    this.loadCountryComboBox();
    this.loadDivisionsIntoComboBox();
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
    this.setIsUpdating(true);
    this.idTextField.setText(String.valueOf(this.customer.getCustomerID()));
    this.nameTextField.setText(this.customer.getCustomerName());
    this.addressTextField.setText(this.customer.getAddress());
    this.postalCodeTextField.setText(this.customer.getPostalCode());
    this.phoneTextField.setText(this.customer.getPhone());
    Optional<FirstLevelDivision> optional =
        this.divisions
            .filtered(div -> div.getDivisionID() == this.customer.getDivisionID())
            .stream()
            .findFirst();
    if (optional.isEmpty())
      throw new RuntimeException(
          "Customer's division could not be found. Contact administration to resolve data issue.");
    this.divisionComboBox.getSelectionModel().select(optional.get());
    Optional<Country> optionalCountry =
        this.countryComboBox
            .getItems()
            .filtered(country -> country.getCountryID() == optional.get().getCountryID())
            .stream()
            .findFirst();
    if (optionalCountry.isEmpty())
      throw new RuntimeException(
          "First Level Division's Country could not be found. Contact administration to resolve data issue.");
    this.countryComboBox.getSelectionModel().select(optionalCountry.get());
  }

  public void setSessionUser(User user) {
    this.sessionUser = user;
  }

  public void setIsUpdating(boolean isUpdating) {
    this.isUpdating = isUpdating;
    this.titleLabel.setText(this.resources.getString("customer.title.update"));
  }

  @FXML
  private void handleSubmit(ActionEvent event) throws IOException {
    if (this.isUpdating) this.updateCustomer();
    else this.addCustomer();
    this.redirectToHomePage(event);
  }

  @FXML
  private void handleReset(ActionEvent event) {
    this.nameTextField.setText("");
    this.addressTextField.setText("");
    this.postalCodeTextField.setText("");
    this.phoneTextField.setText("");
    this.divisionComboBox.getSelectionModel().clearSelection();
  }

  @FXML
  private void handleCancel(ActionEvent event) throws IOException {
    this.redirectToHomePage(event);
  }

  @FXML
  private void handleSelectedCountry() {
    Country country = this.countryComboBox.getSelectionModel().getSelectedItem();
    if (country == null) return;
    ObservableList<FirstLevelDivision> filteredDivisions =
        this.divisions.filtered(division -> division.getCountryID() == country.getCountryID());
    this.divisionComboBox.setItems(filteredDivisions);
  }

  private void addCustomer() {
    this.setCustomerValuesFromForm();
    try {
      ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO();
      customerDAO.insert(this.customer);

      Modal modal = new Modal(Modal.SUCCESS, SuccessMessages.CUSTOMER_ADDED);
      modal.display();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateCustomer() {
    this.setCustomerValuesFromForm();
    try {
      ConcreteCustomerDAO customerDAO = new ConcreteCustomerDAO();
      customerDAO.update(this.customer);
      Modal modal = new Modal(Modal.SUCCESS, SuccessMessages.CUSTOMER_UPDATED);
      modal.display();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void setCustomerValuesFromForm() {
    if (!this.validateForm()) return;
    this.customer.setCustomerName(nameTextField.getText());
    this.customer.setAddress(addressTextField.getText());
    this.customer.setPostalCode(postalCodeTextField.getText());
    this.customer.setPhone(phoneTextField.getText());
    FirstLevelDivision selectedDivision = divisionComboBox.getSelectionModel().getSelectedItem();
    this.customer.setDivision(selectedDivision.getDivision());
    this.customer.setDivisionID(selectedDivision.getDivisionID());
    this.customer.setLastUpdatedBy(sessionUser.getUsername());
    if (!this.isUpdating) this.customer.setCreatedBy(sessionUser.getUsername());
  }

  private boolean validateForm() {
    // TODO: Validate the values in form to make sure their applicable (not empty).
    return true;
  }

  private void loadCountryComboBox() {
    try {
      ConcreteCountryDAO countryDAO = new ConcreteCountryDAO();
      this.countries = FXCollections.observableArrayList(countryDAO.findAll());
      this.countryComboBox.setItems(this.countries);
      this.countryComboBox.getSelectionModel().select(0);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void loadDivisionsIntoComboBox() {
    try {
      ConcreteFirstLevelDivisionDAO divisionDAO = new ConcreteFirstLevelDivisionDAO();
      this.divisions = FXCollections.observableArrayList(divisionDAO.findAll());
      this.handleSelectedCountry();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

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
}

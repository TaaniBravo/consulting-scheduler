package edu.wgu.tmaama.controllers.fxml;

import edu.wgu.tmaama.Scheduler;
import edu.wgu.tmaama.db.Appointment.dao.ConcreteAppointmentDAO;
import edu.wgu.tmaama.db.Appointment.model.Appointment;
import edu.wgu.tmaama.db.Database;
import edu.wgu.tmaama.db.Salt.dao.ConcreteSaltDAO;
import edu.wgu.tmaama.db.Salt.model.Salt;
import edu.wgu.tmaama.db.User.dao.ConcreteUserDAO;
import edu.wgu.tmaama.db.User.model.User;
import edu.wgu.tmaama.utils.*;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the Login stage.
 */
public class LoginController {
	private final ResourceBundle resources = ResourceBundle.getBundle("bundles/main");
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Label errorLabel;
	@FXML
	private Label zoneLabel;

	/**
	 * Initialize controller by getting the user's zone and loading for the page's zoneLabel
	 */
	public void initialize() {
		zoneLabel.setText(ZoneId.systemDefault().toString());
	}

	/**
	 * Handles the login button by getting the user from the database and checking they entered their password correctly.
	 * Will also log the user's attempt to login.
	 *
	 * @param actionEvent
	 * @throws IOException
	 */
	@FXML
	private void handleLogin(ActionEvent actionEvent) throws IOException {
		String username = usernameTextField.getText();
		Logger logger = new Logger();
		Instant currentTime = Instant.now();
		try {
			Database db = new Database();
			ConcreteUserDAO userDAO = new ConcreteUserDAO(db);
			ConcreteSaltDAO saltDAO = new ConcreteSaltDAO(db);
			User user = userDAO.findByUsername(username);
			if (user == null) {
				this.printError(this.resources.getString("login.error.username"));
				String logInfo = String.format("%s - %s - success: false", currentTime.toString(), username);
				logger.log(logInfo);
				return;
			}

			Salt salt = saltDAO.findByUserID(user.getUserID());
			Password enteredPassword = new Password(passwordTextField.getText(), salt);
			boolean isPasswordValid = Password.comparePasswords(user.getPassword(), enteredPassword);

			if (!isPasswordValid) {
				this.printError(this.resources.getString("login.error.username"));
				String logInfo = String.format("%s - %s - success: false", currentTime.toString(), username);
				logger.log(logInfo);
				return;
			}

			String logInfo = String.format("%s - %s - success: true", currentTime.toString(), username);
			logger.log(logInfo);
			this.redirectToHomePage(actionEvent, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the sign up button. Currently here to just make testing easier when a new user is needed.
	 *
	 * @param actionEvent
	 */
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

	/**
	 * Redirects to the Home stage.
	 *
	 * @param actionEvent
	 * @param user
	 * @throws IOException
	 */
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
		this.alertUpcomingAppointments(user);
	}

	/**
	 * Prints the error message to the errorLabel JavaFX component.
	 *
	 * @param message
	 */
	private void printError(String message) {
		this.errorLabel.setText(message);
	}

	/**
	 * Filters the appointments list given looking for any appointments that coming up in the next 15 minutes.
	 *
	 * @param appointments
	 * @return
	 */
	private ArrayList<Appointment> filterAppointments(ArrayList<Appointment> appointments) {
		ArrayList<Appointment> filteredList = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = LocalDateTime.now().plusMinutes(15);
		appointments.forEach(appointment -> {
			if (appointment.getLocalStartDateTime().isAfter(start) && appointment.getLocalEndDateTime().isBefore(end))
				filteredList.add(appointment);
		});

		return filteredList;
	}

	/**
	 * Popup modal showing the user if they have any upcoming appointments.
	 *
	 * @param user
	 */
	private void alertUpcomingAppointments(User user) {
		try {
			ConcreteAppointmentDAO appointmentDAO = new ConcreteAppointmentDAO();
			ArrayList<Appointment> appointments =
				appointmentDAO.findAppointmentsByUserID(user.getUserID());
			ArrayList<Appointment> filteredAppointments = this.filterAppointments(appointments);

			if (filteredAppointments.size() == 0) {
				Modal modal = new Modal(Modal.INFO, InfoMessages.NO_UPCOMING_APPOINTMENTS);
				modal.display();
				return;
			}

			StringBuilder stringBuilder = new StringBuilder(InfoMessages.UPCOMING_APPOINTMENTS);
			stringBuilder.append("\n");
			filteredAppointments.forEach(
				appointment -> {
					stringBuilder
						.append(appointment.getAppointmentID())
						.append(". ")
						.append(appointment.getTitle())
						.append(": ")
						.append(appointment.getLocalStartDateTime().toLocalTime().toString())
						.append("-")
						.append(appointment.getLocalEndDateTime().toLocalTime().toString())
						.append("\n");
				});

			Modal modal = new Modal(Modal.INFO, stringBuilder.toString());
			modal.display();
		} catch (SQLException ex) {
			ex.printStackTrace();
			Modal modal = new Modal(Modal.ERROR, ErrorMessages.GET_USER_APPOINTMENTS);
			modal.display();
		}
	}
}

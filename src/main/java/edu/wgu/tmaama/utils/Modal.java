package edu.wgu.tmaama.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Simple modal to display alert messages
 * @author Taanileka Maama
 */
public class Modal {
	private final String title;
	private final String message;
	private static final ResourceBundle resources = ResourceBundle.getBundle("bundles/messages");
	public static final String ERROR = resources.getString("modal.header.error");
	public static final String SUCCESS = resources.getString("modal.header.success");
	public static final String DELETE = resources.getString("modal.header.delete");
	public static final String CANCEL = resources.getString("modal.header.cancel");
	public static final String INFO = resources.getString("modal.header.info");

	public Modal(String title, String message) {
		this.title = title;
		this.message = message;
	}

	/**
	 * Displays the modal. Stops the user from interacting with other windows until the modal is closed.
	 */
	public void display() {
		Stage window = new Stage();

		// Block inputs to other windows until this one is closed.
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(this.title);
		window.setMinWidth(250);

		Label label = new Label();
		label.setText(message);
		this.handleColorText(label);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(actionEvent -> window.close());

		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10));

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

	private void handleColorText(Label label) {
		if (this.title.equals(ERROR))
			label.setTextFill(Color.rgb(148, 0, 0));
		if (this.title.equals(SUCCESS))
			label.setTextFill(Color.rgb(0, 148, 0));
	}
}

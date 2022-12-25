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

/**
 * Simple modal to display alert messages
 * @author Taanileka Maama
 */
public class Modal {
	private final String title;
	private final String message;
	public static final String ERROR = "Error";
	public static final String SUCCESS = "Success";

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
		switch (this.title) {
			case ERROR:
				label.setTextFill(Color.rgb(148, 0, 0));
			case SUCCESS:
				label.setTextFill(Color.rgb(0, 148, 0));
			default:
				break;
		}
	}
}

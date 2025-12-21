package project.task.manager.desktopclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

	int counter = 0;
	public HelloApplication() {
		super();
	}
		@Override
		public void start(Stage stage) throws IOException {
				FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
				Scene scene = new Scene(fxmlLoader.load(), 640, 320);
				stage.setTitle("Task Manager Desktop Client");
				stage.setScene(getChildScene());
				stage.show();
		}
	
	private Scene getChildScene() throws IOException {
		int counter = 0;
		//FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Button button = new Button("Test Button Click!");
		button.setOnAction(e -> onClick(button, counter));
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		//layout.getChildren().add(labelText);
		Scene scene = new Scene(layout, 640, 320);
		
		return scene;
	}
		
		private void onClick(Button button, int counter) {

			button.setText("Clicked! %s times".formatted(increment()));
		
		}
		
		private int increment() {
		return counter++;
		}
}

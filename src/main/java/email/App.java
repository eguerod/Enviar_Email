package email;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private Controller controller;
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new Controller();
		
		App.primaryStage = primaryStage;
		primaryStage.setTitle("Login.fxml");
		primaryStage.setScene(new Scene(controller.getView(), 600, 400));
		primaryStage.getIcons().add(new Image("/images/email-send-icon-32x32.png"));
		primaryStage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}

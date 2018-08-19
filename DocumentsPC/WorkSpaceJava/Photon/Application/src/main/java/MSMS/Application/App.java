package MSMS.Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application 
{
    public static void main( String[] args ) {
    	System.out.println("Inside Launch Method");
    	launch(args);
    	System.out.println("After Launch Method");
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/MSMS/Application/Controller/View/Main.fxml"));
		Scene scene = new Scene(root,700,650);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mobile Stock Management System - Developed By Kaizen");
		primaryStage.show();
		
		//Change Default Close behavior to custom.
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		}); 
	}
}

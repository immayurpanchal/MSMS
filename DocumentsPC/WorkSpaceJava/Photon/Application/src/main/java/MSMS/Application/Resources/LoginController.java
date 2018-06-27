package MSMS.Application.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import MSMS.Application.Brand;
import MSMS.Application.Customer;
import MSMS.Application.CustomerBill;
import MSMS.Application.Login;
import MSMS.Application.Product;
import MSMS.Application.Stock;
import MSMS.Application.Supplier;
import MSMS.Application.SupplierBill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LoginController implements Initializable {
	@FXML private JFXTextField lblUsername;
	@FXML private JFXPasswordField lblPassword;
	@FXML private JFXSnackbar snackbar;
	private MainController mainController;
	
	Configuration config;
	SessionFactory sf;
	Session session;
	
	public void login(ActionEvent event){
		//Create Login Object
		Login l = new Login();
		l.setPassword(lblPassword.getText());
		l.setUsername(lblUsername.getText());

		//Find Username in database
		session.beginTransaction();
		Login temp = (Login) session.get(Login.class, lblUsername.getText());
		session.getTransaction().commit();

		//Show Error message if user doesn't exist
		if (temp == null) {
			System.out.println("User not");
			snackbar.show("Username Not Found", 3000);
			return;
		}
		
		lblUsername.requestFocus(); 

		//Check password if username is correct
		if (temp.getUsername().equals(lblUsername.getText()) && temp.getPassword().equals(lblPassword.getText())) {
			mainController.getMenuBar().setDisable(false);
			mainController.getTabpane().getTabs().remove(0);
			snackbar.show("Login Succeed", 3000);
			mainController.viewDashboard(null);
		}
		else
			snackbar.show("Wrong Password", 3000);

		//Clear TextFields
		lblUsername.setText("");
		lblPassword.setText("");
	}
	
	//Pass MainController object to LoginController
	public void injectMainController(MainController mc) {
		this.mainController = mc;
	}
	
	public void signUp(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("SignUp.fxml"));
			Tab tab = new Tab("SignUp", loader.load());
			TabPane tabpane = mainController.getTabpane();
			tabpane.getTabs().add(tab);	
			tabpane.getSelectionModel().select(tab);
			
			//Get the LoginController object 
			SignUpController signUpCon = loader.getController();
			
			//Pass the MainController object to SignUpController 
			signUpCon.injectMainController(mainController);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		config = new Configuration().configure().addAnnotatedClass(Login.class).addAnnotatedClass(Brand.class)
				.addAnnotatedClass(Product.class).addAnnotatedClass(SupplierBill.class)
				.addAnnotatedClass(CustomerBill.class).addAnnotatedClass(Stock.class).addAnnotatedClass(Supplier.class)
				.addAnnotatedClass(Customer.class);
		sf = config.buildSessionFactory();
		session = sf.openSession();
	}
}

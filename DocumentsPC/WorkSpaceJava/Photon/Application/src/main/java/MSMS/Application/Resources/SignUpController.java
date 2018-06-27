package MSMS.Application.Resources;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import MSMS.Application.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SignUpController implements Initializable{
	@FXML private JFXTextField lblUsername;
	@FXML private JFXPasswordField lblPassword;
	@FXML private JFXSnackbar snackbar;
	
	@SuppressWarnings("unused")
	private MainController mainController;
	
	Configuration config;
	SessionFactory sf;
	Session session;
	
	// JPA to fetch Data using Primary Key
	EntityManagerFactory emf;
	EntityManager em;
	
	public void signUp(ActionEvent event) {
		if(lblUsername.getText().isEmpty() || lblPassword.getText().isEmpty() ) {
			snackbar.show("Fields should not be empty", 3000);
			return;
		}
		
		Login login=null;
		try {
			login = em.find(Login.class, lblUsername.getText());
		}
		catch(NullPointerException e) {
			if(login==null) {
				snackbar.show("User Already Exist", 3000);
				return;
			}
			else {
				login = new Login();
				login.setUsername(lblUsername.getText());
				login.setPassword(lblPassword.getText());
				
				session.beginTransaction();
				session.save(login);
				session.getTransaction().commit();
				
				snackbar.show("Registration Succeed", 3000);
			}
		}
		
	}
	// Pass MainController object to LoginController
	public void injectMainController(MainController mc) {
		this.mainController = mc;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		config = new Configuration().configure().addAnnotatedClass(Login.class);
		sf = config.buildSessionFactory();
		session = sf.openSession();
		
		// Using JPA to fetch the Data using Primary Key
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
}

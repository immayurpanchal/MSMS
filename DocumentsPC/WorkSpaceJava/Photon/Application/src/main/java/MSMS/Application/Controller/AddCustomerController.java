package MSMS.Application.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import MSMS.Application.Brand;
import MSMS.Application.Customer;
import MSMS.Application.CustomerBill;
import MSMS.Application.Product;
import MSMS.Application.Stock;
import MSMS.Application.Supplier;
import MSMS.Application.SupplierBill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AddCustomerController implements Initializable{
    @FXML private JFXTextField cname;
    @FXML private JFXTextField cemail;
    @FXML private JFXTextField cmobile;
    @FXML private JFXTextArea caddress; 
    @FXML private JFXSnackbar status;
    
    Configuration config;
	SessionFactory sf;
	Session session;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
	EntityManager em = emf.createEntityManager();
	
	String regexStr = "^[0-9]{10}$";
	
	@SuppressWarnings("unchecked")
	public void addCustomer(ActionEvent event) {
		
		if(cname.getText().isEmpty()||cmobile.getText().isEmpty()||cemail.getText().isEmpty()||caddress.getText().isEmpty()) {
			status.show("Fields should not be empty", 2000);
			return;
		}
		
		if(!(cmobile.getText().matches(regexStr)))
		{
			status.show("Mobile number is not valid", 3000);
			return;
		}
		//validation for checking already exist customer
		//mobile email
		
		ArrayList<Customer> customerList = new ArrayList<>();
		
		em.getTransaction().begin();
		customerList = (ArrayList<Customer>) em.createQuery("from Customer").getResultList();
		em.getTransaction().commit();
		
		for(Customer customer : customerList) {
			if(customer.getCustomer_email().compareToIgnoreCase(cemail.getText())==0 && Long.toString(customer.getCustomer_mobile()).compareTo(cmobile.getText())==0  ) {
				status.show("Customer Already Exist", 3000);
				return;
			}
		}
		
		//Creating customer object
		Customer c = new Customer();
		c.setCustomer_name(cname.getText());
		c.setCustomer_mobile(Long.parseLong(cmobile.getText()));
		c.setCustomer_email(cemail.getText());
		c.setCustomer_add(caddress.getText());
		
		//Saving Customer Object to Database
		session.beginTransaction();
		session.save(c);
		session.getTransaction().commit();
		
		status.show("Customer Added Successfully", 3000);
		//Clear TextFields After Succesfully Adding the Customer
		cname.setText("");
		caddress.setText("");
		cemail.setText("");
		cmobile.setText("");
		cname.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		config = new Configuration().configure().addAnnotatedClass(Brand.class).addAnnotatedClass(Product.class)
				.addAnnotatedClass(SupplierBill.class).addAnnotatedClass(CustomerBill.class)
				.addAnnotatedClass(Stock.class).addAnnotatedClass(Supplier.class).addAnnotatedClass(Customer.class);
		sf = config.buildSessionFactory();
		session = sf.openSession();
	}
}

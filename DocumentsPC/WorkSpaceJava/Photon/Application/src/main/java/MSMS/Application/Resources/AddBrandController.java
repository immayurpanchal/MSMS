package MSMS.Application.Resources;

import java.io.IOException;
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

public class AddBrandController implements Initializable {
	@FXML private JFXTextField brandName;
	@FXML private JFXSnackbar status;
	
	//Database Connection
	Configuration config;
	SessionFactory sf;
	Session session;
	
	EntityManagerFactory emf;
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public void addBrand(ActionEvent event) throws IOException {
		
		if(brandName.getText().isEmpty()){
			status.show("Fields should not be empty", 2000);
			return;
		}
		
		ArrayList<Brand> brandList = new ArrayList<>();
		
		em.getTransaction().begin();
		brandList = (ArrayList<Brand>) em.createQuery("from Brand").getResultList();
		em.getTransaction().commit();

		System.out.println("Query Succeed");
		
		for(Brand brand : brandList) {
			if(brand.getBrand_name().compareToIgnoreCase(brandName.getText())==0) {
				status.show("Brand Already Exist", 3000);
				return;
			}
		}
		
		Brand b= new Brand();
		b.setBrand_name(brandName.getText());
		
		session.beginTransaction();
		session.save(b);
		session.getTransaction().commit();
		brandName.setText(""); 
		status.show("Brand Added Successfully", 2000);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Database Connection
		config = new Configuration().configure().addAnnotatedClass(Brand.class)
				.addAnnotatedClass(Product.class).addAnnotatedClass(SupplierBill.class)
				.addAnnotatedClass(CustomerBill.class).addAnnotatedClass(Stock.class).addAnnotatedClass(Supplier.class)
				.addAnnotatedClass(Customer.class);
		sf = config.buildSessionFactory();
		session = sf.openSession();
		
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
}

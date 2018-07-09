package MSMS.Application.Controller;

import java.sql.SQLException;

import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SellDatePickerController {
	@FXML private JFXDatePicker startDate;
	@FXML private JFXDatePicker endDate;
	
	public void salePickDate(ActionEvent event) throws ClassNotFoundException, SQLException {
		String sDate = "\'" + startDate.getValue() + "\'"; 
		String eDate = "\'" + endDate.getValue() + "\'";
				
		PrintReport viewReport = new PrintReport(
				"select cb.customerdate, pr.product_name, cb.customerimei, c.customer_name, cb.customertotal from customerbill cb, product pr, customer c where customerdate>= "
						+ sDate + " and customerdate<= " + eDate + " and cb.pid=pr.product_id and cb.cid=c.customer_id",
				"/MSMS/Application/Resources/Reports/SaleReport.jrxml");
		viewReport.showReport();
	}
}

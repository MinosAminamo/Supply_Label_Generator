package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Delivery;
import model.entities.Employee;
import model.services.DeliveryService;
import model.services.EmployeeService;

public class DeliveryFormController implements Initializable {
		
	private EmployeeService employeeService;
	
	@FXML
	private TextField txtRota;
	
	@FXML
	private ComboBox<Employee> comboBoxEmployee;
	
	@FXML
	private TextField txtQbs;
	
	@FXML
	private Button btAdd;
	
	@FXML
	private TableColumn<Employee, Employee> tableColumnEDIT;
	
	@FXML
	private TableColumn<Employee, Employee> tableColumnREMOVE;
	
	private ObservableList<Employee> obsList;
	

	public void setServices(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();		
	}
	
	private void initializeNodes() {
		initializeComboBoxEmployee();
	}
	
	public void loadAssociatedObjects() {
		List<Employee> list = employeeService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxEmployee.setItems(obsList);
	}

	
	private void initializeComboBoxEmployee() {
		Callback<ListView<Employee>, ListCell<Employee>> factory = lv -> new ListCell<Employee>() {
			@Override
			protected void updateItem(Employee item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxEmployee.setCellFactory(factory);
		comboBoxEmployee.setButtonCell(factory.call(null));
	}
	

}

package model.dao;
import db.DB;
import model.dao.impl.DeliveryDaoJDBC;
import model.dao.impl.EmployeeDaoJDBC;

public class DaoFactory {
	public static EmployeeDao createEmployeeDao() {
		return new EmployeeDaoJDBC(DB.getConnection());
		
	}
	public static DeliveryDao createDeliveryDao() {
		return new DeliveryDaoJDBC(DB.getConnection());
		
	}
	

}

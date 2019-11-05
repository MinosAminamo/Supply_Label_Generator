package model.dao;

import java.util.List;

import model.entities.Delivery;
import model.entities.Employee;


public interface DeliveryDao {
	void insert(Delivery obj);
	void update(Delivery obj);
	void deleteById(Integer id);
	Delivery findById(Integer id);
	List<Delivery> findAll();
	List<Delivery> findByEmployee(Employee employee);
	
}

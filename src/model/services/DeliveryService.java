package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DeliveryDao;
import model.entities.Delivery;

public class DeliveryService {

	private DeliveryDao dao = DaoFactory.createDeliveryDao();
	
	public List<Delivery> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Delivery obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Delivery obj) {
		dao.deleteById(obj.getId());
	}
}
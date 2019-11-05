package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Delivery implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id,rota;
	private String obs;	
	private Date Data;
	private Employee employee;
	
	public Delivery() {
		
	}
	
	public Delivery(Integer id, Integer rota, String obs, Date data, Employee employee) {
		this.id = id;
		this.rota = rota;
		this.obs = obs;
		Data = data;
		this.employee = employee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRota() {
		return rota;
	}

	public void setRota(Integer rota) {
		this.rota = rota;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Date getData() {
		return Data;
	}

	public void setData(Date data) {
		this.Data = data;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Data == null) ? 0 : Data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delivery other = (Delivery) obj;
		if (Data == null) {
			if (other.Data != null)
				return false;
		} else if (!Data.equals(other.Data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

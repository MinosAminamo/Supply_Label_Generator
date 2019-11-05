package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DeliveryDao;
import model.entities.Delivery;
import model.entities.Employee;

public class DeliveryDaoJDBC implements DeliveryDao {

	private Connection conn;
	
	public DeliveryDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Delivery obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO delivery "
					+ "(rota, currier, date, obs) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getRota());
			st.setInt(2, obj.getEmployee().getId());
			st.setDate(3, new java.sql.Date(obj.getData().getTime()));
			st.setString(4, obj.getObs());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Delivery obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE delivery "
					+ "SET rota = ?, currier = ?, date = ?, obs = ?"
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getRota());
			st.setInt(2, obj.getEmployee().getId());
			st.setDate(3, new java.sql.Date(obj.getData().getTime()));
			st.setString(4, obj.getObs());
			st.setInt(5, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM delivery WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Delivery findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT delivery.*,currier.name as currier "
					+ "FROM delivery INNER JOIN currier "
					+ "ON delivery.currier = currier.Id "
					+ "WHERE delivery.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Employee emp = instantiateEmployee(rs);
				Delivery obj = instantiateDelivery(rs, emp);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Delivery instantiateDelivery(ResultSet rs, Employee emp) throws SQLException {
		Delivery obj = new Delivery();
		obj.setId(rs.getInt("id"));
		obj.setRota(rs.getInt("rota"));
		obj.setEmployee(emp);
		obj.setData(new java.util.Date(rs.getTimestamp("date").getTime()));
		obj.setObs(rs.getString("obs"));
		return obj;
	}
	

	private Employee instantiateEmployee(ResultSet rs) throws SQLException {
		Employee emp = new Employee();
		emp.setId(rs.getInt("currier"));
		emp.setName(rs.getString("name"));
		emp.setUser(rs.getInt("user"));
		return emp;
	}

	@Override
	public List<Delivery> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT delivery.*,currier.name as name "
					+ "FROM delivery INNER JOIN currier "
					+ "ON delivery.currier = currier.id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Delivery> list = new ArrayList<>();
			Map<Integer, Employee> map = new HashMap<>();
			
			while (rs.next()) {
				
				Employee emp = map.get(rs.getInt("currier"));
				
				if (emp == null) {
					emp = instantiateEmployee(rs);
					map.put(rs.getInt("currier"), emp);
				}
				
				Delivery obj = instantiateDelivery(rs, emp);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Delivery> findByEmployee(Employee employee) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT delivery.*,currier.Name as currier "
					+ "FROM delivery INNER JOIN currier "
					+ "ON delivery.currier = currier.id "
					+ "WHERE currier = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, employee.getId());
			
			rs = st.executeQuery();
			
			List<Delivery> list = new ArrayList<>();
			Map<Integer, Employee> map = new HashMap<>();
			
			while (rs.next()) {
				
				Employee emp = map.get(rs.getInt("currier"));
				
				if (emp == null) {
					emp = instantiateEmployee(rs);
					map.put(rs.getInt("DepartmentId"), emp);
				}
				
				Delivery obj = instantiateDelivery(rs, emp);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}

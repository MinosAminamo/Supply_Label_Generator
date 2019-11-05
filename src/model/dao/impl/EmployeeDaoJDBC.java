package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.EmployeeDao;
import model.entities.Employee;

public class EmployeeDaoJDBC implements EmployeeDao {

	private Connection conn;

	public EmployeeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Employee findById(Integer user) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM currier WHERE user = ?");
			st.setInt(1, user);
			rs = st.executeQuery();
			if (rs.next()) {
				Employee obj = new Employee();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setUser(rs.getInt("user"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Employee> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM currier ORDER BY Name");
			rs = st.executeQuery();

			List<Employee> list = new ArrayList<>();

			while (rs.next()) {
				Employee obj = new Employee();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setUser(rs.getInt("user"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Employee obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO currier " + "(name,user) " + "VALUES " + "(?,?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setInt(2, obj.getUser());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Employee obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE currier " 
			        + "SET name = ? , user = ? "
					+ "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getUser());
			st.setInt(3, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM currier WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
}

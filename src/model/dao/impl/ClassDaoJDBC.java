package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.exceptions.DatabaseException;
import db.DB;
import model.dao.ClassDao;
import model.entities.Class;

public class ClassDaoJDBC implements ClassDao {
	
	private Connection conn;
	
	public ClassDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Class obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_classes " +
					"(Name) " +
					"VALUES " +
					"(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DatabaseException("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Class obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tb_classes " +
					"SET Name = ? " +
					"WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM tb_classes WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Class findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_classes WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Class obj = new Class();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Class> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_classes ORDER BY Id");
			
			rs = st.executeQuery();
			
			List<Class> list = new ArrayList<>();
			
			while (rs.next()) {
				Class obj = new Class();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}

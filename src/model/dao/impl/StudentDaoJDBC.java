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
import model.dao.StudentDao;
import model.entities.Student;

public class StudentDaoJDBC implements StudentDao {
	
	private Connection conn;
	
	public StudentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Student obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_students " +
					"(Name, Email) " +
					"VALUES " +
					"(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			
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
	public void update(Student obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tb_students " +
					"SET Name = ?, Email = ? " +
					"WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setInt(3, obj.getId());
			
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
					"DELETE FROM tb_students WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Student findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_students WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Student obj = new Student();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
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
	public List<Student> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_students ORDER BY Id");
			
			rs = st.executeQuery();
			
			List<Student> list = new ArrayList<>();
			
			while (rs.next()) {
				Student obj = new Student();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
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

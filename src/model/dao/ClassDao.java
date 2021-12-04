package model.dao;

import java.util.List;

import model.entities.Class;

public interface ClassDao {
	void insert (Class obj);
	void update (Class obj);
	void deleteById (Integer id);
	Class findById(Integer id);
	List<Class> findAll();
}

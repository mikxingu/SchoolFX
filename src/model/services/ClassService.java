package model.services;

import java.util.List;

import model.dao.ClassDao;
import model.dao.DaoFactory;
import model.entities.Class;

public class ClassService {
	
	private ClassDao dao = DaoFactory.createClassDao();
	
	public List<Class> findAll() {
		
		return dao.findAll();
		
		
	}
}

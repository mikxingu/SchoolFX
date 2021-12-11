package model.services;

import java.util.List;

import model.dao.LectureDao;
import model.dao.DaoFactory;
import model.entities.Lecture;

public class LectureService {
	
	private LectureDao dao = DaoFactory.createLectureDao();
	
	public List<Lecture> findAll() {
		
		return dao.findAll();
	}
	
	public void saveOrUpdate (Lecture obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Lecture obj) {
		dao.deleteById(obj.getId());
	}
}

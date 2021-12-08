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
}

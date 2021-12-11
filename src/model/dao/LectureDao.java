package model.dao;

import java.util.List;

import model.entities.Lecture;

public interface LectureDao {
	void insert (Lecture obj);
	void update (Lecture obj);
	void deleteById (Integer id);
	Lecture findById(Integer id);
	List<Lecture> findAll();
}

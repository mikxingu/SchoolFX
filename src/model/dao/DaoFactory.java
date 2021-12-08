package model.dao;

import db.DB;
import model.dao.impl.LectureDaoJDBC;

public class DaoFactory {
	
	public static LectureDao createLectureDao() {
		return new LectureDaoJDBC(DB.getConnection());
	}
	
//	public static StudentDao createStudentDao() {
//		return new StudentDaoJDBC(DB.getConnection());
//	}

}

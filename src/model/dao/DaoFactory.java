package model.dao;

import db.DB;
import model.dao.impl.ClassDaoJDBC;

public class DaoFactory {
	
	public static ClassDao createClassDao() {
		return new ClassDaoJDBC(DB.getConnection());
	}
	
//	public static StudentDao createStudentDao() {
//		return new StudentDaoJDBC(DB.getConnection());
//	}

}

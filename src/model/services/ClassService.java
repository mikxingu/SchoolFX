package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Class;

public class ClassService {
	
	public List<Class> findAll() {
		
		List<Class> list = new ArrayList<>();
		
		list.add(new Class(1, "Matematica"));
		list.add(new Class(2, "Portugues"));
		list.add(new Class(3, "Geografia"));
		list.add(new Class(4, "Historia"));
		list.add(new Class(5, "Ciencias"));
		
		return list;
		
		
	}
}

package com.maiora.assignment.StudentRec;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	StudentRepository studRepo;
	
	public List<Student> getAllStudent(){
		return studRepo.findAll();
	}
	
	public Optional<Student> findStudent(int id) {
		return studRepo.findById(id);
	}
	
	public void addOrUpdate(Student student) {
		studRepo.save(student);
	}
}

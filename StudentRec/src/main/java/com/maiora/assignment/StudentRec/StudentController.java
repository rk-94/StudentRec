package com.maiora.assignment.StudentRec;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	
		@Autowired
		StudentService studentService;
		
		/**
		 * gets the list of all the students
		 * @return
		 */
		@GetMapping("/all")
		public List<Student> getAllStudents() {
			return studentService.getAllStudent();
		}
		
		/**
		 * creates a new record of Student
		 * @param student
		 * @return
		 */
		@PostMapping("/add")
		public String addStudent(@RequestBody Student student) {
			LocalDate today = LocalDate.now();
			int year = today.getYear();
			if(student == null) {
				return "You need to enter Student data id, name, date, month, year and age";
			}
			if(student.getName() == null) {
				return "Name can not be null";
			}
			if(student.getDate() == 0 || student.getMonth() == 0 || student.getYear() == 0) {
				return "Enter Valid Date, Month and Year";
			}
			if(student.getMonth()<=0 || student.getMonth()>12) {
				return "Month should be in the range of 1-12";
			}
			if(student.getYear()>year) {
				return "Year should not be more than the current year";
			}
			studentService.addOrUpdate(student);
			return "Added successfully";
		}
		
		/**
		 * Get the Student record based on id passed in the url
		 * @param id
		 * @return
		 */
		@GetMapping("/addAge/{id}")
		public Map<String,List<Student>> findStudent(@PathVariable int id) {
			List<Student> stud = new ArrayList<>();
			Map<String,List<Student>> m = new HashMap<String,List<Student>>();
			Optional<Student> getStud = studentService.findStudent(id);
			if(getStud.isEmpty()) {
				m.put("Id not found, choose the following id",studentService.getAllStudent());
				return m;
			}
			else {
				Student s = getStud.get();
				LocalDate today = LocalDate.now();
				int month = today.getMonthValue(); 
				int year = today.getYear();
				if(s.getMonth()<=month) {
					s.setAge(year-s.getYear());
				}
				else {
					s.setAge((year-1) - s.getYear());
				}
				studentService.addOrUpdate(s);
				stud.add(s);
				m.put("Age updated for the Student id - "+id+"", stud);
			}
			return m;
		}
		
		/**
		 * get the record of all the students aged 18 to 25.
		 * @return
		 */
		@GetMapping("/student18to25")
		public List<Student> findStudent18to25(){
			List<Student> studList = studentService.getAllStudent();
			List<Student> get18to25Stud = new ArrayList<Student>();
			for(Student s : studList) {
				if(s.getAge()>=18 && s.getAge()<=25) {
					get18to25Stud.add(s);
				}
			}
			return get18to25Stud;
		}
}

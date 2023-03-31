package com.atri.UserBackend.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.atri.UserBackend.entity.User;
import com.atri.UserBackend.security.AuthenticationBean;
import com.atri.UserBackend.service.UserService;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserRestController {
	
	private UserService userService;
	private BCryptPasswordEncoder pwdEnc;
	
	@Autowired
	public UserRestController(UserService meetupService, BCryptPasswordEncoder pwdEnc) {
		this.userService = meetupService;
		this.pwdEnc = pwdEnc;
	}
	
	@GetMapping("/status/check")
	public String sayHello() {
		return "Hello World! Time on server is " + LocalDateTime.now();
	}

	// expose "/meetups" and return UserUsers
	@GetMapping("/users")
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable int userId) {
		User emp = userService.findById(userId);
		
		if(emp == null)
			throw new RuntimeException("User id not found - "+userId);
		
		return emp;
	}

	@PostMapping("/user")
	public User addUser(@RequestPart("cap") User emp) {
		
//		User  = new User();
		emp.setId(0);
		emp.setPassword(pwdEnc.encode(emp.getPassword()));
		userService.save(emp);
		return emp;
	}
	
	/*
	 * @PostMapping("/meetups") public User addUser(@RequestBody User emp) {
	 * 
	 * emp.setId(0); meetupService.save(emp); return emp; }
	 */
	
	
	@PutMapping("/user")
	public User updateUser(@RequestBody User emp) {
		userService.save(emp);
		return emp;
	}
	
	@DeleteMapping("/user/{empId}")
	public String deleteUser(@PathVariable int empId) {
		User validEmp = userService.findById(empId);
		if(validEmp == null)
			throw new RuntimeException("User doesnt exist");
		userService.deleteById(empId);
		
		return "User with ID:" + empId + " deleted";
		
	}
	/*
	 * @GetMapping(path = "/basicauth") public AuthenticationBean authenticate() {
	 * //throw new
	 * RuntimeException("Some Error has Happened! Contact Support at ***-***");
	 * return new AuthenticationBean("You are authenticated"); }
	 */
}

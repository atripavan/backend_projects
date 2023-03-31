package com.atri.MeetupBackend.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atri.MeetupBackend.entity.CreateMeetupRequestModel;
import com.atri.MeetupBackend.entity.Meetup;
import com.atri.MeetupBackend.entity.MeetupAttendee;
import com.atri.MeetupBackend.entity.enums.AccessLevel;
import com.atri.MeetupBackend.service.MeetupService;
import com.atri.UserBackend.entity.User;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MeetupRestController {
	
	private MeetupService meetupService;
	
	@Autowired
	public MeetupRestController(MeetupService meetupService) {
		this.meetupService = meetupService;
	}
	
	@GetMapping
	public String sayHello() {
		return "Hello World! Time on server is " + LocalDateTime.now();
	}

	// expose "/meetups" and return MeetupMeetups
	@GetMapping("/meetups")
	public List<Meetup> findAll(){
		return meetupService.findAll();
	}
	
	@GetMapping("/meetups/{meetupId}")
	public Meetup getMeetup(@PathVariable int meetupId) {
		Meetup emp = meetupService.findById(meetupId);
		
		if(emp == null)
			throw new RuntimeException("Meetup id not found - "+meetupId);
		
		return emp;
	}
	
	@GetMapping("/user/meetups/{userId}")
	public List<Meetup> getMeetupsForUserId(@PathVariable int userId) {
		List<Meetup> emp = meetupService.findByUserId(userId);
		
		if(emp == null)
			return Collections.emptyList();
		
		return emp;
	}

	@PostMapping("/meetups")
	public Meetup addMeetup(@RequestPart("cap") CreateMeetupRequestModel req) {

		//		Meetup  = new Meetup();

		Meetup mtup  = new Meetup(); 
		BeanUtils.copyProperties(req, mtup);
		mtup.setId(0);
		meetupService.save(mtup);
		if(!CollectionUtils.isEmpty(req.getMeetupUsers())) {
			List<MeetupAttendee> meetupAttnds = new ArrayList<MeetupAttendee>(req.getMeetupUsers().size());
			for(Integer i : req.getMeetupUsers()) {
				MeetupAttendee ma = new MeetupAttendee();
				ma.setId(0);
				
				RestTemplate restTemplate = new RestTemplate();
				User user = restTemplate.getForObject("http://localhost:8090/api/user/{id}", User.class, i);

				
				ma.setUser(user);
				ma.setMeetup(mtup);
				ma.setAcessLevel(AccessLevel.U);
			}
			meetupService.saveMeetupAttendees(meetupAttnds);
		}
		return mtup;
	}
	
	/*
	 * @PostMapping("/meetups") public Meetup addMeetup(@RequestBody Meetup emp) {
	 * 
	 * emp.setId(0); meetupService.save(emp); return emp; }
	 */
	
	
	@PutMapping("/meetups")
	public Meetup updateMeetup(@RequestBody Meetup emp) {
		meetupService.save(emp);
		return emp;
	}
	
	@DeleteMapping("/meetups/{empId}")
	public String deleteMeetup(@PathVariable int empId) {
		Meetup validEmp = meetupService.findById(empId);
		if(validEmp == null)
			throw new RuntimeException("Meetup doesnt exist");
		meetupService.deleteById(empId);
		
		return "Meetup with ID:" + empId + " deleted";
		
	}
	

	  // Convert a predefined exception to an HTTP Status code
	  @ResponseStatus(value=HttpStatus.NOT_FOUND)  // 417
	  @ExceptionHandler(RuntimeException.class)
	  public ResponseEntity<String> runtimeExc(RuntimeException ex) {
	    // Nothing to do
		  System.out.println(ex.getMessage());
		  return ResponseEntity
			        .status(HttpStatus.NOT_FOUND)
			        .header("status", HttpStatus.NOT_FOUND.toString()).body(ex.getMessage());
	  }
	  
		/*
		 * // Specify name of a specific view that will be used to display the error:
		 * 
		 * @ExceptionHandler({SQLException.class,DataAccessException.class}) public
		 * String databaseError() { // Nothing to do. Returns the logical view name of
		 * an error page, passed // to the view-resolver(s) in usual way. // Note that
		 * the exception is NOT available to this view (it is not added // to the model)
		 * but see "Extending ExceptionHandlerExceptionResolver" // below. return
		 * "databaseError"; }
		 */
		/*
		 * // Total control - setup a model and return the view name yourself. Or //
		 * consider subclassing ExceptionHandlerExceptionResolver (see below).
		 * 
		 * @ExceptionHandler(Exception.class) public ModelAndView
		 * handleError(HttpServletRequest req, Exception ex) { logger.error("Request: "
		 * + req.getRequestURL() + " raised " + ex);
		 * 
		 * ModelAndView mav = new ModelAndView(); mav.addObject("exception", ex);
		 * mav.addObject("url", req.getRequestURL()); mav.setViewName("error"); return
		 * mav; }
		 */
}

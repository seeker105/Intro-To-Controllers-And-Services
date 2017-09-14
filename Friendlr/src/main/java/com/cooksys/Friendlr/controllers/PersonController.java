package com.cooksys.Friendlr.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.Friendlr.dtos.PersonDto;
import com.cooksys.Friendlr.services.PersonService;

@RestController
@RequestMapping("person")
public class PersonController {
	private PersonService service;

	public PersonController(PersonService service){
		this.service = service;
	}
	
	@GetMapping
	public Set<PersonDto> getPersons(){
		System.out.println("getPersons called");
		System.out.println(service.toString());
		return service.getPersons();		
	}
	
	@GetMapping("{id}")
	public PersonDto getPerson(@PathVariable Long id, HttpServletResponse response){
		PersonDto found = service.getPerson(id);
		
		if (found != null) {
			response.setStatus(HttpServletResponse.SC_FOUND);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		System.out.println("getPerson called");
		System.out.println(service.toString());
		return found;
	}
	
	@PostMapping
	public PersonDto createPerson(@RequestBody PersonDto personDto, HttpServletResponse response){
		PersonDto newPerson = service.addPerson(personDto);
		
		if (service.containsId(newPerson.getId())) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		System.out.println("createPerson called");
		System.out.println(service.toString());
		return newPerson;
	}
	
	@PutMapping("{id}")
	public PersonDto replacePerson(@PathVariable Long id, @RequestBody PersonDto personDto, HttpServletResponse response){
		if (service.containsId(id)){
			service.replacePerson(id, personDto);
			return personDto;
		} 
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		System.out.println("replacePerson called");
		System.out.println(service.toString());
		return null;
	}
	
	@DeleteMapping("{id}")
	public PersonDto deletePerson(@PathVariable Long id, HttpServletResponse response){
		if (!service.containsId(id)){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		System.out.println("replacePerson called");
		System.out.println(service.toString());
		return service.deletePerson(id);
	}
	
	@PostMapping("{id}/friend/{friendId}")
	public PersonDto addFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response){
		if (!service.containsId(id) || !service.containsId(friendId)){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return service.addFriend(id, friendId);
	}
	
	@GetMapping("{id}/friends")
	public Set<PersonDto> getFriends(@PathVariable Long id, HttpServletResponse response){
		if (!service.containsId(id)){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return service.getFriends(id);
	}
	
	@DeleteMapping("{id}/friends/{friendId}")
	public PersonDto unFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response){
		if (!service.containsId(id) || !service.containsId(friendId)){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return service.unFriend(id, friendId);
	}
	
}

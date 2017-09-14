package com.cooksys.Friendlr.services;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.Friendlr.beans.Person;
import com.cooksys.Friendlr.dtos.PersonDto;
import com.cooksys.Friendlr.mappers.PersonMapper;

@Service
public class PersonService {
	
	private HashMap<Long, Person> persons;
	private Long index;
	private PersonMapper personMapper;
	
	public PersonService(PersonMapper personMapper){
		this.personMapper = personMapper;
		index = (long) 0;
		this.persons = new HashMap<Long, Person>();
		setupTestData();
	}
	
	private void setupTestData(){
		addPerson(new PersonDto("Ham", "Burg"));
		addPerson(new PersonDto("Hot", "Dog"));
		addPerson(new PersonDto("Quesa", "Dilla"));
		addPerson(new PersonDto("Jack", "Daniels"));
	}
	
	public Set<PersonDto> getPersons(){
		return persons.values().stream().map(personMapper::toPersonDto).collect(Collectors.toSet());
	}
	
	public PersonDto getPerson(Long id){
		return personMapper.toPersonDto(persons.get(id));
	}
	
	public PersonDto addPerson(PersonDto personDto){
		Person newPerson = personMapper.toPerson(personDto);
		index = index + 1;
		newPerson.setId(index);
		personDto.setId(index);
		persons.put(index, newPerson);
		return personDto;
	}
	
	public boolean containsId(Long id){
		return persons.containsKey(id);
	}
	
	public PersonDto replacePerson(Long id, PersonDto personDto){
		Person newPerson = personMapper.toPerson(personDto);
		personDto.setId(id);
		newPerson.setId(id);
		persons.put(id, newPerson);
		return personDto;
	}
	
	public PersonDto deletePerson(Long id){
		Person target = persons.get(id);
		for (Person p:persons.values()){
			p.removeFriend(target);
		}
		return personMapper.toPersonDto(persons.remove(id));
	}
	
	public PersonDto addFriend(Long id, Long friendId){
		Person target = persons.get(id);
		Person friend = persons.get(friendId);
		target.addFriend(friend);
		friend.addFriend(target);
		return personMapper.toPersonDto(friend);
	}
	
	public Set<PersonDto> getFriends(Long id){
		Person target = persons.get(id);
		return target.getFriends().stream().map(personMapper::toPersonDto).collect(Collectors.toSet());
	}
	
	public PersonDto unFriend(Long id, Long friendId){
		Person target = persons.get(id);
		Person friend = persons.get(friendId);
		if (target.removeFriend(friend) && friend.removeFriend(target)){
			return personMapper.toPersonDto(friend);
		}
		return null;
	}
	
	public String toString(){
		String result = "";
		for (Person person : persons.values()){
			result = result + person.toString() + "\n";
		}
		return result;
	}
}

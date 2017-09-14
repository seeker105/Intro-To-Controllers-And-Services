package com.cooksys.Friendlr.mappers;

import org.mapstruct.Mapper;

import com.cooksys.Friendlr.beans.Person;
import com.cooksys.Friendlr.dtos.PersonDto;

@Mapper(componentModel="spring")
public interface PersonMapper {
	
	PersonDto toPersonDto(Person person);
	
	Person toPerson(PersonDto personDto);
}

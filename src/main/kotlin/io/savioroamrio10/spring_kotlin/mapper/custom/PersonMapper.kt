package io.savioroamrio10.spring_kotlin.mapper.custom

import org.springframework.stereotype.Service
import java.util.Date
import io.savioroamrio10.spring_kotlin.model.Person
import io.savioroamrio10.spring_kotlin.data.vo.v2.PersonVO

@Service
class PersonMapper{

  fun mapEntityToVO(person: Person): PersonVO{
    val vo = PersonVO()
    vo.id = person.id
    vo.firstName = person.firstName
    vo.lastName = person.lastName
    vo.birthDay = Date()
    vo.address = person.address
    vo.gender = person.gender
    return vo
  }

  fun mapVOToEntity(person: PersonVO): Person{
    val entity = Person()
    entity.id = person.id
    entity.firstName = person.firstName
    entity.lastName = person.lastName
    entity.address = person.address
    entity.gender = person.gender
    return entity
  }
}
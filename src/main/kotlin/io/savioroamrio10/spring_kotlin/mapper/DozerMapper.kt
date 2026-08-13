package io.savioroamrio10.spring_kotlin.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper

import kotlin.collections.List
import java.util.ArrayList

object DozerMapper {

  private val mapper: Mapper = DozerBeanMapperBuilder.buildDefault()

  fun <O, D> parseObject(origin: O, destination: Class<D>):D{
    return mapper.map(origin, destination)
  }

  fun <O, D> parseListObjects(origin: List<O>, destination: Class<D>): List<D>{

    val destinationObject: ArrayList<D> = ArrayList()

    for(o in origin){
      destinationObject.add(mapper.map(o, destination))
    }

    return destinationObject
  }
  
}